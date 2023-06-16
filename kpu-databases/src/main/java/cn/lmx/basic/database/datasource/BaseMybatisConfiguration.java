package cn.lmx.basic.database.datasource;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.constant.Constants;
import cn.lmx.basic.context.ContextUtil;
import cn.lmx.basic.database.injector.KpuSqlInjector;
import cn.lmx.basic.database.mybatis.WriteInterceptor;
import cn.lmx.basic.database.mybatis.typehandler.FullLikeTypeHandler;
import cn.lmx.basic.database.mybatis.typehandler.LeftLikeTypeHandler;
import cn.lmx.basic.database.mybatis.typehandler.RightLikeTypeHandler;
import cn.lmx.basic.database.plugins.SchemaInterceptor;
import cn.lmx.basic.database.properties.DatabaseProperties;
import cn.lmx.basic.database.properties.MultiTenantType;
import cn.lmx.basic.uid.dao.WorkerNodeDao;
import cn.lmx.basic.utils.ArgumentAssert;
import com.baidu.fsg.uid.UidGenerator;
import com.baidu.fsg.uid.buffer.RejectedPutBufferHandler;
import com.baidu.fsg.uid.buffer.RejectedTakeBufferHandler;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.baidu.fsg.uid.impl.DefaultUidGenerator;
import com.baidu.fsg.uid.impl.HuToolUidGenerator;
import com.baidu.fsg.uid.worker.DisposableWorkerIdAssigner;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author lmx
 * @version 1.0
 * @description: Mybatis 常用重用拦截器，kpu.database.multiTenantType=任意模式 都需要实例出来
 * <p>
 * 拦截器执行一定是：
 * WriteInterceptor > DataScopeInterceptor > PaginationInterceptor
 * @date 2023/6/16 15:50
 */
@Slf4j
public abstract class BaseMybatisConfiguration {
    protected final DatabaseProperties databaseProperties;

    public BaseMybatisConfiguration(final DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    /**
     * 演示环境权限拦截器
     *
     * @return 写入拦截器
     */
    @Bean
    @Order(15)
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "isNotWrite", havingValue = "true")
    public WriteInterceptor getWriteInterceptor() {
        return new WriteInterceptor();
    }


    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     * <p>
     * 注意:
     * 如果内部插件都是使用,需要注意顺序关系,建议使用如下顺序
     * 多租户插件,动态表名插件
     * 分页插件,乐观锁插件
     * sql性能规范插件,防止全表更新与删除插件
     * 总结: 对sql进行单次改造的优先放入,不对sql进行改造的最后放入
     * <p>
     * 参考：
     * https://mybatis.plus/guide/interceptor.html#%E4%BD%BF%E7%94%A8%E6%96%B9%E5%BC%8F-%E4%BB%A5%E5%88%86%E9%A1%B5%E6%8F%92%E4%BB%B6%E4%B8%BE%E4%BE%8B
     */
    @Bean
    @Order(5)
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        log.info("检测到 kpu.database.multiTenantType={}，已启用 {} 模式", databaseProperties.getMultiTenantType().name(), databaseProperties.getMultiTenantType().getDescribe());
        if (StrUtil.equalsAny(databaseProperties.getMultiTenantType().name(),
                MultiTenantType.SCHEMA.name(), MultiTenantType.SCHEMA_COLUMN.name())) {
            ArgumentAssert.notNull(databaseProperties.getDbType(), "SCHEMA 模式请在mysql.yml、oracle.yml、sqlserver.yml中配置: {}.dbType", DatabaseProperties.PREFIX);

            // SCHEMA 动态表名插件
            SchemaInterceptor schemaInterceptor = new SchemaInterceptor(databaseProperties.getTenantDatabasePrefix(), databaseProperties.getOwner(), databaseProperties.getDbType());
            interceptor.addInnerInterceptor(schemaInterceptor);
        }
        if (StrUtil.equalsAny(databaseProperties.getMultiTenantType().name(),
                MultiTenantType.COLUMN.name(), MultiTenantType.SCHEMA_COLUMN.name(), MultiTenantType.DATASOURCE_COLUMN.name())) {
            // COLUMN 模式 多租户插件
            TenantLineInnerInterceptor tli = new TenantLineInnerInterceptor();
            tli.setTenantLineHandler(new TenantLineHandler() {
                @Override
                public String getTenantIdColumn() {
                    return databaseProperties.getTenantIdColumn();
                }

                @Override
                public boolean ignoreTable(String tableName) {
                    return databaseProperties.getIgnoreTables() != null && databaseProperties.getIgnoreTables().contains(tableName);
                }

                @Override
                public Expression getTenantId() {
                    return MultiTenantType.COLUMN.eq(databaseProperties.getMultiTenantType()) ?
                            new StringValue(ContextUtil.getTenant()) :
                            new StringValue(ContextUtil.getSubTenant());
                }
            });
            interceptor.addInnerInterceptor(tli);
        }

        List<InnerInterceptor> beforeInnerInterceptor = getPaginationBeforeInnerInterceptor();
        if (!beforeInnerInterceptor.isEmpty()) {
            beforeInnerInterceptor.forEach(interceptor::addInnerInterceptor);
        }

