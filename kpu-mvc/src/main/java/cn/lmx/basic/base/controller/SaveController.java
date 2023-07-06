package cn.lmx.basic.base.controller;

import cn.lmx.basic.annotation.log.SysLog;
import cn.lmx.basic.annotation.security.PreAuth;
import cn.lmx.basic.base.R;
import cn.lmx.basic.base.entity.SuperEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

/**
 * @param <Id>        ID
 * @param <Entity>    实体
 * @param <SaveVO>    保存VO
 * @param <UpdateVO>  修改VO
 * @param <PageQuery> 分页查询参数
 * @param <ResultVO>  返回VO
 * @author lmx
 * @version 1.0
 * @description: 新增
 * @date 2023/7/4 14:27
 */
public interface SaveController<Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO> extends BaseController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {

    /**
     * 新增
     *
     * @param saveVO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    @SysLog(value = "新增", request = false)
    @PreAuth("hasAnyPermission('{}add')")
    default R<Entity> save(@RequestBody @Validated SaveVO saveVO) {
        R<Entity> result = handlerSave(saveVO);
        if (result.getDefExec()) {
            return R.success(getSuperService().save(saveVO));
        }
        return result;
    }

    /**
     * 新增
     *
     * @param id ID
     * @return 实体
     */
    @ApiOperation(value = "复制")
    @PostMapping
    @SysLog(value = "复制", request = false)
    default R<Entity> save(@RequestParam("id") Id id) {
        return R.success(getSuperService().copy(id));
    }

    /**
     * 自定义新增
     *
     * @param model 保存对象
     * @return 返回SUCCESS_RESPONSE, 调用默认更新, 返回其他不调用默认更新
     */
    default R<Entity> handlerSave(SaveVO model) {
        return R.successDef();
    }

}
