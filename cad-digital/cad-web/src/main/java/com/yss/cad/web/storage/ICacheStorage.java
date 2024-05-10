package com.yss.cad.web.storage;

import com.yss.cad.web.dto.ParseDto;
import com.yss.cad.web.enums.ParseState;
import com.yss.cad.web.vo.ParseVO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author 杨森森
 * @Data 2024/5/7  11:04
 */
public interface ICacheStorage {
    /**
     * 将信息存储至缓存
     * @param token 令牌
     * @param key 当前key
     * @param fileName 文件名称
     * @param filePath 文件路径
     */
    void storage(String token, String key, String fileName, String filePath, ParseDto info) throws IOException;

    boolean delete(String key);

    /**
     * 判断某key值是否存在
     */
    boolean exist(String key);

    /**
     * 设定状态为成功
     */
    void success(String key);

    /**
     * 当前状态
     * @param key key
     */
    ParseState state(String key);

    /**
     * 获取解析结果
     * @param token 用户令牌
     * @return 数组
     */
    List<ParseVO> getParseList(String token) throws IOException, ClassNotFoundException;

    /**
     * 获取信息，通过key
     * @param key key
     * @return vo
     */
    ParseDto getInfo(String key) throws IOException, ClassNotFoundException;

    /**
     * 获取map
     * @param key
     * @return
     */
    Map<String, String> getCacheMap(String key);

    /**
     * 获取token
     * @param key
     * @return
     */
    String getToken(String key);

    /**
     * 获取Vo
     * @return
     */
    ParseVO getVo(String key) throws IOException, ClassNotFoundException;
}
