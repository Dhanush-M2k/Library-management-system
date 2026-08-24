package net.javaguides.studentmanagement.controller;

import net.javaguides.studentmanagement.model.BookOrder;
import net.javaguides.studentmanagement.service.BookOrderService;
import net.javaguides.studentmanagement.service.BookService;
import net.javaguides.studentmanagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/orders")
public class BookOrderController {

    private final BookOrderService bookOrderService;
    private final BookService bookService;
    private final MemberService memberService;

    @Autowired
    public BookOrderController(BookOrderService bookOrderService, BookService bookService, MemberService memberService) {
        this.bookOrderService = bookOrderService;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @GetMapping
    public String viewOrderList(Model model) {
        model.addAttribute("orders", bookOrderService.getAllOrders());
        return "orders";
    }

    @GetMapping("/new")
    public String showNewOrderForm(Model model) {
        model.addAttribute("order", new BookOrder());
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("books", bookService.getAllBooks());
        return "new_order";
    }

    @PostMapping
    public String saveOrder(@ModelAttribute("order") BookOrder order,
                            @RequestParam("memberId") long memberId,
                            @RequestParam("bookId") long bookId) {
        order.setMember(memberService.getMemberById(memberId));
        order.setBook(bookService.getBookById(bookId));
        order.setOrderDate(LocalDate.now());
        order.setDueDate(LocalDate.now().plusDays(14));
        order.setStatus("ISSUED");
        bookOrderService.saveOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try {
            bookOrderService.deleteOrderById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Order deleted successfully.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "This order can't be deleted because a return record is linked to it. Remove that return first.");
        }
        return "redirect:/orders";
    }
}
