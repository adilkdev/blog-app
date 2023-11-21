package com.adil.blog.services.impl;

import com.adil.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // File name
        String name = file.getOriginalFilename();

        // Generate random file name
        String randomID = UUID.randomUUID().toString();
        String fileName = null;
        if (name != null) {
            fileName = randomID.concat(name.substring(name.lastIndexOf(".")));
        }

        // Full path
        String filePath = path + File.separator + fileName;

        // Create folder if not created
        File newFile = new File(path);
        if (!newFile.exists())
            newFile.mkdir();

        // Copy file
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }
}
