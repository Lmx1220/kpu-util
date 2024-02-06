package cn.lmx.basic.cache.properties;

/**
 * 缓存类型
 *
 * @author lmx
 * @date 2024/02/07  02:04 下午
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
