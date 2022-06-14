package hac.ex4.beans;

import hac.ex4.repo.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.io.Serializable;
import java.util.HashMap;

@Component
public class ShoppingCart implements Serializable {
    private HashMap<Book, Integer> shoppingCart;

    public ShoppingCart() { this.shoppingCart = new HashMap<Book, Integer>(); }

    public HashMap<Book, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(HashMap<Book, Integer> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void add(Book book) {
        boolean isFound = false;

        for (Book currBook : shoppingCart.keySet()) {
            if(currBook.getId() == book.getId()){
                shoppingCart.put(currBook, shoppingCart.get(currBook) + 1);
                isFound = true;
                break;
            }
        }
        if(!isFound){
            shoppingCart.put(book, 1);
        }
    }

    public Double getTotalSum(){
        double totalSum = 0.0;

        for(Book book : shoppingCart.keySet()){
            totalSum += (book.getPrice() - (book.getPrice() * (book.getDiscount()/100))) * shoppingCart.get(book);
        }
        return totalSum;
    }

    public void delete(Book book) {
        if(shoppingCart.get(book) > 1) {
            shoppingCart.put(book, shoppingCart.get(book) - 1);
        } else {
            shoppingCart.remove(book);
        }
    }

    public Integer getShoppingCartSize() {
        int size = 0;
        for (Book book : shoppingCart.keySet()) {
            size += shoppingCart.get(book);
        }
        return size;
    }

    @Bean
    @SessionScope
    public ShoppingCart sessionShoppingCart() {
        return new ShoppingCart();
    }
}

