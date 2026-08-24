package net.javaguides.studentmanagement.repository;

import net.javaguides.studentmanagement.model.BookOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    List<BookOrder> findByStatus(String status);
}
