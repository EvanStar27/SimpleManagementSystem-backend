package io.github.managementsystem.managementsystem.Subjects;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseSubjectRowMapper implements RowMapper<CourseSubjectMapping> {

    @Override
    public CourseSubjectMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CourseSubjectMapping
                .builder()
                .subjectId(new BigInteger(String.valueOf(rs.getInt("subject_id"))))
                .courseId(new BigInteger(String.valueOf(rs.getInt("course_id"))))
                .courseName(rs.getString("course_name"))
                .subjectName(rs.getString("subject_name"))
                .build();
    }
}
