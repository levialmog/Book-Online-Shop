package hac.ex4.repo;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Length(message = "Image url length should be a maximum of 255 characters!", max = 255)
    private String image;

    @NotNull(message = "Quantity is mandatory")
    @Min(message = "The quantity should be non-negative!", value = 0)
    private Integer quantity;

    @NotNull(message = "Price is mandatory")
    @Min(message = "The price should be positive!", value = 1)
    private Double price;

    @NotNull(message = "Discount is mandatory")
    @Min(message = "The discount should be non-negative!", value = 0)
    private Double discount;

    public Book() {}

    public Book(String name, String image, Integer quantity, Double price, Double discount) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name.trim();
    }
    public void setImage(String image) {
        this.image = image.trim();
    }
    public void setQuantity(Integer quantity) {
        if(quantity < 0){
            throw new IllegalArgumentException(name);
        }
        this.quantity = quantity;
    }
    public void setPrice(Double price) {this.price = price;}
    public void setDiscount(Double discount) {this.discount = discount;}

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Double getPrice() {
        return price;
    }
    public Double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", Name=" + name + ", Image=" + image +
                ", Quantity=" + quantity + ", Price=" + price + ", Discount=" + discount + '}';
    }
}
