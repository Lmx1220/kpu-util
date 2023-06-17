package cn.lmx.basic.validator.mateconstraint.impl;

import cn.lmx.basic.validator.mateconstraint.IConstraintConverter;
import cn.lmx.basic.validator.utils.ValidatorConstants;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lmx
 * @description: 其他 转换器
 * @date 2023/6/17 13:24
 */
public class OtherConstraintConverter extends BaseConstraintConverter implements IConstraintConverter {

    @Override
    protected String getType(Class<? extends Annotation> type) {
        return type.getSimpleName();
    }

    @Override
    protected List<Class<? extends Annotation>> getSupport() {
        return new ArrayList<>();
    }


    @Override
    protected List<String> getMethods() {
        return Arrays.asList(ValidatorConstants.MESSAGE);
    }
}
