package com.nazarvalko.lms.service;

import com.nazarvalko.lms.entity.RequestBook;
import com.nazarvalko.lms.entity.UserBook;

import java.sql.Date;
import java.util.List;

public interface AdminService {

    void confirmBookRequest(RequestBook requestBook, Date takenDate, Date submitDate);

    void rejectRequest(int id);

    List<UserBook> allIssuedBooks();
}
