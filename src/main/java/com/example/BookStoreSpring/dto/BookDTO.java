package com.example.BookStoreSpring.dto;

import com.example.BookStoreSpring.Category;

import java.time.LocalDate;

public class BookDTO {
    private Long id;
    private Long isbn;
    private String title;
    private String author;
    private Category category;
    private String language;
    private Integer numberOfPages;
    private LocalDate releaseDate;
    private LibraryDTO libraryDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
