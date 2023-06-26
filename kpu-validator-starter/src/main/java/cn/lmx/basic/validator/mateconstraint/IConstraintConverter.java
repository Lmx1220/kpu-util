package cn.lmx.basic.validator.mateconstraint;

import cn.lmx.basic.validator.model.ConstraintInfo;

import java.lang.annotation.Annotation;


/**
 * @author lmx
 * @description: 约束转换器
 * @date 2023/7/4 14:27
 */
public interface IConstraintConverter {

    /**
     * 支持的类型
     *
     * @param clazz 类型
     * @return 是否支持
     */
    boolean support(Class<? extends Annotation> clazz);

    /**
     * 转换
     *
     * @param ano 注解
     * @return 约束信息
     * @throws Exception 异常信息
     */
    ConstraintInfo converter(Annotation ano) throws Exception;
}
