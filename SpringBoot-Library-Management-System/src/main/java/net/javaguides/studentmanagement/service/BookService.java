package net.javaguides.studentmanagement.service;

import net.javaguides.studentmanagement.model.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book saveBook(Book book);
    Book getBookById(long id);
    void deleteBookById(long id);
}
