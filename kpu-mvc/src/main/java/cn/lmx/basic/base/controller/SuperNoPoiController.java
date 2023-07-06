package cn.lmx.basic.base.controller;


import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.service.SuperService;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

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
 * @description: SuperNoPoiController
 * <p>
 * 继承该类，就拥有了如下方法：
 * 1，page 分页查询，并支持子类扩展4个方法：handlerQueryParams、query、handlerWrapper、handlerResult
 * 2，save 保存，并支持子类扩展方法：handlerSave
 * 3，update 修改，并支持子类扩展方法：handlerUpdate
 * 4，delete 删除，并支持子类扩展方法：handlerDelete
 * 5，get 单体查询， 根据ID直接查询DB
 * 6，list 列表查询，根据参数条件，查询列表
 * <p>
 * 若重写扩展方法无法满足，则可以重写page、save等方法，但切记不要修改 @RequestMapping 参数
 * @date 2023/7/4 14:27
 */
public abstract class SuperNoPoiController<S extends SuperService<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO>, Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO> extends SuperSimpleController<S, Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO>
        implements SaveController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO>, UpdateController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO>, DeleteController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO>, QueryController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {

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
            this.resultVOClass = (Class<ResultVO>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        }
        return this.resultVOClass;
    }
}
