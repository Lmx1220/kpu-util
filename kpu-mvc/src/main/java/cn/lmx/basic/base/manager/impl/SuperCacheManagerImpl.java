package cn.lmx.basic.base.manager.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.manager.SuperCacheManager;
import cn.lmx.basic.base.mapper.SuperMapper;
import cn.lmx.basic.cache.redis2.CacheResult;
import cn.lmx.basic.cache.repository.CacheOps;
import cn.lmx.basic.model.cache.CacheKey;
import cn.lmx.basic.model.cache.CacheKeyBuilder;
import cn.lmx.basic.utils.CollHelper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lmx
 * @version v1.0.0
 * @date 2023/07/06  16:10
 */
public abstract class SuperCacheManagerImpl<M extends SuperMapper<T>, T extends SuperEntity> extends SuperManagerImpl<M, T> implements SuperCacheManager<T> {
    protected static final int MAX_BATCH_KEY_SIZE = 500;
    @Autowired
    protected CacheOps cacheOps;

    /**
     * 缓存key 构造器
     *
     * @return 缓存key构造器
     */
    protected abstract CacheKeyBuilder cacheKeyBuilder();


    @Override
    @Transactional(readOnly = true)
    public T getByIdCache(Serializable id) {
        CacheKey cacheKey = cacheKeyBuilder().key(id);
        CacheResult<T> result = cacheOps.get(cacheKey, k -> super.getById(id));
        return result.getValue();
    }

        private List<CacheResult<T>> find(List<CacheKey> keys) {
        return cacheOps.find(keys);
    }
    @Override
    @Transactional(readOnly = true)
    public List<T> findByIds(@NonNull Collection<? extends Serializable> ids, Function<Collection<? extends Serializable>, Collection<T>> loader) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        // 拼接keys
        List<CacheKey> keys = ids.stream().map(cacheKeyBuilder()::key).collect(Collectors.toList());
        // 切割
        List<List<CacheKey>> partitionKeys = Lists.partition(keys, MAX_BATCH_KEY_SIZE);

        // 用切割后的 partitionKeys 分批去缓存查， 返回的是缓存中存在的数据
        List<CacheResult<T>> valueList = partitionKeys.stream().map(this::find).flatMap(Collection::stream).collect(Collectors.toList());

        // 所有的key
        List<Serializable> keysList = Lists.newArrayList(ids);
        log.debug(StrUtil.format("keysList:{}, valueList:{}", keysList.size(), valueList.size()));
        // 缓存不存在的key
        Set<Serializable> missedIds = Sets.newLinkedHashSet();

        Map<Serializable,T> allMap = new LinkedHashMap<>();
        for (int i = 0; i < valueList.size(); i++) {
            CacheResult<T> v = valueList.get(i);
            Serializable k = keysList.get(i);
            if (v == null || v.isNull() ) {
                missedIds.add(k);
                allMap.put(k,null);
            } else {
                allMap.put(k,v.getValue());
            }
        }
        // 加载miss 的数据，并设置到缓存
        if (CollUtil.isNotEmpty(missedIds)) {
            if (loader == null) {
                loader = missIds -> super.listByIds(missIds.stream().filter(Objects::nonNull).map(Convert::toLong).collect(Collectors.toList()));
            }
            Collection<T> missList = loader.apply(missedIds);
            ImmutableMap<Serializable, T> missMap = CollHelper.uniqueIndex(missList, item ->(Serializable) item.getId(), item -> item);
            allMap.forEach((k,v) -> {
                if(missMap.containsKey(k)){
                    allMap.put(k,missMap.get(k));
                }
            });

            // 讲缓存中不存在的数据设置到缓存,缓存 "空值" 或 “实际值"
            for (Serializable missedKey: missedIds
                 ) {
                CacheKey key = cacheKeyBuilder().key(missedKey);
                // 存在就缓存 实际值
                // 不存在就缓存 空值，防止缓存穿透
                cacheOps.set(key, missMap.getOrDefault(missedKey,null));
            }

        }
        Collection<T> values = allMap.values();
        return Lists.newArrayList(values);
    }

    @Override
    @Transactional(readOnly = true)
    public T getByKey(CacheKey key, Function<CacheKey, Object> loader) {
        CacheResult<Object> result = cacheOps.get(key, loader);
        return result.isNull() ? null : getByIdCache(Convert.toLong(result.getValue()));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        boolean bool = super.removeById(id);
        delCache(id);
        return bool;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<?> idList) {
        if (CollUtil.isEmpty(idList)) {
            return true;
        }
        boolean flag = super.removeByIds(idList);
        delCache(idList);
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(T model) {
        boolean save = super.save(model);
        setCache(model);
        return save;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAllById(T model) {
        boolean save = super.updateAllById(model);
        delCache(model);
        return save;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(T model) {
        boolean save = super.updateById(model);
        delCache(model);
        return save;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            sqlSession.insert(sqlStatement, entity);

            // 设置缓存
            setCache(entity);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(getEntityClass());
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");

        BiPredicate<SqlSession, T> predicate = (sqlSession, entity) -> {
            Object idVal = ReflectionKit.getFieldValue(entity, keyProperty);
            return StringUtils.checkValNull(idVal)
                    || CollectionUtils.isEmpty(sqlSession.selectList(getSqlStatement(SqlMethod.SELECT_BY_ID), entity));
        };

        BiConsumer<SqlSession, T> consumer = (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(getSqlStatement(SqlMethod.UPDATE_BY_ID), param);

            // 清理缓存
            delCache(entity);
        };

        String sqlStatement = SqlHelper.getSqlStatement(this.mapperClass, SqlMethod.INSERT_ONE);
        return SqlHelper.executeBatch(getEntityClass(), log, entityList, batchSize, (sqlSession, entity) -> {
            if (predicate.test(sqlSession, entity)) {
                sqlSession.insert(sqlStatement, entity);
                // 设置缓存
                setCache(entity);
            } else {
                consumer.accept(sqlSession, entity);
            }
        });


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.UPDATE_BY_ID);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);

            // 清理缓存
            delCache(entity);
        });
    }

    @Override
    public void refreshCache() {
        list().forEach(this::setCache);
    }

    @Override
    public void clearCache() {
        list().forEach(this::delCache);
    }


    protected void delCache(Serializable... ids) {
        delCache(Arrays.asList(ids));
    }

    protected void delCache(Collection<?> idList) {
        CacheKey[] keys = idList.stream().map(id -> cacheKeyBuilder().key(id)).toArray(CacheKey[]::new);
        cacheOps.del(keys);
    }

    protected void delCache(T model) {
        Object id = getId(model);
        if (id != null) {
            CacheKey key = cacheKeyBuilder().key(id);
            cacheOps.del(key);
        }
    }

    protected void setCache(T model) {
        Object id = getId(model);
        if (id != null) {
            CacheKey key = cacheKeyBuilder().key(id);
            cacheOps.set(key, model);
        }
    }

    protected Object getId(T model) {
        if (model instanceof SuperEntity) {
            return ((SuperEntity) model).getId();
        } else {
            // 实体没有继承 Entity 和 SuperEntity
            TableInfo tableInfo = TableInfoHelper.getTableInfo(getEntityClass());
            if (tableInfo == null) {
                return null;
            }
            // 主键类型
            Class<?> keyType = tableInfo.getKeyType();
            if (keyType == null) {
                return null;
            }
            // id 字段名
            String keyProperty = tableInfo.getKeyProperty();

            // 反射得到 主键的值
            Field idField = ReflectUtil.getField(getEntityClass(), keyProperty);
            return ReflectUtil.getFieldValue(model, idField);
        }
    }
}
