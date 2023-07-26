package cn.lmx.basic.base.service;

import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.manager.SuperManager;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @param <T> 实体
 * @author lmx
 * @version 1.0
 * @description: 基于MP的 IService 新增了2个方法： saveBatchSomeColumn、updateAllById
 * 其中：
 * 1，updateAllById 执行后，会清除缓存
 * 2，saveBatchSomeColumn 批量插入
 * @date 2023/7/4 14:27
 */
@SuppressWarnings("ALL")
public interface SuperService<Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO> {
    Class<Id> getIdClass();

    Class<Entity> getEntityClass();


    SuperManager getSuperManager();


    Entity save(SaveVO saveVO);

    Entity updateById(UpdateVO updateVO);

    Entity updateAllById(UpdateVO updateVO);

    Entity getById(Serializable id);

    void removeByIds(List<Id> ids);

    void page(IPage<Entity> page, Wrapper<Entity> wrapper);

    List<Entity> list();

    List<Entity> list(Wrapper<Entity> wrapper);

    Entity copy(Id id);

    List<Entity> listByIds(Collection<? extends Serializable> idList);

    boolean removeById(Serializable id);

    boolean removeByIds(Collection<?> idList);

    boolean remove(Wrapper<Entity> wrapper);

    boolean saveBatch(Collection<Entity> entityList);

}
