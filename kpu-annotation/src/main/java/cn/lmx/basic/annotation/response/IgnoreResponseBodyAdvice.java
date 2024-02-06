package cn.lmx.basic.annotation.response;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略全局响应包装
 *
 * @author lmx
 * @date 2024/02/07  02:04 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreResponseBodyAdvice {

}
