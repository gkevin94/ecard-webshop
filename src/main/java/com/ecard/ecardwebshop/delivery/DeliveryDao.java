package com.ecard.ecardwebshop.delivery;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class DeliveryDao {

    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Delivery> DELIVERY_ROW_MAPPER = (rs, rowNum) -> new Delivery(
            rs.getLong("id"),
            rs.getString("address"),
            rs.getLong("user_id")
    );

    private static final RowMapper<Delivery> DELIVERY_ROW_MAPPER_WITH_PAYMENT = (rs, rowNum) -> new Delivery(
            rs.getLong("id"),
            rs.getString("address"),
            rs.getLong("user_id"),
            rs.getString("payment_type_name")
    );

    public DeliveryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long saveDeliveryAndGetId(String userName, Delivery delivery){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO delivery (address, user_id, payment_type_id) " +
                            "VALUES (?,(SELECT id FROM users WHERE user_name = ?), (SELECT id FROM payment_type WHERE name = ?))",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, delivery.getDeliveryAddress());
            ps.setString(2, userName);
            ps.setString(3, delivery.getPaymentType());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Delivery getDeliveryById(long id){
        return jdbcTemplate.queryForObject("SELECT id, address, user_id FROM delivery WHERE id = ?", DELIVERY_ROW_MAPPER, id);
    }

    public List<Delivery> getDeliveries(){
        return jdbcTemplate.query("SELECT id, address, user_id FROM delivery", DELIVERY_ROW_MAPPER);
    }

    public List<Delivery> getDeliveriesByUserId(Authentication authentication){
        return jdbcTemplate.query("SELECT delivery.id as id, address, user_id, payment_type.name as payment_type_name \n" +
                        "FROM delivery \n" +
                        "LEFT JOIN users ON delivery.user_id = users.id \n" +
                        "LEFT JOIN payment_type ON delivery.payment_type_id = payment_type.id\n" +
                        "WHERE users.user_name = ? OR user_id IS NULL",
                DELIVERY_ROW_MAPPER_WITH_PAYMENT, authentication.getName());
    }
}
