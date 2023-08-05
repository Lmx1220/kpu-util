package cn.lmx.basic.cache.redis;

import cn.lmx.basic.utils.ArgumentAssert;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lmx
 * @version v1.0.0
 * @date 2023/07/27  12:57
 */
@Getter
@Slf4j
public abstract class BaseRedis {

    protected static final String KEY_NOT_NULL = "key不能为空";
    protected static final String CACHE_KEY_NOT_NULL = "cacheKey不能为空";
    protected static final int BATCH_SIZE = 1000;

    protected static final Map<String, Object> KEY_LOCKS = new ConcurrentHashMap<>();
    protected final RedisTemplate<String, Object> redisTemplate;
    protected final ValueOperations<String, Object> valueOps;
    protected final HashOperations<String, Object, Object> hashOps;
    protected final ListOperations<String, Object> listOps;
    protected final SetOperations<String, Object> setOps;
    protected final ZSetOperations<String, Object> zSetOps;
    protected final StringRedisTemplate stringRedisTemplate;
    /**
     * 全局配置是否缓存null值
     */
    protected final boolean defaultCacheNullVal;

    public BaseRedis(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate, boolean defaultCacheNullVal) {
        this.redisTemplate = redisTemplate;
        ArgumentAssert.notNull(redisTemplate, "redisTemplate 为空");
        valueOps = redisTemplate.opsForValue();
        hashOps = redisTemplate.opsForHash();
        listOps = redisTemplate.opsForList();
        setOps = redisTemplate.opsForSet();
        zSetOps = redisTemplate.opsForZSet();
        this.stringRedisTemplate = stringRedisTemplate;
        this.defaultCacheNullVal = defaultCacheNullVal;
    }

}