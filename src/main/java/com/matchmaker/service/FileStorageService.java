package com.matchmaker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {

    @Value("${upload.path:uploads}")
    private String uploadDir;

    public List<String> saveFiles(List<MultipartFile> files, Long userId) throws IOException {
        List<String> filePaths = new ArrayList<>();

        Path userFolder = Paths.get(uploadDir, "user_" + userId);
        if (!Files.exists(userFolder)) {
            Files.createDirectories(userFolder);
        }

        for (MultipartFile file : files) {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = userFolder.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            filePaths.add(filePath.toString());
        }

        return filePaths;
    }
}
