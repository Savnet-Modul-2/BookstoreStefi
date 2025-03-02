package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.entities.Librarian;
import com.example.BookStoreSpring.entitiesDTO.LibrarianDTO;
import com.example.BookStoreSpring.mapper.LibrarianMapper;
import com.example.BookStoreSpring.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/librarians")
public class LibrarianController {
    private final LibrarianService librarianService;

    @Autowired
    public LibrarianController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LibrarianDTO librarianDTO) {
        Librarian librarianToCreate = LibrarianMapper.librarianDTO2Librarian(librarianDTO);
        Librarian createdLibrarian = librarianService.create(librarianToCreate);

        librarianService.sendVerificationCode(createdLibrarian);

        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDTO(createdLibrarian));
    }

    @GetMapping(path = "/{librarianID}")
    public ResponseEntity<?> findByID(@PathVariable(name = "librarianID") Long librarianID) {
        Librarian foundLibrarian = librarianService.findByID(librarianID);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDTO(foundLibrarian));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Librarian> librarians = librarianService.findAll();

        return ResponseEntity.ok(librarians.stream()
                .map(LibrarianMapper::librarian2LibrarianDTO)
                .toList());
    }

    @DeleteMapping(path = "/{librarianID}")
    public ResponseEntity<?> delete(@PathVariable(name = "librarianID") Long librarianID) {
        librarianService.delete(librarianID);
        return ResponseEntity.ok().body("Deleted librarian with ID " + librarianID + ".");
    }

    @PostMapping(path = "/{librarianID}")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable(name = "librarianID") Long librarianID) {
        Librarian librarian = librarianService.checkEmail(librarianID);

        librarianService.sendVerificationCode(librarian);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/verification")
    public ResponseEntity<?> verifyAccount(@RequestParam Long librarianID, @RequestParam String code) {
        Librarian librarian = librarianService.checkVerificationCode(librarianID, code);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDTO(librarian));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestParam String emailAddress, @RequestParam String password) {
        Librarian librarian = librarianService.login(emailAddress, password);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDTO(librarian));
    }
}
