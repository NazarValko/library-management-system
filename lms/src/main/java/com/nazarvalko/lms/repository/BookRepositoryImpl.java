package com.nazarvalko.lms.repository;

import com.nazarvalko.lms.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private EntityManager entityManager;


    @Autowired
    public BookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<UserBook> getUserBooks(User user) {
        TypedQuery<UserBook> query =
                entityManager.createQuery("select b from UserBook b where b.user=:data", UserBook.class);
        query.setParameter("data", user);
        return query.getResultList();
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> query =
                entityManager.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> viewArchivedBooks() {
        return null;
    }

    @Override
    public Book findBookById(int id) {
        TypedQuery<Book> query =
                entityManager.createQuery("select b from Book b where b.id=:id", Book.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Book findBookByISBN(long isbn) {
        TypedQuery<Book> query =
                entityManager.createQuery("select b from Book b where b.ISBN=:isbn", Book.class);
        query.setParameter("isbn", isbn);
        return query.getSingleResult();
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        TypedQuery<Book> query =
                entityManager.createQuery("select b from Book b where lower(b.title) like lower(concat('%', :title, '%'))", Book.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByAuthorId(int id) {
        TypedQuery<Book> query =
                entityManager.createQuery("select b from Book b where b.author=:author", Book.class);
        query.setParameter("author", id);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        Book existingBook = findBookByISBN(book.getISBN());


        existingBook.setTitle(book.getTitle());
        existingBook.getAuthor().setName(book.getAuthor().getName());
        existingBook.setPublishedDate(book.getPublishedDate());
        existingBook.setCategory(book.getCategory());

        addBook(existingBook);
    }

    @Override
    @Transactional
    public void deleteBook(int id) {
        Book book = findBookById(id);
        entityManager.remove(book);
    }

    @Override
    @Transactional
    public void deleteBookByTitle(String title) {
        entityManager.remove(findBookByTitle(title));
    }

    @Override
    public List<Book> getBooksByCategory(List<Integer> categories) {
        TypedQuery<Book> query =
                entityManager.createQuery("SELECT b FROM Book b WHERE b.category.id IN :categoryIds", Book.class);
        query.setParameter("categoryIds", categories);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void addBook(Book book) {
        entityManager.persist(book);
    }

    @Override
    public long countUserBook(User user) {
        TypedQuery<Long> query =
                entityManager.createQuery("select count (b) from UserBook b where b.user=:data", Long.class);

        query.setParameter("data", user);

        return query.getSingleResult();
    }

    @Override
    public long countAllBooks() {
        TypedQuery<Long> query =
                entityManager.createQuery("select count (b) from Book b", Long.class);
        return query.getSingleResult();
    }

    @Override
    public long countAllBorrowedBooks() {
        TypedQuery<Long> query =
                entityManager.createQuery("select count (b) from UserBook b", Long.class);
        return query.getSingleResult();
    }


}
