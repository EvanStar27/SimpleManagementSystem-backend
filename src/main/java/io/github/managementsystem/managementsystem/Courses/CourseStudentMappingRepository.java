package io.github.managementsystem.managementsystem.Courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseStudentMappingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tbl_course_student_mappings (" +
                "cs_mapping_id INT PRIMARY KEY AUTO_INCREMENT," +
                "course_id INT NOT NULL," +
                "student_id INT NOT NULL," +
                "FOREIGN KEY (course_id) REFERENCES tbl_courses(course_id) ON DELETE CASCADE," +
                "FOREIGN KEY (student_id) REFERENCES tbl_students(student_id) ON DELETE CASCADE);";

        jdbcTemplate.update(sql);
        System.out.println("tbl_course_student_mappings created...");
    }
}
