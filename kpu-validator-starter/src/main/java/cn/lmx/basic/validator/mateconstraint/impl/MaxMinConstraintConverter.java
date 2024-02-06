package cn.lmx.basic.validator.mateconstraint.impl;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import cn.lmx.basic.validator.mateconstraint.IConstraintConverter;
import cn.lmx.basic.validator.utils.ValidatorConstants;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * 长度 转换器
 *
 * @author lmx
 * @date 2024/02/07  02:04
 */
public class MaxMinConstraintConverter extends BaseConstraintConverter implements IConstraintConverter {

    @Override
    protected List<String> getMethods() {
        return Arrays.asList("value", ValidatorConstants.MESSAGE);
    }

    @Override
    protected String getType(Class<? extends Annotation> type) {
        return type.getSimpleName();
    }

    @Override
    protected List<Class<? extends Annotation>> getSupport() {
        return Arrays.asList(Max.class, Min.class, DecimalMax.class, DecimalMin.class);
    }

}
