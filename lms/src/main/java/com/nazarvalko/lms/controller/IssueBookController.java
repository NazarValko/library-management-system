package com.nazarvalko.lms.controller;

import com.nazarvalko.lms.entity.Book;
import com.nazarvalko.lms.entity.RequestBook;
import com.nazarvalko.lms.entity.User;
import com.nazarvalko.lms.entity.UserBook;
import com.nazarvalko.lms.service.BookService;
import com.nazarvalko.lms.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/issue-book-form")
public class IssueBookController {

    private UserService userService;
    private BookService bookService;


    @Autowired
    public IssueBookController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String showIssueBook(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Book book = (Book) session.getAttribute("book");

        model.addAttribute("user", user);
        model.addAttribute("book", book);

        return "issue-book";
    }

    @GetMapping("/search-user")
    public String searchUser(@RequestParam("username") String username, HttpSession session) {
        User user = userService.findUserByUsername(username);
        session.setAttribute("user", user);
        return "redirect:/issue-book-form/";
    }
    @GetMapping("/search-book")
    public String searchBook(@RequestParam("bookName") String bookName, HttpSession session) {
        Book book = bookService.findBookByISBN(Long.parseLong(bookName));
        session.setAttribute("book", book);
        return "redirect:/issue-book-form/";
    }

    @GetMapping("/issue-book")
    public String issueBook(HttpSession session,
                            @RequestParam("takenDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String takenDate,
                            @RequestParam("submitDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String submitDate) throws ParseException {
        User user = (User) session.getAttribute("user");
        Book book = (Book) session.getAttribute("book");

        User userData = userService.findUserByEmail(user.getUsername());
        Book bookData = bookService.findBookByISBN(book.getISBN());


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        UserBook userBook = new UserBook();
        userBook.setUser(userData);
        userBook.setBook(bookData);
        userBook.setTakenDate(new Date(simpleDateFormat.parse(takenDate).getTime()));
        userBook.setSubmitDate(new Date(simpleDateFormat.parse(submitDate).getTime()));

        bookData.setAvailable(false);

        bookService.addBookToUser(userBook);
        return "redirect:/dashboard/borrowed";
    }
}
