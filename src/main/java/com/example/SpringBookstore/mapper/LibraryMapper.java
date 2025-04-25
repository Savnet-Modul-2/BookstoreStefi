package com.example.SpringBookstore.mapper;

import com.example.SpringBookstore.dto.LibraryDTO;
import com.example.SpringBookstore.entity.Library;

public class LibraryMapper {
    public static Library libraryDTO2Library(LibraryDTO libraryDTO) {
        Library library = new Library();

        library.setName(libraryDTO.getName());
        library.setAddress(libraryDTO.getAddress());
        library.setPhoneNumber(libraryDTO.getPhoneNumber());

        return library;
    }

    public static LibraryDTO library2LibraryDTO(Library library) {
        LibraryDTO libraryDTO = new LibraryDTO();

        libraryDTO.setId(library.getId());
        libraryDTO.setName(library.getName());
        libraryDTO.setAddress(library.getAddress());
        libraryDTO.setPhoneNumber(library.getPhoneNumber());

        return libraryDTO;
    }
}
