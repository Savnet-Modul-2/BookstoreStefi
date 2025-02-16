package com.example.BookStoreSpring.mapper;

import com.example.BookStoreSpring.dto.UserDTO;
import com.example.BookStoreSpring.entities.User;

public class UserMapper {
    public static User userDTO2User(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setGender(userDTO.getGender());
        user.setCountry(userDTO.getCountry());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setYearOfBirth(userDTO.getYearOfBirth());
        user.setVerifiedAccount(userDTO.getVerifiedAccount());
        return user;
    }

    public static UserDTO user2UserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setGender(user.getGender());
        userDTO.setCountry(user.getCountry());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setYearOfBirth(user.getYearOfBirth());
        userDTO.setVerifiedAccount(user.getVerifiedAccount());
        return userDTO;
    }
}
