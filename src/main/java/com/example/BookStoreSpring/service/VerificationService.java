package com.example.BookStoreSpring.service;

import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service()
public class VerificationService {
    @Autowired()
    private UserRepository userRepository;

    public String verifyCode(User user, String code) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration elapsedTime = Duration.between(user.getVerificationCodeGenerationTime(), currentTime);

        if (elapsedTime.toMinutes() > 5) {
            user.setVerificationCode(null);

            userRepository.save(user);

            return "\nVerification code expired. Request a new verification code.";
        } else if (!user.getVerificationCode().equals(code)) {
            return "\nUser account verification unsuccessful. Invalid code provided.";
        } else {
            user.setVerifiedAccount(true);
            user.setVerificationCode(null);
            user.setVerificationCodeGenerationTime(null);

            userRepository.save(user);

            return "\nUser account verification successful.";
        }
    }
}
