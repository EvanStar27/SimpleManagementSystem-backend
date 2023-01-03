package io.github.managementsystem.managementsystem.Enclosures;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enclosure {
    private BigInteger enclosureId;

    private String enclosureName;

    private Boolean required;

    private List<String> fileTypes;

    private Long fileSize;

    private String type;
}
