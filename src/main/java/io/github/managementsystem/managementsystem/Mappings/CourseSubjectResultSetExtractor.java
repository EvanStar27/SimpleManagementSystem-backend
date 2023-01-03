package io.github.managementsystem.managementsystem.Mappings;

import io.github.managementsystem.managementsystem.Courses.CourseStudentMapping;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseSubjectResultSetExtractor implements ResultSetExtractor<CourseStudentMapping> {
    @Override
    public CourseStudentMapping extractData(ResultSet rs) throws SQLException, DataAccessException {
        return null;
    }
}
