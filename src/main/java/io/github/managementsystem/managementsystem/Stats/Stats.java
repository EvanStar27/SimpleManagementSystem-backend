package io.github.managementsystem.managementsystem.Stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stats {
    private BigInteger csMappingId;
    private BigInteger courseId;
    private String courseName;
    private Integer students;
}
