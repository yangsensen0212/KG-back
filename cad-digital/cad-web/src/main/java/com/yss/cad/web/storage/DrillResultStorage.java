package com.yss.cad.web.storage;

import com.yss.cad.web.dto.ParseDto;
import com.yss.cad.web.enums.ParseState;
import com.yss.cad.web.factory.CacheStorageFactory;
import com.yss.cad.web.factory.FileStorageFactory;
import com.yss.cad.web.vo.ParseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    public void storage(String token, String key, File file, String originFileName, ParseDto info) throws IOException {
        IFileStorage fileStorage = fileStorageFactory.getFileStorage();
        String path = fileStorage.storage(file);
        ICacheStorage cacheStorage = cacheStorageFactory.getCacheStorage();
        cacheStorage.storage(token, key, originFileName, path, info);
    }

    /**
     * 设置当前解析状态为成功
     * @param key 当前key
     */
    public void success(String key) {
        ICacheStorage cacheStorage = cacheStorageFactory.getCacheStorage();
        cacheStorage.success(key);
    }

    public List<ParseVO> getParseList(String token) throws IOException, ClassNotFoundException {
        ICacheStorage cacheStorage = cacheStorageFactory.getCacheStorage();
        return cacheStorage.getParseList(token);
    }
}
