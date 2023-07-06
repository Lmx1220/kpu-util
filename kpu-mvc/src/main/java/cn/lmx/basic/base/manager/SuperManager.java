package cn.lmx.basic.base.manager;

import cn.lmx.basic.base.mapper.SuperMapper;
import cn.lmx.basic.exception.BizException;
import cn.lmx.basic.exception.code.ExceptionCode;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

import java.util.List;

/**
 * @author lmx
 * @version v1.0.0
 * @date 2023/07/06  14:23
 */
public interface SuperManager<T> extends IService<T> {
    @Override
    Class<T> getEntityClass();

    /**
     * 批量保存数据
     * <p>
     * 注意：该方法仅仅测试过mysql
     *
     * @param entityList
     * @return
     */
    default boolean saveBatchSomeColumn(List<T> entityList) {
        if (entityList.isEmpty()) {
            return true;
        }
        if (entityList.size() > 5000) {
            throw BizException.wrap(ExceptionCode.TOO_MUCH_DATA_ERROR);
        }
        return SqlHelper.retBool(((SuperMapper) getBaseMapper()).insertBatchSomeColumn(entityList));
    }

    /**
     * 根据id修改 entity 的所有字段
     *
     * @param entity
     * @return
     */
    boolean updateAllById(T entity);
}
