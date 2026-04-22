package com.example.demo.service;

import com.example.demo.entity.PasswordResetOtp;
import com.example.demo.repository.PasswordResetOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private PasswordResetOtpRepository otpRepository;

    private static final int OTP_EXPIRY_MINUTES = 10;

    @Transactional
    public String generateAndSaveOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpRepository.deleteByEmail(email);
        PasswordResetOtp entry = new PasswordResetOtp();
        entry.setEmail(email);
        entry.setOtp(otp);
        entry.setCreatedAt(LocalDateTime.now());
        otpRepository.save(entry);
        return otp;
    }

    // ✅ consume=false → just check (used in verify step)
    // ✅ consume=true  → check and delete (used in reset step)
    public boolean verifyOtp(String email, String otp, boolean consume) {
        Optional<PasswordResetOtp> entryOpt = otpRepository.findByEmail(email);
        if (entryOpt.isEmpty()) return false;

        PasswordResetOtp entry = entryOpt.get();

        if (entry.getCreatedAt().plusMinutes(OTP_EXPIRY_MINUTES).isBefore(LocalDateTime.now())) {
            otpRepository.deleteByEmail(email);
            return false;
        }

        boolean valid = entry.getOtp().equals(otp);
        if (valid && consume) {
            otpRepository.deleteByEmail(email);
        }
        return valid;
    }

    @Transactional
    public void deleteOtp(String email) {
        otpRepository.deleteByEmail(email);
    }
}