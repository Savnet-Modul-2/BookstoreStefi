package com.example.BookStoreSpring.mapper;

import com.example.BookStoreSpring.entities.Reservation;
import com.example.BookStoreSpring.entitiesDTO.ReservationDTO;

public class ReservationMapper {
    public static ReservationDTO reservation2ReservationDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setID(reservation.getID());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setExemplaryDTO(ExemplaryMapper.exemplary2ExemplaryDTO(reservation.getExemplary()));

        if (reservation.getUser() != null) {
            reservationDTO.setUserDTO(UserMapper.user2UserDTO(reservation.getUser()));
        }

        return reservationDTO;
    }
}
