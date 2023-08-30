package com.nazarvalko.lms.repository;

import com.nazarvalko.lms.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookRepository extends JpaRepository<UserBook, Integer> {
}
