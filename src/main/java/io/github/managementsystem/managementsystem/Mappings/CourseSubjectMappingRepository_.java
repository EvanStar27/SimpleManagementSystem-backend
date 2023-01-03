package io.github.managementsystem.managementsystem.Mappings;

import io.github.managementsystem.managementsystem.Exceptions.NotFoundException;
import io.github.managementsystem.managementsystem.Subjects.CourseSubjectMapping;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseSubjectMappingRepository_ {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tbl_course_subject_mappings
                (course_subject_mapping_id INT PRIMARY KEY AUTO_INCREMENT,
                cs_mapping_id INT NOT NULL,
                subject_id INT NOT NULL,
                FOREIGN KEY (cs_mapping_id) REFERENCES tbl_course_student_mappings(cs_mapping_id) ON DELETE CASCADE,
                FOREIGN KEY (subject_id) REFERENCES tbl_subjects(subject_id) ON DELETE CASCADE);
                """;

        jdbcTemplate.update(sql);
        System.out.println("tbl_course_subject_mappings created...");
    }

    public CourseSubjectMapping_ save(CourseSubjectMapping_ cSubMapping) {
        String sql = """
                SELECT * FROM tbl_subjects
                WHERE subject_id = ?
                """;

        Optional<CourseSubjectMapping_> subjectObj = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CourseSubjectMapping_.class), cSubMapping.getSubjectId())
                .stream().findFirst();

        if (subjectObj.isEmpty()) {
            throw new NotFoundException("Subject Not Found");
        }

        String insertSql = """
                INSERT INTO tbl_course_subject_mappings
                (cs_mapping_id, subject_id)
                VALUES (?, ?);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, cSubMapping.getCsMappingId().intValue());
            preparedStatement.setInt(2, cSubMapping.getSubjectId().intValue());
            return preparedStatement;
        }, keyHolder);

        cSubMapping.setCourseSubjectMappingId((BigInteger) keyHolder.getKey());
        return cSubMapping;
    }

    public List<BigInteger> findSubjectsByCsMappingId(BigInteger csMappingId) {
        String sql = """
                SELECT * FROM tbl_course_subject_mappings
                WHERE cs_mapping_id = ?;
                """;

        List<CourseSubjectMapping_> courseSubjectMappingList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CourseSubjectMapping_.class), csMappingId);
        return courseSubjectMappingList.stream().map(cSubMapping -> cSubMapping.getSubjectId()).toList();
    }
}
