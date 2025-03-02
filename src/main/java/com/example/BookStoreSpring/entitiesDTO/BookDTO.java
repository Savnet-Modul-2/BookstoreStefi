package com.example.BookStoreSpring.entitiesDTO;

import com.example.BookStoreSpring.BookCategory;

import java.time.LocalDate;

public class BookDTO {
    private Long id;
    private Long isbn;
    private String title;
    private String author;
    private BookCategory bookCategory;
    private String language;
    private Integer numberOfPages;
    private LocalDate releaseDate;
    private LibraryDTO libraryDTO;

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public Long getISBN() {
        return isbn;
    }

    public void setISBN(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookCategory getCategory() {
        return bookCategory;
    }

    public void setCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Integer getReleaseDate() {
        return releaseDate.getYear();
    }

    public void setReleaseDate(Integer releaseDate) {
        this.releaseDate = LocalDate.ofYearDay(releaseDate, 1);
    }

    public LibraryDTO getLibraryDTO() {
        return libraryDTO;
    }

    public void setLibraryDTO(LibraryDTO libraryDTO) {
        this.libraryDTO = libraryDTO;
    }
}
