package com.nazarvalko.lms.controller;

import com.nazarvalko.lms.entity.Book;
import com.nazarvalko.lms.entity.Category;
import com.nazarvalko.lms.service.BookService;
import com.nazarvalko.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminController {
    private UserService userService;

    private BookService bookService;

    @Autowired
    public AdminController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }


    @GetMapping("/show-add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("authors", bookService.getAllAuthors());
        model.addAttribute("categories", bookService.getCategories());
        model.addAttribute("book", new Book());
        return "add-book";
    }
    @PostMapping("/add-book")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/show-add-book";
    }

    @GetMapping("/delete-book")
    public String deleteBook(@RequestParam("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/";
    }

    @GetMapping("/categories")
    public String categoriesList(Model model) {
        model.addAttribute("categories", bookService.getCategories());
        return "categories";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("categoryName") String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        bookService.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete-category")
    public String deleteCategory(@RequestParam("categoryId") int categoryId) {
        bookService.deleteCategory(categoryId);
        return "redirect:/categories";
    }

    @GetMapping("/update-category")
    public String updateCategory(@RequestParam("id") int id, @ModelAttribute("categoryName") String categoryName) {
        bookService.updateCategory(id, categoryName);
        return "redirect:/categories";
    }



}
