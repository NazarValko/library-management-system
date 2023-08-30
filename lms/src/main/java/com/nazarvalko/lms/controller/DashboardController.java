package com.nazarvalko.lms.controller;

import com.nazarvalko.lms.entity.Book;
import com.nazarvalko.lms.entity.Role;
import com.nazarvalko.lms.entity.User;
import com.nazarvalko.lms.entity.UserBook;
import com.nazarvalko.lms.model.IssuedBook;
import com.nazarvalko.lms.service.AdminService;
import com.nazarvalko.lms.service.BookService;
import com.nazarvalko.lms.service.RoleService;
import com.nazarvalko.lms.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private UserService userService;
    private BookService bookService;

    private AdminService adminService;

    private RoleService roleService;


    @Autowired
    public DashboardController(UserService userService, BookService bookService, RoleService roleService,
                               AdminService adminService) {
        this.userService = userService;
        this.bookService = bookService;
        this.roleService = roleService;
        this.adminService = adminService;
    }

    @GetMapping("/")
    public String showStatistics(Model model, Principal principal) {

        User user = userService.findUserByUsername(principal.getName());
        Role role_admin = roleService.findRoleByName("ROLE_ADMIN");



        model.addAttribute("allBooks", bookService.countAllBooks());
        model.addAttribute("myBooks", bookService.countUserBooks(user));


        if (user.getRoles().contains(role_admin)) {
            model.addAttribute("issuedBooksCount", adminService.allIssuedBooks().size());
        } else {
            bookService.issueBook(user.getId());
            model.addAttribute("issuedBooksCount", bookService.getAllIssuedBooks(user));
        }

        model.addAttribute("borrowedBooks", bookService.countAllBorrowedBooks());
        model.addAttribute("allUsers", userService.countAllUsers());


        return "dashboard";
    }

    @GetMapping("/user-books")
    public String userBooks(Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        model.addAttribute("userBooks", bookService.getUserBooks(user));
        return "user-books";
    }
    @GetMapping("/borrowed")
    public String borrowedBooks(Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        model.addAttribute("userBooks", bookService.getAllUserBooks());
        return "borrowed-books";
    }

    @GetMapping("/show-users")
    public String users(Model model) {
        model.addAttribute("usersList", userService.getAllUsers());

        return "users";
    }

    @GetMapping("/issued-books")
    public String issued(Model model) {
        model.addAttribute("issuedBooks", adminService.allIssuedBooks());
        return "issued";
    }

    @GetMapping("/delete-user")
    public String deleteUser(@RequestParam("id") int userId) {
        userService.deleteUser(userId);
        return "redirect:/dashboard/show-users";
    }

    @GetMapping("/return")
    public String returnBook(@RequestParam("id") int id) {

        bookService.returnBook(id);
        return "redirect:/dashboard/borrowed";
    }
}
