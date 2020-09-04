package model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "product_sequence",
                       allocationSize = 1)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductType type;

    @Column
    private Double price;

    @Column
    @NotNull
    private Integer amount;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Product() {
    }

    public Product(String name, ProductType type, Double price, int amount) {
        this();
        this.setName(name);
        this.setType(type);
        this.setPrice(price);
        this.setAmount(amount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}