package io.github.managementsystem.managementsystem.Mappings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSubjectMapping_ {
    private BigInteger courseSubjectMappingId;
    private BigInteger csMappingId;
    private BigInteger courseId;
    private BigInteger subjectId;
}
