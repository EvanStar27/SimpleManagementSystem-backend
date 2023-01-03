package io.github.managementsystem.managementsystem.Enclosures;

import lombok.*;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnclosureRequest {
    private BigInteger enclosureId;

    private String enclosureName;

    private Boolean required;

    private String fileTypes;

    private Long fileSize;

    private String type;
}
