package net.javaguides.studentmanagement.service.impl;

import net.javaguides.studentmanagement.model.Book;
import net.javaguides.studentmanagement.model.BookOrder;
import net.javaguides.studentmanagement.repository.BookOrderRepository;
import net.javaguides.studentmanagement.repository.BookRepository;
import net.javaguides.studentmanagement.service.BookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookOrderServiceImpl implements BookOrderService {

    private final BookOrderRepository bookOrderRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookOrderServiceImpl(BookOrderRepository bookOrderRepository, BookRepository bookRepository) {
        this.bookOrderRepository = bookOrderRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookOrder> getAllOrders() { return bookOrderRepository.findAll(); }

    @Override
    public BookOrder saveOrder(BookOrder order) {
        // Decrease available copies when issuing
        Book book = order.getBook();
        if (book != null && book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
        }
        return bookOrderRepository.save(order);
    }

    @Override
    public BookOrder getOrderById(long id) {
        return bookOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public void deleteOrderById(long id) { bookOrderRepository.deleteById(id); }

    @Override
    public List<BookOrder> getOrdersByStatus(String status) { return bookOrderRepository.findByStatus(status); }
}
