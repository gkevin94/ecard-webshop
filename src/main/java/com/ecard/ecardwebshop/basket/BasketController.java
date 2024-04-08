package com.ecard.ecardwebshop.basket;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BasketController {

    private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/basket/{address}")
    public long saveBasketItemAndGetId(@PathVariable String address, @RequestBody BasketItem basketItem, Authentication authentication) {
        return authentication == null ? -1 : basketService.saveBasketItemAndGetId(address, authentication, basketItem);
    }

    @GetMapping("/basket")
    public List<BasketItem> getBasketItems(Authentication authentication) {
        return authentication == null ? null : basketService.getBasketItems(authentication);
    }

    @DeleteMapping("/basket")
    public void deleteBasket(Authentication authentication) {
        if (authentication != null)
            basketService.deleteBasket(authentication);
    }


    @DeleteMapping("/basket/{address}")
    public void deleteOneItem(Authentication authentication, @PathVariable String address) {
        if (authentication != null)
            basketService.deleteOneItem(authentication, address);
    }

    @PostMapping("/basket")
    public void updateBasketItemPieces(@RequestBody BasketItem basketItem, Authentication authentication) {
        if (authentication != null)
            basketService.updateBasketItemPieces(authentication, basketItem);
    }
}
