package cn.lmx.basic.cache.properties;

/**
 * @author lmx
 * @version 1.0
 * @description: 序列化类型
 * @date 2023/6/16 16:15
 */
public enum SerializerType {
    /**
     * json 序列化
     */
    JACK_SON,
    /**
     * 默认:ProtoStuff 序列化
     */
    ProtoStuff,
    /**
     * jdk 序列化
     */
    JDK
}
