package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.dto.UserDTO;
import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.exceptions.AccountNotVerifiedException;
import com.example.BookStoreSpring.mapper.UserMapper;
import com.example.BookStoreSpring.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//!!! sa nu mai pun cale( pt postman) in cazurile in care este evident
//!! sa se faca treaba cu mail ul din user - din service de la user sa se faca asta!
// din service cand creez un user se trimite si email ul
//cand inregistrez un user sa dau mail ul nu sa apelez un endpoint
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //Create user
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) { //val sa fie preluata din corp Json
        User user = UserMapper.userDTO2User(userDTO);
        User userCreated = userService.create(user);
        return ResponseEntity.ok(UserMapper.user2UserDTO(userCreated));
    }

    //Find all
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<User> userList = userService.findAll();
        return ResponseEntity.ok(userList.stream().map(UserMapper::user2UserDTO).toList());
    }

    //Find by id
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) { //ia valoarea din link*
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.user2UserDTO(user));
    }

    //Delete by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("User with id " + id + " deleted");
    }

    //Update by id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = userService.updateById(id, userDTO);
        return ResponseEntity.ok(UserMapper.user2UserDTO(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO.getEmail(), userDTO.getPassword());

        return ResponseEntity.ok(UserMapper.user2UserDTO(user));
    }
}
