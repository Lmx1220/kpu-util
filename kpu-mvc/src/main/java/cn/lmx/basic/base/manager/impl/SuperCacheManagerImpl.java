package cn.lmx.basic.base.manager.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.base.manager.SuperCacheManager;
import cn.lmx.basic.base.mapper.SuperMapper;
import cn.lmx.basic.cache.repository.CacheOps;
import cn.lmx.basic.model.cache.CacheKey;
import cn.lmx.basic.model.cache.CacheKeyBuilder;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
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
        return cacheOps.get(cacheKey, k -> super.getById(id));
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
        List<T> valueList = partitionKeys.stream().map(ks -> cacheOps.<T>find(ks)).flatMap(Collection::stream).collect(Collectors.toList());

        // 所有的key
        List<Serializable> keysList = Lists.newArrayList(ids);
        log.debug(StrUtil.format("keysList:{}, valueList:{}", keysList.size(), valueList.size()));
        // 缓存不存在的key
        Set<Serializable> missedKeys = Sets.newLinkedHashSet();

        List<T> allList = new ArrayList<>();
        for (int i = 0; i < valueList.size(); i++) {
            T v = valueList.get(i);
            Serializable k = keysList.get(i);
            if (v == null) {
                missedKeys.add(k);
            } else {
                allList.add(v);
            }
        }
        // 加载miss 的数据，并设置到缓存
        if (CollUtil.isNotEmpty(missedKeys)) {
            if (loader == null) {
                loader = this::listByIds;
            }
            Collection<T> missList = loader.apply(missedKeys);
            missList.forEach(this::setCache);
            allList.addAll(missList);
        }
        return allList;
    }
//    @Transactional(readOnly = true)
//    public <E> Set<E> findCollectByIds(List<Long> keyIdList, Function<Collection<? extends Serializable>, Collection<T>> loader) {
//        if (CollUtil.isEmpty(keyIdList)) {
//            return Collections.emptySet();
//        }
//        // 拼接keys
//        List<CacheKey> cacheKeys = keyIdList.stream().map(cacheKeyBuilder()::key).collect(Collectors.toList());
//       List<E>  resultList=cacheOps.find(cacheKeys);
//       if (resultList.size() != cacheKeys.size()){
//           log.warn("key和结果数据不一致，请排查原因！");
//
//       }
//       Set<E> resultIdSet = new HashSet<>();
//       /*
//        * 有可能缓存中不存在某些缓存，导致resultList中部分元素是null
//        */
//        for (int i = 0; i < resultList.size(); i++) {
//            List<E> resultIdList = (List<E>) resultList.get(i);
//            if(resultIdList!=null) {
//                resultIdSet.addAll(resultIdList);
//            }else {
//                Long keyId = keyIdList.get(i);
//                loader.apply(keyId).forEach(this::setCache);
//            }
//        }
//        return resultIdSet;
//    }

    @Override
    @Transactional(readOnly = true)
    public T getByKey(CacheKey key, Function<CacheKey, Object> loader) {
        Object id = cacheOps.get(key, loader);
        return id == null ? null : getByIdCache(Convert.toLong(id));
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
