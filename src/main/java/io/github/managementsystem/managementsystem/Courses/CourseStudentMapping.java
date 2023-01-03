package io.github.managementsystem.managementsystem.Courses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseStudentMapping {
    private BigInteger csMappingId;

    private BigInteger courseId;

    private BigInteger studentId;

    @NotBlank(message = "Subjects is required")
    private String subjects;

    private List<BigInteger> subjectList;

    private BigInteger subjectId;

    @NotBlank(message = "Course Name is required")
    private String courseName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotBlank(message = "Gender is required")
    private String gender;
}
