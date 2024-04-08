package com.ecard.ecardwebshop.feedback;

import com.ecard.ecardwebshop.product.ProductDao;
import com.ecard.ecardwebshop.user.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackDao {

    private JdbcTemplate jdbcTemplate;

    private UserDao userDao;

    private ProductDao productDao;

    private final RowMapper<Feedback> FEEDBACK_ROW_MAPPER = ((rs, i) -> new Feedback(
            rs.getLong("id"),
            rs.getTimestamp("feedback_date").toLocalDateTime(),
            rs.getString("feedback"),
            rs.getInt("rating"),
            userDao.getUserById(rs.getLong("user_id")),
            productDao.getProductById(rs.getLong("product_id"))
    ));

    public FeedbackDao(JdbcTemplate jdbcTemplate, UserDao userDao, ProductDao productDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    public List<Feedback> listFeedBacksByProductId(long productId) {
        return jdbcTemplate.query("SELECT id, feedback_date, feedback, rating, user_id, product_id FROM feedback WHERE product_id = ? ORDER BY feedback_date DESC",
                FEEDBACK_ROW_MAPPER, productId);
    }

    public void giveAFeedback(Feedback feedback) {
        jdbcTemplate.update("INSERT INTO feedback(feedback_date, feedback, rating, user_id, product_id)"
                + "VALUES (?,?,?,?,?)", feedback.getFeedbackDate(), feedback.getFeedback(), feedback.getRating(), feedback.getUser().getId(), feedback.getProduct().getId());
    }

    public void deleteFeedbackById(long id) {
        jdbcTemplate.update("DELETE FROM feedback WHERE id = ?", id);
    }

    public boolean userCanGiveAFeedback(long userId, long productId) {

        int numberOfShippedProductsWhichTheUserOrdered =
                jdbcTemplate.queryForObject("SELECT count(ordered_products.id) as OrderedAndShippedProducts\n" +
                        "FROM orders\n" +
                        "JOIN ordered_products ON orders.id = ordered_products.order_id\n" +
                        "WHERE orders.user_id = ? AND ordered_products.product_id = ? AND orders.order_status = 'SHIPPED'", Integer.class, userId, productId);

        return numberOfShippedProductsWhichTheUserOrdered >= 1;
    }

    public void updateFeedback(Feedback feedback, long feedbackId) {
        jdbcTemplate.update("UPDATE feedback SET feedback_date= ?,feedback= ?,rating= ? WHERE id = ?",
                feedback.getFeedbackDate(), feedback.getFeedback(), feedback.getRating(), feedbackId);
    }

    public boolean alreadyGaveAFeedback(long userId, long productId) {
        int numberOfFeedbacksOfTheGivenUserForACertainProduct =
                jdbcTemplate.queryForObject("SELECT count(*) numberOfFeedback FROM feedback WHERE product_id = ? AND user_id = ?", Integer.class, productId, userId);
        return numberOfFeedbacksOfTheGivenUserForACertainProduct == 1;
    }

    public int getFeedbackIdByUserIdAndProductId(long userId, long productId){
        return jdbcTemplate.queryForObject("SELECT id FROM feedback WHERE user_id = ? AND product_id = ?", Integer.class, userId, productId);
    }

    public int getSizeOfFeedbacks(){
        return jdbcTemplate.queryForObject("SELECT count(*) FROM feedback", Integer.class);
    }

}
