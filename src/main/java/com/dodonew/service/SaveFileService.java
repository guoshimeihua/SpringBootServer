package com.dodonew.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Bruce on 2017/11/4.
 */
@Service("saveFileService")
public class SaveFileService {
    private final Path rootLocation = Paths.get("upload");

    public boolean store(MultipartFile file) {
        boolean isSucess = false;
        try {
            // 创建一个文件夹
            Files.createDirectories(rootLocation);
            Path savePath = this.rootLocation.resolve(file.getOriginalFilename()+".jpeg");
            file.transferTo(new File(savePath.toAbsolutePath().toString()));
            isSucess = true;
        } catch (IOException e) {
            isSucess = false;
        }
        return isSucess;
    }

    public boolean deleteAll() {
        return FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public Resource loadFile(String fileName) {
        Path path = rootLocation.resolve(fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Load File Fail!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return resource;
    }
}
