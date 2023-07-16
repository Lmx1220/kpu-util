package cn.lmx.basic.base.controller;

import cn.lmx.basic.annotation.log.SysLog;
import cn.lmx.basic.annotation.security.PreAuth;
import cn.lmx.basic.base.R;
import cn.lmx.basic.base.entity.SuperEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

/**
 * @param <Entity> 实体
 * @param <Id>     主键
 * @author lmx
 * @version 1.0
 * @description: 删除Controller
 * @date 2023/7/4 14:27
 */
public interface DeleteController<Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO> extends BaseController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {

    /**
     * 删除方法
     *
     * @param ids id
     * @return 是否成功
     */
    @ApiOperation(value = "删除")
    @DeleteMapping
    @SysLog("'删除:' + #ids")
    @PreAuth("hasAnyPermission('{}delete')")
    default R<Boolean> delete(@RequestBody List<Id> ids) {
        R<Boolean> result = handlerDelete(ids);
        if (result.getDefExec()) {
            getBaseService().removeByIds(ids);
        }
        return result;
    }

    /**
     * 自定义删除
     *
     * @param ids id
     * @return 返回SUCCESS_RESPONSE, 调用默认更新, 返回其他不调用默认更新
     */
    default R<Boolean> handlerDelete(List<Id> ids) {
        return R.successDef(true);
    }

}
