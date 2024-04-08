package com.ecard.ecardwebshop.delivery;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    private DeliveryDao deliveryDao;

    public DeliveryService(DeliveryDao deliveryDao) {
        this.deliveryDao = deliveryDao;
    }

    public List<Delivery> getDeliveriesByUserId(Authentication authentication){
        return deliveryDao.getDeliveriesByUserId(authentication);
    }

    public Delivery getDeliveryById(long id){
        return deliveryDao.getDeliveryById(id);
    }

    public long saveDeliveryAndGetId(Authentication authentication, Delivery delivery){
        return deliveryDao.saveDeliveryAndGetId(authentication.getName(), delivery);
    }
}
