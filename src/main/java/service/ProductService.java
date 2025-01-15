package service;

import model.Product;
import model.ProductType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private Transaction transaction = null;

    public void saveProduct(Product product) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(product);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = session.find(Product.class, id);
            session.delete(product);
            transaction.commit();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product findProductById(Long id) {
        Product product = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            product = session.find(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> findProductsByNameFragment(String nameFragment) {
        List<Product> products = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.where(builder.like(root.get("name"), "%" + nameFragment + "%"))
                 .orderBy(builder.asc(root.get("type")), builder.asc(root.get("name")));
            products = session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> findProductsByType(ProductType type) {
        List<Product> products = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.where(builder.equal(root.get("type"), type))
                 .orderBy(builder.asc(root.get("type")), builder.asc(root.get("name")));
            products = session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product findProductByNameAndType(Product product) {
        Product result = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery =
                    criteriaBuilder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            Predicate[] predicates = new Predicate[2];
            predicates[0] = criteriaBuilder.equal(root.get("name"), product.getName());
            predicates[1] = criteriaBuilder.equal(root.get("type"), product.getType());
            criteriaQuery.select(root).where(predicates);
            try {
                Query<Product> query = session.createQuery(criteriaQuery);
                result = query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.orderBy(builder.asc(root.get("type")), builder.asc(root.get("name")));
            products = session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public void addInitialProductsToDB() {
        List<Product> products = List.of(
                new Product("Core i7-6700k", ProductType.CPU, 1240.00, 3),
                new Product("Core i7-9700k", ProductType.CPU, 1460.00, 1),
                new Product("BenQ BL2410", ProductType.DISPLAY, 560.00, 2),
                new Product("Gateway FPD2485W", ProductType.DISPLAY, 560.00, 2),
                new Product("Samsung-SSD 512GB", ProductType.DISC_DRIVE, 250.00, 3),
                new Product("OCZ-SSD 128GB", ProductType.DISC_DRIVE, 150.00, 2),
                new Product("GeForce GTX1070 OC", ProductType.GPU, 1490.00, 1),
                new Product("GeForce RTX2080", ProductType.GPU, 3450.00, 4),
                new Product("HyperX 16GB PC3200", ProductType.RAM, 425.00, 3),
                new Product("Viper 16GB PC3600", ProductType.RAM, 325.00, 4),
                new Product("Razer Naga Mouse", ProductType.ACCESSORY, 149.00, 3),
                new Product("Xbox One Controller", ProductType.ACCESSORY, 125.00, 2)
        );
        for (Product p : products) {
            saveProduct(p);
        }
    }
}
