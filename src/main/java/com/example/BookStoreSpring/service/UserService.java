package com.example.BookStoreSpring.service;

import com.example.BookStoreSpring.dto.UserDTO;
import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.exceptions.AccountNotVerifiedException;
import com.example.BookStoreSpring.exceptions.InvalidPasswordException;
import com.example.BookStoreSpring.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public User create(User user) {
        if (user.getId() != null) {
            throw new RuntimeException("Cannot provide an id when creating a new user.");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        emailService.send(user.getEmail(), "Aaa", "bbb");
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No user found"));
    }

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Not found");
        }
    }

    public User updateById(Long id, UserDTO userDTO) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found"));
            user.setCountry(userDTO.getCountry());
            user.setVerifiedAccount(userDTO.getVerifiedAccount());
            user.setEmail(userDTO.getEmail());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setPassword(userDTO.getPassword());
            user.setGender(userDTO.getGender());
            user.setYearOfBirth(userDTO.getYearOfBirth());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());

            return userRepository.save(user);

        } else {
            throw new EntityNotFoundException("Not found");
        }
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Nu s-a gasit user-ul"));
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Invalid user password");
        }
        if (!user.getVerifiedAccount()) {
            throw new AccountNotVerifiedException("Account is not verified");
        }

        return user;
    }
}
