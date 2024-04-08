package com.ecard.ecardwebshop;

import com.ecard.ecardwebshop.basket.BasketController;
import com.ecard.ecardwebshop.basket.BasketDao;
import com.ecard.ecardwebshop.basket.BasketItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/init.sql")
public class BasketTests {

    @Autowired
    private BasketController basketController;
    @Autowired
    private BasketDao basketDao;

    @Test
    public void testGetBasketItems() {
        // When
        List<BasketItem> basketItems = basketController.getBasketItems(new TestingAuthenticationToken("user", "user", "ROLE_USER"));
        // Then
        assertEquals(2, basketController.getBasketItems(new TestingAuthenticationToken("user", "user", "ROLE_USER")).size());
    }

    @Test
    public void testDeleteBasket() {

        // When
        basketController.deleteBasket(new TestingAuthenticationToken("user", "user", "ROLE_USER"));

        List<BasketItem> basketItems = basketController.getBasketItems(new TestingAuthenticationToken("user", "user", "ROLE_USER"));
        // Then
        assertEquals(0, basketController.getBasketItems(new TestingAuthenticationToken("user", "user", "ROLE_USER")).size());
    }

    @Test
    public void testDeleteOneItem() {
        // Given
        List<BasketItem> basketItemsBeforeDelete = basketController.getBasketItems(new TestingAuthenticationToken("user", "user", "ROLE_USER"));
        assertEquals(2, basketItemsBeforeDelete.size());
        long productIdNumberOneDominion = basketItemsBeforeDelete.stream().filter(p -> p.getAddress().equals("dominion")).findFirst().get().getProductId();
        long productIdNumberTwoMagicBooster = basketItemsBeforeDelete.stream().filter(p -> p.getAddress().equals("magic_booster")).findFirst().get().getProductId();

        assertEquals(5, productIdNumberOneDominion);
        assertEquals(3,productIdNumberTwoMagicBooster);

        // When
        basketController.deleteOneItem(new TestingAuthenticationToken("user", "user", "ROLE_USER"), "magic_booster");
        List<BasketItem> basketItemsAfterDelete = basketController.getBasketItems(new TestingAuthenticationToken("user", "user", "ROLE_USER"));

        // Then
        assertEquals(1, basketItemsAfterDelete.size());
        assertEquals("dominion", basketItemsAfterDelete.get(0).getAddress());
    }


    @Test
    public void testSaveBasketItemAndGetId() {
        BasketItem basketItemExample = new BasketItem(8, 0, "Yu-Gi-Oh! Starter Deck", "yugioh_starter_deck", 4500, 2);
        // When
        long anotherNewId = basketController.saveBasketItemAndGetId("yugioh_starter_deck", basketItemExample, new TestingAuthenticationToken("user", "user", "ROLE_USER"));
        List<BasketItem> list = basketController.getBasketItems(new TestingAuthenticationToken("user", "user", "ROLE_USER"));
        // Then
        assertEquals(3, list.size());
        assertTrue(list.stream().map(BasketItem::getAddress).collect(Collectors.toList()).contains("dominion"));
        assertTrue(list.stream().map(BasketItem::getAddress).collect(Collectors.toList()).contains("magic_booster"));
        assertTrue(list.stream().map(BasketItem::getAddress).collect(Collectors.toList()).contains("yugioh_starter_deck"));

    }

    @Test
    public void testUpdateBasketItemPieces() {
        List<BasketItem> basketItems = basketController.getBasketItems(new TestingAuthenticationToken("user", "user", "ROLE_USER"));

        assertEquals(2, basketController.getBasketItems(new TestingAuthenticationToken("user", "user", "ROLE_USER")).size());

        BasketItem existingBasketItemExample = basketItems.get(0);

        assertTrue(existingBasketItemExample != null);

        int piecesBeforeIncreasingTheAmountByOne = existingBasketItemExample.getPieces();
        existingBasketItemExample.setPieces(piecesBeforeIncreasingTheAmountByOne + 1);
        basketController.updateBasketItemPieces(existingBasketItemExample, (new TestingAuthenticationToken("user", "user", "ROLE_USER")));
        int piecesAfterIncreasingTheAmountByOne = existingBasketItemExample.getPieces();
        assertTrue(piecesBeforeIncreasingTheAmountByOne+1 == piecesAfterIncreasingTheAmountByOne);
    }


}

