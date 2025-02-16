package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.mapper.UserMapper;
import com.example.BookStoreSpring.repositories.UserRepository;
import com.example.BookStoreSpring.service.EmailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/email")
public class EmailController {
    @Autowired()
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/sendEmail/{id_user}")
    public ResponseEntity<?> sendEmail(@PathVariable(name = "id_user") Long id_user, @RequestParam() String subject, @RequestParam() String text) {
        User user = userRepository.findById(id_user).orElseThrow(() -> new EntityNotFoundException("Not found"));

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Not found");
        }

        String message = "Email sent";

        emailService.send(user.getEmail(), subject, text);

        return ResponseEntity.ok(UserMapper.user2UserDTO(user) + message);
    }

    @PostMapping(path = "/send-verification/{id_user}")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable(name = "id_user") Long id_user) {
        User user = userRepository.findById(id_user).orElseThrow(() -> new EntityNotFoundException("Not found"));

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " does not have an email address.");
        }

        String message = "\nVerification email sent to user with ID " + id_user + ".";

        emailService.sendVerification(user);

        return ResponseEntity.ok(UserMapper.user2UserDTO(user) + message);
    }
}
