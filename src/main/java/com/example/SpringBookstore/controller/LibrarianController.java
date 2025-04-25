package com.example.SpringBookstore.controller;

import com.example.SpringBookstore.dto.LibrarianDTO;
import com.example.SpringBookstore.entity.Librarian;
import com.example.SpringBookstore.mapper.LibrarianMapper;
import com.example.SpringBookstore.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        librarianService.sendVerificationEmail(createdLibrarian);

        return ResponseEntity.ok().body(LibrarianMapper.librarian2LibrarianDTO(createdLibrarian));
    }

    @GetMapping(path = "/{librarianId}")
    public ResponseEntity<?> findById(@PathVariable(name = "librarianId") Long librarianId) {
        Librarian foundLibrarian = librarianService.findById(librarianId);
        return ResponseEntity.ok().body(LibrarianMapper.librarian2LibrarianDTO(foundLibrarian));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) Integer pageSize) {
        Page<Librarian> foundLibrarians = librarianService.findAll(pageSize);
        return ResponseEntity.ok().body(foundLibrarians.stream()
                .map(LibrarianMapper::librarian2LibrarianDTO)
                .toList());
    }

    @PutMapping(path = "/{librarianId}")
    public ResponseEntity<?> update(@PathVariable(name = "librarianId") Long librarianId,
                                    @RequestBody LibrarianDTO librarianDTO) {
        Librarian updatedLibrarian = librarianService.update(librarianId, librarianDTO);
        return ResponseEntity.ok().body(LibrarianMapper.librarian2LibrarianDTO(updatedLibrarian));
    }

    @DeleteMapping(path = "/{librarianId}")
    public ResponseEntity<?> delete(@PathVariable(name = "librarianId") Long librarianId) {
        librarianService.delete(librarianId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/send/{librarianId}")
    public ResponseEntity<?> sendEmail(@PathVariable(name = "librarianId") Long librarianId) {
        librarianService.sendEmail(librarianId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/resend/{librarianId}")
    public ResponseEntity<?> resendVerificationEmail(@PathVariable(name = "librarianId") Long librarianId) {
        librarianService.resendVerificationEmail(librarianId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/verify/{librarianId}")
    public ResponseEntity<?> verifyAccount(@PathVariable(name = "librarianId") Long userId,
                                           @RequestParam String verificationCode) {
        Librarian verifiedLibrarian = librarianService.verifyAccount(userId, verificationCode);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDTO(verifiedLibrarian));
    }

    @GetMapping(path = "/login/{librarianId}")
    public ResponseEntity<?> login(@RequestParam String emailAddress,
                                   @RequestParam String password) {
        Librarian loggedLibrarian = librarianService.login(emailAddress, password);
        return ResponseEntity.ok(LibrarianMapper.librarian2LibrarianDTO(loggedLibrarian));
    }
}
