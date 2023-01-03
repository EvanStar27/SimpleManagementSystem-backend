package io.github.managementsystem.managementsystem.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class FileRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tbl_files (
                    file_id INT PRIMARY KEY AUTO_INCREMENT,
                    student_id INT NOT NULL,
                    enclosure_id INT NOT NULL,
                    file_name VARCHAR(255) NOT NULL,
                    file_type VARCHAR(255) NOT NULL,
                    file BLOB NOT NULL,
                    FOREIGN KEY (enclosure_id) REFERENCES tbl_enclosures(enclosure_id) ON DELETE CASCADE,
                    FOREIGN KEY (student_id) REFERENCES tbl_students(student_id) ON DELETE CASCADE
                );
                """;

        jdbcTemplate.update(sql);
        System.out.println("tbl_files created...");
    }

    public File save(BigInteger studentId, BigInteger enclosureId, File file) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO tbl_files (file_name, file_type, file, student_id, enclosure_id)
                VALUES (?, ?, ?, ?, ?);
                """;

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, file.getFileName());
            preparedStatement.setString(2, file.getFileType());
            preparedStatement.setBytes(3, file.getFile());
            preparedStatement.setInt(4, studentId.intValue());
            preparedStatement.setInt(5, enclosureId.intValue());
            return preparedStatement;
        }, keyHolder);

        file.setFileId((BigInteger) keyHolder.getKey());
        return file;
    }


    public Optional<File> getFileByID(BigInteger id) {
        String sql = "SELECT * FROM tbl_files WHERE student_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(File.class), id)
                .stream().findFirst();
    }

    public File update(BigInteger studentId, File file) {
        String sql = """
                UPDATE tbl_files 
                SET file_name = ?, file_type = ?, file = ?
                WHERE student_id = ?;
                """;

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, file.getFileName());
            preparedStatement.setString(2, file.getFileType());
            preparedStatement.setBytes(3, file.getFile());
            preparedStatement.setInt(4, studentId.intValue());
            return preparedStatement;
        });

        return file;
    }

    public Optional<File> fetchFileByStudentId(BigInteger id) {
        String sql = """
                SELECT * FROM tbl_files WHERE student_id = ?;
                """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(File.class), id)
                .stream().findFirst();
    }
}
