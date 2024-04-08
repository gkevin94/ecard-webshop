package com.ecard.ecardwebshop.delivery;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeliveryController {

    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/delivery")
    public List<Delivery> getDeliveriesByUserId(Authentication authentication){
        return deliveryService.getDeliveriesByUserId(authentication);
    }

    @GetMapping("/delivery/{id}")
    public Delivery getDeliveryById(@PathVariable long id){
        return deliveryService.getDeliveryById(id);
    }

    @PostMapping("/delivery")
    public int saveDeliveryAndGetId(Authentication authentication, @RequestBody Delivery delivery){
        try {
            deliveryService.saveDeliveryAndGetId(authentication, delivery);
            return 200;
        } catch (IllegalStateException | IllegalArgumentException e) {
        return 400;
    }
    }
}
