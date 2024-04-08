package com.ecard.ecardwebshop.basket;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class BasketDao {

    private JdbcTemplate jdbcTemplate;

    public BasketDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<BasketItem> BASKETITEM_ROW_MAPPER = ((resultSet, i) -> new BasketItem(
            resultSet.getLong("product_id"),
            resultSet.getLong("basket.id"),
            resultSet.getString("products.name"),
            resultSet.getString("products.address"),
            resultSet.getInt("products.price"),
            resultSet.getInt("pieces")
    ));

    public long saveBasketItemAndGetId(BasketItem basketItem) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO basket(user_id, product_id, pieces) VALUES ((SELECT id FROM users WHERE user_name = ?), (SELECT id FROM products WHERE address = ?), ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, basketItem.getUsername());
            ps.setString(2, basketItem.getAddress());
            ps.setInt(3, basketItem.getPieces());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<BasketItem> getBasketItems(String userName) {
        return jdbcTemplate.query(
                "SELECT product_id, basket.id, products.name, products.address, products.price, pieces FROM basket \n" +
                        "JOIN products ON basket.product_id=products.id \n" +
                        "JOIN users ON basket.user_id=users.id \n" +
                        "WHERE user_name = ?",
                BASKETITEM_ROW_MAPPER,
                userName);
    }

    public BasketItem getBasketItem(BasketItem basketItem) {
        return jdbcTemplate.queryForObject(
                "SELECT product_id, basket.id, products.name, products.address, products.price, pieces FROM basket \n" +
                        "JOIN products ON basket.product_id=products.id \n" +
                        "JOIN users ON basket.user_id=users.id \n" +
                        "WHERE users.user_name = ? and products.address = ?",
                BASKETITEM_ROW_MAPPER,
                basketItem.getUsername(), basketItem.getAddress());
    }

    public void updateBasketItemPieces(BasketItem basketItem) {
        jdbcTemplate.update("UPDATE basket SET pieces = ? WHERE product_id = (SELECT id FROM products WHERE address = ?) AND user_id = (SELECT id FROM users WHERE user_name = ?)",
                basketItem.getPieces(), basketItem.getAddress(), basketItem.getUsername());
    }

    public void deleteBasket(String userName) {
        jdbcTemplate.update("DELETE FROM basket WHERE user_id =(SELECT id FROM users WHERE users.user_name = ?)", userName);
    }

    public void deleteOneItem(String userName, String address) {
        jdbcTemplate.update("DELETE FROM basket WHERE user_id=(SELECT id FROM users WHERE users.user_name = ?) and product_id=(SELECT id FROM products WHERE products.address=?)", userName, address);
    }
}

