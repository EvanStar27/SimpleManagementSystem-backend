package io.github.managementsystem.managementsystem.Subjects;

import io.github.managementsystem.managementsystem.Courses.Course;
import io.github.managementsystem.managementsystem.Courses.CourseStudentMapping;
import io.github.managementsystem.managementsystem.Mappings.CourseSubjectMapping_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class SubjectRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tbl_subjects (
                    subject_id INT PRIMARY KEY AUTO_INCREMENT,
                    subject_name VARCHAR(255) NOT NULL,
                    course_id INT NOT NULL,
                    FOREIGN KEY (course_id) REFERENCES tbl_courses(course_id) ON DELETE CASCADE
                );
                """;

        jdbcTemplate.update(sql);
        System.out.println("tbl_subjects created...");
    }

    public List<CourseSubjectMapping> fetchAllSubjects() {
        String sql = """
                SELECT * FROM tbl_subjects
                JOIN tbl_courses
                ON tbl_subjects.course_id = tbl_courses.course_id;
                """;

        return jdbcTemplate.query(sql, new CourseSubjectRowMapper());
    }

    public Subject save(Subject subject) {
        String sql = """
                INSERT INTO tbl_subjects (subject_name, course_id)
                VALUES (?, ?);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, subject.getSubjectName());
            preparedStatement.setInt(2, subject.getCourseId().intValue());
            return preparedStatement;
        }, keyHolder);

        subject.setSubjectId((BigInteger) keyHolder.getKey());
        return subject;
    }

    public Subject update(Subject subject) {
        String sql = "SELECT * FROM tbl_subjects WHERE subject_id = ?;";

        try {
            Subject subjectObj =
                    jdbcTemplate.queryForObject(
                            sql,
                            new BeanPropertyRowMapper<>(Subject.class),
                            subject.getSubjectId());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        String updateSql = """
                UPDATE tbl_subjects 
                SET subject_name = ?, course_id = ?
                WHERE subject_id = ?;
                """;

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(updateSql);
            preparedStatement.setString(1, subject.getSubjectName());
            preparedStatement.setInt(2, subject.getCourseId().intValue());
            preparedStatement.setInt(3, subject.getSubjectId().intValue());
            return preparedStatement;
        });

        return subject;
    }

    public Subject delete(BigInteger id) {
        String sql = "SELECT * FROM tbl_subjects WHERE subject_id = ?;";
        Subject subjectObj = null;
        try {
            subjectObj =
                    jdbcTemplate.queryForObject(
                            sql,
                            new BeanPropertyRowMapper<>(Subject.class),
                            id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        String deleteSql = """
                DELETE FROM tbl_subjects WHERE subject_id = ?;
                """;

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
            preparedStatement.setInt(1, id.intValue());
            return preparedStatement;
        });

        return subjectObj;
    }

    public List<Subject> fetchSubjectsByCourseId(BigInteger id) {
        String sql = """
                SELECT * FROM tbl_subjects
                WHERE course_id = ?;
                """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Subject.class), id);
    }

    public List<CourseSubjectMapping_> fetchSubjectsByStudentId(BigInteger id) {
//        String joinSql = """
//                SELECT * FROM tbl_course_student_mappings
//                JOIN tbl_courses ON tbl_course_student_mappings.course_id = tbl_courses.course_id
//                JOIN tbl_course_subject_mappings
//                ON tbl_course_subject_mappings.cs_mapping_id = tbl_course_student_mappings.cs_mapping_id
//                WHERE tbl_course_student_mappings.student_id = ?;
//                """;

        String sql = """
                SELECT * FROM tbl_course_student_mappings
                JOIN tbl_course_subject_mappings
                USING (cs_mapping_id)
                WHERE tbl_course_student_mappings.student_id = ?
                """;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CourseSubjectMapping_.class), id);
    }
}