package com.example.BookStoreSpring.dto;

public class LibrarianDTO {
    private Long id;
    private String name;
    private String emailAddress;
    private String password;
    private Boolean verifiedAccount = false;
    private Boolean loggedIn = false;
    private LibraryDTO libraryDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getVerifiedAccount() {
        return verifiedAccount;
    }

    public void setVerifiedAccount(Boolean verifiedAccount) {
        this.verifiedAccount = verifiedAccount;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public LibraryDTO getLibraryDTO() {
        return libraryDTO;
    }

    public void setLibraryDTO(LibraryDTO libraryDTO) {
        this.libraryDTO = libraryDTO; //ii dau si un librarian
        libraryDTO.setLibrarianDTO(this);
    }
}
