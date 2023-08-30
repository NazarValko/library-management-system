package com.nazarvalko.lms.service;

import com.nazarvalko.lms.entity.*;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    List<UserBook> getUserBooks(User User);

    List<RequestBook> getAllRequestedBooks();

    List<RequestBook> findAllByUser(User user);

    void deleteRequest(int id);
    List<UserBook> getAllUserBooks();

    void addBook(Book book);

    void issueBook(int userId);

    void requestBook(RequestBook book);

    void addReview(Review review);

    List<Book> viewArchivedBooks();

    void returnBook(UserBook userBook);

    Book findBookById(int id);

    Book findBookByISBN(long isbn);

    List<Book> findBookByTitle(String title);

    List<Book> getBooksByAuthorId(int id);

    void updateBook(Book book);

    void deleteBook(int id);

    void deleteBookByTitle(String title);

    List<Book> getBooksByCategory(List<Integer> categories);

    long countUserBooks(User user);

    long countAllBooks();

    long getAllIssuedBooks(User user);

    List<UserBook> getIssuedBooks();

    long countAllBorrowedBooks();

    long countAllIssuedBooks();

    RequestBook findRequestBookById(int id);

    List<Category> getCategories();

    List<Author> getAllAuthors();

    void addCategory(Category category);

    void updateCategory(int id, String newName);

    void deleteCategory(int id);

    void addAuthor(Author author);

    Category findCategoryByName(String name);

    void addBookToUser(UserBook userBook);

    void returnBook(int id);

}
