package com.ecard.ecardwebshop.order;

import com.ecard.ecardwebshop.delivery.Delivery;
import com.ecard.ecardwebshop.product.ProductService;
import com.ecard.ecardwebshop.product.ResultStatus;
import com.ecard.ecardwebshop.product.ResultStatusEnum;
import com.ecard.ecardwebshop.user.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private OrderService orderService;
    private OrderValidator validator;

    public OrderController(OrderService orderService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.validator = new OrderValidator(orderService, userService, productService);
    }

    @PostMapping("/myorders")
    public ResultStatus saveOrderAndGetId(Authentication authentication, @RequestBody Delivery delivery) {
        try {
            long id = orderService.saveOrderAndGetId(authentication, delivery);
            return new ResultStatus(ResultStatusEnum.OK, String.format("Order successfully created with id %d", id));
        } catch (IllegalStateException | IllegalArgumentException e) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, e.getMessage());
        }
    }

    @GetMapping("/myorders")
    public List<Order> listMyOrders(Authentication authentication) {
        return orderService.listMyOrders(authentication);
    }

    @GetMapping("/orders")
    public List<Order> listAllOrders() {
        return orderService.listAllOrders();
    }

    @GetMapping("/orders/{id}")
    public List<OrderedProduct> listOrderedProductsByOrderId(@PathVariable long id) {
        return orderService.listOrderedProductsByOrderId(id);
    }

    @PostMapping("/orders/{id}")
    public ResultStatus deleteOrder(@PathVariable long id) {
        if (validator.isValidOrderId(id)) {
            orderService.deleteOrder(id);
            return new ResultStatus(ResultStatusEnum.OK, String.format("Order successfully deleted with id %d", id));
        } else {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Invalid id");
        }
    }

    @DeleteMapping("/orders/{id}/{address}")
    public ResultStatus deleteOneItemFromOrder(@PathVariable long id, @PathVariable String address) {
        try {
            if (validator.isExistingOrderId(id) && validator.isExistingProductAddress(address)) {
                orderService.deleteOneItemFromOrder(id, address);
                return new ResultStatus(ResultStatusEnum.OK, "Ordered product deleted successfully");
            } else {
                return new ResultStatus(ResultStatusEnum.NOT_OK, "Invalid id, or address");
            }
        } catch (DataAccessException sql) {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Invalid id, or address");
        }
    }

    @PostMapping("/orders/{id}/{status}")
    public ResultStatus updateOrderStatus(@PathVariable long id, @PathVariable String status) {
        if (validator.isValidStatus(status.toUpperCase()) && validator.isValidOrderId(id)) {
            orderService.updateOrderStatus(id, status.toUpperCase());
            return new ResultStatus(ResultStatusEnum.OK, String.format("Order status successfully updated with id %d", id));
        } else {
            return new ResultStatus(ResultStatusEnum.NOT_OK, "Invalid id or status");
        }
    }

    @PostMapping("/orders/piece")
    public void updateOrderedProductPiece(@RequestBody OrderedProduct op) {
        orderService.updateOrderedProductPiece(op);
    }


}
