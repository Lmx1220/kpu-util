package cn.lmx.basic.annotation.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lmx
 * @version 1.0
 * @description: 忽略全局响应包装
 * @date 2023/6/16 12:21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreResponseBodyAdvice {
}
