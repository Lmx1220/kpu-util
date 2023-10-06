package cn.lmx.basic.database.plugins;


import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lmx
 * @version 1.0
 * @date 2023/7/4 14:27
 */
//@Service
public class TenantLineHelper {
    final static Map<String, Boolean> CACHE = new ConcurrentHashMap<>();
    /**
     * 判断 mapper id 是否启用了 @TenantLine 注解
     * @param id
     * @return boolean
     * @author lmx
     * @date 2023/10/6 13:01
     */
    public static boolean willTenantLie(String id) {
        Boolean cache = CACHE.get(id);
        if (cache == null) {
            cache = CACHE.get(id.substring(0, id.lastIndexOf(StringPool.DOT)));
        }
        if (cache != null) {
            return cache;
        }
        return false;
    }

}
