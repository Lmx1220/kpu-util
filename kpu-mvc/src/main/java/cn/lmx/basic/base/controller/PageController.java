package cn.lmx.basic.base.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.lmx.basic.base.request.PageParams;
import cn.lmx.basic.database.mybatis.conditions.Wraps;
import cn.lmx.basic.database.mybatis.conditions.query.QueryWrap;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @param <Entity>    实体
 * @param <PageQuery> 分页参数
 * @author lmx
 * @version 1.0
 * @description: 分页控制器
 * @date 2023/6/16 18:03
 */
public interface PageController<Entity, PageQuery> extends BaseController<Entity> {

    /**
     * 处理查询参数
     *
     * @param params 前端传递的参数
     * @author lmx
     * @date 2023/6/17 13:24
     */
    default void handlerQueryParams(PageParams<PageQuery> params) {
    }

    /**
     * 执行分页查询
     * <p>
     * 子类可以覆盖后重写查询逻辑
     *
     * @param params 分页参数
     * @return 分页信息
     */
    default IPage<Entity> query(PageParams<PageQuery> params) {
        // 处理查询参数，如：覆盖前端传递的 current、size、sort 等参数 以及 model 中的参数 【提供给之类重写】【无默认实现】
        handlerQueryParams(params);

        // 构建分页参数(current、size)和排序字段等
        IPage<Entity> page = params.buildPage(getEntityClass());
        Entity model = BeanUtil.toBean(params.getModel(), getEntityClass());

        // 根据前端传递的参数，构建查询条件【提供给之类重写】【有默认实现】
        QueryWrap<Entity> wrapper = handlerWrapper(model, params);

        // 执行单表分页查询
        getBaseService().page(page, wrapper);

        // 处理查询后的分页结果， 如：调用EchoService回显字典、关联表数据等 【提供给之类重写】【无默认实现】
        handlerResult(page);
        return page;
    }

    /**
     * 处理对象中的非空参数和扩展字段中的区间参数，可以覆盖后处理组装查询条件
     *
     * @param model  实体类
     * @param params 分页参数
     * @return 查询构造器
     */
    default QueryWrap<Entity> handlerWrapper(Entity model, PageParams<PageQuery> params) {
        return Wraps.q(model, params.getExtra(), getEntityClass());
    }

    /**
     * 处理查询后的数据
     * <p>
     * 如：执行@Echo回显
     *
     * @param page 分页对象
     */
    default void handlerResult(IPage<Entity> page) {
    }
}
