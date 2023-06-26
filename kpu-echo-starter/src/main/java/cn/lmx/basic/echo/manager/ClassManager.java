package cn.lmx.basic.echo.manager;


import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.annotation.echo.Echo;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类管理器
 *
 * @author lmx
 * @date 2023/7/4 14:27
 */
@Slf4j
public class ClassManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Map<String, List<Field>> CACHE = new HashMap<>();

    public static List<Field> getFields(Class<?> clazz) {
        if (CACHE.containsKey(clazz.getName())) {
            return CACHE.get(clazz.getName());
        }

        Field[] declaredFields = ReflectUtil.getFields(clazz);
        int mod;
        // 循环遍历所有的属性进行判断
        List<Field> fieldList = new ArrayList<>();
        for (Field field : declaredFields) {
            mod = field.getModifiers();
            // 如果是 static, final, volatile, transient 的字段，则直接跳过
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod) || Modifier.isVolatile(mod)) {
                continue;
            }
            Echo echo = field.getDeclaredAnnotation(Echo.class);
            if (echo == null) {
                continue;
            }
            if (StrUtil.hasEmpty(echo.api())) {
                log.warn("类 {} 属性 [{}] api 为空。", clazz.getName(), field.getName());
                continue;
            }
            fieldList.add(field);
        }
        log.debug("已扫描到{}中存在{}个@Echo字段", clazz.getName(), fieldList.size());
        CACHE.put(clazz.getName(), fieldList);
        return fieldList;
    }
}
