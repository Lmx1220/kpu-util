package cn.lmx.basic.base.controller;

import cn.lmx.basic.annotation.log.SysLog;
import cn.lmx.basic.annotation.security.PreAuth;
import cn.lmx.basic.base.R;
import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.service.SuperCacheService;
import cn.lmx.basic.utils.BeanPlusUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.Serializable;

/**
 * @author lmx
 * @version 1.0
 * @description: SuperCacheController
 * <p>
 * 继承该类，在SuperController类的基础上扩展了以下方法：
 * 1，get ： 根据ID查询缓存，若缓存不存在，则查询DB
 * @date 2023/7/4 14:27
 */
public abstract class SuperCacheController<S extends SuperCacheService<Entity>, Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO>
        extends SuperController<S, Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {

    /**
     * 查询
     *
     * @param id 主键id
     * @return 查询结果
     */
    @Override
    @SysLog("'查询:' + #id")
    @PreAuth("hasAnyPermission('{}view')")
    public R<ResultVO> get(@PathVariable Id id) {
        return success(BeanPlusUtil.toBean(baseService.getByIdCache(id), getResultVOClass()));
    }


    /**
     * 刷新缓存
     *
     * @return 是否成功
     */
    @ApiOperation(value = "刷新缓存", notes = "刷新缓存")
    @PostMapping("refreshCache")
    @SysLog("'刷新缓存'")
    @PreAuth("hasAnyPermission('{}add')")
    public R<Boolean> refreshCache() {
        baseService.refreshCache();
        return success(true);
    }

    /**
     * 清理缓存
     *
     * @return 是否成功
     */
    @ApiOperation(value = "清理缓存", notes = "清理缓存")
    @PostMapping("clearCache")
    @SysLog("'清理缓存'")
    @PreAuth("hasAnyPermission('{}add')")
    public R<Boolean> clearCache() {
        baseService.clearCache();
        return success(true);
    }
}
