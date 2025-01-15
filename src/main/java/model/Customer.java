package model;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "customers")
//@EntityListeners({CustomerListener.class})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "customer_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "last_name")
//    @NotNull
    private String lastName;

    @Column(name = "first_name")
//    @NotNull
    private String firstName;

    @Column(name = "address_street")
//    @NotNull
    private String addressStreet;

    @Column(name = "address_postal_code")
//    @NotNull
    private String addressPostalCode;

    @Column(name = "address_city")
//    @NotNull
    private String addressCity;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Column(name = "removed")
    private boolean isRemoved;

    public Customer() {
    }

    public Customer(String lastName, String firstName, String street, String code,
                    String city) {
        this();
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setAddressStreet(street);
        this.setAddressPostalCode(code);
        this.setAddressCity(city);
        this.setRemoved(false);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
