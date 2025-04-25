package com.example.SpringBookstore.controller;

import com.example.SpringBookstore.dto.CopiesCreateDTO;
import com.example.SpringBookstore.dto.CopyDTO;
import com.example.SpringBookstore.dto.validation.information.ValidationOrder;
import com.example.SpringBookstore.entity.Copy;
import com.example.SpringBookstore.mapper.CopyMapper;
import com.example.SpringBookstore.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/copies")
public class CopyController {
    private final CopyService copyService;

    @Autowired
    public CopyController(CopyService copyService) {
        this.copyService = copyService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated(value = ValidationOrder.class) @RequestBody CopyDTO copyDTO) {
        Copy copyToCreate = CopyMapper.copyDTO2Copy(copyDTO);
        Copy createdCopy = copyService.create(copyToCreate);

        return ResponseEntity.ok().body(CopyMapper.copy2CopyDTO(createdCopy));
    }

    @GetMapping(path = "/{copyId}")
    public ResponseEntity<?> findById(@PathVariable(name = "copyId") Long copyId) {
        Copy foundCopy = copyService.findById(copyId);
        return ResponseEntity.ok().body(CopyMapper.copy2CopyDTO(foundCopy));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) Integer pageSize) {
        Page<Copy> foundCopies = copyService.findAll(pageSize);
        return ResponseEntity.ok().body(foundCopies.stream()
                .map(CopyMapper::copy2CopyDTO)
                .toList());
    }

    @PutMapping(path = "/{copyId}")
    public ResponseEntity<?> update(@PathVariable(name = "copyId") Long copyId,
                                    @Validated(value = ValidationOrder.class) @RequestBody CopyDTO copyDTO) {
        Copy updatedCopy = copyService.update(copyId, copyDTO);
        return ResponseEntity.ok().body(CopyMapper.copy2CopyDTO(updatedCopy));
    }

    @DeleteMapping(path = "/{copyId}")
    public ResponseEntity<?> delete(@PathVariable(name = "copyId") Long copyId) {
        copyService.delete(copyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/paginated/{bookId}")
    public ResponseEntity<?> listPaginated(@PathVariable(name = "bookId") Long bookId,
                                           @RequestParam(required = false) Integer pageNumber,
                                           @RequestParam(required = false) Integer pageSize) {
        Page<Copy> copies = copyService.listPaginated(bookId, pageNumber, pageSize);
        return ResponseEntity.ok(copies.stream()
                .map(CopyMapper::copy2CopyDTO)
                .toList());
    }

    @PutMapping(path = "/{copyId}/{bookId}")
    public ResponseEntity<?> addToBook(@PathVariable(name = "copyId") Long copyId,
                                       @PathVariable(name = "bookId") Long bookId) {
        Copy addedCopy = copyService.addToBook(copyId, bookId);
        return ResponseEntity.ok(CopyMapper.copy2CopyDTO(addedCopy));
    }

    @DeleteMapping(path = "/{copyId}/{bookId}")
    public ResponseEntity<?> removeFromBook(@PathVariable(name = "copyId") Long copyId,
                                            @PathVariable(name = "bookId") Long bookId) {
        copyService.removeFromBook(copyId, bookId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "multiple")
    public ResponseEntity<?> createMultiple(@Validated(value = ValidationOrder.class) @RequestBody CopiesCreateDTO copiesCreateDTO) {
        List<Copy> copiesToCreate = CopyMapper.copiesCreateDTO2Copies(copiesCreateDTO);
        List<Copy> createdCopies = copyService.createMultiple(copiesToCreate, copiesCreateDTO.getBookId());

        return ResponseEntity.ok(createdCopies.stream()
                .map(CopyMapper::copy2CopyDTO)
                .toList());
    }
}
