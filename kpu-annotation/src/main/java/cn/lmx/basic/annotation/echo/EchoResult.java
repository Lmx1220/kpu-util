package cn.lmx.basic.annotation.echo;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lmx
 * @version 1.0
 * @description: 将该注解标记在service方法上， 调用该方法后，返回值中标记了@Echo 注解的字段将会自动注入属性
 * <p>
 * 注意，该方法不能写在 Mapper 的方法上。
 * @date 2023/6/16 12:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface EchoResult {
    String[] ignoreFields() default {};
}
