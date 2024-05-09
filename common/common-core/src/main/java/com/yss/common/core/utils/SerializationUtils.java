package com.yss.common.core.utils;

import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;

/**
 * @Author 杨森森
 * @Data 2024/5/9  15:22
 */
public class SerializationUtils<T extends Serializable> {
    // 将对象序列化为字节流
    public byte[] serializeObject(T obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
        return bos.toByteArray();
    }

    // 将字节流反序列化为对象
    public T deserializeObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        T obj = (T) ois.readObject();
        ois.close();
        return obj;
    }

    public String objectToString(T value) throws IOException {
        byte[] bytes = serializeObject(value);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public T stringToObject(String str) throws IOException, ClassNotFoundException {
        if (ObjectUtils.isEmpty(str)) {
            return null;
        }
        byte[] bytes = Base64.getDecoder().decode(str);
        return deserializeObject(bytes);
    }
}
