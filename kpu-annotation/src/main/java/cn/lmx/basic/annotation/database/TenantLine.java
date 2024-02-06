package cn.lmx.basic.annotation.database;

import java.lang.annotation.*;

/**
  * @author lmx
 * @version v1.0
 * @date 2024/02/07  02:04 PM
 * @create [2024/02/07  02:04 PM ] [lmx] [初始创建]
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TenantLine {
    boolean value() default true;
}
