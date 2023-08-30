package com.nazarvalko.lms.model;

import com.nazarvalko.lms.entity.Book;
import com.nazarvalko.lms.entity.User;
import com.nazarvalko.lms.entity.UserBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssuedBook {
    private static Map<User, List<UserBook>> userIssuedBooks = new HashMap<>();

    private IssuedBook () {}

    public static Map<User, List<UserBook>> getBooks() {
        return userIssuedBooks;
    }

    public static List<UserBook> getUserIssuedBooks(User user) {
        for (User temp : userIssuedBooks.keySet()) {
            if (temp.equals(user)) {
                return temp.getBooks();
            }
        }
        return null;
    }
}
