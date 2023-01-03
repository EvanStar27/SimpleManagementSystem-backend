package io.github.managementsystem.managementsystem.Enclosures;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EnclosureRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tbl_enclosures
                (enclosure_id INT PRIMARY KEY AUTO_INCREMENT,
                enclosure_name VARCHAR(255) NOT NULL,
                required BOOLEAN NOT NULL,
                file_types JSON NOT NULL,
                file_size INT NOT NULL,
                type VARCHAR(100) NOT NULL);
                """;

        jdbcTemplate.update(sql);
        System.out.println("tbl_enclosures created...");
    }

    public List<Enclosure> fetchAll() {
        String sql = """
                SELECT * FROM tbl_enclosures;
                """;

        return jdbcTemplate.query(sql, new EnclosureRowMapper());
    }

    public Enclosure save(EnclosureRequest enclosureRequest) {
        String sql = """
                INSERT INTO tbl_enclosures
                (enclosure_name, required, file_types, file_size, type)
                VALUES (?, ?, ?, ?, ?);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, enclosureRequest.getEnclosureName());
            preparedStatement.setBoolean(2, enclosureRequest.getRequired());
            preparedStatement.setString(3, enclosureRequest.getFileTypes());
            preparedStatement.setLong(4, enclosureRequest.getFileSize());
            preparedStatement.setString(5, enclosureRequest.getType());
            return preparedStatement;
        }, keyHolder);

        ObjectMapper mapper = new ObjectMapper();
        List<String> fileTypes = new ArrayList<>();
        try {
            fileTypes = mapper.readValue(enclosureRequest.getFileTypes(), new TypeReference<List<String>>() {
            });
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Enclosure.builder()
                .enclosureId((BigInteger) keyHolder.getKey())
                .enclosureName(enclosureRequest.getEnclosureName())
                .required(enclosureRequest.getRequired())
                .fileTypes(fileTypes)
                .fileSize(enclosureRequest.getFileSize())
                .type(enclosureRequest.getType())
                .build();
    }

    public Enclosure update(BigInteger enclosureId, EnclosureRequest enclosureRequest) {
        String sql = """
                UPDATE tbl_enclosures
                SET enclosure_name = ?, required = ?, file_types = ?, file_size = ?, type = ?
                WHERE enclosure_id = ?;
                """;

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, enclosureRequest.getEnclosureName());
            preparedStatement.setBoolean(2, enclosureRequest.getRequired());
            preparedStatement.setString(3, enclosureRequest.getFileTypes());
            preparedStatement.setLong(4, enclosureRequest.getFileSize());
            preparedStatement.setString(5, enclosureRequest.getType());
            preparedStatement.setInt(6, enclosureId.intValue());
            return preparedStatement;
        });

        ObjectMapper mapper = new ObjectMapper();
        List<String> fileTypes = new ArrayList<>();
        try {
            fileTypes = mapper.readValue(enclosureRequest.getFileTypes(), new TypeReference<List<String>>() {
            });
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Enclosure.builder()
                .enclosureId(enclosureRequest.getEnclosureId())
                .enclosureName(enclosureRequest.getEnclosureName())
                .required(enclosureRequest.getRequired())
                .fileTypes(fileTypes)
                .fileSize(enclosureRequest.getFileSize())
                .type(enclosureRequest.getType())
                .build();
    }

    public Optional<Enclosure> findById(BigInteger enclosureId) {
        String sql = """
                SELECT * FROM tbl_enclosures
                WHERE enclosure_id = ?;
                """;

        return jdbcTemplate.query(sql, new EnclosureRowMapper(), enclosureId)
                .stream().findFirst();
    }

    public int delete(BigInteger enclosureId) {
        String sql = """
                DELETE FROM tbl_enclosures
                WHERE enclosure_id = ?;
                """;

        return jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, enclosureId.intValue());
            return preparedStatement;
        });
    }

    public List<Enclosure> fetchByType(String type) {
        String sql = """
                SELECT * FROM tbl_enclosures
                WHERE type = ?
                """;

        return jdbcTemplate.query(sql, new EnclosureRowMapper(), type);
    }
}