        // 分页插件
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // 单页分页条数限制
        paginationInterceptor.setMaxLimit(databaseProperties.getMaxLimit());
        // 数据库类型
        if (databaseProperties.getDbType() != null) {
            paginationInterceptor.setDbType(databaseProperties.getDbType());
        }
        // 溢出总页数后是否进行处理
        paginationInterceptor.setOverflow(databaseProperties.getOverflow());
        // 生成 countSql 优化掉 join 现在只支持 left join
        paginationInterceptor.setOptimizeJoin(databaseProperties.getOptimizeJoin());
        interceptor.addInnerInterceptor(paginationInterceptor);


        List<InnerInterceptor> afterInnerInterceptor = getPaginationAfterInnerInterceptor();
        if (!afterInnerInterceptor.isEmpty()) {
            afterInnerInterceptor.forEach(interceptor::addInnerInterceptor);
        }

        //防止全表更新与删除插件
        if (databaseProperties.getIsBlockAttack()) {
            interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        }
        // sql性能规范插件
        if (databaseProperties.getIsIllegalSql()) {
            interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        }

        return interceptor;
    }


    /**
     * 数据库配置
     *
     * @return 配置
     */
    @Bean
    public DatabaseIdProvider getDatabaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("Oracle", DbType.ORACLE.getDb());
        properties.setProperty("MySQL", DbType.MYSQL.getDb());
        properties.setProperty("Microsoft SQL Server", DbType.SQL_SERVER.getDb());
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }


    /**
     * 分页拦截器之后的插件
     *
     * @return 分页拦截器之后的插件
     */
    protected List<InnerInterceptor> getPaginationAfterInnerInterceptor() {
        return new ArrayList<>();
    }

    /**
     * 分页拦截器之前的插件
     *
     * @return 分页拦截器之前的插件
     */
    protected List<InnerInterceptor> getPaginationBeforeInnerInterceptor() {
        return new ArrayList<>();
    }

    /**
     * Mybatis Plus 注入器
     *
     * @return 注入器
     */
    @Bean("myMetaObjectHandler")
    @ConditionalOnMissingBean
    public MetaObjectHandler getMyMetaObjectHandler() {
        return new KpuMetaObjectHandler();
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnExpression("'DEFAULT'.equals('${" + Constants.PROJECT_PREFIX + ".database.id-type:DEFAULT}') || 'CACHE'.equals('${" + Constants.PROJECT_PREFIX + ".database.id-type:DEFAULT}')")
    public DisposableWorkerIdAssigner disposableWorkerIdAssigner(WorkerNodeDao workerNodeDao) {
        return new DisposableWorkerIdAssigner(workerNodeDao);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "id-type", havingValue = "DEFAULT", matchIfMissing = true)
    public UidGenerator getDefaultUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
        DefaultUidGenerator uidGenerator = new DefaultUidGenerator();
        BeanUtil.copyProperties(databaseProperties.getDefaultId(), uidGenerator);
        uidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        return uidGenerator;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "id-type", havingValue = "CACHE")
    public UidGenerator getCacheUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner) {
        CachedUidGenerator uidGenerator = new CachedUidGenerator();
        DatabaseProperties.CacheId cacheId = databaseProperties.getCacheId();
        BeanUtil.copyProperties(cacheId, uidGenerator);
        if (cacheId.getRejectedPutBufferHandlerClass() != null) {
            RejectedPutBufferHandler rejectedPutBufferHandler = ReflectUtil.newInstance(cacheId.getRejectedPutBufferHandlerClass());
            uidGenerator.setRejectedPutBufferHandler(rejectedPutBufferHandler);
        }
        if (cacheId.getRejectedTakeBufferHandlerClass() != null) {
            RejectedTakeBufferHandler rejectedTakeBufferHandler = ReflectUtil.newInstance(cacheId.getRejectedTakeBufferHandlerClass());
            uidGenerator.setRejectedTakeBufferHandler(rejectedTakeBufferHandler);
        }
        uidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        return uidGenerator;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "id-type", havingValue = "HU_TOOL")
    public UidGenerator getHuToolUidGenerator() {
        DatabaseProperties.HutoolId id = databaseProperties.getHutoolId();
        return new HuToolUidGenerator(id.getWorkerId(), id.getDataCenterId());
    }


    /**
     * Mybatis 自定义的类型处理器： 处理XML中  #{name,typeHandler=leftLike} 类型的参数
     * 用于左模糊查询时使用
     * <p>
     * eg：
     * and name like #{name,typeHandler=leftLike}
     *
     * @return 左模糊处理器
     */
    @Bean
    public LeftLikeTypeHandler getLeftLikeTypeHandler() {
        return new LeftLikeTypeHandler();
    }

    /**
     * Mybatis 自定义的类型处理器： 处理XML中  #{name,typeHandler=rightLike} 类型的参数
     * 用于右模糊查询时使用
     * <p>
     * eg：
     * and name like #{name,typeHandler=rightLike}
     *
     * @return 右模糊处理器
     */
    @Bean
    public RightLikeTypeHandler getRightLikeTypeHandler() {
        return new RightLikeTypeHandler();
    }

    /**
     * Mybatis 自定义的类型处理器： 处理XML中  #{name,typeHandler=fullLike} 类型的参数
     * 用于全模糊查询时使用
     * <p>
     * eg：
     * and name like #{name,typeHandler=fullLike}
     *
     * @return 全模糊处理器
     */
    @Bean
    public FullLikeTypeHandler getFullLikeTypeHandler() {
        return new FullLikeTypeHandler();
    }


    @Bean
    @ConditionalOnMissingBean
    public KpuSqlInjector getMySqlInjector() {
        return new KpuSqlInjector();
    }

}