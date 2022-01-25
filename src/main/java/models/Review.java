package models;

public class Review {
    private int id;
    private int rating;
    private int cleanerId;
    private String reviewer;
    private String reviewerLocation;
    private String reviewMessage;
    private String createdAt;

    public Review(int rating, int cleanerId, String reviewer, String reviewerLocation, String reviewMessage, String createdAt) {
        this.rating = rating;
        this.cleanerId = cleanerId;
        this.reviewer = reviewer;
        this.reviewerLocation = reviewerLocation;
        this.reviewMessage = reviewMessage;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(int cleanerId) {
        this.cleanerId = cleanerId;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewerLocation() {
        return reviewerLocation;
    }

    public void setReviewerLocation(String reviewerLocation) {
        this.reviewerLocation = reviewerLocation;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
