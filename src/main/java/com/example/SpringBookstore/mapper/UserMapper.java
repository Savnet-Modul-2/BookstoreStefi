package com.example.SpringBookstore.mapper;

import com.example.SpringBookstore.dto.UserDTO;
import com.example.SpringBookstore.entity.User;

public class UserMapper {
    public static User userDTO2User(UserDTO userDTO) {
        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setGender(userDTO.getGender());
        user.setAge(userDTO.getAge());
        user.setBirthDate(userDTO.getBirthDate());
        user.setCountry(userDTO.getCountry());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmailAddress(userDTO.getEmailAddress());
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public static UserDTO user2UserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setGender(user.getGender());
        userDTO.setAge(user.getAge());
        userDTO.setBirthDate(user.getBirthDate());
        userDTO.setCountry(user.getCountry());
        userDTO.setEmailAddress(user.getEmailAddress());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPassword(user.getPassword());
        userDTO.setVerifiedAccount(user.getVerifiedAccount());

        return userDTO;
    }
}
