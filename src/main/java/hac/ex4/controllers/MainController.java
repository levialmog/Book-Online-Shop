package hac.ex4.controllers;

import hac.ex4.beans.ShoppingCart;
import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Resource(name = "sessionShoppingCart")
    private ShoppingCart sessionShoppingCart;

    @GetMapping("/")
    public String homePageGet(Model model) {
        model.addAttribute("numOfProducts", sessionShoppingCart.getShoppingCartSize());
        model.addAttribute("top5", getRepo().findFirst5ByOrderByDiscountDesc());
        return "store/index";
    }

    @GetMapping("/search{name}")
    public String searchByNameGet(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("top5", getRepo().findByNameIsLike(name));
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
        Book book = getRepo()
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid user Id:" + id)
                );
        sessionShoppingCart.delete(book);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCart/clearCart")
    public String clearCartGet(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/shoppingCart";
    }

    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }
}
