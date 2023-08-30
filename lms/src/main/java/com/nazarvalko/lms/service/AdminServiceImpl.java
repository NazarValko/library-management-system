package com.nazarvalko.lms.service;


import com.nazarvalko.lms.entity.Book;
import com.nazarvalko.lms.entity.RequestBook;
import com.nazarvalko.lms.entity.User;
import com.nazarvalko.lms.entity.UserBook;
import com.nazarvalko.lms.repository.AdminRepository;
import com.nazarvalko.lms.repository.BookRepository;
import com.nazarvalko.lms.repository.RequestBookRepository;
import com.nazarvalko.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    private RequestBookRepository requestBookRepository;

    private UserRepository userRepository;

    private BookRepository bookRepository;


    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, RequestBookRepository requestBookRepository,
                            UserRepository userRepository, BookRepository bookRepository) {
        this.adminRepository = adminRepository;
        this.requestBookRepository = requestBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void confirmBookRequest(RequestBook requestBook, Date takenDate, Date submitDate) {
        Book tempBook = requestBook.getBook();
        User tempUser = requestBook.getUser();
        System.out.println("------------" + tempUser.getId());
        requestBook.getBook().setAvailable(false);
        adminRepository.confirmBookRequest(tempBook.getId(), tempUser.getId(), takenDate, submitDate);
        requestBookRepository.deleteRequestBookById(requestBook.getId());
    }

    @Override
    public void rejectRequest(int id) {
        RequestBook requestBook = requestBookRepository.findRequestBookById(id);
        requestBookRepository.deleteRequestBookById(id);
        requestBook.getBook().setAvailable(true);
    }

    @Override
    public List<UserBook> allIssuedBooks() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date current = new java.util.Date();
        String formatted = dateFormat.format(current);
        List<UserBook> issued = new ArrayList<>();
        List<User> users = userRepository.getAllUsers();


        try {
            current = dateFormat.parse(formatted);
            for (User user : users) {
                List<UserBook> userBooks = user.getBooks();
                for (UserBook userBook : userBooks) {
                    if (current.after(userBook.getSubmitDate())) {
                        issued.add(userBook);
                    }
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return issued;
    }

}
