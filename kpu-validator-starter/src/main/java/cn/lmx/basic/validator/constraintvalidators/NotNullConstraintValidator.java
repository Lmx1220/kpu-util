package cn.lmx.basic.validator.constraintvalidators;


import cn.lmx.basic.interfaces.validator.IValidatable;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;

/**
 * @author lmx
 * @version 1.0
 * @description: 自定义一个验证 NotNull 的校验器。自定义类需要实现IValidatable接口
 * @date 2023/6/17 13:24
 */
public class NotNullConstraintValidator implements ConstraintValidator<NotNull, IValidatable> {

    private final NotNullValidator notNullValidator = new NotNullValidator();

    @Override
    public void initialize(NotNull parameters) {
        notNullValidator.initialize(parameters);
    }

    @Override
    public boolean isValid(IValidatable value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value.value() != null;
    }
}
