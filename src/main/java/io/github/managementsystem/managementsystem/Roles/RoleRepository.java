package io.github.managementsystem.managementsystem.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_roles (" +
                "role_id INT PRIMARY KEY AUTO_INCREMENT," +
                "role_name VARCHAR(255) NOT NULL);";

        jdbcTemplate.update(sql);
        System.out.println("tbl_roles created...");
    }

    public void createRoles() {
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.builder().roleName("ROLE_ADMIN").build());
        roleList.add(Role.builder().roleName("ROLE_STUDENT").build());

        for (Role role: roleList) {
            String sql = """
                INSERT INTO tbl_roles (role_name)
                SELECT * FROM (SELECT ?) AS tmp
                WHERE NOT EXISTS (
                        SELECT role_name FROM tbl_roles WHERE role_name = ?
                ) LIMIT 1;
            """;
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, role.getRoleName());
                preparedStatement.setString(2, role.getRoleName());
                return preparedStatement;
            });
        }

        System.out.println("Roles Created...");
    }

    public Role findRoleById(BigInteger roleId) {
        String sql = "SELECT * FROM tbl_roles WHERE role_id = ?;";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Role.class),
                    roleId
            );
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
