package com.ecard.ecardwebshop.report;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportDao {


    private static final RowMapper<OrderReport> ORDER_ROW_MAPPER = (rs, rowNum) -> new OrderReport(
            rs.getInt("year"),
            rs.getInt("month"),
            rs.getString("order_status"),
            rs.getInt("total"),
            rs.getInt("count")
    );
    private static final RowMapper<ShippedProductReport> PRODUCT_ROW_MAPPER = (rs, rowNum) -> new ShippedProductReport(
            rs.getInt("year"),
            rs.getInt("month"),
            rs.getString("productname"),
            rs.getInt("price"),
            rs.getInt("count"),
            rs.getInt("total")
    );
    private JdbcTemplate jdbcTemplate;

    public ReportDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<OrderReport> getMonthlyIncomeOfOrders() {
        return jdbcTemplate.query(
                        "SELECT year(purchase_date) as year, month(purchase_date) as month, order_status, sum(pieces*ordering_price) AS total, count(*) AS count " +
                        "FROM orders JOIN ordered_products ON orders.id = order_id WHERE order_status <> 'DELETED' " +
                        "GROUP BY year(purchase_date), month(purchase_date), order_status ORDER BY orders.order_status, year, month ASC", ORDER_ROW_MAPPER);
    }

    public List<ShippedProductReport> getShippedProducts() {
        return jdbcTemplate.query("SELECT year(purchase_date) as year, month(purchase_date) as month," +
                "ordered_products.ordering_name as productname, products.price as price, count(*) as count," +
                "sum(ordering_price) as total FROM orders LEFT JOIN ordered_products ON ordered_products.order_id = orders.id " +
                "LEFT JOIN products ON products.id = ordered_products.product_id WHERE orders.order_status = 'SHIPPED' GROUP BY YEAR(purchase_date)," +
                "month(purchase_date), ordered_products.ordering_name, products.price", PRODUCT_ROW_MAPPER);
    }
}

