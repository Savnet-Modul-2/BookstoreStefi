package com.example.SpringBookstore.controller;

import com.example.SpringBookstore.dto.UserDTO;
import com.example.SpringBookstore.entity.Library;
import com.example.SpringBookstore.entity.User;
import com.example.SpringBookstore.mapper.LibraryMapper;
import com.example.SpringBookstore.mapper.UserMapper;
import com.example.SpringBookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        User userToCreate = UserMapper.userDTO2User(userDTO);
        User createdUser = userService.create(userToCreate);

        userService.sendVerificationEmail(createdUser);

        return ResponseEntity.ok().body(UserMapper.user2UserDTO(createdUser));
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<?> findById(@PathVariable(name = "userId") Long userId) {
        User foundUser = userService.findById(userId);
        return ResponseEntity.ok().body(UserMapper.user2UserDTO(foundUser));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) Integer pageSize) {
        Page<User> foundUsers = userService.findAll(pageSize);
        return ResponseEntity.ok().body(foundUsers.stream()
                .map(UserMapper::user2UserDTO)
                .toList());
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<?> update(@PathVariable(name = "userId") Long userId,
                                    @RequestBody UserDTO userDTO) {
        User updatedUser = userService.update(userId, userDTO);
        return ResponseEntity.ok().body(UserMapper.user2UserDTO(updatedUser));
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<?> delete(@PathVariable(name = "userId") Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/send/{userId}")
    public ResponseEntity<?> sendEmail(@PathVariable(name = "userId") Long userId) {
        userService.sendEmail(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/resend/{userId}")
    public ResponseEntity<?> resendVerificationEmail(@PathVariable(name = "userId") Long userId) {
        userService.resendVerificationEmail(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/verify/{userId}")
    public ResponseEntity<?> verifyAccount(@PathVariable(name = "userId") Long userId,
                                           @RequestParam String verificationCode) {
        User verifiedUser = userService.verifyAccount(userId, verificationCode);
        return ResponseEntity.ok(UserMapper.user2UserDTO(verifiedUser));
    }

    @GetMapping(path = "/login")
    public ResponseEntity<?> login(@RequestParam String emailAddress,
                                   @RequestParam String password) {
        User loggedUser = userService.login(emailAddress, password);
        return ResponseEntity.ok(UserMapper.user2UserDTO(loggedUser));
    }

    @PutMapping(path = "/add/{userId}/{libraryId}")
    public ResponseEntity<?> addLibraryToFavourites(@PathVariable(name = "userId") Long userId, @PathVariable(name = "libraryId") Long libraryId) {
        Library library = userService.addLibraryToFavourites(userId, libraryId);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDTO(library));
    }

    @PutMapping(path = "/remove/{userId}/{libraryId}")
    public ResponseEntity<?> removeLibraryToFavourites(@PathVariable(name = "userId") Long userId, @PathVariable(name = "libraryId") Long libraryId) {
        Library library = userService.removeLibraryFromFavourites(userId, libraryId);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDTO(library));
    }

    @GetMapping(path = "/list/{userId}")
    public ResponseEntity<?> listFavouriteLibrariesPaginated(@PathVariable(name = "userId") Long userId,
                                                             @RequestParam(required = false) Integer pageNumber,
                                                             @RequestParam(required = false) Integer pageSize) {
        Page<Library> favouriteLibraries = userService.listFavouriteLibrariesPaginated(userId, pageNumber, pageSize);
        return ResponseEntity.ok(favouriteLibraries.stream()
                .map(LibraryMapper::library2LibraryDTO)
                .toList());
    }
}
