package net.javaguides.studentmanagement.controller;

import net.javaguides.studentmanagement.model.Book;
import net.javaguides.studentmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping
    public String viewBookList(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/new")
    public String showNewBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "new_book";
    }

    @PostMapping
    public String saveBook(@ModelAttribute("book") Book book) {
        book.setAvailableCopies(book.getTotalCopies());
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "update_book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable long id, @ModelAttribute("book") Book book) {
        book.setId(id);
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBookById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "This book can't be deleted because it has existing orders linked to it. Remove those orders first.");
        }
        return "redirect:/books";
    }
}
