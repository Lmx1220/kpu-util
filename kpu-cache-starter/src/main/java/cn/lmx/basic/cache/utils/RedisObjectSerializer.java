package cn.lmx.basic.cache.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import cn.lmx.basic.jackson.JsonUtil;

/**
 * 此时定义的序列化操作表示可以序列化所有类的对象，当然，这个对象所在的类一定要实现序列化接口
 *
 * @author lmx
 * @date 2024/02/07  02:04
 */
public class RedisObjectSerializer extends Jackson2JsonRedisSerializer<Object> {
    public RedisObjectSerializer() {
        super(buildObjectMapper(), Object.class);
    }

    private static ObjectMapper buildObjectMapper() {
        ObjectMapper objectMapper = JsonUtil.newInstance();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        return objectMapper;
    }
}
