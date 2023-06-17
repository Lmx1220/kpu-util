package cn.lmx.basic.validator.annotation;

import cn.lmx.basic.validator.config.ValidatorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lmx
 * @version 1.0
 * @description: 在启动类上添加该注解来启动表单验证功能
 * @date 2023/6/17 13:22
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ValidatorConfiguration.class)
public @interface EnableFormValidator {
}
