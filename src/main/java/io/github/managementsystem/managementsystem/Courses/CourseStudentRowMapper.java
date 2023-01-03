package io.github.managementsystem.managementsystem.Courses;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseStudentRowMapper implements RowMapper<CourseStudentMapping> {
    @Override
    public CourseStudentMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CourseStudentMapping
                .builder()
                .csMappingId(new BigInteger(String.valueOf(rs.getInt("cs_mapping_id"))))
                .courseId(new BigInteger(String.valueOf(rs.getInt("course_id"))))
                .studentId(new BigInteger(String.valueOf(rs.getInt("student_id"))))
                .subjects(rs.getString("subjects"))
                .courseName(rs.getString("course_name"))
                .description(rs.getString("description"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .gender(rs.getString("gender"))
                .build();
    }
}
