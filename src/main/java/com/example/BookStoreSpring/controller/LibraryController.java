package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.entities.Library;
import com.example.BookStoreSpring.dto.LibraryDTO;
import com.example.BookStoreSpring.mapper.LibraryMapper;
import com.example.BookStoreSpring.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/libraries")
public class LibraryController {
    @Autowired()
    private LibraryService libraryService;

    @PostMapping(path = "/create-library")
    public ResponseEntity<?> create(@RequestBody() LibraryDTO libraryDTO) {
        Library library = LibraryMapper.libraryDTO2Library(libraryDTO);
        Library createdLibrary = libraryService.create(library);

        return ResponseEntity.ok(LibraryMapper.library2LibraryDTO(createdLibrary));
    }

    @GetMapping(path = "/find-library/{libraryID}")
    public ResponseEntity<?> findByID(@PathVariable(name = "libraryID") Long libraryID) {
        Library library = libraryService.findByID(libraryID);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDTO(library));
    }
}
