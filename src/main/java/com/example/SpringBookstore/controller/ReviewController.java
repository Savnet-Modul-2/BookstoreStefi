package com.example.SpringBookstore.controller;

import com.example.SpringBookstore.dto.ReviewDTO;
import com.example.SpringBookstore.entity.Review;
import com.example.SpringBookstore.mapper.ReviewMapper;
import com.example.SpringBookstore.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(path = "/{userId}/{bookId}")
    public ResponseEntity<?> addReview(@PathVariable(name = "userId") Long userId,
                                       @PathVariable(name = "bookId") Long bookId,
                                       @RequestBody ReviewDTO reviewDTO) {
        Review reviewToAdd = ReviewMapper.reviewDTO2Review(reviewDTO);
        Review addedReview = reviewService.addReview(userId, bookId, reviewToAdd);

        return ResponseEntity.ok(ReviewMapper.review2ReviewDTO(addedReview));
    }

    @PutMapping(path = "/{userId}/{bookId}/{reviewId}")
    public ResponseEntity<?> modifyReview(@PathVariable(name = "userId") Long userId,
                                          @PathVariable(name = "bookId") Long bookId,
                                          @PathVariable(name = "reviewId") Long reviewId,
                                          @RequestBody ReviewDTO reviewDTO) {
        Review reviewUpdate = ReviewMapper.reviewDTO2Review(reviewDTO);
        Review updatedReview = reviewService.modifyReview(userId, bookId, reviewId, reviewUpdate);

        return ResponseEntity.ok(ReviewMapper.review2ReviewDTO(updatedReview));
    }

    @DeleteMapping(path = "/{userId}/{bookId}/{reviewId}")
    public ResponseEntity<?> removeReview(@PathVariable(name = "userId") Long userId,
                                          @PathVariable(name = "bookId") Long bookId,
                                          @PathVariable(name = "reviewId") Long reviewId) {
        reviewService.removeReview(userId, bookId, reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<?> listUserReviewsPaginated(@RequestParam(required = false, defaultValue = "ASC") String sortDirection,
                                                      @RequestParam(required = false, defaultValue = "date") String sortCriteria,
                                                      @RequestParam(required = false) Integer pageNumber,
                                                      @RequestParam(required = false) Integer pageSize) {
        Page<Review> reviews = reviewService.listUserReviewsPaginated(sortDirection, sortCriteria, pageNumber, pageSize);
        return ResponseEntity.ok(reviews.stream()
                .map(ReviewMapper::review2ReviewDTO)
                .toList());
    }
}
