package cn.lmx.basic.validator.constraintvalidators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;
import cn.lmx.basic.interfaces.validator.IValidatable;

/**
 * 自定义一个验证 NotNull 的校验器。自定义类需要实现IValidatable接口
 *
 * @author lmx
 * @date 2024/02/07  02:04
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
