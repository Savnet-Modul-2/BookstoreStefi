package com.example.SpringBookstore.mapper;

import com.example.SpringBookstore.dto.ReservationDTO;
import com.example.SpringBookstore.entity.Reservation;

public class ReservationMapper {
    public static Reservation reservationDTO2Reservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();

        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus(reservationDTO.getStatus());

        if (reservationDTO.getCopyDTO() != null) {
            reservation.setCopy(CopyMapper.copyDTO2Copy(reservationDTO.getCopyDTO()));
        }

        if (reservationDTO.getUserDTO() != null) {
            reservation.setUser(UserMapper.userDTO2User(reservationDTO.getUserDTO()));
        }

        return reservation;
    }

    public static ReservationDTO reservation2ReservationDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setId(reservation.getId());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStatus(reservation.getStatus());

        if (reservation.getCopy() != null) {
            reservationDTO.setCopyDTO(CopyMapper.copy2CopyDTO(reservation.getCopy()));
        }

        if (reservation.getUser() != null) {
            reservationDTO.setUserDTO(UserMapper.user2UserDTO(reservation.getUser()));
        }

        return reservationDTO;
    }
}
