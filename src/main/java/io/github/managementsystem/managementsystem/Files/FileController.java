package io.github.managementsystem.managementsystem.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload/photo")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public FileResponse uploadPhoto(
            @RequestParam("studentId") BigInteger studentId,
            @RequestParam("enclosureId") BigInteger enclosureId,
            @RequestParam("photo") MultipartFile photo) throws Exception {
        File file = fileService.savePhoto(studentId, enclosureId, photo);
        String downloadURL = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/files/download/photo/")
                .path(studentId.toString())
                .toUriString();

        return FileResponse.builder()
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .fileSize(photo.getSize())
                .fileType(photo.getContentType())
                .downloadURL(downloadURL)
                .build();
    }

    @PutMapping("/update/photo")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public FileResponse updatePhoto(
            @RequestParam("studentId") BigInteger studentId,
            @RequestParam("photo") MultipartFile photo) throws Exception {
        File file = fileService.updatePhoto(studentId, photo);
        String downloadURL = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/files/download/photo/")
                .path(studentId.toString())
                .toUriString();

        return FileResponse.builder()
                .fileId(file.getFileId())
                .fileName(file.getFileName())
                .fileSize(photo.getSize())
                .fileType(photo.getContentType())
                .downloadURL(downloadURL)
                .build();
    }

    @GetMapping("/download/photo/{id}")
    public ResponseEntity<?> downloadPhoto(@PathVariable("id")BigInteger id) throws Exception {
        if (id.equals(0))
            return new ResponseEntity<>("Test Download", HttpStatus.OK);

        File file = fileService.getFileById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                        file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFile()));
    }
}
