package hac.ex4.services;

import hac.ex4.beans.ShoppingCart;
import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import hac.ex4.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * The class provides service of the shopping cart.
 */
@Service
public class ShoppingCartService {
    /**
     * Inject via its type the Book repo bean.
     */
    @Autowired
    private BookRepository repository;
    private BookRepository getRepo() {
        return repository;
    }

    @Resource(name = "sessionShoppingCart")
    private ShoppingCart sessionShoppingCart;

    /**
     * The function updates the quantities of the books in the database according to the shopping cart.
     * @throws IllegalArgumentException If the new quantity obtained is negative then the setQuantity function will
     * throw an exception which will cause a transaction- i.e. the database will return to its previous state and all
     * changes made since the beginning of the function will be canceled.
     */
    @Transactional
    public void updateStock() throws IllegalArgumentException {
        for (Book book : sessionShoppingCart.getShoppingCart().keySet()) {
            Book stockBook = getRepo()
                    .findById(book.getId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Invalid user Id:" + book.getId())
                    );
            stockBook.setQuantity(stockBook.getQuantity() - sessionShoppingCart.getShoppingCart().get(book));
            getRepo().save(stockBook);
        }
    }
}
