package net.javaguides.studentmanagement.controller;

import net.javaguides.studentmanagement.model.BookReturn;
import net.javaguides.studentmanagement.service.BookOrderService;
import net.javaguides.studentmanagement.service.BookReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/returns")
public class BookReturnController {

    private final BookReturnService bookReturnService;
    private final BookOrderService bookOrderService;

    @Autowired
    public BookReturnController(BookReturnService bookReturnService, BookOrderService bookOrderService) {
        this.bookReturnService = bookReturnService;
        this.bookOrderService = bookOrderService;
    }

    @GetMapping
    public String viewReturnList(Model model) {
        model.addAttribute("returns", bookReturnService.getAllReturns());
        return "returns";
    }

    @GetMapping("/new")
    public String showNewReturnForm(Model model) {
        model.addAttribute("bookReturn", new BookReturn());
        model.addAttribute("orders", bookOrderService.getOrdersByStatus("ISSUED"));
        return "new_return";
    }

    @PostMapping
    public String saveReturn(@RequestParam("orderId") long orderId,
                             @RequestParam("bookCondition") String bookCondition,
                             @RequestParam(value = "remarks", required = false) String remarks) {
        BookReturn bookReturn = new BookReturn();
        bookReturn.setBookOrder(bookOrderService.getOrderById(orderId));
        bookReturn.setReturnDate(LocalDate.now());
        bookReturn.setBookCondition(bookCondition);
        bookReturn.setRemarks(remarks);
        // Calculate fine: Rs 5 per day if overdue
        LocalDate dueDate = bookReturn.getBookOrder().getDueDate();
        if (LocalDate.now().isAfter(dueDate)) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
            bookReturn.setFineAmount(daysLate * 5.0);
        } else {
            bookReturn.setFineAmount(0.0);
        }
        bookReturnService.saveReturn(bookReturn);
        return "redirect:/returns";
    }

    @GetMapping("/delete/{id}")
    public String deleteReturn(@PathVariable long id) {
        bookReturnService.deleteReturnById(id);
        return "redirect:/returns";
    }
}
