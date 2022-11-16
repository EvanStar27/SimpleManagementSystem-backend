package io.github.managementsystem.managementsystem.Roles;

import io.github.managementsystem.managementsystem.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserRoleMappingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_user_role_mappings (" +
                "ur_mapping_id INT PRIMARY KEY AUTO_INCREMENT," +
                "user_id INT NOT NULL," +
                "role_id INT NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES tbl_users(user_id) ON DELETE CASCADE," +
                "FOREIGN KEY (role_id) REFERENCES tbl_roles(role_id) ON DELETE CASCADE);";

        jdbcTemplate.update(sql);
        System.out.println("tbl_user_role_mappings created...");
    }

    public List<UserRoleMapping> findByUserId(BigInteger userId) {
        String sql = "SELECT * FROM tbl_user_role_mappings WHERE user_id = " + userId;

        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserRoleMapping.class));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createAdminUser() {
        String sql = """
            INSERT INTO tbl_user_role_mappings (ur_mapping_id, user_id, role_id)
            VALUES (1, ?, ?) ON DUPLICATE KEY UPDATE user_id = ?;
        """;

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 1);
            preparedStatement.setInt(3, 1);
            return preparedStatement;
        });

        System.out.println("AdminRole created...");
    }
}
