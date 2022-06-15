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
     * @param shoppingCart A hashtable which represents the shopping cart to be set in the class shopping cart.
     */
    public void setShoppingCart(Hashtable<Book, Integer> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * The function gets a book and adds it to the shopping cart.
     * If the book already exits in the shopping cart, it increases the amount of book by 1.
     * If the book does not exist it adds it to the shopping cart with quantity 1.
     * @param book The book to be added to the shopping cart.
     */
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

    /**
     * The function sums all the book prices in the cart according to the quantities,
     * and returns the total amount of money.
     * @return The total amount of money of the shopping cart.
     */
    public Double getTotalSum(){
        double totalSum = 0.0;

        for(Book book : shoppingCart.keySet()){
            totalSum += (book.getPrice() - (book.getPrice() * (book.getDiscount()/100))) * shoppingCart.get(book);
        }
        return totalSum;
    }

    /**
     * The function gets an ID of a book and decreases its quantity by 1.
     * If the quantity of that book was only one then it deletes it from the shopping cart.
     * @param id The id of the book to be deleted.
     */
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

    /**
     * The function returns the amount of books in the shopping cart.
     * @return The amount of books in the shopping cart.
     */
    public Integer getShoppingCartSize() {
        int size = 0;
        for (Book book : shoppingCart.keySet()) {
            size += shoppingCart.get(book);
        }
        return size;
    }

    /**
     * The function clears the shopping cart.
     */
    public void clearShoppingCart() {shoppingCart.clear();}

    /**
     * The bean function that returns the shopping cart in the session.
     * @return The shopping cart in the session.
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ShoppingCart sessionShoppingCart() {
        return new ShoppingCart();
    }
}

