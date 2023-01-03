package io.github.managementsystem.managementsystem.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Override
    public File savePhoto(BigInteger studentId, BigInteger enclosureId, MultipartFile photo) throws Exception {
        String fileName = StringUtils.cleanPath(photo.getOriginalFilename());

        try {
            if (fileName.contains(".."))
                throw new Exception("Filename contains invalid path sequence: " + fileName);

            File file = File.builder()
                    .fileName(fileName)
                    .fileType(photo.getContentType())
                    .file(photo.getBytes())
                    .build();
            return fileRepository.save(studentId, enclosureId, file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Couldn't save file: " + fileName);
        }
    }

    @Override
    public File getFileById(BigInteger id) throws Exception {
        return fileRepository.getFileByID(id)
                .orElseThrow(() -> new Exception("File not found with student_id: " + id));
    }

    @Override
    public File updatePhoto(BigInteger studentId, MultipartFile photo) throws Exception {
        String fileName = StringUtils.cleanPath(photo.getOriginalFilename());

        try {
            if (fileName.contains(".."))
                throw new Exception("Filename contains invalid path sequence: " + fileName);

            System.out.println("--------- CONTENT TYPE: " + photo.getContentType());

            File file = File.builder()
                    .fileName(fileName)
                    .fileType(photo.getContentType())
                    .file(photo.getBytes())
                    .build();
            return fileRepository.update(studentId, file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Couldn't save file: " + fileName);
        }
    }


}
