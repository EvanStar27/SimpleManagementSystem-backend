package io.github.managementsystem.managementsystem.Courses;

import io.github.managementsystem.managementsystem.Stats.Stats;
import io.github.managementsystem.managementsystem.Stats.StatsRowMapper;
import io.github.managementsystem.managementsystem.Students.Student;
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

@Repository
public class CourseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_courses (" +
                "course_id INT PRIMARY KEY AUTO_INCREMENT," +
                "course_name VARCHAR(255) NOT NULL," +
                "description VARCHAR(255) NOT NULL);";

        jdbcTemplate.update(sql);
        System.out.println("tbl_courses created...");
    }

    public List<Course> findAll(String name) {
        String sql = "SELECT * FROM tbl_courses " +
                "WHERE course_name LIKE ?;";
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Course.class),
                "%" + name + "%");
    }

    public Course save(Course course) {
        String sql = "INSERT INTO tbl_courses " +
                "(course_name, description) " +
                "VALUES (?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getDescription());
            return preparedStatement;
        }, keyHolder);

        course.setCourseId((BigInteger) keyHolder.getKey());
        return course;
    }

    public Course update(Course course) {
        String sql = "SELECT * FROM tbl_courses WHERE course_id = ?;";

        try {
            Course courseObj =
                    jdbcTemplate.queryForObject(
                            sql,
                            new BeanPropertyRowMapper<>(Course.class),
                            course.getCourseId());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        String updateSql = "UPDATE tbl_courses " +
                "SET course_name = ?, description = ? " +
                "WHERE course_id = ?;";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(updateSql);
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setInt(3, course.getCourseId().intValue());
            return preparedStatement;
        });

        return course;
    }

    public Course delete(BigInteger courseId) {
        String sql = "SELECT * FROM tbl_courses WHERE course_id = ?;";
        Course course;

        try {
            course = jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Course.class),
                    courseId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        String deleteSql = "DELETE FROM tbl_courses " +
                "WHERE course_id = ?;";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
            preparedStatement.setInt(1, courseId.intValue());
            return preparedStatement;
        });

        return course;
    }

    public List<CourseStudentMapping> findCourseApplicants(BigInteger courseId) {
        String sql = "SELECT * FROM tbl_courses WHERE course_id = ?";
        Course course;

        try {
            course = jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Course.class),
                    courseId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        String joinSql = "SELECT * FROM `tbl_courses` " +
                "JOIN `tbl_course_student_mappings`" +
                "ON `tbl_courses`.`course_id` = `tbl_course_student_mappings`.`course_id` " +
                "JOIN `tbl_students`" +
                "ON `tbl_students`.`student_id` = `tbl_course_student_mappings`.`student_id` " +
                "WHERE `tbl_courses`.`course_id` = ?;";

        return jdbcTemplate.query(joinSql, new CourseStudentRowMapper(), courseId);
    }

    public CourseStudentMapping deleteCourseApplicants(BigInteger csMappingId) {
        String sql = "SELECT * FROM `tbl_courses` " +
                "JOIN `tbl_course_student_mappings`" +
                "ON `tbl_courses`.`course_id` = `tbl_course_student_mappings`.`course_id` " +
                "JOIN `tbl_students`" +
                "ON `tbl_students`.`student_id` = `tbl_course_student_mappings`.`student_id` " +
                "WHERE `tbl_course_student_mappings`.`cs_mapping_id` = ?;";
        CourseStudentMapping courseStudentMapping;

        try {
            courseStudentMapping = jdbcTemplate.queryForObject(
                    sql,
                    new CourseStudentRowMapper(),
                    csMappingId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        String deleteSql = "DELETE FROM tbl_course_student_mappings " +
                "WHERE cs_mapping_id = ?;";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
            preparedStatement.setInt(1, csMappingId.intValue());
            return preparedStatement;
        });

        return courseStudentMapping;
    }

    public CourseStudentMapping enrollToCourse(CourseStudentMapping courseStudentMapping) {
        String studentSql = "SELECT * FROM tbl_students WHERE student_id = ?";
        String courseSql = "SELECT * FROM tbl_courses WHERE course_id = ?";
        String sql = "INSERT INTO tbl_course_student_mappings " +
                "(course_id, student_id) VALUES (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.queryForObject(
                    studentSql,
                    new BeanPropertyRowMapper<>(Student.class),
                    courseStudentMapping.getStudentId());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        try {
            jdbcTemplate.queryForObject(
                    courseSql,
                    new BeanPropertyRowMapper<>(Course.class),
                    courseStudentMapping.getCourseId());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setInt(1, courseStudentMapping.getCourseId().intValue());
            preparedStatement.setInt(2, courseStudentMapping.getStudentId().intValue());
            return preparedStatement;
        }, keyHolder);

        courseStudentMapping.setCsMappingId((BigInteger) keyHolder.getKey());
        return courseStudentMapping;
    }

    public Integer getTotalCourses() {
        String sql = "SELECT COUNT(*) FROM tbl_courses;";
        return jdbcTemplate.queryForObject(
                sql, Integer.class);
    }

    public List<Stats> getDashboardChartStats() {
        String sql = "SELECT `tbl_course_student_mappings`.`cs_mapping_id`, `tbl_courses`.`course_id`, `tbl_courses`.`course_name`, " +
                "COUNT(`tbl_course_student_mappings`.`student_id`) as `n_students`" +
                "FROM `tbl_courses` JOIN `tbl_course_student_mappings`" +
                "ON `tbl_courses`.`course_id` = `tbl_course_student_mappings`.`course_id`" +
                "GROUP BY `tbl_course_student_mappings`.`course_id`;";

        return jdbcTemplate.query(sql, new StatsRowMapper());
    }
}
