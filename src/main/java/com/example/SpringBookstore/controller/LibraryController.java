package com.example.SpringBookstore.controller;

import com.example.SpringBookstore.dto.LibraryDTO;
import com.example.SpringBookstore.entity.Library;
import com.example.SpringBookstore.mapper.LibraryMapper;
import com.example.SpringBookstore.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

        return ResponseEntity.ok().body(LibraryMapper.library2LibraryDTO(createdLibrary));
    }

    @GetMapping(path = "/{libraryId}")
    public ResponseEntity<?> findById(@PathVariable(name = "libraryId") Long libraryId) {
        Library foundLibrary = libraryService.findById(libraryId);
        return ResponseEntity.ok().body(LibraryMapper.library2LibraryDTO(foundLibrary));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) Integer pageSize) {
        Page<Library> foundLibraries = libraryService.findAll(pageSize);
        return ResponseEntity.ok().body(foundLibraries.stream()
                .map(LibraryMapper::library2LibraryDTO)
                .toList());
    }

    @PutMapping(path = "/{libraryId}")
    public ResponseEntity<?> update(@PathVariable(name = "libraryId") Long libraryId,
                                    @RequestBody LibraryDTO libraryDTO) {
        Library updatedLibrary = libraryService.update(libraryId, libraryDTO);
        return ResponseEntity.ok().body(LibraryMapper.library2LibraryDTO(updatedLibrary));
    }

    @DeleteMapping(path = "/{libraryId}")
    public ResponseEntity<?> delete(@PathVariable(name = "libraryId") Long libraryId) {
        libraryService.delete(libraryId);
        return ResponseEntity.noContent().build();
    }
}
