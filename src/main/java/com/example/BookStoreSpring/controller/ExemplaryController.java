package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.dto.ExemplarsCreateDTO;
import com.example.BookStoreSpring.dto.ExemplaryDTO;
import com.example.BookStoreSpring.dto.LibrarianDTO;
import com.example.BookStoreSpring.entities.Exemplary;
import com.example.BookStoreSpring.entities.Librarian;
import com.example.BookStoreSpring.mapper.ExemplaryMapper;
import com.example.BookStoreSpring.mapper.LibrarianMapper;
import com.example.BookStoreSpring.repositories.ExemplaryRepository;
import com.example.BookStoreSpring.repositories.LibrarianRepository;
import com.example.BookStoreSpring.service.ExemplaryService;
import com.example.BookStoreSpring.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/exemplars")
public class ExemplaryController {
    @Autowired()
    private ExemplaryRepository exemplaryRepository;

    private final ExemplaryService exemplaryService;

    public ExemplaryController(ExemplaryService exemplaryService) {
        this.exemplaryService = exemplaryService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ExemplarsCreateDTO exemplarsCreateDTO) {
        List<Exemplary> exemplarsToCreate = ExemplaryMapper.exemplarsCreateDTO2Exemplars(exemplarsCreateDTO);
        List<Exemplary> createdExemplars = exemplaryService.create(exemplarsToCreate, exemplarsCreateDTO.getBookId());

        return ResponseEntity.ok(createdExemplars.stream()
                .map(ExemplaryMapper::exemplary2ExemplaryDTO)
                .toList());
    }

    @GetMapping
    public ResponseEntity<?> listPaginated(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer numberOfElements) {
        Page<Exemplary> exemplars = exemplaryService.listPaginated(pageNumber, numberOfElements);

        List<ExemplaryDTO> exemplaryDTOS = exemplars.stream()
                .map(ExemplaryMapper::exemplary2ExemplaryDTO)
                .toList();

        return ResponseEntity.ok(exemplaryDTOS);
    }

    @DeleteMapping(path = "/{exemplaryID}")
    public ResponseEntity<?> delete(@PathVariable(name = "exemplaryID") Long exemplaryID) {
        exemplaryService.delete(exemplaryID);
        return ResponseEntity.noContent().build();
    }

}
