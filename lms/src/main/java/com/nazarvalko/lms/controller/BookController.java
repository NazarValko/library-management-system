package com.nazarvalko.lms.controller;

import com.nazarvalko.lms.entity.Author;
import com.nazarvalko.lms.entity.Book;
import com.nazarvalko.lms.entity.Category;
import com.nazarvalko.lms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;


    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Book> results = bookService.findBookByTitle(query);
        model.addAttribute("books", results);
        return "search-results";
    }

    @GetMapping("/view-book")
    public String viewBook(@RequestParam("id") int id, Model model) {
        Book book = bookService.findBookById(id);
        model.addAttribute("book", book);
        return "view-book";
    }

    @PostMapping("/update-book")
    public String updateBook(@ModelAttribute("book") Book book) {

        if (bookService.findCategoryByName(book.getCategory().getName()) != null) {
            book.setCategory(bookService.findCategoryByName(book.getCategory().getName()));
            bookService.updateBook(book);
        } else {
            bookService.updateBook(book);
        }

        return "redirect:/";
    }
}
