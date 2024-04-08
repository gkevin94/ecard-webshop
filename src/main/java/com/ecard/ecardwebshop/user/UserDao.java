package com.ecard.ecardwebshop.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserDao {

    private static final RowMapper<User> USER_ROW_MAPPER = ((rs, i) -> new User(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("user_name"),
            rs.getString("password"),
            rs.getInt("enabled"),
            rs.getString("role"),
            rs.getString("user_status")));
    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> listUsers() {
        return jdbcTemplate.query("SELECT id, name, email, user_name, password, enabled, role, user_status FROM users", USER_ROW_MAPPER);
    }

    public void deleteUserById(long id) {
        jdbcTemplate.update("UPDATE orders SET user_id = NULL WHERE user_id = ?", id);
        jdbcTemplate.update("UPDATE basket SET user_id = NULL WHERE user_id = ?", id);
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    public void updateUser(long id, User user) {
        jdbcTemplate.update("UPDATE users SET name = ?, email = ?, user_name = ?, password = ?, enabled = ?, role = ?, user_status = ? WHERE id = ?",
                user.getName(), user.getEmail(), user.getUserName(), user.getPassword(), user.getEnabled(), user.getRole(), user.getUserStatus(), id);
    }

    public void updateUserWithoutPassword(long id, User user) {
        jdbcTemplate.update("UPDATE users SET name = ?, email = ?, user_name = ?, enabled = ?, role = ?, user_status = ? WHERE id = ?",
                user.getName(), user.getEmail(), user.getUserName(), user.getEnabled(), user.getRole(), user.getUserStatus(), id);
    }

    public long insertUserAndGetId(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("INSERT INTO users(name, enabled, user_name, password, email) VALUES ( ?, ?, ?, ?, ?)",
                                    Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, user.getName());
                    ps.setInt(2, 1);
                    ps.setString(3, user.getUserName());
                    ps.setString(4, user.getPassword());
                    ps.setString(5, user.getEmail());
                    return ps;
                }, keyHolder
        );

        return keyHolder.getKey().longValue();
    }

    public User getUserById(long id) {
        return jdbcTemplate.queryForObject("SELECT id, name, email, user_name, password, enabled, role, user_status FROM users WHERE id = ? ",
                USER_ROW_MAPPER, id);
    }

    public List<User> getUserByName(String userName) {
        return jdbcTemplate.query("SELECT id, name, email, user_name, password, enabled, role, user_status FROM users WHERE user_name = ? LIMIT 1",
                USER_ROW_MAPPER, userName);
    }
}
