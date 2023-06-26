package cn.lmx.basic.validator.constraintvalidators;


import cn.lmx.basic.interfaces.validator.IValidatable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.internal.constraintvalidators.hv.LengthValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author lmx
 * @version 1.0
 * @description: 自定义一个验证length的校验器。自定义类需要实现IValidatable接口
 * @date 2023/7/4 14:27
 */
public class LengthConstraintValidator implements ConstraintValidator<Length, IValidatable> {

    private final LengthValidator lengthValidator = new LengthValidator();

    @Override
    public void initialize(Length parameters) {
        lengthValidator.initialize(parameters);
    }

    @Override
    public boolean isValid(IValidatable value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.value() == null) {
            return true;
        }
        return lengthValidator.isValid(String.valueOf(value.value()), constraintValidatorContext);
    }
}
