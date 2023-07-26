package cn.lmx.basic.base.controller;

import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.service.SuperService;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @param <S>         Service
 * @param <Id>        ID
 * @param <Entity>    实体
 * @param <SaveVO>    保存VO
 * @param <UpdateVO>  修改VO
 * @param <PageQuery> 分页查询参数
 * @param <ResultVO>  返回VO
 * @author lmx
 * @version 1.0
 * @description: 简单的实现了BaseController，为了获取注入 Service 和 实体类型
 * <p>
 * 基类该类后，没有任何方法。
 * 可以让业务Controller继承 SuperSimpleController 后，按需实现 *Controller 接口
 * @date 2023/7/4 14:27
 */
public abstract class SuperSimpleController<S extends SuperService<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO>,
        Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO>
        implements BaseController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {

    @Autowired
    protected S superService;
    protected Class<Entity> entityClass = currentModelClass();
    protected Class<ResultVO> resultVOClass = currentResultVOClass();

    protected Class<Entity> currentModelClass() {
        return (Class<Entity>) ReflectionKit.getSuperClassGenericType(this.getClass(), SuperSimpleController.class, 2);
    }

    protected Class<ResultVO> currentResultVOClass() {
        return (Class<ResultVO>) ReflectionKit.getSuperClassGenericType(this.getClass(), SuperSimpleController.class, 6);
    }

    @Override
    public Class<Entity> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public Class<ResultVO> getResultVOClass() {
        return this.resultVOClass;
    }

    @Override
    public SuperService<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> getSuperService() {
        return superService;
    }
}
