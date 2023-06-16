package cn.lmx.basic.cache.properties;

/**
 * @author lmx
 * @version 1.0
 * @description: 缓存类型
 * @date 2023/6/16 16:16
 */
public enum CacheType {
    /**
     * 内存
     */
    CAFFEINE,
    /**
     * redis
     */
    REDIS,
    ;

    public boolean eq(CacheType cacheType) {
        return cacheType != null && this.name().equals(cacheType.name());
    }
}
