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

/**
 * The class represents a book.
 */
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

    /**
     * An empty constructor.
     */
    public Book() {}


    /**
     * A constructor of the class that gets a various variables of the book.
     * @param name The name of the book - String.
     * @param image An url of the image of the book - String.
     * @param quantity The quantity of the book in the store stock - Integer.
     * @param price The full price of the book - Double.
     * @param discount The discount on the book price.
     */
    public Book(String name, String image, Integer quantity, Double price, Double discount) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    /**
     * The function sets the ID of the book with a given ID.
     * @param id The ID of the book to be set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * The function sets the name of the book with a given name.
     * @param name The name of the book to be set.
     */
    public void setName(String name) {
        this.name = name.trim();
    }

    /**
     * The function sets the image URL of the book with a given image URL.
     * @param image The image URL of the book to be set.
     */
    public void setImage(String image) {
        this.image = image.trim();
    }

    /**
     * The function sets the quantity of the book with a given quantity.
     * @param quantity The quantity of the book to be set.
     */
    public void setQuantity(Integer quantity) {
        if(quantity < 0){
            throw new IllegalArgumentException(name);
        }
        this.quantity = quantity;
    }

    /**
     * The function sets the price of the book with a given price.
     * @param price The price of the book to be set.
     */
    public void setPrice(Double price) {this.price = price;}

    /**
     * The function sets the discount of the book with a given discount.
     * @param discount The discount of the book to be set.
     */
    public void setDiscount(Double discount) {this.discount = discount;}

    /**
     * The function returns the ID of the book.
     * @return The ID of the book.
     */
    public long getId() {
        return id;
    }

    /**
     * The function returns the name of the book.
     * @return The name of the book.
     */
    public String getName() {
        return name;
    }

    /**
     * The function returns the image URL of the book.
     * @return The image URL of the book.
     */
    public String getImage() {
        return image;
    }

    /**
     * The function returns the quantity of the book.
     * @return The quantity of the book.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * The function returns the price of the book.
     * @return The price of the book.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * The function returns the discount of the book.
     * @return The discount of the book.
     */
    public Double getDiscount() {
        return discount;
    }

    /**
     * The function returns a string that describes the book.
     * @return A string that describes the book.
     */
    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", Name=" + name + ", Image=" + image +
                ", Quantity=" + quantity + ", Price=" + price + ", Discount=" + discount + '}';
    }
}
