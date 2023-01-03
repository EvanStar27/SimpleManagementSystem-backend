package io.github.managementsystem.managementsystem.Subjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {

    private BigInteger subjectId;

    @NotBlank(message = "Subject Name is required")
    private String subjectName;

    @NotBlank(message = "Course is required")
    private BigInteger courseId;
}
