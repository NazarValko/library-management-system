package com.nazarvalko.lms.repository;

import com.nazarvalko.lms.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface AdminRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Transactional
    @Query(value = "insert into user_book (book_id, taken_date, submit_date, user_id) values (:bookId, :takenDate, :submitDate, :userId)",
            nativeQuery = true)
    void confirmBookRequest(@Param("bookId") Integer bookId, @Param("userId") Integer userId,
                            @Param("takenDate") Date takenDate, @Param("submitDate") Date submitDate);

}
