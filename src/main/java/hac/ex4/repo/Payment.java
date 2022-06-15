package hac.ex4.repo;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

/**
 * The class represents a payment.
 */
@Entity
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(message = "The price should be positive!", value = 1)
    private Double amount;

    @CreationTimestamp
    private Date datetime;

    /**
     * An empty constructor.
     */
    public Payment() {}

    /**
     * A constructor that gets the amount of the payment and sets it.
     * @param amount The amount of the payment.
     */
    public Payment(Double amount) {
        this.amount = amount;
    }

    /**
     * The function sets the ID of the payment with a given ID.
     * @param id The ID of the payment to be set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * The function sets the amount of the payment with a given amount.
     * @param amount The amount of the payment to be set.
     */
    public void setAmount(Double amount) { this.amount = amount; }

    /**
     * The function sets the datetime of the payment with a given datetime.
     * @param datetime The datetime of the payment to be set.
     */
    public void setDatetime(Date datetime) { this.datetime = datetime; }

    /**
     * The function returns the ID of the book.
     * @return The ID of the book.
     */
    public long getId() {
        return id;
    }

    /**
     * The function returns the amount of the book.
     * @return The amount of the book.
     */
    public Double getAmount() { return amount; }

    /**
     * The function returns the datetime of the book.
     * @return The datetime of the book.
     */
    public Date getDatetime() { return datetime; }

    /**
     * The function returns a string that describes the payment.
     * @return A string that describes the payment.
     */
    @Override
    public String toString() {
        return "Payment{" + "id=" + id + "Amount=" + amount + ", Datetime=" + datetime + '}';
    }
}
