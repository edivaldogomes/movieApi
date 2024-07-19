package com.movie.show.movie.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements  FileService{

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        String directory = "poster";
        Path directoryPath = Paths.get(directory);

        // get name of the file
        String fileName = file.getOriginalFilename();
        // get the file path
        String filePath = path + File.separator + fileName;

        // create file object
        File newFile = new File(filePath);
        if (Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        if(!newFile.exists()){
            newFile.mkdir();
        }
        // copy the file or upload file to the path
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
        String filePath = path + File.separator + filename;
        return new FileInputStream(filePath);
    }
}
