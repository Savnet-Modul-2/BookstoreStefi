package com.example.BookStoreSpring.mapper;

import com.example.BookStoreSpring.entities.Book;
import com.example.BookStoreSpring.entitiesDTO.BookDTO;

public class BookMapper {
    public static Book bookDTO2Book(BookDTO bookDTO) {
        Book book = new Book();

        book.setID(bookDTO.getID());
        book.setISBN(bookDTO.getISBN());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setCategory(bookDTO.getCategory());
        book.setLanguage(bookDTO.getLanguage());
        book.setNumberOfPages(bookDTO.getNumberOfPages());
        book.setReleaseDate(bookDTO.getReleaseDate());

        if (bookDTO.getLibraryDTO() != null) {
            book.setLibrary(LibraryMapper.libraryDTO2Library(bookDTO.getLibraryDTO()));
        }

        return book;
    }

    public static BookDTO book2BookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();

        bookDTO.setID(book.getID());
        bookDTO.setISBN(book.getISBN());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setCategory(book.getCategory());
        bookDTO.setLanguage(book.getLanguage());
        bookDTO.setNumberOfPages(book.getNumberOfPages());
        bookDTO.setReleaseDate(book.getReleaseDate());

        if (book.getLibrary() != null) {
            bookDTO.setLibraryDTO(LibraryMapper.library2LibraryDTO(book.getLibrary()));
        }

        return bookDTO;
    }
}
