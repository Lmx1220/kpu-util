package cn.lmx.basic.base.controller;

import cn.lmx.basic.annotation.log.SysLog;
import cn.lmx.basic.base.R;
import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.database.mybatis.conditions.Wraps;
import cn.lmx.basic.database.mybatis.conditions.query.QueryWrap;
import cn.lmx.basic.interfaces.echo.EchoService;
import cn.lmx.basic.utils.BeanPlusUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @param <Entity>    实体
 * @param <Id>        主键
 * @param <PageQuery> 分页参数
 * @author lmx
 * @version 1.0
 * @description: 查询Controller
 * @date 2023/7/4 14:27
 */
public interface QueryController<Id extends Serializable, Entity extends SuperEntity<Id>, SaveVO, UpdateVO, PageQuery, ResultVO>
        extends PageController<Id, Entity, SaveVO, UpdateVO, PageQuery, ResultVO> {
    /**
     * 单体查询
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "path"),
    })
    @ApiOperation(value = "单体查询", notes = "单体查询")
    @GetMapping("/{id}")
    @SysLog("'查询:' + #id")
    default R<ResultVO> get(@PathVariable Id id) {
        Entity entity = getSuperService().getById(id);
        return success(BeanPlusUtil.toBean(entity, getResultVOClass()));
    }

    /**
     * 详情查询
     *
     * @param id 主键id
     * @return 查询结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "long", paramType = "path"),
    })
    @ApiOperation(value = "查询单体详情", notes = "查询单体详情")
    @GetMapping("/detail")
    @SysLog("'查询:' + #id")
    default R<ResultVO> getDetail(@RequestParam("id") Id id) {
        Entity entity = getSuperService().getById(id);
        ResultVO resultVO = BeanPlusUtil.toBean(entity, getResultVOClass());
        EchoService echoService = getEchoService();
        if (echoService != null) {
            echoService.action(resultVO);
        }
        return success(resultVO);
    }

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    @SysLog("批量查询")
    default R<List<ResultVO>> query(@RequestBody PageQuery data) {
        Entity entity = BeanPlusUtil.toBean(data, getEntityClass());
        QueryWrap<Entity> wrapper = Wraps.q(entity);
        List<Entity> list = getSuperService().list(wrapper);
        return success(BeanPlusUtil.toBeanList(list, getResultVOClass()));
    }
}
