package hac.ex4.services;

import hac.ex4.beans.ShoppingCart;
import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import hac.ex4.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class ShoppingCartService {

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

    @Resource(name = "sessionShoppingCart")
    private ShoppingCart sessionShoppingCart;

    @Transactional
    public void updateStock() {
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
