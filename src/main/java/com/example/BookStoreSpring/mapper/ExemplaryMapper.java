package com.example.BookStoreSpring.mapper;

import com.example.BookStoreSpring.entities.Exemplary;
import com.example.BookStoreSpring.entitiesDTO.ExemplarsCreateDTO;
import com.example.BookStoreSpring.entitiesDTO.ExemplaryDTO;

import java.util.ArrayList;
import java.util.List;

public class ExemplaryMapper {
    public static ExemplaryDTO exemplary2ExemplaryDTO(Exemplary exemplary) {
        ExemplaryDTO exemplaryDTO = new ExemplaryDTO();

        exemplaryDTO.setID(exemplary.getID());
        exemplaryDTO.setPublisher(exemplary.getPublisher());
        exemplaryDTO.setMaximumBookingTime(exemplary.getMaximumBookingTime());
        exemplaryDTO.setBookDTO(BookMapper.book2BookDTO(exemplary.getBook()));

        return exemplaryDTO;
    }

    public static List<Exemplary> exemplarsCreateDTO2Exemplars(ExemplarsCreateDTO exemplarsCreateDTO) {
        List<Exemplary> exemplars = new ArrayList<>();

        for (int i = 0; i < exemplarsCreateDTO.getNumberOfExemplars(); i++) {
            Exemplary exemplary = new Exemplary();

            exemplary.setPublisher(exemplarsCreateDTO.getPublisher());
            exemplary.setMaximumBookingTime(exemplarsCreateDTO.getMaximumBookingTime());

            exemplars.add(exemplary);
        }

        return exemplars;
    }
}
