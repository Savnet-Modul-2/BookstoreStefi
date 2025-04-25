package com.example.SpringBookstore.dto;

public class LibraryDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private LibrarianDTO librarianDTO;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LibrarianDTO getLibrarianDTO() {
        return librarianDTO;
    }

    public void setLibrarianDTO(LibrarianDTO librarianDTO) {
        this.librarianDTO = librarianDTO;
    }
}
