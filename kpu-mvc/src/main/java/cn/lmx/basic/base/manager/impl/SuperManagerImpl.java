package cn.lmx.basic.base.manager.impl;

import cn.lmx.basic.base.manager.SuperManager;
import cn.lmx.basic.base.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author lmx
 * @version v1.0.0
 * @date 2023/07/06  14:24
 */

public class SuperManagerImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements SuperManager<T> {

    @Override
    public boolean updateAllById(T entity) {
        return retBool(baseMapper.updateAllById(entity));
    }
}
