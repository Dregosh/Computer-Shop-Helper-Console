package view;

import controller.OrderController;
import controller.ProductController;
import model.*;

import java.time.LocalDate;
import java.util.Objects;

public class OrderMenuState extends MenuState {
    private final OrderController orderController;
    private final ProductController productController;
    public static final int DISCOUNT_THRESHOLD = 15;

    public OrderMenuState(OrderController orderController,
                          ProductController productController) {
        this.orderController = orderController;
        this.productController = productController;
    }

    @Override
    public void show() {
        System.out.println("\nORDER MENU");
        System.out.println("(1) Show all orders in the DataBase");
        System.out.println("(2) Find orders of a chosen Customer");
        System.out.println("(3) Create new order");
        //other order-related options to be added here
        System.out.println("(0) Return to previous menu");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                showAllOrdersInDBOption();
                break;
            case 2:
                reportNotImplented();
                break;
            case 3:
                createNewOrderOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void createNewOrderOption() {
        Order order = new Order();
        System.out.print("SET ORDER DATE: ");
        order.setOrderDate(defineDate());
        order.setCustomer(defineCustomer());
        order.setStatus(defineStatus());
        System.out.print("SET PAYMENT DATE: ");
        order.setPaymentDate(defineDate());
        showFormatterOrder(order);
        System.out.print("Save order in the DataBase? ");
        if (userConfirms()) {
            orderController.addNewOrderToDB(order);
            reportOperationSuccessful();
        } else {
            reportOperationCancelled();
        }
        if (Objects.nonNull(order.getId())) {
            while (promptUserForOrderItemInsert()) {
                addNewOrderItem(order);
            }
        }
    }

    private LocalDate defineDate() {
        System.out.print("enter the date in format 'yyyy-mm-dd' " +
                         "or leave field blank to use current date: ");
        return requestDateInput(BLANK_INPUT_ALLOWED);
    }

    private Customer defineCustomer() {
        //customer selection logic to be implemented here..
        System.out.println("SET CUSTOMER: <using hardcoded Customer supposedly " +
                           "persisted in DB under the ID #01>");
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setLastName("Agrestecki");
        customer.setFirstName("Sebastian");
        return customer;
    }

    private OrderStatus defineStatus() {
        System.out.print("SET ORDER STATUS: ");
        showEnumTypes(OrderStatus.class);
        int typeNumber = defineLegitEnumTypeNumber(OrderStatus.class, ZERO_NOT_ALLOWED);
        return OrderStatus.values()[typeNumber - 1];
    }

    private boolean promptUserForOrderItemInsert() {
        System.out.print("Add new Item to the current Order? [Y/N] ");
        return "Y".equals(in.nextLine().toUpperCase());
    }

    private void addNewOrderItem(Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        productController.toProductSelectionMenu();
        Product product = productController.getModelProduct();
        if (Objects.isNull(product)) {
            reportOperationCancelled();
            return;
        }
        orderItem.setProduct(product);
        int salesAmount = defineOrderItemSalesAmount(product, BLANK_INPUT_NOT_ALLOWED);
        product.setAmount(product.getAmount() - salesAmount);
        orderItem.setSalesAmount(salesAmount);
        orderItem.setDiscountPercent(defineOrderItemSalesDiscount(
                product, DISCOUNT_THRESHOLD, BLANK_INPUT_NOT_ALLOWED));
        double salesPrice = product.getPrice() *
                            ((double)(100 - orderItem.getDiscountPercent()) / 100);
        System.out.printf("SALES PRICE: %.2f\n", salesPrice);
        orderItem.setSalesPrice(salesPrice);
        showFormattedOrderItem(orderItem);
        System.out.print("Add this Item to the Order and save it in the DataBase? ");
        if (userConfirms()) {
            orderController.addNewOrderItemToDB(orderItem);
            productController.updateProductInDB(product);
            reportOperationSuccessful();
        } else {
            reportOperationCancelled();
        }
    }

    private int defineOrderItemSalesAmount(Product product, boolean allowBlank) {
        boolean legitInput;
        int salesAmount;
        do {
            System.out.print("SALES AMOUNT: enter sales amount (min = 1, " +
                             "max = " + product.getAmount() + "): ");
            salesAmount = (int) requestNumberInput(allowBlank);
            if (salesAmount == BLANK_INPUT_MARKER && allowBlank) {
                return BLANK_INPUT_MARKER;
            }
            legitInput = (salesAmount > 0 && salesAmount <= product.getAmount());
            if (!legitInput) {
                System.out.println("Amount not within given range");
            }
        } while (!legitInput);
        return salesAmount;
    }

    private Integer defineOrderItemSalesDiscount(
            Product product, int discountThreshold, boolean allowBlank) {
        boolean legitInput;
        int salesDiscount;
        System.out.println("DISCOUNT %: enter discount in range [0, " +
                           discountThreshold + "]%  (0 will leave original product " +
                           "price = " + product.getPrice() + "): ");
        do {
            System.out.print("> ");
            salesDiscount = (int) requestNumberInput(allowBlank);
            if (salesDiscount == BLANK_INPUT_MARKER && allowBlank) {
                return BLANK_INPUT_MARKER;
            }
            legitInput = (salesDiscount >= 0 && salesDiscount <= discountThreshold);
            if (!legitInput) {
                System.out.println("Discount not within given range");
            }
        } while (!legitInput);
        return salesDiscount;
    }

    private void showAllOrdersInDBOption() {
        showFormattedOrders(orderController.findAllOrders());
    }

    private void returnToPreviousMenuOption() {
        orderController.returnToPreviousMenu();
    }
}