package net.javaguides.studentmanagement.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book_returns")
public class BookReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private BookOrder bookOrder;

    private LocalDate returnDate;
    private double fineAmount;
    private String remarks;

    @Column(name = "book_condition") // 'condition' is a reserved SQL keyword
    private String bookCondition; // GOOD, DAMAGED, LOST

    public BookReturn() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BookOrder getBookOrder() { return bookOrder; }
    public void setBookOrder(BookOrder bookOrder) { this.bookOrder = bookOrder; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public double getFineAmount() { return fineAmount; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public String getBookCondition() { return bookCondition; }
    public void setBookCondition(String bookCondition) { this.bookCondition = bookCondition; }
}
