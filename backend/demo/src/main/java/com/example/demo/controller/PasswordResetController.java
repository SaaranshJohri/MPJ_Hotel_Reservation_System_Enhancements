package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.OtpService;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    @Autowired private OtpService otpService;
    @Autowired private EmailService emailService;
    @Autowired private CustomerRepository customerRepository;

    // Step 1: Send OTP
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Email not registered"));
        }
        String otp = otpService.generateAndSaveOtp(email);
        emailService.sendOtpEmail(email, otp);
        return ResponseEntity.ok(Map.of("message", "OTP sent successfully"));
    }

    // Step 2: Verify OTP — consume=false, keeps OTP alive for reset step
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String otp = body.get("otp");
        boolean valid = otpService.verifyOtp(email, otp, false);  // ← changed
        if (!valid) {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid or expired OTP"));
        }
        return ResponseEntity.ok(Map.of("message", "OTP verified"));
    }

    // Step 3: Reset Password — consume=true, deletes OTP after use
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String otp = body.get("otp");
        String newPassword = body.get("newPassword");

        boolean valid = otpService.verifyOtp(email, otp, true);  // ← changed
        if (!valid) {
            return ResponseEntity.status(400).body(Map.of("message", "OTP expired or invalid"));
        }

        Customer customer = customerRepository.findByEmail(email).orElseThrow();
        customer.setPassword(newPassword);
        customerRepository.save(customer);

        // No need to call deleteOtp() — consume=true already deleted it above
        return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
    }
}