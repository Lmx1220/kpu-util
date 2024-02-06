package cn.lmx.basic.validator.mateconstraint.impl;

import cn.lmx.basic.validator.mateconstraint.IConstraintConverter;
import cn.lmx.basic.validator.utils.ValidatorConstants;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 其他 转换器
 *
 * @author lmx
 * @date 2024/02/07  02:04
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
        return Collections.singletonList(ValidatorConstants.MESSAGE);
    }
}
