package io.github.managementsystem.managementsystem.Files;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {
    private BigInteger fileId;
    private BigInteger studentId;
    private BigInteger enclosureId;
    private String fileName;
    private String fileType;
    private byte[] file;
}
