package io.github.managementsystem.managementsystem.Courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private BigInteger courseId;

    @NotBlank(message = "Course Name is required")
    private String courseName;

    @NotBlank(message = "Description is required")
    private String description;
}
