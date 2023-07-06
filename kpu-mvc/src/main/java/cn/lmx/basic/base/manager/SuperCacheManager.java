package cn.lmx.basic.base.manager;

import cn.lmx.basic.model.cache.CacheKey;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author lmx
 * @version v1.0.0
 * @date 2023/07/06  16:09
 */
public interface SuperCacheManager<T> extends SuperManager<T> {
    T getByKey(CacheKey key, Function<CacheKey, Object> loader);

    /**
     * 刷新缓存
     */
    void refreshCache();

    /**
     * 清理缓存
     */
    void clearCache();

    T getByIdCache(Serializable id);

    List<T> findByIds(Collection<? extends Serializable> ids, Function<Collection<? extends Serializable>, Collection<T>> loader);
}
