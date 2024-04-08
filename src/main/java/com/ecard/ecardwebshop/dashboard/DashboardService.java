package com.ecard.ecardwebshop.dashboard;

import com.ecard.ecardwebshop.order.OrderDao;
import com.ecard.ecardwebshop.product.ProductDao;
import com.ecard.ecardwebshop.user.UserDao;
import org.springframework.stereotype.Service;


@Service
public class DashboardService {
    private UserDao userDao;
    private ProductDao productDao;
    private OrderDao orderDao;

    public DashboardService(UserDao userDao, ProductDao productDao, OrderDao orderDao) {
        this.userDao = userDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
    }

    public Dashboard listOfResult(){
        return new Dashboard(userDao.listUsers().size(),
                productDao.getAllProducts().size(), productDao.getProducts().size(),
                orderDao.listAllOrders().size(), orderDao.listAllActiveOrders().size());
    }
}
