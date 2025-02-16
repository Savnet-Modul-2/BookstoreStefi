package com.example.BookStoreSpring.service;

import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;

    @Value("${spring.mail.username}") //se ia din applicationproperty
    private String sender;

    public void send(String receiver, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(sender);
        email.setTo(receiver);
        email.setSubject(subject);
        email.setText(text);

        javaMailSender.send(email);
    }

    public void sendVerification(User user) {
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeGenerationTime(LocalDateTime.now());
        userRepository.save(user);

        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(sender);
        email.setTo(user.getEmail());
        email.setSubject("Verification code");
        email.setText("Your verification code is " + user.getVerificationCode() + ".\nThis verification code will expire in 5 minutes.");

        javaMailSender.send(email);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100000, 999999));
    }
}
