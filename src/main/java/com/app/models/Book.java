package com.app.models;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Book {
    @Id
    @NotNull(message = "ISBN is mandatory")
    private Long isbn;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Author is mandatory")
    private String author;
    @NotNull
    private Integer quantity;
    @NotNull(message = "Price is mandatory")
    private Double price;

    public Book() {
    }

    public Book(Long isbn, @NotBlank(message = "Title cannot be empty") String title, @NotBlank(message = "Author cannot be empty") String author, int quantity, @NotNull(message = "Price cannot be empty") double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int qty) {
        this.quantity = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", qty=" + quantity +
                ", price=" + price +
                '}';
    }
}
