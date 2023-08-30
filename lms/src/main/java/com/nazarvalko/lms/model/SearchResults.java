package com.nazarvalko.lms.model;

import com.nazarvalko.lms.entity.Book;
import com.nazarvalko.lms.entity.User;

public class SearchResults {
    private User user;
    private Book book;

    public SearchResults(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
