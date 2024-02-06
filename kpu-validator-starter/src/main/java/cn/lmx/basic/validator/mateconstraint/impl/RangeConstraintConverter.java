package cn.lmx.basic.validator.mateconstraint.impl;


import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
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
public class RangeConstraintConverter extends BaseConstraintConverter implements IConstraintConverter {

    @Override
    protected List<String> getMethods() {
        return Arrays.asList("min", "max", ValidatorConstants.MESSAGE);
    }

    @Override
    protected String getType(Class<? extends Annotation> type) {
        return "Range";
    }

    @Override
    protected List<Class<? extends Annotation>> getSupport() {
        return Arrays.asList(Length.class, Size.class, Range.class);
    }
}
