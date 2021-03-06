package net.stackoverflow.spectre.transport.serialize;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json序列化实现
 *
 * @author wormhole
 */
public class JsonSerializeManager implements SerializeManager {

    private static final Logger log = LoggerFactory.getLogger(JsonSerializeManager.class);

    /**
     * 序列化
     *
     * @param object 序列化的对象
     * @return
     */
    @Override
    public byte[] serialize(Object object) {
        byte[] bytes = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            bytes = mapper.writeValueAsBytes(object);
        } catch (Exception e) {
            log.error("SerializeManager fail to serialize", e);
        }
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @param clazz Class对象
     * @param <T>   泛型
     * @return
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        T obj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            obj = mapper.readValue(bytes, clazz);
        } catch (Exception e) {
            log.error("SerializeManager fail to deserialize", e);
        }
        return obj;
    }

    public <T> T deserialize(byte[] bytes, TypeReference<T> reference) {
        T obj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            obj = mapper.readValue(bytes, reference);
        } catch (Exception e) {
            log.error("SerializeManager fail to deserialize", e);
        }
        return obj;
    }
}
