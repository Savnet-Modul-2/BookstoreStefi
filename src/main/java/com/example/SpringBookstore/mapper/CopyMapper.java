package com.example.SpringBookstore.mapper;

import com.example.SpringBookstore.dto.CopiesCreateDTO;
import com.example.SpringBookstore.dto.CopyDTO;
import com.example.SpringBookstore.entity.Copy;

import java.util.ArrayList;
import java.util.List;

public class CopyMapper {
    public static Copy copyDTO2Copy(CopyDTO copyDTO) {
        Copy copy = new Copy();

        copy.setPublisher(copyDTO.getPublisher());
        copy.setMaximumBookingTime(copyDTO.getMaximumBookingTime());

        return copy;
    }

    public static CopyDTO copy2CopyDTO(Copy copy) {
        CopyDTO copyDTO = new CopyDTO();

        copyDTO.setId(copy.getId());
        copyDTO.setPublisher(copy.getPublisher());
        copyDTO.setMaximumBookingTime(copy.getMaximumBookingTime());

        return copyDTO;
    }

    public static List<Copy> copiesCreateDTO2Copies(CopiesCreateDTO copiesCreateDTO) {
        List<Copy> copies = new ArrayList<>();

        for (int i = 0; i < copiesCreateDTO.getNumberOfCopies(); i++) {
            Copy copy = new Copy();

            copy.setPublisher(copiesCreateDTO.getPublisher());
            copy.setMaximumBookingTime(copiesCreateDTO.getMaximumBookingTime());

            copies.add(copy);
        }

        return copies;
    }
}
