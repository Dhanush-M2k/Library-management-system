package net.javaguides.studentmanagement.service;

import net.javaguides.studentmanagement.model.BookOrder;
import java.util.List;

public interface BookOrderService {
    List<BookOrder> getAllOrders();
    BookOrder saveOrder(BookOrder order);
    BookOrder getOrderById(long id);
    void deleteOrderById(long id);
    List<BookOrder> getOrdersByStatus(String status);
}
