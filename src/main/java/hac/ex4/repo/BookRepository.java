package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * The interface contains various functions that query and returns information about the book
 * from the database.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findFirst5ByOrderByDiscountDesc();
    List<Book> findByNameContains(String name);
}
