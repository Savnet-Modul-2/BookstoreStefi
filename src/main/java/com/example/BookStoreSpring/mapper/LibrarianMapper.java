package com.example.BookStoreSpring.mapper;

import com.example.BookStoreSpring.entities.Librarian;
import com.example.BookStoreSpring.dto.LibrarianDTO;

public class LibrarianMapper {
    public static Librarian librarianDTO2Librarian(LibrarianDTO librarianDTO) {
        Librarian librarian = new Librarian();

        librarian.setId(librarianDTO.getId());
        librarian.setName(librarianDTO.getName());
        librarian.setEmail(librarianDTO.getEmailAddress());
        librarian.setPassword(librarianDTO.getPassword());
        librarian.setVerifiedAccount(librarianDTO.getVerifiedAccount());
        librarian.setLoggedIn(librarianDTO.getLoggedIn());

        if (librarianDTO.getLibraryDTO() != null) {
            librarian.setLibrary(LibraryMapper.libraryDTO2Library(librarianDTO.getLibraryDTO()));
        }

        return librarian;
    }

    public static LibrarianDTO librarian2LibrarianDTO(Librarian librarian) {
        LibrarianDTO librarianDTO = new LibrarianDTO();

        librarianDTO.setId(librarian.getId());
        librarianDTO.setName(librarian.getName());
        librarianDTO.setEmailAddress(librarian.getEmail());
        librarianDTO.setPassword(librarian.getPassword());
        librarianDTO.setVerifiedAccount(librarian.getVerifiedAccount());
        librarian.setLoggedIn(librarianDTO.getLoggedIn());

        return librarianDTO;
    }
}
