package hac.ex4.repo;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(message = "The price should be positive!", value = 1)
    private Double amount;

    @CreationTimestamp
    private Date datetime;

    public Payment() {}

    public Payment(Double amount) {
        this.amount = amount;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setDatetime(Date datetime) { this.datetime = datetime; }

    public long getId() {
        return id;
    }
    public Double getAmount() { return amount; }
    public Date getDatetime() { return datetime; }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + "Amount=" + amount + ", Datetime=" + datetime + '}';
    }
}
