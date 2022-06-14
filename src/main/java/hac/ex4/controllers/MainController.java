package hac.ex4.controllers;

import hac.ex4.beans.ShoppingCart;
import hac.ex4.listeners.SessionListenerCounter;
import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import hac.ex4.repo.Payment;
import hac.ex4.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    private BookRepository repository;
    private BookRepository getRepo() {
        return repository;
    }

    @Autowired
    private PaymentRepository paymentRepository;
    private PaymentRepository getRepoPayment() {
        return paymentRepository;
    }

    @Resource(name = "sessionBeanExample")
    private ShoppingCart sessionShoppingCart;

    @GetMapping("/")
    public String homePageGet(Model model) {
        model.addAttribute("numOfProducts", sessionShoppingCart.getShoppingCartSize());
        model.addAttribute("top5", getRepo().findFirst5ByOrderByDiscountDesc());
        return "store/index";
    }

    @GetMapping("/search")
    public String searchByNameGet(@RequestParam("query") String query, Model model) {
        model.addAttribute("searchResults", getRepo().findByNameContains(query));
        return "/store/search_results";
    }

    @GetMapping("/addToCart")
    public String addToCartGet() {
        return "redirect:/";
    }

    @PostMapping("/addToCart")
    public String addToCartPost(@RequestParam("id") long id, Model model) {
        Book book = getRepo()
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid user Id:" + id)
                );
        sessionShoppingCart.add(book);
        return "redirect:/";
    }

    @GetMapping("/shoppingCart")
    public String shoppingCartGet(Model model) {
        model.addAttribute("books", sessionShoppingCart.getShoppingCart());
        model.addAttribute("totalSum", sessionShoppingCart.getTotalSum());
        return "store/shopping_cart";
    }

    @GetMapping("/shoppingCart/delete")
    public String deleteGet(Book book) {
        return "redirect:/";
    }

    @PostMapping("/shoppingCart/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        sessionShoppingCart.delete(id);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCart/clearCart")
    public String clearCartGet(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCart/pay")
    public String payGet(Model model) {
        if(sessionShoppingCart.getTotalSum() != 0) {
            for (Book currBook : sessionShoppingCart.getShoppingCart().keySet()) {
                 Book book = getRepo()
                        .findById(currBook.getId())
                        .orElseThrow(
                                () -> new IllegalArgumentException("Invalid user Id:" + currBook.getId())
                        );
                if (currBook.getQuantity() < sessionShoppingCart.getShoppingCart().get(currBook)) {
                    sessionShoppingCart.getShoppingCart().put(currBook, currBook.getQuantity());
                }
                book.setQuantity(book.getQuantity() - sessionShoppingCart.getShoppingCart().get(currBook));
                getRepo().save(book);
            }
            getRepoPayment().save(new Payment(sessionShoppingCart.getTotalSum()));
        }
        return "redirect:/shoppingCart/receipt";
    }

    @GetMapping("/shoppingCart/receipt")
    public String receiptGet(HttpServletRequest request, Model model) {
        model.addAttribute("shoppingCart", sessionShoppingCart.getShoppingCart());
        model.addAttribute("totalSum", sessionShoppingCart.getTotalSum());
        request.getSession().invalidate();
        return "store/receipt";
    }

    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }
}
