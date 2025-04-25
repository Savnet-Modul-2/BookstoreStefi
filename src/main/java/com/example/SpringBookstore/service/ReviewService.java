package com.example.SpringBookstore.service;

import com.example.SpringBookstore.entity.Book;
import com.example.SpringBookstore.entity.Review;
import com.example.SpringBookstore.entity.User;
import com.example.SpringBookstore.repository.BookRepository;
import com.example.SpringBookstore.repository.ReviewRepository;
import com.example.SpringBookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Review addReview(Long userId, Long bookId, Review review) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found."));

        review.setDate(LocalDateTime.now());
        review.setBook(book);
        review.setUser(user);

        user.getReviews().add(review);

        book.getReviews().add(review);
        book.calculateRating();

        reviewRepository.save(review);
        bookRepository.save(book);
        userRepository.save(user);

        return review;
    }

    @Transactional
    public Review modifyReview(Long userId, Long bookId, Long reviewId, Review reviewUpdate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found."));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review with ID " + reviewId + " not found."));

        Integer oldGrade = review.getGrade();

        if (reviewUpdate.getGrade() != null) {
            review.setGrade(reviewUpdate.getGrade());
        }

        if (reviewUpdate.getDescription() != null) {
            review.setDescription(reviewUpdate.getDescription());
        }

        review.setDate(LocalDateTime.now());

        book.calculateRating();

        reviewRepository.save(review);
        bookRepository.save(book);
        userRepository.save(user);

        return review;
    }

    @Transactional
    public void removeReview(Long userId, Long bookId, Long reviewId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found."));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found."));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review with ID " + reviewId + " not found."));

        user.getReviews().remove(review);

        book.getReviews().remove(review);
        book.calculateRating();

        bookRepository.save(book);
        userRepository.save(user);
    }

    public Page<Review> listUserReviewsPaginated(String sortDirection, String sortCriteria, Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortCriteria);
        Pageable pageable = (pageNumber != null && pageSize != null) ? PageRequest.of(pageNumber, pageSize, sort) : Pageable.unpaged();
        return reviewRepository.findAll(pageable);
    }
}
