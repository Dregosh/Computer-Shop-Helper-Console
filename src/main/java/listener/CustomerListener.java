package listener;

import model.Customer;
import model.Order;
import util.HibernateUtil;

import jakarta.persistence.PreRemove;
import java.util.List;

public class CustomerListener {

    /*@PreRemove
    void setToDummyCustomer(Object o) {
        Customer customer = (Customer) o;
        List<Order> orders = customer.getOrders();
        if (orders.size() > 0) {
            Customer dummyCustomer = HibernateUtil.getSession().find(Customer.class, 1L);
            for (Order order : orders) {
                order.setCustomer(dummyCustomer);
            }
        }
    }*/
}