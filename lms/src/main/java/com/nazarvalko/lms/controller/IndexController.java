package com.nazarvalko.lms.controller;


import com.nazarvalko.lms.entity.Book;
import com.nazarvalko.lms.entity.Review;
import com.nazarvalko.lms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class IndexController {

    private BookService bookService;


    @Autowired
    public IndexController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1") int page,
                        @RequestParam(name = "categories", required = false) List<Integer> categories) {

        List<Book> books;

        if (categories != null && !categories.isEmpty()) {
            books = bookService.getBooksByCategory(categories);
        } else {
            books = bookService.getAllBooks();
        }

        int booksPerPage = 7;
        int totalPages = (int) Math.ceil((double) books.size() / booksPerPage);
        int startIndex = (page - 1) * booksPerPage;
        int endIndex = Math.min(startIndex + booksPerPage, books.size());
        List<Book> booksOnPage = books.subList(startIndex, endIndex);


        model.addAttribute("books", booksOnPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("allThemes", bookService.getCategories());
        return "index";
    }
}
