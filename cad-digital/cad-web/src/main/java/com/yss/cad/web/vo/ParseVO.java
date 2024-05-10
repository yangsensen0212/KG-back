package com.yss.cad.web.vo;

import com.yss.cad.web.dto.ParseDto;
import com.yss.cad.web.enums.ParseState;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @Author 杨森森
 * @Data 2024/5/9  14:12
 */
@Data
public class ParseVO {
    private String name;
    private Date date;
    private ParseState state;
    private String key;
    private Map<String, String> pathMap;

    public ParseVO(String key, ParseDto info, Map<String, String> cacheMap, ParseState state) {
        this.name = info.getName();
        this.date = info.getDate();
        this.state = state;
        this.key = key;
        this.pathMap = cacheMap;
    }
}
