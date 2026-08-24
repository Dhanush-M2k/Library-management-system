package net.javaguides.studentmanagement.service.impl;

import net.javaguides.studentmanagement.model.Book;
import net.javaguides.studentmanagement.repository.BookRepository;
import net.javaguides.studentmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() { return bookRepository.findAll(); }

    @Override
    public Book saveBook(Book book) { return bookRepository.save(book); }

    @Override
    public Book getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Override
    public void deleteBookById(long id) { bookRepository.deleteById(id); }
}
