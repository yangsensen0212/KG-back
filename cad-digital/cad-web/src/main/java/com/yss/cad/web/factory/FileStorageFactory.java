package com.yss.cad.web.factory;

import com.yss.cad.web.storage.IFileStorage;
import com.yss.cad.web.storage.LocalFileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @Author 杨森森
 * @Data 2024/5/7  10:02
 */
@Component
public class FileStorageFactory {
    @Value("${storage.fileStorage}")
    private String fileStoragePolicy;

    private IFileStorage fileStorage;

    @PostConstruct
    public void initialize() {
        switch (fileStoragePolicy){
            case "local": {
                fileStorage = new LocalFileStorage();
                break;
            }
            case "minio": {
                break;
            }
        }
    }

    public IFileStorage getFileStorage() {
        return fileStorage;
    }
}
