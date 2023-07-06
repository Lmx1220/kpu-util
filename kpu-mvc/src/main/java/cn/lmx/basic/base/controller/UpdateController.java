package cn.lmx.basic.base.controller;

import cn.lmx.basic.annotation.log.SysLog;
import cn.lmx.basic.annotation.security.PreAuth;
import cn.lmx.basic.base.R;
import cn.lmx.basic.base.entity.SuperEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
 * @description: 修改Controller
 * @date 2023/7/4 14:27
 */
public interface UpdateController<Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO> extends BaseController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {

    /**
     * 修改
     *
     * @param updateVO 修改DTO
     * @return 修改后的实体数据
     */
    @ApiOperation(value = "修改", notes = "修改UpdateDTO中不为空的字段")
    @PutMapping
    @SysLog(value = "'修改:' + #updateDTO?.id", request = false)
    @PreAuth("hasAnyPermission('{}edit')")
    default R<Entity> update(@RequestBody @Validated(SuperEntity.Update.class) UpdateVO updateVO) {
        R<Entity> result = handlerUpdate(updateVO);
        if (result.getDefExec()) {
            return R.success(getSuperService().updateById(updateVO));
        }
        return result;
    }

    /**
     * 修改所有字段
     *
     * @param entity 实体
     * @return
     */
    @ApiOperation(value = "修改所有字段", notes = "修改所有字段，没有传递的字段会被置空")
    @PutMapping("/all")
    @SysLog(value = "'修改所有字段:' + #entity?.id", request = false)
    @PreAuth("hasAnyPermission('{}edit')")
    default R<Entity> updateAll(@RequestBody @Validated(SuperEntity.Update.class) UpdateVO entity) {
        R<Entity> result = handlerUpdate(entity);
        if (result.getDefExec()) {
            return R.success(getSuperService().updateAllById(entity));
        }
        return result;
    }

    /**
     * 自定义更新
     *
     * @param model 修改DTO
     * @return 返回SUCCESS_RESPONSE, 调用默认更新, 返回其他不调用默认更新
     */
    default R<Entity> handlerUpdate(UpdateVO model) {
        return R.successDef();
    }
}
