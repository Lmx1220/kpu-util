package cn.lmx.basic.database.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.constant.Constants;
import cn.lmx.basic.context.ContextUtil;
import cn.lmx.basic.exception.BizException;
import cn.lmx.basic.utils.SpringUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Properties;

import static org.apache.ibatis.mapping.SqlCommandType.*;


/**
 * @author lmx
 * @version 1.0
 * @description: 演示环境写权限控制 拦截器 该拦截器只用于演示环境， 开发和生产都不需要
 * @date 2023/6/16 15:43
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
@Slf4j
@NoArgsConstructor
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class WriteInterceptor implements Interceptor {


    @Override
    @SneakyThrows
    public Object intercept(Invocation invocation) {
        // 为什么在拦截器里使用 @RefreshScope 无效？
        if (SpringUtils.getApplicationContext() == null) {
            return invocation.proceed();
        }
        if (!SpringUtils.getApplicationContext().getEnvironment().getProperty(Constants.PROJECT_PREFIX + ".database.isNotWrite", Boolean.class, false)) {
            return invocation.proceed();
        }

        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
//        sqlParser(metaObject);
        // 读操作 放行
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }
        // 记录日志相关的 放行
        if (StrUtil.containsAnyIgnoreCase(mappedStatement.getId(), "uid", "resetPassErrorNum", "updateLastLoginTime")) {
            return invocation.proceed();
        }
        // userId=1 的超级管理员 放行
        Long userId = ContextUtil.getUserId();
        String tenant = ContextUtil.getTenant();
        log.info("mapper id={}, userId={}", mappedStatement.getId(), userId);


        //演示用的超级管理员 能查 和 增
        if (Long.valueOf(2).equals(userId) && (DELETE.equals(mappedStatement.getSqlCommandType()))) {
            throw new BizException(-1, "演示环境，无删除权限，请本地部署后测试");
        }

        //内置的租户 不能 修改、删除 权限数据
        boolean isAuthority = StrUtil.containsAnyIgnoreCase(mappedStatement.getId(), "Tenant", "GlobalUser", "User", "Menu", "Resource", "Role", "Dictionary", "Parameter", "Application");
        boolean isWrite = CollectionUtil.contains(Arrays.asList(DELETE, UPDATE, INSERT), mappedStatement.getSqlCommandType());
        if ("0000".equals(tenant) && isWrite && isAuthority) {
            throw new BizException(-1, "演示环境禁止修改、删除重要数据！请登录租户【0000】，账号【kpu_pt】创建其他租户管理员账号后测试全部功能");
        }

        // 你还可以自定义其他限制规则， 比如：IP 等

        //放行
        return invocation.proceed();
    }

    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * mybatis配置的属性
     *
     * @param properties mybatis配置的属性
     */
    @Override
    public void setProperties(Properties properties) {

    }

}
