package com.nazarvalko.lms.repository;

import com.nazarvalko.lms.entity.Book;
import com.nazarvalko.lms.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> getReviewsByBook(Book book);
}
