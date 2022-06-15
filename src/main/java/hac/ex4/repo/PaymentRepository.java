package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * The interface contains various functions that query and returns information about the payment
 * from the database.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrderByDatetime();
}
