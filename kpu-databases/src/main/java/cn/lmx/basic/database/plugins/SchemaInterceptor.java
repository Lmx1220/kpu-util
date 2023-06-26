package cn.lmx.basic.database.plugins;

import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.context.ContextUtil;
import cn.lmx.basic.database.parsers.ReplaceSql;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author lmx
 * @version 1.0
 * @description: SCHEMA模式插件
 * @date 2023/7/4 14:27
 */
@Slf4j
public class SchemaInterceptor implements InnerInterceptor {

    private final String owner;
    private final String tenantDatabasePrefix;
    private final DbType dbType;

    public SchemaInterceptor(String tenantDatabasePrefix, String owner, DbType dbType) {
        this.tenantDatabasePrefix = tenantDatabasePrefix;
        this.owner = owner;
        this.dbType = dbType;
    }

    protected String changeTable(String sql) {
        // 想要 执行sql时， 不切换到 kpu_base_{TENANT} 库, 请直接返回null
        String tenantCode = ContextUtil.getTenant();
        String database = ContextUtil.getDatabase();
        if (StrUtil.isEmpty(tenantCode)) {
            return sql;
        }

        String schemaName = StrUtil.format("{}_{}", StrUtil.isEmpty(database) ? tenantDatabasePrefix : database, tenantCode);
        if (StrUtil.isNotEmpty(owner)) {
            schemaName += "." + owner;
        }
        // 想要 执行sql时， 切换到 切换到自己指定的库， 直接修改 setSchemaName
        return ReplaceSql.replaceSql(dbType, schemaName, sql);
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        // 统一交给 beforePrepare 处理,防止某些sql解析不到，又被beforePrepare重复处理
    }


    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE || sct == SqlCommandType.SELECT) {
            if (InterceptorIgnoreHelper.willIgnoreDynamicTableName(ms.getId())) {
                return;
            }
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            log.debug("未替换前的sql: {}", mpBs.sql());
            mpBs.sql(this.changeTable(mpBs.sql()));
        }
        ContextUtil.clearDatabase();
    }
}
