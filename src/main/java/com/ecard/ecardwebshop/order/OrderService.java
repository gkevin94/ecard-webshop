package com.ecard.ecardwebshop.order;

import com.ecard.ecardwebshop.basket.BasketDao;
import com.ecard.ecardwebshop.basket.BasketItem;
import com.ecard.ecardwebshop.delivery.Delivery;
import com.ecard.ecardwebshop.delivery.DeliveryDao;
import com.ecard.ecardwebshop.user.UserDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    private OrderDao orderDao;
    private BasketDao basketDao;
    private UserDao userDao;
    private DeliveryDao deliveryDao;

    public OrderService(OrderDao orderDao, BasketDao basketDao, UserDao userDao, DeliveryDao deliveryDao) {
        this.orderDao = orderDao;
        this.basketDao = basketDao;
        this.userDao = userDao;
        this.deliveryDao = deliveryDao;
    }

    public List<Order> listAllOrders() {
        return orderDao.listAllOrders();
    }

    public List<OrderedProduct> listOrderedProductsByOrderId(long id) {
        return orderDao.listOrderedProductsByOrderId(id);
    }

    public List<OrderedProduct> listAllOrderedProduct() {
        return orderDao.listAllOrderedProduct();
    }

    public long saveOrderAndGetId(Authentication authentication, Delivery delivery) {
        if (authentication == null)
            return 0;
        int basketSize = basketDao.getBasketItems(authentication.getName()).size();
        Order o = new Order(0, userDao.getUserByName(authentication.getName()).get(0).getId(),
                -1, -1, "ACTIVE");
        o.setDelivery(checkIfNewDeliveryAddress(authentication, delivery));
        if (basketSize > 0) {
            long id = orderDao.saveOrderAndGetId(authentication.getName(), o);
            o.setId(id);
            addOrderedProducts(authentication, o);
            basketDao.deleteBasket(authentication.getName());
            return id;
        } else {
            throw new IllegalStateException("The basket is empty");
        }
    }

    public List<Order> listMyOrders(Authentication authentication) {
        if (authentication == null)
            return Collections.emptyList();
        List<Order> orders = orderDao.listMyOrders(authentication.getName());
        for (Order o : orders) {
            if (o.getOrderStatus() == OrderStatus.ACTIVE || o.getOrderStatus() == OrderStatus.SHIPPED) {
                o.setOrderedProducts(orderDao.listOrderedProductsByOrderId(o.getId()));
            }
            try {
                o.setDelivery(orderDao.getDeliveryById(o.getDelivery()));
            } catch (EmptyResultDataAccessException sql) {
                o.setDelivery(orderDao.getDefaultDelivery());
            }
        }
        return orders;
    }

    public void deleteOneItemFromOrder(long orderId, String address) {
        orderDao.deleteOneItemFromOrder(orderId, address);
        if (orderDao.findOrderById(orderId).getSumQuantity() == 0) {
            orderDao.deleteOrder(orderId);
        }
    }

    public void deleteOrder(long id) {
        orderDao.deleteOrder(id);
    }

    public void updateOrderStatus(long id, String status) {
        orderDao.updateOrderStatus(id, status);
    }

    public void updateOrderedProductPiece(OrderedProduct op) {
        orderDao.updateOrderedProductPiece(op);
    }

    private void addOrderedProducts(Authentication authentication, Order order) {
        for (BasketItem bi : basketDao.getBasketItems(authentication.getName())) {
            orderDao.saveOrderedProductAndGetId(
                    new OrderedProduct(
                            bi.getProductId(), order.getId(), bi.getPrice(), bi.getName(), bi.getPieces()));
        }
    }

    private Delivery checkIfNewDeliveryAddress(Authentication authentication, Delivery delivery) {
        List<Delivery> deliveries = deliveryDao.getDeliveriesByUserId(authentication);

        for (Delivery d : deliveries) {
            if (d.getDeliveryAddress().trim().toLowerCase().replaceAll("[\\-,. ]", "").equals(
                    delivery.getDeliveryAddress().trim().toLowerCase().replaceAll("[\\-,. ]", "")
            )) {
                return d;
            }
        }
        deliveryDao.saveDeliveryAndGetId(authentication.getName(), delivery);
        return delivery;
    }

}