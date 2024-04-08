package com.ecard.ecardwebshop.order;

import com.ecard.ecardwebshop.delivery.Delivery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;
    private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, rowNum) -> new Order(
            rs.getLong("orders.id"),
            rs.getTimestamp("purchase_date").toLocalDateTime(),
            rs.getLong("user_id"),
            rs.getLong("total"),
            rs.getLong("sum_quantity"),
            rs.getString("order_status"),
            new Delivery(rs.getLong("delivery_id"), rs.getString("delivery.address"), rs.getLong("user_id"))
    );
    private static final RowMapper<OrderedProduct> ORDERED_PRODUCT_ROW_MAPPER = (rs, rowNum) -> new OrderedProduct(
            rs.getLong("id"),
            rs.getLong("product_id"),
            rs.getLong("order_id"),
            rs.getLong("ordering_price"),
            rs.getString("ordering_name"),
            rs.getInt("pieces")
    );

    private static final RowMapper<Delivery> DELIVERY_ROW_MAPPER = (rs, rowNum) -> new Delivery(
            rs.getLong("id"),
            rs.getString("address"),
            rs.getLong("user_id"),
            rs.getString("payment_type_name")
    );

    private static final String SQL_SELECT_ORDER = "SELECT orders.id, purchase_date, orders.user_id, " +
            "sum(pieces*ordering_price) AS total, sum(pieces) AS sum_quantity, order_status, delivery_id, delivery.address " +
            "FROM orders JOIN ordered_products ON orders.id = order_id LEFT JOIN delivery ON orders.delivery_id = delivery.id ";

    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long saveOrderAndGetId(String userName, Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO orders (purchase_date, user_id, delivery_id) " +
                            "VALUES (?,(SELECT id FROM users WHERE user_name = ?), (SELECT id FROM delivery WHERE address = ? LIMIT 1))",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(order.getPurchaseDate()));
            ps.setString(2, userName);
            ps.setString(3, order.getDelivery().getDeliveryAddress());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Order findOrderById(long id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_ORDER +
                        "WHERE orders.id = ?",
                ORDER_ROW_MAPPER, id);
    }

    public List<Order> listMyOrders(String username) {
        return jdbcTemplate.query(SQL_SELECT_ORDER +
                        "WHERE orders.user_id = (SELECT id FROM users WHERE user_name = ?) GROUP BY orders.id ORDER BY purchase_date DESC",
                ORDER_ROW_MAPPER, username);
    }

    public List<Order> listAllOrders() {
        return jdbcTemplate.query(SQL_SELECT_ORDER +
                        "GROUP BY order_id, purchase_date, orders.user_id, delivery_id, order_status, delivery.address ORDER BY purchase_date DESC",
                ORDER_ROW_MAPPER);
    }

    public List<Order> listAllActiveOrders() {
        return jdbcTemplate.query(SQL_SELECT_ORDER +
                        "WHERE order_status = 'ACTIVE' GROUP BY order_id, purchase_date, orders.user_id, delivery_id, order_status, delivery.address ORDER BY purchase_date DESC",
                ORDER_ROW_MAPPER);
    }

    public long saveOrderedProductAndGetId(OrderedProduct orderedProduct) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ordered_products " +
                            "(product_id, order_id, ordering_price, ordering_name, pieces) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, orderedProduct.getProductId());
            ps.setLong(2, orderedProduct.getOrderId());
            ps.setLong(3, orderedProduct.getOrderingPrice());
            ps.setString(4, orderedProduct.getOrderingName());
            ps.setInt(5, orderedProduct.getPieces());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<OrderedProduct> listOrderedProductsByOrderId(long id) {
        return jdbcTemplate.query("SELECT ordered_products.id, product_id, order_id, ordering_price, ordering_name, pieces " +
                        "FROM ordered_products JOIN products ON product_id=products.id WHERE order_id =?",
                ORDERED_PRODUCT_ROW_MAPPER, id);
    }

    public List<OrderedProduct> listAllOrderedProduct() {
        return jdbcTemplate.query("SELECT ordered_products.id, product_id, order_id, ordering_price, ordering_name, pieces " +
                        "FROM ordered_products JOIN products ON product_id=products.id",
                ORDERED_PRODUCT_ROW_MAPPER);
    }

    public void deleteOneItemFromOrder(long orderId, String address) {
        jdbcTemplate.update("DELETE ordered_products FROM ordered_products INNER JOIN products ON product_id = products.id " +
                "WHERE products.address = ? AND ordered_products.order_id = ?", address, orderId);
    }

    public void deleteOrder(long id) {
        jdbcTemplate.update("UPDATE orders SET order_status = 'DELETED' WHERE id = ?", id);
    }

    public void updateOrderStatus(long id, String status) {
        jdbcTemplate.update("UPDATE orders SET order_status = ? WHERE id = ?", status, id);
    }

    public void updateOrderedProductPiece(OrderedProduct op) {
        jdbcTemplate.update("UPDATE ordered_products SET pieces = ? WHERE product_id = ?", op.getPieces(), op.getProductId());
    }

    public Delivery getDeliveryById(Delivery delivery) {
        return jdbcTemplate.queryForObject("SELECT delivery.id, delivery.address, delivery.user_id, payment_type.name AS payment_type_name \n" +
                "FROM delivery \n" +
                "LEFT JOIN payment_type ON delivery.payment_type_id = payment_type.id \n" +
                "WHERE delivery.id = ?", DELIVERY_ROW_MAPPER, delivery.getDeliveryId());
    }

    public Delivery getDefaultDelivery() {
        return jdbcTemplate.queryForObject("SELECT delivery.id, delivery.address, delivery.user_id, payment_type.name AS payment_type_name \n" +
                "FROM delivery \n" +
                "LEFT JOIN payment_type ON delivery.payment_type_id = payment_type.id \n" +
                "WHERE delivery.id = 1", DELIVERY_ROW_MAPPER);
    }
}
