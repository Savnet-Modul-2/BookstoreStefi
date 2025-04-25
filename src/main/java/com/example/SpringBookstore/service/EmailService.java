package com.example.SpringBookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    @Value(value = "spring.mail.username")
    private String emailAddress;

    private final JavaMailSender javaMailSender;

    private final int maximumVerificationTime = 5;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public int getMaximumVerificationTime() {
        return maximumVerificationTime;
    }

    public SimpleMailMessage createEmail(String recipient, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(emailAddress);
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(text);

        return email;
    }

    public void sendEmail(String recipient, String subject, String text) {
        SimpleMailMessage email = createEmail(recipient, subject, text);
        javaMailSender.send(email);
    }

    @Async
    public void sendVerificationEmail(String recipient, String verificationCode, long verificationTime) {
        String subject = "Account Verification";
        String text = "Your verification code is " + verificationCode + ".\nThis verification code will expire in " + verificationTime + " minute(s)";

        SimpleMailMessage verificationEmail = createEmail(recipient, subject, text);

        javaMailSender.send(verificationEmail);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();

        while (verificationCode.length() < 6) {
            verificationCode.append(random.nextInt(0, 9));
        }

        return verificationCode.toString();
    }
}
