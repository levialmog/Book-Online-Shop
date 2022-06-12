package hac.ex4.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;

@Component
public class ShoppingCart implements Serializable {
    private ArrayList<String> shoppingCart;

    public ShoppingCart() {
        this.shoppingCart = new ArrayList<>();
    }

    public ArrayList<String>  getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ArrayList<String>  shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void add (String product) {
        shoppingCart.add(product);
    }

    @Bean
    @SessionScope
    public ShoppingCart sessionShoppingCart() {
        return new ShoppingCart();
    }
}
