package net.javaguides.studentmanagement.service.impl;

import net.javaguides.studentmanagement.model.Book;
import net.javaguides.studentmanagement.model.BookOrder;
import net.javaguides.studentmanagement.model.BookReturn;
import net.javaguides.studentmanagement.repository.BookOrderRepository;
import net.javaguides.studentmanagement.repository.BookRepository;
import net.javaguides.studentmanagement.repository.BookReturnRepository;
import net.javaguides.studentmanagement.service.BookReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookReturnServiceImpl implements BookReturnService {

    private final BookReturnRepository bookReturnRepository;
    private final BookOrderRepository bookOrderRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookReturnServiceImpl(BookReturnRepository bookReturnRepository,
                                  BookOrderRepository bookOrderRepository,
                                  BookRepository bookRepository) {
        this.bookReturnRepository = bookReturnRepository;
        this.bookOrderRepository = bookOrderRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookReturn> getAllReturns() { return bookReturnRepository.findAll(); }

    @Override
    public BookReturn saveReturn(BookReturn bookReturn) {
        // Update order status to RETURNED
        BookOrder order = bookReturn.getBookOrder();
        if (order != null) {
            order.setStatus("RETURNED");
            bookOrderRepository.save(order);
            // Restore available copies
            Book book = order.getBook();
            if (book != null) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                bookRepository.save(book);
            }
        }
        return bookReturnRepository.save(bookReturn);
    }

    @Override
    public BookReturn getReturnById(long id) {
        return bookReturnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Return not found with id: " + id));
    }

    @Override
    public void deleteReturnById(long id) { bookReturnRepository.deleteById(id); }
}
