package com.nazarvalko.lms.controller;

import com.nazarvalko.lms.entity.*;
import com.nazarvalko.lms.service.AdminService;
import com.nazarvalko.lms.service.BookService;
import com.nazarvalko.lms.service.RoleService;
import com.nazarvalko.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class UserController {

    private UserService userService;
    private BookService bookService;
    private RoleService roleService;

    private AdminService adminService;

    @Autowired
    public UserController(UserService userService, BookService bookService,
                          RoleService roleService, AdminService adminService) {
        this.userService = userService;
        this.bookService = bookService;
        this.roleService = roleService;
        this.adminService = adminService;
    }

    @GetMapping("/user-books")
    public String getUserBooks(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());

        model.addAttribute("userBooks", user.getBooks());
        return "user-books";
    }

    @GetMapping("/request-book")
    public String request(Principal principal, @RequestParam("bookId") int bookId) {
        User user = userService.findUserByEmail(principal.getName());
        Book book = bookService.findBookById(bookId);
        RequestBook requestBook = new RequestBook();


        requestBook.setUser(user);
        requestBook.setBook(book);

        bookService.requestBook(requestBook);


        return "redirect:/";
    }

    @GetMapping("/request-list")
    public String myRequests(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        Role role_admin = roleService.findRoleByName("ROLE_ADMIN");

        if (user.getRoles().contains(role_admin)) {
            model.addAttribute("requests", bookService.getAllRequestedBooks());


        } else {
            model.addAttribute("requests", bookService.findAllByUser(user));
        }
        return "request-list";
    }
    @GetMapping("/delete-request")
    public String deleteRequest(@RequestParam("requestId") int requestId) {
        bookService.deleteRequest(requestId);
        return "redirect:/request-list";
    }
    @PostMapping("/approve-request")
    public String approve(@RequestParam("requestId") int requestId,
                          @RequestParam("takenDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String takenDate,
                          @RequestParam("submitDate") @DateTimeFormat(pattern = "yyyy-MM-dd") String submitDate) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        RequestBook requestBook = bookService.findRequestBookById(requestId);
        adminService.confirmBookRequest(requestBook,
                new Date(simpleDateFormat.parse(takenDate).getTime()),
                new Date(simpleDateFormat.parse(submitDate).getTime()));
        return "redirect:/request-list";
    }

    @GetMapping("/reject-request")
    public String reject(@RequestParam("requestId") int requestId) {
        adminService.rejectRequest(requestId);
        return "redirect:/request-list";
    }

    @GetMapping("/show-users")
    public String users(Model model) {
        model.addAttribute("usersList", userService.getAllUsers());

        return "users";
    }

    @GetMapping("/add-user-form")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addNewUser(user);
        return "redirect:/dashboard/show-users";
    }

    @PostMapping("/add-review")
    public String addReview(@RequestParam("id") int id, @ModelAttribute("comment") String comment, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        Book temp = bookService.findBookById(id);
        Review review = new Review();
        review.setDescription(comment);
        review.setBook(temp);
        review.setUser(user);
        bookService.addReview(review);
        return "redirect:/";
    }
}
