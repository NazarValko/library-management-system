package com.nazarvalko.lms.repository;

import com.nazarvalko.lms.entity.RequestBook;
import com.nazarvalko.lms.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestBookRepository extends JpaRepository<RequestBook, Integer> {
    List<RequestBook> findAllByUser(User user);

    @Transactional
    void deleteRequestBookById(int id);

    RequestBook findRequestBookById(int id);

}
