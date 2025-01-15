package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(name = "sequence_generator", sequenceName = "order_sequence",
                       allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus status;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    public Order() {
    }

    public Order(Customer customer, LocalDate orderDate, OrderStatus status,
                 LocalDate paymentDate) {
        this();
        this.setCustomer(customer);
        this.setOrderDate(orderDate);
        this.setStatus(status);
        this.setPaymentDate(paymentDate);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.getOrderItems().add(orderItem);
        orderItem.setOrder(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
