package io.github.managementsystem.managementsystem.Stats;

import io.github.managementsystem.managementsystem.Courses.CourseStudentMapping;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsRowMapper implements RowMapper<Stats> {
    @Override
    public Stats mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Stats
                .builder()
                .csMappingId(new BigInteger(String.valueOf(rs.getInt("cs_mapping_id"))))
                .courseId(new BigInteger(String.valueOf(rs.getInt("course_id"))))
                .courseName(rs.getString("course_name"))
                .students(rs.getInt("n_students"))
                .build();
    }
}
