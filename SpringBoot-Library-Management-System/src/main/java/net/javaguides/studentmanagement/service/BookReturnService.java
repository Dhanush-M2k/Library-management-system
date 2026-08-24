package net.javaguides.studentmanagement.service;

import net.javaguides.studentmanagement.model.BookReturn;
import java.util.List;

public interface BookReturnService {
    List<BookReturn> getAllReturns();
    BookReturn saveReturn(BookReturn bookReturn);
    BookReturn getReturnById(long id);
    void deleteReturnById(long id);
}
