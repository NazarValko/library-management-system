package com.nazarvalko.lms.repository;

import com.nazarvalko.lms.entity.Book;
import com.nazarvalko.lms.entity.User;
import com.nazarvalko.lms.entity.UserBook;

import java.util.List;

public interface BookRepository {
    List<UserBook> getUserBooks(User user);

    List<Book> getAllBooks();

    List<Book> viewArchivedBooks();

    Book findBookById(int id);

    Book findBookByISBN(long isbn);

    List<Book> findBookByTitle(String title);

    List<Book> getBooksByAuthorId(int id);

    void updateBook(Book book);

    void deleteBook(int id);

    void deleteBookByTitle(String title);

    List<Book> getBooksByCategory(List<Integer> categories);

    void addBook(Book book);

    long countUserBook(User user);

    long countAllBooks();

    long countAllBorrowedBooks();

}
