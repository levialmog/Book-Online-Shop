package hac.ex4.beans;

import hac.ex4.repo.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * This is a bean class instantiated in session.
 * It represents the shopping cart of a single user.
 * Contains a hashtable which holds the type of book and the quantity
 * that the user wants to purchase.
 * The class contains the functionality of the shopping cart.
 */
@Component
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = -3084341050809484186L;

    private Hashtable<Book, Integer> shoppingCart;

    /**
     * The constructor of the class.
     */
    public ShoppingCart() { this.shoppingCart = new Hashtable<>(); }

    /**
     * Returns the shopping cart.
     * @return A hashtable which represents the shopping cart.
     */
    public Hashtable<Book, Integer> getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Sets the shopping cart with a shopping cart received.
     * @param shoppingCart
     */
    public void setShoppingCart(Hashtable<Book, Integer> shoppingCart) {
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

    public void delete(long id) {
        for (Book book : shoppingCart.keySet()) {
            if(book.getId() == id){
                if(shoppingCart.get(book) > 1) {
                    shoppingCart.put(book, shoppingCart.get(book) - 1);
                } else {
                    shoppingCart.remove(book);
                }
                break;
            }
        }
    }

    public Integer getShoppingCartSize() {
        int size = 0;
        for (Book book : shoppingCart.keySet()) {
            size += shoppingCart.get(book);
        }
        return size;
    }

    public void clearShoppingCart() {shoppingCart.clear();}

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ShoppingCart sessionShoppingCart() {
        return new ShoppingCart();
    }
}

