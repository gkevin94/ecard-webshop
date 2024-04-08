package com.ecard.ecardwebshop;

import com.ecard.ecardwebshop.delivery.Delivery;
import com.ecard.ecardwebshop.delivery.DeliveryController;
import com.ecard.ecardwebshop.delivery.DeliveryDao;
import com.ecard.ecardwebshop.delivery.DeliveryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")

public class DeliveryTest {

    @Autowired
    private DeliveryDao deliveryDao;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliveryController deliveryController;

    @Test
    public void deliveryCreateTest() {

        Delivery delivery = new Delivery(0, "Szeged Szeva u. 11.", 4);

        assertEquals("Szeged Szeva u. 11.", delivery.getDeliveryAddress());
        assertEquals(4, delivery.getUserId());

    }

    @Test
    public void setDeliveryCreateTest() {

        Delivery delivery = new Delivery(0, "Szeged Szeva u. 11.", 4);

        delivery.setDeliveryId(1);
        assertEquals(1, delivery.getDeliveryId());

        delivery.setDeliveryAddress("Szolnok Szeva u. 12");
        assertEquals("Szolnok Szeva u. 12", delivery.getDeliveryAddress());

        delivery.setUserId(5);
        assertEquals(5, delivery.getUserId());

    }


    @Test
    public void listDeliveriesTest() {

        List<Delivery> deliveries = deliveryDao.getDeliveries();

        assertEquals(1, deliveries.size());
    }

    @Test
    public void addNewDeliveryTest() {

        Delivery delivery = new Delivery(0, "Szeged Szeva u. 11.", 4);


        long id = deliveryDao.saveDeliveryAndGetId("user", delivery);

        List<Delivery> deliveries = deliveryDao.getDeliveries();

        assertEquals(deliveries.get(deliveries.size() - 1).getDeliveryId(), id);
        assertEquals("Szeged Szeva u. 11.", deliveries.get(deliveries.size() - 1).getDeliveryAddress());

    }

    @Test
    public void getDeliveryByIdControllerTest() {

        Delivery delivery = new Delivery(0, "Szeged Szeva u. 11.", 4);

        long id = deliveryDao.saveDeliveryAndGetId("user", delivery);

        Delivery result = deliveryController.getDeliveryById(id);

        assertEquals("Szeged Szeva u. 11.", result.getDeliveryAddress());
    }

    @Test

    public void getDeliveriesByUserIdControllerTest() {

        TestingAuthenticationToken tat = new TestingAuthenticationToken("user", "user");

        Delivery delivery = new Delivery(0, "Szeged Szeva u. 11.", 4);

        deliveryDao.saveDeliveryAndGetId("user", delivery);

        List<Delivery> deliveries = deliveryController.getDeliveriesByUserId(tat);

        assertEquals(2, deliveries.size());
    }
}
