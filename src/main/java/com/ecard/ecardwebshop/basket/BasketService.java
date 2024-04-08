package com.ecard.ecardwebshop.basket;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

    private BasketDao basketDao;

    public BasketService(BasketDao basketDao) {
        this.basketDao = basketDao;
    }

    public long saveBasketItemAndGetId(String address, Authentication authentication, BasketItem basketItem) {
        basketItem.setUsername(authentication.getName());
        basketItem.setAddress(address);
        try {
            int piecesInBasket = basketDao.getBasketItem(basketItem).getPieces();
            basketItem.setPieces(basketItem.getPieces() + piecesInBasket);
            basketDao.updateBasketItemPieces(basketItem);
            return basketItem.getBasketId();
        } catch (EmptyResultDataAccessException noResult) {
            return basketDao.saveBasketItemAndGetId(basketItem);
        }
    }

    public void updateBasketItemPieces(Authentication authentication, BasketItem basketItem) {
        basketItem.setUsername(authentication.getName());
        basketDao.updateBasketItemPieces(basketItem);
    }

    public List<BasketItem> getBasketItems(Authentication authentication) {
        return basketDao.getBasketItems(authentication.getName());
    }

    public void deleteBasket(Authentication authentication) {
        basketDao.deleteBasket(authentication.getName());
    }

    public void deleteOneItem(Authentication authentication, String address) {
        basketDao.deleteOneItem(authentication.getName(), address);
    }
}
