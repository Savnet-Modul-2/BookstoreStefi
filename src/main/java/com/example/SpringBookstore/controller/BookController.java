package com.example.SpringBookstore.controller;

import com.example.SpringBookstore.dto.BookDTO;
import com.example.SpringBookstore.entity.Book;
import com.example.SpringBookstore.mapper.BookMapper;
import com.example.SpringBookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookDTO bookDTO) {
        Book bookToCreate = BookMapper.bookDTO2Book(bookDTO);
        Book createdBook = bookService.create(bookToCreate);

        return ResponseEntity.ok().body(BookMapper.book2BookDTO(createdBook));
    }

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<?> findById(@PathVariable(name = "bookId") Long bookId) {
        Book foundBook = bookService.findById(bookId);
        return ResponseEntity.ok().body(BookMapper.book2BookDTO(foundBook));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(required = false) Integer pageSize) {
        Page<Book> foundBooks = bookService.findAll(pageSize);
        return ResponseEntity.ok().body(foundBooks.stream()
                .map(BookMapper::book2BookDTO)
                .toList());
    }

    @PutMapping(path = "/{bookId}")
    public ResponseEntity<?> update(@PathVariable(name = "bookId") Long bookId,
                                    @RequestBody BookDTO bookDTO) {
        Book updatedBook = bookService.update(bookId, bookDTO);
        return ResponseEntity.ok().body(BookMapper.book2BookDTO(updatedBook));
    }

    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<?> delete(@PathVariable(name = "bookId") Long bookId) {
        bookService.delete(bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/paginated")
    public ResponseEntity<?> listPaginated(@RequestParam(required = false) Integer pageNumber,
                                           @RequestParam(required = false) Integer pageSize) {
        Page<Book> books = bookService.listPaginated(pageNumber, pageSize);
        return ResponseEntity.ok(books.stream()
                .map(BookMapper::book2BookDTO)
                .toList());
    }

    @PutMapping(path = "/{bookId}/{libraryId}")
    public ResponseEntity<?> addToLibrary(@PathVariable(name = "bookId") Long bookId,
                                          @PathVariable(name = "libraryId") Long libraryId) {
        Book addedBook = bookService.addToLibrary(bookId, libraryId);
        return ResponseEntity.ok(BookMapper.book2BookDTO(addedBook));
    }

    @DeleteMapping(path = "/{bookId}/{libraryId}")
    public ResponseEntity<?> removeFromLibrary(@PathVariable(name = "bookId") Long bookId,
                                               @PathVariable(name = "libraryId") Long libraryId) {
        bookService.removeFromLibrary(bookId, libraryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{libraryId}")
    public ResponseEntity<?> createForLibrary(@RequestBody BookDTO bookDTO,
                                              @PathVariable(name = "libraryId") Long libraryId) {
        Book bookToCreate = BookMapper.bookDTO2Book(bookDTO);
        Book createdBook = bookService.createForLibrary(bookToCreate, libraryId);

        return ResponseEntity.ok(BookMapper.book2BookDTO(createdBook));
    }
}
