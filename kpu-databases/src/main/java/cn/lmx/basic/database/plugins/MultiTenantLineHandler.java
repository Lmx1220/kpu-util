package cn.lmx.basic.database.plugins;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.ValueListExpression;

/**
 * @author lmx
 * @version 1.0
 * @date 2023/7/4 14:27
 */
public interface MultiTenantLineHandler extends TenantLineHandler {
    /**
     * 获取租户 ID 值表达式，支持多个 ID 值
     * <p>
     *
     * @return 租户 ID 值表达式
     */
    ValueListExpression getTenantIdList();
}
