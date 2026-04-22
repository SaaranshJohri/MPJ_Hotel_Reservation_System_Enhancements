package com.example.demo.repository;

import com.example.demo.entity.PasswordResetOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetOtp, Long> {
    Optional<PasswordResetOtp> findByEmail(String email);

    @Transactional          // ← was missing, causes runtime error without it
    void deleteByEmail(String email);
}