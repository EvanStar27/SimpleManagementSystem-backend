package io.github.managementsystem.managementsystem.Subjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSubjectMapping {

    private BigInteger courseId;
    private BigInteger subjectId;
    private String courseName;
    private String subjectName;
}
