package hac.ex4.controllers;

import hac.ex4.beans.ShoppingCart;
import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import hac.ex4.repo.Payment;
import hac.ex4.repo.PaymentRepository;
import hac.ex4.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * The department is the controller of the store.
 * Performs operations and returns pages for store addresses.
 */
@Controller
public class MainController {
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

    /**
     * Inject via its type the Shopping Cart repo bean.
     */
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Resource(name = "sessionShoppingCart")
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
        sessionShoppingCart.clearShoppingCart();
        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCart/pay")
    public String payGet(Model model) {
        try{
            shoppingCartService.updateStock();
            if(sessionShoppingCart.getTotalSum() == 0){
                throw new IllegalStateException();
            }
            getRepoPayment().save(new Payment(sessionShoppingCart.getTotalSum()));
            return "redirect:/shoppingCart/receipt";
        }
        catch (IllegalArgumentException e){
            model.addAttribute("books", sessionShoppingCart.getShoppingCart());
            model.addAttribute("totalSum", sessionShoppingCart.getTotalSum());
            model.addAttribute("error", "The purchase was canceled because the book \"" + e.getMessage() + "\"" +
                    " is not available in sufficient quantity for your request.");
            return "store/shopping_cart";
        }
        catch (IllegalStateException e){
            model.addAttribute("books", sessionShoppingCart.getShoppingCart());
            model.addAttribute("totalSum", sessionShoppingCart.getTotalSum());
            model.addAttribute("error", "It is not possible to make a purchase for 0 $");
            return "store/shopping_cart";
        }
    }

    @GetMapping("/shoppingCart/receipt")
    public String receiptGet(Model model) {
        model.addAttribute("shoppingCart", sessionShoppingCart.getShoppingCart());
        model.addAttribute("totalSum", sessionShoppingCart.getTotalSum());
        sessionShoppingCart.clearShoppingCart();
        return "store/receipt";
    }

    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }
}
