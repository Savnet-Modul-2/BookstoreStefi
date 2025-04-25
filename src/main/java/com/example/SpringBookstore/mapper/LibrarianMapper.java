package com.example.SpringBookstore.mapper;

import com.example.SpringBookstore.dto.LibrarianDTO;
import com.example.SpringBookstore.entity.Librarian;

public class LibrarianMapper {
    public static Librarian librarianDTO2Librarian(LibrarianDTO librarianDTO) {
        Librarian librarian = new Librarian();

        librarian.setFirstName(librarianDTO.getFirstName());
        librarian.setLastName(librarianDTO.getLastName());
        librarian.setEmailAddress(librarianDTO.getEmailAddress());
        librarian.setPassword(librarian.getEmailAddress());
        librarian.setVerifiedAccount(librarianDTO.getVerifiedAccount());

        if (librarianDTO.getLibraryDTO() != null) {
            librarian.setLibrary(LibraryMapper.libraryDTO2Library(librarianDTO.getLibraryDTO()));
        }

        return librarian;
    }

    public static LibrarianDTO librarian2LibrarianDTO(Librarian librarian) {
        LibrarianDTO librarianDTO = new LibrarianDTO();

        librarianDTO.setId(librarian.getId());
        librarianDTO.setFirstName(librarian.getFirstName());
        librarianDTO.setLastName(librarian.getLastName());
        librarianDTO.setEmailAddress(librarian.getEmailAddress());
        librarianDTO.setPassword(librarian.getPassword());
        librarianDTO.setVerifiedAccount(librarian.getVerifiedAccount());

        if (librarian.getLibrary() != null) {
            librarianDTO.setLibraryDTO(LibraryMapper.library2LibraryDTO(librarian.getLibrary()));
        }

        return librarianDTO;
    }
}
