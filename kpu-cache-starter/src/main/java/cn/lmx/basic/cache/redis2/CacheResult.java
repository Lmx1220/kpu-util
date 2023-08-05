package cn.lmx.basic.cache.redis2;

import cn.lmx.basic.cache.redis.NullVal;
import cn.lmx.basic.model.cache.CacheKey;
import cn.lmx.basic.utils.ArgumentAssert;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * @author lmx
 * @version v1.0.0
 * @date 2023/07/27  13:07
 */
@Getter
@Setter
public class CacheResult<T> {
    private String key;
    private Object field;
    private Duration duration;
    private T rawValue;

    public CacheResult(String key) {
        this.key = key;
    }

    public CacheResult(String key, T rawValue) {
        this.key = key;
        this.rawValue = rawValue;
    }

    public CacheResult(String key, Duration duration, T rawValue) {
        this.key = key;
        this.duration = duration;
        this.rawValue = rawValue;
    }

    public CacheResult(CacheKey cacheKey) {
        ArgumentAssert.notNull(cacheKey, "key 不能为空");
        this.key = cacheKey.getKey();
        this.duration = cacheKey.getExpire();
    }

    public CacheResult(CacheKey cacheKey, T rawValue) {
        ArgumentAssert.notNull(cacheKey, "key 不能为空");
        this.key = cacheKey.getKey();
        this.duration = cacheKey.getExpire();
        this.rawValue = rawValue;
    }

    public T getValue() {
        boolean isNull = rawValue == null || NullVal.class.equals(rawValue.getClass());
        boolean isObj = rawValue == null || Object.class.equals(rawValue.getClass());
        boolean isEmpty = (rawValue instanceof Map && ((Map<?, ?>) rawValue).isEmpty());
        if (isNull || isObj || isEmpty) {
            return null;
        }
        return rawValue;
    }

    @JsonIgnore
    public boolean isNullVal() {
        return rawValue != null &&
                (NullVal.class.equals(rawValue.getClass()) ||
                        (rawValue instanceof Map && ((Map<?, ?>) rawValue).isEmpty()));
    }

    @JsonIgnore
    public boolean isNull() {
        return rawValue == null;
    }

    @JsonIgnore
    public <E> List<E> asList() {
        if (rawValue == null) {
            return null;
        }
        if (rawValue instanceof List) {
            return (List<E>) rawValue;
        }
        throw new RuntimeException("value is not List");
    }
}
