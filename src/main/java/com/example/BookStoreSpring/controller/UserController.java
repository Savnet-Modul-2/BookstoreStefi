package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.dto.UserDTO;
import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.mapper.UserMapper;
import com.example.BookStoreSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<?> findAll(){
        List<User> userList= userService.findAll();
        return ResponseEntity.ok(userList.stream().map(UserMapper::user2UserDTO).toList());
    }
    //Find by id
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){ //ia valoarea din link*
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.user2UserDTO(user));
    }
    //Delete by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.ok("User with id "+id+" deleted");
    }
    //Update by id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id,@RequestBody UserDTO userDTO){
        User user= userService.updateById(id,userDTO);
        return ResponseEntity.ok(UserMapper.user2UserDTO(user));
    }
}
