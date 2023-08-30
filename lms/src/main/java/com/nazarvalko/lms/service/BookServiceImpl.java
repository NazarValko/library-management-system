package com.nazarvalko.lms.service;

import com.nazarvalko.lms.entity.*;
import com.nazarvalko.lms.model.IssuedBook;
import com.nazarvalko.lms.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    private UserRepository userRepository;

    private ReviewRepository reviewRepository;

    private RequestBookRepository requestBookRepository;

    private UserBookRepository userBookRepository;

    private CategoryRepository categoryRepository;

    private AuthorRepository authorRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository,
                           ReviewRepository reviewRepository, RequestBookRepository requestBookRepository,
                           UserBookRepository userBookRepository, CategoryRepository categoryRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.requestBookRepository = requestBookRepository;
        this.userBookRepository = userBookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Override
    public List<UserBook> getUserBooks(User user) {
        return bookRepository.getUserBooks(user);
    }

    @Override
    public List<RequestBook> getAllRequestedBooks() {
        return requestBookRepository.findAll();
    }

    @Override
    public List<RequestBook> findAllByUser(User user) {
        return requestBookRepository.findAllByUser(user);
    }

    @Override
    public void deleteRequest(int id) {
        requestBookRepository.deleteRequestBookById(id);
    }

    @Override
    public List<UserBook> getAllUserBooks() {
        return userBookRepository.findAll();
    }

    @Override
    public void addBook(Book book) {
        book.setAvailable(true);
        Author author = book.getAuthor();
        Category category = book.getCategory();
        Optional<Author> isAuthor = authorRepository.findById(author.getId());
        Optional<Category> isCategory = categoryRepository.findById(category.getId());


        if (isAuthor.isEmpty()) {
            authorRepository.save(author);
        }
        if (isCategory.isEmpty()) {
            categoryRepository.save(category);
        }

        bookRepository.addBook(book);

    }

    @Override
    public long countAllIssuedBooks() {
        return 1;
    }

    @Override
    public long getAllIssuedBooks(User user) {
        if (IssuedBook.getBooks().containsKey(user)) {
            return IssuedBook.getBooks().get(user).size();
        } else {
            return 0;
        }
    }

    @Override
    public List<UserBook> getIssuedBooks() {
        List<UserBook> result = new ArrayList<>();
        Collection<List<UserBook>> temp = IssuedBook.getBooks().values();
        for (List<UserBook> l : temp) {
            for (UserBook userBook : l) {
                result.add(userBook);
            }
        }
        return result;
    }


    @Override
    public void issueBook(int userId) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = userRepository.findUserById(userId);
        List<UserBook> userBooks = bookRepository.getUserBooks(userRepository.findUserById(userId));
        Date current = new Date();

        String formatted = dateFormat.format(current);

        try {
            current = dateFormat.parse(formatted);
            List<UserBook> toPut = new ArrayList<>();
            for (UserBook book : userBooks) {
                if (current.after(book.getSubmitDate())) {
                    toPut.add(book);
                    IssuedBook.getBooks().put(user, toPut);
                }
            }
        } catch (ParseException e) {

        }
    }

    @Override
    public void requestBook(RequestBook book) {
        requestBookRepository.save(book);
    }

    @Override
    public void addReview(Review review) {
        reviewRepository.save(review);
        //Book book = bookRepository.findBookById(review.getBook().getId());
//        if (book != null) {
//            book.getReviews().add(review);
//            bookRepository.updateBook(book);
//        }

    }


    @Override
    public List<Book> viewArchivedBooks() {
        return null;
    }

    @Override
    public void returnBook(UserBook userBook) {
        userBookRepository.delete(userBook);
    }

    @Override
    public Book findBookById(int id) {
        return bookRepository.findBookById(id);
    }

    @Override
    public Book findBookByISBN(long isbn) {
        return bookRepository.findBookByISBN(isbn);
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title);
    }

    @Override
    public List<Book> getBooksByAuthorId(int id) {
       return bookRepository.getBooksByAuthorId(id);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.updateBook(book);
    }

    @Override
    public void deleteBook(int id) {
        bookRepository.deleteBook(id);
    }

    @Override
    public void deleteBookByTitle(String title) {
        bookRepository.deleteBookByTitle(title);
    }

    @Override
    public List<Book> getBooksByCategory(List<Integer> categories) {
        return bookRepository.getBooksByCategory(categories);
    }

    @Override
    public long countUserBooks(User user) {
       return bookRepository.countUserBook(user);
    }

    @Override
    public long countAllBooks() {
        return bookRepository.countAllBooks();
    }

    @Override
    public long countAllBorrowedBooks() {
        return bookRepository.countAllBorrowedBooks();
    }

    @Override
    public RequestBook findRequestBookById(int id) {
        return requestBookRepository.findRequestBookById(id);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(int id, String newName) {
        categoryRepository.updateCategory(id, newName);
    }

    @Override
    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(null);

        for (Book book : category.getBooks()) {
            book.setCategory(null);
        }
        category.setBooks(null);
        categoryRepository.delete(category);
    }

    @Override
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    @Override
    public void addBookToUser(UserBook userBook) {
        userBookRepository.save(userBook);
    }

    @Override
    public void returnBook(int id) {
        Optional<UserBook> result = userBookRepository.findById(id);

        UserBook userBook = result.orElse(null);

        userBook.getBook().setAvailable(true);
        userBookRepository.deleteById(id);

    }

}
