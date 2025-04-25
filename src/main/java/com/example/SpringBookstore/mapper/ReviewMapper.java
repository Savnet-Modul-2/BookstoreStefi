package com.example.SpringBookstore.mapper;

import com.example.SpringBookstore.dto.ReviewDTO;
import com.example.SpringBookstore.entity.Review;

public class ReviewMapper {
    public static Review reviewDTO2Review(ReviewDTO reviewDTO) {
        Review review = new Review();

        review.setGrade(reviewDTO.getGrade());
        review.setDescription(reviewDTO.getDescription());
        review.setDate(reviewDTO.getDate());

        if (reviewDTO.getBookDTO() != null) {
            review.setBook(BookMapper.bookDTO2Book(reviewDTO.getBookDTO()));
        }

        if (reviewDTO.getUserDTO() != null) {
            review.setUser(UserMapper.userDTO2User(reviewDTO.getUserDTO()));
        }

        return review;
    }

    public static ReviewDTO review2ReviewDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setId(review.getId());
        reviewDTO.setGrade(review.getGrade());
        reviewDTO.setDescription(review.getDescription());
        reviewDTO.setDate(review.getDate());

        if (review.getBook() != null) {
            reviewDTO.setBookDTO(BookMapper.book2BookDTO(review.getBook()));
        }

        if (review.getUser() != null) {
            reviewDTO.setUserDTO(UserMapper.user2UserDTO(review.getUser()));
        }

        return reviewDTO;
    }
}
