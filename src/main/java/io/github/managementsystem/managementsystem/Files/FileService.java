package io.github.managementsystem.managementsystem.Files;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

public interface FileService {
    File savePhoto(BigInteger studentId, BigInteger enclosureId, MultipartFile photo) throws Exception;

    File getFileById(BigInteger id) throws Exception;

    File updatePhoto(BigInteger studentId, MultipartFile photo) throws Exception;
}
