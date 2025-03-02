package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.entities.Library;
import com.example.BookStoreSpring.entitiesDTO.LibraryDTO;
import com.example.BookStoreSpring.mapper.LibraryMapper;
import com.example.BookStoreSpring.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/libraries")
public class LibraryController {
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LibraryDTO libraryDTO) {
        Library libraryToCreate = LibraryMapper.libraryDTO2Library(libraryDTO);
        Library createdLibrary = libraryService.create(libraryToCreate);

        return ResponseEntity.ok(LibraryMapper.library2LibraryDTO(createdLibrary));
    }

    @GetMapping(path = "/{libraryID}")
    public ResponseEntity<?> findByID(@PathVariable(name = "libraryID") Long libraryID) {
        Library foundLibrary = libraryService.findByID(libraryID);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDTO(foundLibrary));
    }
}
