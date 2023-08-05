package cn.lmx.basic.base.service.impl;

import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.manager.SuperCacheManager;
import cn.lmx.basic.base.service.SuperCacheService;
import cn.lmx.basic.model.cache.CacheKey;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


/**
 * @param <M>
 * @param <T>
 * @author lmx
 * @version 1.0
 * @description: 基于 CacheOps 实现的 缓存实现
 * 默认的key规则： #{CacheKeyBuilder#key()}:id
 * <p>
 * 1，getByIdCache：新增的方法： 先查缓存，在查db
 * 2，removeById：重写 ServiceImpl 类的方法，删除db后，淘汰缓存
 * 3，removeByIds：重写 ServiceImpl 类的方法，删除db后，淘汰缓存
 * 4，updateAllById： 新增的方法： 修改数据（所有字段）后，淘汰缓存
 * 5，updateById：重写 ServiceImpl 类的方法，修改db后，淘汰缓存
 * @date 2023/7/4 14:27
 */
public abstract class SuperCacheServiceImpl<M extends SuperCacheManager<Entity>, Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO>
        extends SuperServiceImpl<M, Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO>
        implements SuperCacheService<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {


    @Override
    @Transactional(readOnly = true)
    public Entity getByIdCache(Id id) {
        return superManager.getByIdCache(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Entity getByKey(CacheKey key, Function<CacheKey, Object> loader) {
        return superManager.getByKey(key, loader);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entity> findByIds(Collection<? extends Serializable> ids, Function<Collection<? extends Serializable>, Collection<Entity>> loader) {
        return superManager.findByIds(ids, loader);
    }

    @Override
    public void refreshCache() {
        superManager.refreshCache();
    }

    @Override
    public void clearCache() {
        superManager.clearCache();

    }
}
