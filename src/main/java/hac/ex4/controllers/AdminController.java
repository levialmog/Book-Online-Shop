package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private BookRepository repository;
    private BookRepository getRepo() {
        return repository;
    }

    @GetMapping("")
    public String main(Model model) {
        model.addAttribute("books", getRepo().findAll());
        return "admin/admin";
    }

    @GetMapping("/addBook")
    public String addBook(Book book, Model model) {
        model.addAttribute("action", "/admin/add");
        model.addAttribute("title", "Add Book");
        return "admin/book_form";
    }

    @GetMapping("/add")
    public String addGet(Book book) {
        return "redirect:/admin";
    }

    @PostMapping("/add")
    public String add(@Valid Book book, BindingResult result, Model model) {
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
    public String deleteBook(@RequestParam("id") long id, Model model) {
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
    public String updateBook(@RequestParam("id") long id, Model model) {
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
    public String update(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
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
}
