package com.example.BookStoreSpring.service;

import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.entitiesDTO.UserDTO;
import com.example.BookStoreSpring.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class UserService extends EmailService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(JavaMailSender javaMailSender, UserRepository userRepository) {
        super(javaMailSender);
        this.userRepository = userRepository;
    }

    public User create(User userToCreate) {
        if (userToCreate.getID() != null) {
            throw new RuntimeException("Cannot provide an ID when creating a new user.");
        }

        userToCreate.setPassword(DigestUtils.md5DigestAsHex(userToCreate.getPassword().getBytes(StandardCharsets.UTF_8)));

        return userRepository.save(userToCreate);
    }

    public User findByID(Long userID) {
        return userRepository.findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userID + "not found."));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(Long userID, UserDTO userUpdate) {
        User userToUpdate = findByID(userID);

        userToUpdate.setFirstName(userUpdate.getFirstName());
        userToUpdate.setLastName(userUpdate.getLastName());
        userToUpdate.setGender(userUpdate.getGender());
        userToUpdate.setCountry(userUpdate.getCountry());
        userToUpdate.setBirthDate(userUpdate.getBirthDate());
        userToUpdate.setEmail(userUpdate.getEmail());
        userToUpdate.setPhoneNumber(userUpdate.getPhoneNumber());

        return userRepository.save(userToUpdate);
    }

    public void delete(Long userID) {
        if (userRepository.existsById(userID)) {
            userRepository.deleteById(userID);
        }
    }

    public User checkEmail(Long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userID + "not found."));

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User with ID " + user.getID() + " does not have an email address.");
        }

        return user;
    }

    public void sendVerificationCode(User user) {
        if (!user.getVerifiedAccount()) {
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeGenerationTime(LocalDateTime.now());

            userRepository.save(user);

            sendEmail(user.getEmail(), "User Verification Email", "Your verification code is " + user.getVerificationCode() + ".\nThis verification code will expire in 5 minutes.");
        }
    }

    public User checkVerificationCode(Long userID, String code) {
        User user = findByID(userID);

        LocalDateTime currentTime = LocalDateTime.now();
        Duration elapsedTime = Duration.between(user.getVerificationCodeGenerationTime(), currentTime);

        if (elapsedTime.toMinutes() > 5) {
            user.setVerificationCode(null);

            userRepository.save(user);

            throw new RuntimeException("Verification code expired. Request a new verification code.");
        } else if (!user.getVerificationCode().equals(code)) {
            throw new RuntimeException("User account verification unsuccessful. Invalid code provided.");
        }

        user.setVerifiedAccount(true);
        user.setVerificationCode(null);
        user.setVerificationCodeGenerationTime(null);

        userRepository.save(user);

        return user;
    }

    public User login(String emailAddress, String password) {
        User user = userRepository.findByEmail(emailAddress)
                .orElseThrow(() -> new EntityNotFoundException("User with email address " + emailAddress + " not found."));

        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        if (!user.getEmail().equals(emailAddress) || !user.getPassword().equals(encryptedPassword)) {
            throw new InputMismatchException("Login unsuccessful. Invalid email address or password.");
        }

        userRepository.save(user);

        return user;
    }
}
