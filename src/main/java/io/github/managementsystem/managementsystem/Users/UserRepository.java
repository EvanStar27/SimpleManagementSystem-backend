package io.github.managementsystem.managementsystem.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_users (" +
                "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                "username VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL);";

        jdbcTemplate.update(sql);
        System.out.println("tbl_users created...");
    }

    public void createAdminUser(String username, String password) {
        String insertSql = """
            INSERT INTO tbl_users (user_id, username, password)
            SELECT * FROM (SELECT 1, ?, ?) AS tmp
            WHERE NOT EXISTS (
                    SELECT username FROM tbl_users WHERE username = ?
            ) LIMIT 1;
        """;
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(insertSql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, username);
            return preparedStatement;
        });

        System.out.println("Admin user created...");
    }

    public UserDto save(UserDto user) {
        String sql = "INSERT INTO tbl_users " +
                "(username, password) " +
                "VALUES (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            return preparedStatement;
        }, keyHolder);

        user.setUserId((BigInteger) keyHolder.getKey());
        return user;
    }

    public UserDetails loadUserByUsername(String username) {
        String sql = "SELECT * FROM tbl_users WHERE username = ?;";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(User.class),
                    username);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return null;
    }


}
