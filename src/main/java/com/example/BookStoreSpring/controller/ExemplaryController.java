package com.example.BookStoreSpring.controller;

import com.example.BookStoreSpring.entities.Exemplary;
import com.example.BookStoreSpring.entitiesDTO.ExemplarsCreateDTO;
import com.example.BookStoreSpring.mapper.ExemplaryMapper;
import com.example.BookStoreSpring.service.ExemplaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/exemplars")
public class ExemplaryController {
    private final ExemplaryService exemplaryService;

    @Autowired
    public ExemplaryController(ExemplaryService exemplaryService) {
        this.exemplaryService = exemplaryService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ExemplarsCreateDTO exemplarsCreateDTO) {
        List<Exemplary> exemplarsToCreate = ExemplaryMapper.exemplarsCreateDTO2Exemplars(exemplarsCreateDTO);
        List<Exemplary> createdExemplars = exemplaryService.create(exemplarsToCreate, exemplarsCreateDTO.getBookID());

        return ResponseEntity.ok(createdExemplars.stream()
                .map(ExemplaryMapper::exemplary2ExemplaryDTO)
                .toList());
    }

    @GetMapping
    public ResponseEntity<?> listPaginated(@RequestParam(required = false) Long bookID, @RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer numberOfElements) {
        Page<Exemplary> exemplars = exemplaryService.listPaginated(bookID, pageNumber, numberOfElements);

        return ResponseEntity.ok(exemplars.stream()
                .map(ExemplaryMapper::exemplary2ExemplaryDTO)
                .toList());
    }

    @DeleteMapping(path = "/{exemplaryID}")
    public ResponseEntity<?> delete(@PathVariable(name = "exemplaryID") Long exemplaryID) {
        exemplaryService.delete(exemplaryID);
        return ResponseEntity.noContent().build();
    }
}
