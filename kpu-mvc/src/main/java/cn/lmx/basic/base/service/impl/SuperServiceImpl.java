package cn.lmx.basic.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.manager.SuperManager;
import cn.lmx.basic.base.manager.impl.SuperManagerImpl;
import cn.lmx.basic.base.service.SuperService;
import cn.lmx.basic.database.mybatis.conditions.query.QueryWrap;
import cn.lmx.basic.utils.ArgumentAssert;
import cn.lmx.basic.utils.BeanPlusUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * @author lmx
 * @date 2023/7/4 14:27
 */

/**
 * @param <M>         Manager
 * @param <Id>        ID
 * @param <Entity>    实体
 * @param <SaveVO>    保存VO
 * @param <UpdateVO>  修改VO
 * @param <PageQuery> 分页查询参数
 * @param <ResultVO>  返回VO
 * @author lmx
 * @version 1.0
 * @description: 不含缓存的Service实现
 * <p>
 * 2，removeById：重写 ServiceImpl 类的方法，删除db
 * 3，removeByIds：重写 ServiceImpl 类的方法，删除db
 * 4，updateAllById： 新增的方法： 修改数据（所有字段）
 * 5，updateById：重写 ServiceImpl 类的方法，修改db后
 * @date 2023/7/4 14:27
 */


public class SuperServiceImpl<M extends SuperManager<Entity>, Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO>
        implements SuperService<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {
    @Autowired
    protected M superManager;
    protected Class<M> managerClass = currentManagerClass();
    protected Class<Entity> entityClass = currentModelClass();
    protected Class<Id> idClass = currentIdClass();

    @Override
    public M getSuperManager() {
        return superManager;
    }

    @Override
    public Class<Entity> getEntityClass() {
        return entityClass;
    }

    @Override
    public Class<Id> getIdClass() {
        return idClass;
    }

    protected Class<M> currentManagerClass() {
        return (Class<M>) ReflectionKit.getSuperClassGenericType(this.getClass(), SuperManagerImpl.class, 0);
    }

    protected Class<Id> currentIdClass() {
        return (Class<Id>) ReflectionKit.getSuperClassGenericType(this.getClass(), SuperManagerImpl.class, 1);
    }

    protected Class<Entity> currentModelClass() {
        return (Class<Entity>) ReflectionKit.getSuperClassGenericType(this.getClass(), SuperManagerImpl.class, 2);
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
    public void page(IPage<Entity> page, QueryWrap<Entity> wrapper) {
        getSuperManager().page(page, wrapper);
    }

    @Override
    public List<Entity> list() {
        return getSuperManager().list();
    }

    @Override
    public List<Entity> list(QueryWrap<Entity> wrapper) {
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


}
