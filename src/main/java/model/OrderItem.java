package model;

import jakarta.persistence.*;

@Entity(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator",
                       sequenceName = "order_item_sequence",
                       allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "sales_amount")
    private Integer salesAmount;

    @Column(name = "sales_price")
    private Double salesPrice;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    public OrderItem() {
    }

    public OrderItem(Product product, Integer salesAmount, Integer discountPercent) {
        this.product = product;
        this.salesAmount = salesAmount;
        this.salesPrice = null;
        this.discountPercent = discountPercent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Integer salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }
}
