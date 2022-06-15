package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import hac.ex4.repo.Payment;
import hac.ex4.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Objects;

/**
 * The department is the controller of the admin.
 * Performs operations and returns pages for admin addresses.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    /**
     * Inject via its type the Book repo bean.
     */
    @Autowired
    private BookRepository repository;
    private BookRepository getRepo() {
        return repository;
    }

    /**
     * Inject via its type the Payment repo bean.
     */
    @Autowired
    private PaymentRepository paymentRepository;
    private PaymentRepository getRepoPayment() {
        return paymentRepository;
    }

    @GetMapping("")
    public String main(Model model) {
        model.addAttribute("books", getRepo().findAll());
        return "admin/admin";
    }

    @GetMapping("/addBook")
    public String addBookGet(Book book, Model model) {
        model.addAttribute("action", "/admin/add");
        model.addAttribute("title", "Add Book");
        return "admin/book_form";
    }

    @GetMapping("/add")
    public String addGet(Book book) {
        return "redirect:/admin";
    }

    @PostMapping("/add")
    public String addPost(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/book_form";
        }
        if(Objects.equals(book.getImage(), "")){
            book.setImage("default_image.png");
        }
        getRepo().save(book);
        model.addAttribute("books", getRepo().findAll());
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String deleteGet(Book book) {
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deletePost(@RequestParam("id") long id, Model model) {
        Book book = getRepo()
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid user Id:" + id)
                );
        getRepo().delete(book);
        model.addAttribute("books", getRepo().findAll());
        return "redirect:/admin";
    }

    @GetMapping("/updateBook")
    public String updateBookGet(Book book) {
        return "redirect:/admin";
    }

    @PostMapping("/updateBook")
    public String updateBookPost(@RequestParam("id") long id, Model model) {
        Book book = getRepo()
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid user Id:" + id)
                );
        model.addAttribute("book", book);
        model.addAttribute("title", "Update Book");
        model.addAttribute("action", "/admin/update/" + book.getId());
        return "admin/book_form";
    }

    @GetMapping("/update/{id}")
    public String updateGet(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        return "redirect:/admin";
    }

    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            model.addAttribute("book", book);
            model.addAttribute("title", "Update Book");
            model.addAttribute("action", "/admin/update/" + book.getId());
            return "admin/book_form";
        }
        if(Objects.equals(book.getImage(), "")){
            book.setImage("default_image.png");
        }
        getRepo().save(book);
        model.addAttribute("books", getRepo().findAll());
        return "redirect:/admin";
    }

    @GetMapping("/payments")
    public String paymentsGet(Model model) {
        model.addAttribute("payments", getRepoPayment().findByOrderByDatetime());
        double sum = 0;
        for(Payment payment : getRepoPayment().findByOrderByDatetime()){
            sum += payment.getAmount();
        }
        model.addAttribute("sum", sum);
        return "admin/payments";
    }
}
