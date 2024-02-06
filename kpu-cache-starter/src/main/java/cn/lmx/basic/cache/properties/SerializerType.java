package cn.lmx.basic.cache.properties;

/**
 * 序列化类型
 *
 * @author lmx
 * @date 2024/02/07  02:04
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
