package cn.lmx.basic.base.controller;

import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.service.SuperService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * @param <S>      Service
 * @param <Entity> 实体
 * @author lmx
 * @version 1.0
 * @description: 简单的实现了BaseController，为了获取注入 Service 和 实体类型
 * <p>
 * 基类该类后，没有任何方法。
 * 可以让业务Controller继承 SuperSimpleController 后，按需实现 *Controller 接口
 * @date 2023/7/4 14:27
 */
public abstract class SuperSimpleController<S extends SuperService<Entity>, Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO> implements BaseController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {

    @Autowired
    protected S baseService;
    Class<Entity> entityClass = null;
    Class<ResultVO> resultVOClass = null;

    @Override
    public Class<Entity> getEntityClass() {
        if (entityClass == null) {
            this.entityClass = (Class<Entity>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        }
        return this.entityClass;
    }

    @Override
    public Class<ResultVO> getResultVOClass() {
        if (resultVOClass == null) {
            this.resultVOClass = (Class<ResultVO>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[6];
        }
        return this.resultVOClass;
    }

    @Override
    public SuperService<Entity> getBaseService() {
        return baseService;
    }
}
