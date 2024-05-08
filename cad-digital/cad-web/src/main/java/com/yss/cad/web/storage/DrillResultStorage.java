package com.yss.cad.web.storage;

import com.yss.cad.web.factory.CacheStorageFactory;
import com.yss.cad.web.factory.FileStorageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @Author 杨森森
 * @Data 2024/5/7  9:45
 */
@Component
public class DrillResultStorage {
    @Autowired
    private CacheStorageFactory cacheStorageFactory;
    @Autowired
    private FileStorageFactory fileStorageFactory;

    public void storage(String token, String key, File file, String originFileName) throws IOException {
        IFileStorage fileStorage = fileStorageFactory.getFileStorage();
        String path = fileStorage.storage(file);
        ICacheStorage cacheStorage = cacheStorageFactory.getCacheStorage();
        cacheStorage.storage(token, key, originFileName, path);
    }
}
