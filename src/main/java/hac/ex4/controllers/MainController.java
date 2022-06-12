package hac.ex4.controllers;

import hac.ex4.beans.ShoppingCart;
import hac.ex4.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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
        model.addAttribute("top5", getRepo().findFirst5ByOrderByDiscountDesc());
        return "store/index";
    }

    @GetMapping("/search{name}")
    public String searchByNameGet(@PathVariable("name") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("top5", getRepo().findByNameIsLike(name));
        return "/store/search_results";
    }

    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }
}
