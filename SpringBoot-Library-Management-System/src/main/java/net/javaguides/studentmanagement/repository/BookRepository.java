package net.javaguides.studentmanagement.repository;

import net.javaguides.studentmanagement.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}
