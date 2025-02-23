package com.example.BookStoreSpring.service;

import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.dto.UserDTO;
import com.example.BookStoreSpring.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service()
public class UserService {
    @Autowired()
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public User create(User user) {
        if (user.getId() != null) throw new RuntimeException("Cannot provide an ID when creating a new user.");
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        return userRepository.save(user);
    }

    public User findByID(Long userID) {
        return userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("User with ID " + userID + "not found."));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(User userToUpdate, UserDTO userUpdate) {
        userToUpdate.setFirstName(userUpdate.getFirstName());
        userToUpdate.setLastName(userUpdate.getLastName());
        userToUpdate.setGender(userUpdate.getGender());
        userToUpdate.setCountry(userUpdate.getCountry());
        userToUpdate.setYearOfBirth(userUpdate.getYearOfBirth());
        userToUpdate.setEmail(userUpdate.getEmail());
        userToUpdate.setPhoneNumber(userUpdate.getPhoneNumber());

        return userRepository.save(userToUpdate);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User checkUser(Long userID) {
        User user = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("User with ID " + userID + "not found."));

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " does not have an email address.");
        }

        return user;
    }

    public void send(String recipient, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(sender);
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(text);

        javaMailSender.send(email);
    }

    public void sendVerification(User user) {
        if (!user.getVerifiedAccount()) {
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeGenerationTime(LocalDateTime.now());
            userRepository.save(user);

            send(user.getEmail(), "Bookstore Verification Email", "Your verification code is " + user.getVerificationCode() + ".\nThis verification code will expire in 5 minutes.");
        }
    }

    public String generateVerificationCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100000, 999999));
    }

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

    public Boolean login(User user, String emailAddress, String password) {
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        if (!user.getEmail().equals(emailAddress) || !user.getPassword().equals(encryptedPassword)) return false;

        user.setLoggedIn(true);
        userRepository.save(user);

        return true;
    }
}
