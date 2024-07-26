package com.movie.show.movie.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@Service
public class FileServiceImpl implements  FileService{

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // Define the directory
        String directory = "poster";
        Path directoryPath = Paths.get(directory);

        // Get the name of the file
        String fileName = file.getOriginalFilename();
        // Get the full file path
        String filePath = path + File.separator + fileName;

        // Create the directory if it doesn't exist
        if (Files.notExists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // Create the file object
        File newFile = new File(filePath);

        // Check if the file already exists
        if (newFile.exists()) {
            throw new FileAlreadyExistsException("File already exists: " + filePath);
        }

        // Create the new file
        newFile.createNewFile();

        // Copy the file or upload the file to the path
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
        String filePath = path + File.separator + filename;
        return new FileInputStream(filePath);
    }
}
