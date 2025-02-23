package com.example.BookStoreSpring.mapper;

import com.example.BookStoreSpring.entities.User;
import com.example.BookStoreSpring.dto.UserDTO;

public class UserMapper {
    public static User userDTO2User(UserDTO userDTO) {
        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setGender(userDTO.getGender());
        user.setCountry(userDTO.getCountry());
        user.setYearOfBirth(userDTO.getYearOfBirth());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());
        user.setVerifiedAccount(userDTO.getVerifiedAccount());

        return user;
    }

    public static UserDTO user2UserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setGender(user.getGender());
        userDTO.setCountry(user.getCountry());
        userDTO.setYearOfBirth(user.getYearOfBirth());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPassword(user.getPassword());
        userDTO.setVerifiedAccount(user.getVerifiedAccount());

        return userDTO;
    }
}
