package cn.lmx.basic.base.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import cn.lmx.basic.base.manager.SuperManager;
import cn.lmx.basic.base.mapper.SuperMapper;
import cn.lmx.basic.exception.BizException;

import java.lang.reflect.ParameterizedType;

import static cn.lmx.basic.exception.code.ExceptionCode.SERVICE_MAPPER_ERROR;

/**
 * 不含缓存的Service实现
 * <p>
 * <p>
 * 2，removeById：重写 ServiceImpl 类的方法，删除db
 * 3，removeByIds：重写 ServiceImpl 类的方法，删除db
 * 4，updateAllById： 新增的方法： 修改数据（所有字段）
 * 5，updateById：重写 ServiceImpl 类的方法，修改db后
 *
 * @param <M> Mapper
 * @param <T> 实体
 * @author lmx
 * @date 2024/02/07  02:04
 */
public class SuperManagerImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements SuperManager<T> {

    private Class<T> entityClass = null;

    public SuperMapper getSuperMapper() {
        if (baseMapper != null) {
            return baseMapper;
        }
        throw BizException.wrap(SERVICE_MAPPER_ERROR);
    }

    @Override
    public Class<T> getEntityClass() {
        if (entityClass == null) {
            this.entityClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        }
        return this.entityClass;
    }


    @Override
    public boolean updateAllById(T model) {
        return SqlHelper.retBool(getSuperMapper().updateAllById(model));
    }

}
