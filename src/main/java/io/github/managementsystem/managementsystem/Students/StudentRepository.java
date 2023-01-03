package io.github.managementsystem.managementsystem.Students;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_students (" +
                "student_id INT PRIMARY KEY AUTO_INCREMENT," +
                "user_id INT NOT NULL," +
                "first_name VARCHAR(255) NOT NULL," +
                "last_name VARCHAR(255) NOT NULL," +
                "gender VARCHAR(7) NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES tbl_users(user_id) ON DELETE CASCADE);";

        jdbcTemplate.update(sql);
        System.out.println("tbl_students created...");
    }

    public List<Student> fetchAll(String name) {
        String sql = "SELECT * FROM tbl_students " +
                "WHERE first_name LIKE ? OR last_name LIKE ?;";
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Student.class),
                "%" + name + "%",
                "%" + name + "%");
    }

    public Optional<Student> findById(BigInteger studentId) {
        String sql = "SELECT * FROM tbl_students WHERE student_id = ?;";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Student.class),
                studentId
        ).stream().findFirst();
    }

    public Student save(Student student) {
        String sql = "INSERT INTO tbl_students (user_id, first_name, last_name, gender) " +
                "VALUES (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setInt(1, student.getUserId().intValue());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setString(4, student.getGender());
            return preparedStatement;
        }, keyHolder);

        student.setStudentId((BigInteger) keyHolder.getKey());
        return student;
    }

    public Student update(Student student) {
        String sql = "SELECT * FROM tbl_students WHERE student_id = ?;";

        try {
            Student studentObj =
                    jdbcTemplate.queryForObject(
                            sql,
                            new BeanPropertyRowMapper<>(Student.class),
                            student.getStudentId());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        String updateSql = "UPDATE tbl_students SET first_name = ?, last_name = ?, gender = ? " +
                "WHERE student_id = ?;";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(updateSql);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getGender());
            preparedStatement.setInt(4, student.getStudentId().intValue());
            return preparedStatement;
        });

        return student;
    }

    public Student delete(BigInteger studentId) {
        String sql = "SELECT * FROM tbl_students WHERE student_id = ?;";
        Student student;

        try {
            student = jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Student.class),
                    studentId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        String deleteSql = "DELETE FROM tbl_students " +
                "WHERE student_id = ?;";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
            preparedStatement.setInt(1, studentId.intValue());
            return preparedStatement;
        });

        return student;
    }

    public Integer getTotalStudents() {
        String sql = "SELECT COUNT(*) FROM tbl_students;";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Optional<Student> findByUserId(BigInteger userId) {
        String sql = """
                SELECT * FROM tbl_students
                WHERE user_id = ?;
                """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class), userId)
                .stream().findFirst();
    }
}
