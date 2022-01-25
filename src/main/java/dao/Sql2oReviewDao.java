package dao;

import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oReviewDao implements ReviewDao {
    private final Sql2o sql2o;

    public Sql2oReviewDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public static void getDrivers(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addReview(Review review) {
        getDrivers();
        try(Connection conn = sql2o.open()){
            String sql = "INSERT INTO reviews (rating, cleanerId, reviewer, reviewerLocation, reviewMessage, createdAt) VALUES  (:rating, :cleanerId, :reviewer, :reviewerLocation, :reviewMessage, :createdAt)";
            int id = (int) conn.createQuery(sql, true)
                    .bind(review)
                    .executeUpdate()
                    .getKey();
            review.setId(id);
        }catch(Sql2oException e){
            System.out.println(e);
        }
    }

    @Override
    public List<Review> getAllReviews() {
        getDrivers();
        try(Connection conn = sql2o.open()){
            String sql = "SELECT * FROM reviews";
            return conn.createQuery(sql)
                    .executeAndFetch(Review.class);
        }
    }

    @Override
    public Review getReviewById(int id) {
        getDrivers();
        try(Connection conn = sql2o.open()){
            String sql = "SELECT * FROM reviews WHERE id = :id";
            return conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Review.class);
        }
    }

    @Override
    public List<Review> getReviewsByCleanerId(int cleanerId) {
        getDrivers();
        try(Connection conn = sql2o.open()){
            String sql = "SELECT * FROM reviews WHERE cleanerId = :cleanerId";
            return conn.createQuery(sql)
                    .addParameter("cleanerId", cleanerId)
                    .executeAndFetch(Review.class);
        }
    }

}
