package com.ecard.ecardwebshop.category;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.sql.PreparedStatement;

@Repository
public class CategoryDao {

    private JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, rowNum) -> new Category(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getLong("ordinal")
    );

    public List<Category> listCategories() {
        return jdbcTemplate.query("SELECT id, name, ordinal FROM category ORDER BY ordinal",
                CATEGORY_ROW_MAPPER);
    }

    public List<Category> getCategoryByName(String categoryName) {
        return jdbcTemplate.query("SELECT id, name, ordinal FROM category WHERE name = ? LIMIT 1",
                CATEGORY_ROW_MAPPER, categoryName);
    }

    public long createCategoryAndGetId(Category category) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO category (name, ordinal) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
            ps.setLong(2, category.getOrdinal());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Long getMaxOrdinal() {
        return jdbcTemplate.queryForObject("SELECT MAX(ordinal) FROM category",
                (rs, rowNum) -> rs.getLong("MAX(ordinal)"));
    }

    public void increaseOrdinalFrom(long ordinalFrom) {
        jdbcTemplate.update("UPDATE category SET ordinal = ordinal +1 WHERE ordinal >= ?", ordinalFrom);
    }

    public void increaseOrdinalFromTo(long ordinalFrom, long ordinalTo) {
        jdbcTemplate.update("UPDATE category SET ordinal = ordinal +1 WHERE ordinal >= ? AND ordinal < ? ", ordinalFrom, ordinalTo);
    }

    public void decreaseOrdinalFrom(long ordinalFrom) {
        jdbcTemplate.update("UPDATE category SET ordinal = ordinal -1 WHERE ordinal <= ?", ordinalFrom);
    }

    public void decreaseOrdinalFromTo(long ordinalFrom, long ordinalTo) {
        jdbcTemplate.update("UPDATE category SET ordinal = ordinal -1 WHERE ordinal <= ? AND ordinal > ? ", ordinalFrom, ordinalTo);
    }

    public int deleteCategory(long id) {
        return jdbcTemplate.update("DELETE FROM category WHERE id = ?", id);
    }

    public Category getCategoryById(long id) {
        return jdbcTemplate.queryForObject("SELECT id, name, ordinal FROM category WHERE id = ?", CATEGORY_ROW_MAPPER, id);
    }

    public int updateCategory(long id, Category category){
        return jdbcTemplate.update("UPDATE category SET name = ?, ordinal = ? WHERE id = ?",
                category.getName(),
                category.getOrdinal(),
                id);
    }
}
