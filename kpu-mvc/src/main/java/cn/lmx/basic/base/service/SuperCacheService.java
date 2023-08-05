package cn.lmx.basic.base.service;

import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.model.cache.CacheKey;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @param <Entity> 实体
 * @author lmx
 * @version 1.0
 * @description:基于MP的 IService 新增了3个方法： getByIdCache
 * <p>
 * 其中：
 * 1，getByIdCache 方法 会先从缓存查询，后从DB查询 （取决于实现类）
 * 2、SuperService 上的方法
 * @date 2023/7/4 14:27
 */
public interface SuperCacheService<Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO>
        extends SuperService<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {

    Entity getByIdCache(Id id);

    Entity getByKey(CacheKey key, Function<CacheKey, Object> loader);

    /**
     * 可能会缓存穿透
     *
     * @param ids    主键id
     * @param loader 回调
     * @return 对象集合
     */
    List<Entity> findByIds(@NonNull Collection<? extends Serializable> ids, Function<Collection<? extends Serializable>, Collection<Entity>> loader);

    /**
     * 刷新缓存
     */
    void refreshCache();

    /**
     * 清理缓存
     */
    void clearCache();
}
