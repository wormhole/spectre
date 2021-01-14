package net.stackoverflow.spectre.transport.serialize;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 序列化接口
 *
 * @author wormhole
 */
public interface SerializeManager {

    /**
     * 序列化
     *
     * @param object 序列化的对象
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @param clazz Class对象
     * @param <T>   泛型
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);

    /**
     * 泛型反序列化
     *
     * @param bytes
     * @param reference
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] bytes, TypeReference<T> reference);
}
