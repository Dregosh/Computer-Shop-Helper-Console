package service;

import model.Customer;
import model.Order;
import model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {

    public void saveOrUpdateOrder(Order order, Map<Long, Integer> deltaMap) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(order);
            deltaMap.forEach((key, value) -> {
                Product product = session.find(Product.class, key);
                product.setAmount(product.getAmount() + value);
            });
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Order> findAllOrdersWithOrderItems() {
        List<Order> orders = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Order> query = session.createQuery(
                    "SELECT o FROM orders o JOIN FETCH o.orderItems", Order.class);
            orders = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> findByCustomerWithOrderItems(Customer customer) {
        List<Order> orders = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Order> query = session
                    .createQuery("SELECT o FROM orders o JOIN FETCH o.orderItems" +
                                 " WHERE o.customer = :customer", Order.class)
                    .setParameter("customer", customer);
            orders = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order findOrderByIdWithOrderItems(long id) {
        Order order = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Order> query = session
                    .createQuery("SELECT o FROM orders o JOIN FETCH o.orderItems" +
                                 " WHERE o.id = :id", Order.class)
                    .setParameter("id", id);
            order = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("An Order under the given ID could not be found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }
}
