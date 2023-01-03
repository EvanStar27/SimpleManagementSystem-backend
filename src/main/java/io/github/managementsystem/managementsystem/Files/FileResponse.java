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
public class FileResponse {

    private BigInteger fileId;
    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}
