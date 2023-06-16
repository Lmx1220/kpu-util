package cn.lmx.basic.interfaces.echo;


import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author lmx
 * @version 1.0
 * @description: 加载数据
 * <p>
 * 只保留一个方法，若一个表，想要有多重回显场景，可以新建多个实现类，返回不一样的Map
 * @date 2023/6/16 13:10
 */
public interface LoadService {
    /**
     * 根据id查询待回显参数
     *
     * @param ids 唯一键（可能不是主键ID)
     * @return
     */
    Map<Serializable, Object> findByIds(Set<Serializable> ids);
}
