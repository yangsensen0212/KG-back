package com.yss.cad.web.storage;

import com.yss.cad.web.dto.ParseDto;
import com.yss.cad.web.enums.ParseState;
import com.yss.cad.web.factory.FileStorageFactory;
import com.yss.cad.web.vo.ParseVO;
import com.yss.common.core.utils.BeanUtils;
import com.yss.common.core.utils.SerializationUtils;
import com.yss.common.redis.service.IRedisService;
import com.yss.common.redis.service.impl.RedisServiceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author 杨森森
 * @Data 2024/5/7  11:22
 */
@Component
@Slf4j
public class RedisStorage implements ICacheStorage{

    private static final String PREFIX = "REDIS-STORAGE-DRILL";
    private static final String SUFFIX = "REDIS-STORAGE-TOKEN";

    @Autowired
    private FileStorageFactory fileStorageFactory;

    @Autowired
    private IRedisService<String> redisService;

    private SerializationUtils<ParseDto> serializationUtils = new SerializationUtils<>();

    @Value("${storage.expired}")
    private Long expired;

//    public RedisStorage() {
//        this.redisService = BeanUtils.getBean(RedisServiceImpl.class);
//    }

    /**
     * 将信息存储至缓存
     *
     * @param token    令牌
     * @param key      当前key
     * @param fileName 文件名称
     * @param filePath 文件路径
     */
    @Override
    public void storage(String token, String key, String fileName, String filePath, ParseDto info) throws IOException {
        /*
        由于key过期后，事件通知只能获得过期key，不能获取value，这里解决方案如下：
        将key分为两个：key 和 key2。
        key 设置过期时间，key2 存数据。
        检测到key过期时，获取key2的数据，然后删除key2
         */
        redisService.addCatchSet(token, key);
        setCacheMap(key, fileName, filePath);
        setToken(key, token);
        setInfo(key, info);
        redisService.setCacheObject(key, ParseState.PARSEING.getName());
        redisService.setExpired(key, expired);
    }

    private void setInfo(String key, ParseDto info) throws IOException {
        redisService.setCacheObject(PREFIX + key + SUFFIX, serializationUtils.objectToString(info));
    }

    public ParseDto getInfo(String key) throws IOException, ClassNotFoundException {
        String cacheObject = redisService.getCacheObject(PREFIX + key + SUFFIX);
        return serializationUtils.stringToObject(cacheObject);
    }

    private void deleteInfo(String key) {
        redisService.deleteCache(PREFIX + key + SUFFIX);
    }

    private void setCacheMap(String key, String fileName, String  filePath) {
        redisService.addToCacheMap(PREFIX + key, fileName, filePath);
    }

    public Map<String, String> getCacheMap(String key) {
        return redisService.getCacheMap(PREFIX + key);
    }

    private void deleteCacheMap(String key) {
        redisService.deleteCache(PREFIX + key);
    }

    private void setToken(String key, String token) {
        redisService.setCacheObject(key + SUFFIX, token);
    }



    public String getToken(String key) {
        return redisService.getCacheObject(key + SUFFIX);
    }

    /**
     * 获取Vo
     *
     * @param key
     * @return
     */
    @Override
    public ParseVO getVo(String key) throws IOException, ClassNotFoundException {
        ParseDto info = getInfo(key);
        Map<String, String> cacheMap = getCacheMap(key);
        ParseState state = state(key);
        return new ParseVO(key, info, cacheMap, state);
    }

    private void deleteToken(String key) {
        redisService.deleteCache(key + SUFFIX);
    }


    @Override
    public boolean delete(String key) {
        log.info("移除：{}", key);
        String token = getToken(key);
        Map<String, String> map = getCacheMap(key);
        redisService.removeFromSet(token, key);
        deleteToken(key);
        deleteCacheMap(key);
        deleteInfo(key);
        IFileStorage fileStorage = fileStorageFactory.getFileStorage();
        for (String filePath : map.values()) {
            try {
                fileStorage.delete(filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean exist(String key) {
        String token = getToken(key);
        return !ObjectUtils.isEmpty(token);
    }

    /**
     * 设定状态为成功
     *
     */
    @Override
    public void success(String key) {
        redisService.setCacheObject(key, ParseState.SUCCESS.getName());
    }

    /**
     * 当前状态
     *
     * @param key key
     */
    @Override
    public ParseState state(String key) {
        String name = redisService.getCacheObject(key);
        return ParseState.getByName(name);
    }

    /**
     * 获取解析结果
     *
     * @param token 用户令牌
     * @return 数组
     */
    @Override
    public List<ParseVO> getParseList(String token) throws IOException, ClassNotFoundException {
        Set<String> cacheSet = redisService.getCacheSet(token);
        List<ParseVO> voList = new ArrayList<>();
        for (String key : cacheSet) {
            ParseVO vo = getVo(key);
            voList.add(vo);
        }
        return voList;
    }
}
