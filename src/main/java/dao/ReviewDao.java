package dao;

import models.Review;

import java.util.List;

public interface ReviewDao {
    //Create
    void addReview(Review review);

    //Read
    List<Review> getAllReviews();
    Review getReviewById(int id);

    List<Review> getReviewsByCleanerId(int cleanerId);

}
