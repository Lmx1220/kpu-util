package cn.lmx.basic.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.manager.SuperCacheManager;
import cn.lmx.basic.base.service.SuperCacheService;
import cn.lmx.basic.cache.repository.CacheOps;
import cn.lmx.basic.utils.ArgumentAssert;
import cn.lmx.basic.utils.BeanPlusUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    protected CacheOps cacheOps;

    @Override
    @Transactional(readOnly = true)
    public Entity getByIdCache(Id id) {
        return superManager.getByIdCache(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Entity save(SaveVO saveVO) {
        Entity entity = saveBefore(saveVO);
        this.getSuperManager().save(entity);
        saveAfter(saveVO, entity);
        return entity;
    }

    /**
     * 保存之前处理参数等操作
     *
     * @param saveVO 保存VO
     */
    protected Entity saveBefore(SaveVO saveVO) {
        return BeanUtil.toBean(saveVO, getEntityClass());
    }

    /**
     * 保存之后设置参数值，淘汰缓存等操作
     *
     * @param saveVO 保存VO
     * @param entity 实体
     */
    protected void saveAfter(SaveVO saveVO, Entity entity) {
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Entity updateById(UpdateVO updateVO) {
        Entity entity = updateBefore(updateVO);
        getSuperManager().updateById(entity);
        updateAfter(updateVO, entity);
        return entity;
    }

    /**
     * 修改之前处理参数等操作
     *
     * @param updateVO 修改VO
     */
    protected Entity updateBefore(UpdateVO updateVO) {
        return BeanUtil.toBean(updateVO, getEntityClass());
    }

    /**
     * 修改之后设置参数值，淘汰缓存等操作
     *
     * @param updateVO 修改VO
     * @param entity   实体
     */
    protected void updateAfter(UpdateVO updateVO, Entity entity) {
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Entity updateAllById(UpdateVO updateVO) {
        Entity entity = updateAllBefore(updateVO);
        getSuperManager().updateAllById(entity);
        updateAllAfter(updateVO, entity);
        return entity;
    }

    @Override
    public Entity getById(Serializable id) {
        return getSuperManager().getById(id);
    }

    @Override
    public void removeByIds(List<Id> ids) {
        getSuperManager().removeByIds(ids);
    }

    @Override
    public void page(IPage<Entity> page, Wrapper<Entity> wrapper) {
        getSuperManager().page(page, wrapper);
    }

    @Override
    public List<Entity> list() {
        return getSuperManager().list();
    }

    @Override
    public List<Entity> list(Wrapper<Entity> wrapper) {
        return getSuperManager().list(wrapper);
    }

    @Override
    public Entity copy(Id id) {
        Entity old = getById(id);
        ArgumentAssert.notNull(old, "您要复制的数据不存在或已被删除，请刷新重试");
        Entity entity = BeanPlusUtil.toBean(old, getEntityClass());
        entity.setId(null);
        superManager.save(entity);

        return entity;
    }

    @Override
    public List<Entity> listByIds(Collection<? extends Serializable> idList) {
        return getSuperManager().listByIds(idList);
    }

    @Override
    public boolean removeById(Serializable id) {
        return getSuperManager().removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<?> idList) {
        return getSuperManager().removeByIds(idList);
    }

    /**
     * 修改之前处理参数等操作
     *
     * @param updateVO 修改VO
     */
    protected Entity updateAllBefore(UpdateVO updateVO) {
        return BeanUtil.toBean(updateVO, getEntityClass());
    }

    /**
     * 修改之后设置参数值，淘汰缓存等操作
     *
     * @param updateVO 修改VO
     * @param entity   实体
     */
    protected void updateAllAfter(UpdateVO updateVO, Entity entity) {
    }

    @Override
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