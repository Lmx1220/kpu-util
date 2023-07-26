package cn.lmx.basic.base.manager.impl;

import cn.lmx.basic.base.manager.SuperManager;
import cn.lmx.basic.base.mapper.SuperMapper;
import cn.lmx.basic.exception.BizException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.lang.reflect.ParameterizedType;

/**
 * @author lmx
 * @version v1.0.0
 * @date 2023/07/06  14:24
 */

public class SuperManagerImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements SuperManager<T> {

    private Class<T> entityClass = null;

    public SuperMapper<T> getSuperMapper() {
        if (baseMapper != null) {
            return baseMapper;
        }
        throw BizException.wrap("Service Mapper Error");
    }

    @Override
    public Class<T> getEntityClass() {
        if (entityClass == null) {
            this.entityClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return this.entityClass;
    }

    @Override
    public boolean updateAllById(T entity) {
        return retBool(baseMapper.updateAllById(entity));
    }
}
