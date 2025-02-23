package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.dto.UserDTO;
import com.example.BookStoreSpring.mapper.UserMapper;
import com.example.BookStoreSpring.repositories.UserRepository;
import com.example.BookStoreSpring.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/users")
public class UserController {
    @Autowired()
    private UserRepository userRepository;
    @Autowired()
    private UserService userService;

    @PostMapping(path = "/create-user")
    public ResponseEntity<?> create(@RequestBody() UserDTO userDTO) {
        User user = UserMapper.userDTO2User(userDTO);
        User createdUser = userService.create(user);

        userService.sendVerification(createdUser);

        return ResponseEntity.ok(UserMapper.user2UserDTO(createdUser));
    }

    @GetMapping(path = "/find-user-by-id/{userID}")
    public ResponseEntity<?> findByID(@PathVariable(name = "userID") Long userID) {
        User user = userService.findByID(userID);

        return ResponseEntity.ok(UserMapper.user2UserDTO(user));
    }

    @GetMapping(path = "/find-all-users")
    public ResponseEntity<?> findAll() {
        List<User> foundUsers = userService.findAll();
        List<UserDTO> userDTOS = foundUsers.stream().map(UserMapper::user2UserDTO).toList();

        return ResponseEntity.ok(userDTOS);
    }

    @PutMapping(path = "/update-user/{userID}")
    public ResponseEntity<?> update(@PathVariable(name = "userID") Long userID, @RequestBody UserDTO userDTO) {
        User user = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Not found"));
        User updatedUser = userService.update(user, userDTO);

        return ResponseEntity.ok(UserMapper.user2UserDTO(updatedUser));
    }

    @DeleteMapping(path = "/delete-user/{userID}")
    public ResponseEntity<?> delete(@PathVariable(name = "userID") Long userID) {
        User user = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Not found"));
        userService.delete(user);

        return ResponseEntity.ok().body("Deleted user with ID " + userID + ".");
    }

    @PostMapping(path = "/send-email/{userID}")
    public ResponseEntity<?> sendEmail(@PathVariable(name = "userID") Long userID, @RequestParam() String subject, @RequestParam() String text) {
        User user = userService.checkUser(userID);

        userService.send(user.getEmail(), subject, text);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/send-verification-email/{userID}")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable(name = "userID") Long userID) {
        User user = userService.checkUser(userID);

        userService.sendVerification(user);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/verify-code")
    public ResponseEntity<?> verifyCode(@RequestParam() Long userID, @RequestParam() String code) {
        User user = userRepository.findById(userID).orElseThrow(() -> new EntityNotFoundException("Not found"));
        String message = userService.verifyCode(user, code);

        return ResponseEntity.ok(UserMapper.user2UserDTO(user) + message);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestParam() String emailAddress, @RequestParam() String password) {
        User user = userRepository.findByEmail(emailAddress).orElseThrow(() -> new EntityNotFoundException("Not found"));
        Boolean loginValidation = userService.login(user, emailAddress, password);

        if (loginValidation) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body("Email sau parola gresita");
        }
    }
}
