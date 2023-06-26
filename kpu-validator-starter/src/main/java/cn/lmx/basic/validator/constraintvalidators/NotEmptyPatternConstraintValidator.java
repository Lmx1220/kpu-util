package cn.lmx.basic.validator.constraintvalidators;

import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.annotation.constraints.NotEmptyPattern;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.internal.engine.messageinterpolation.util.InterpolationHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

/**
 * @author lmx
 * @version 1.0
 * @description: NotEmptyPattern注解的 约束验证器
 * @date 2023/7/4 14:27
 */
public class NotEmptyPatternConstraintValidator implements ConstraintValidator<NotEmptyPattern, CharSequence> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private java.util.regex.Pattern pattern;
    private String escapedRegexp;

    @Override
    public void initialize(NotEmptyPattern parameters) {
        NotEmptyPattern.Flag[] flags = parameters.flags();
        int intFlag = 0;
        for (NotEmptyPattern.Flag flag : flags) {
            intFlag = intFlag | flag.getValue();
        }

        try {
            pattern = java.util.regex.Pattern.compile(parameters.regexp(), intFlag);
        } catch (PatternSyntaxException e) {
            throw LOG.getInvalidRegularExpressionException(e);
        }

        escapedRegexp = InterpolationHelper.escapeMessageParameter(parameters.regexp());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isEmpty(value)) {
            return true;
        }

        if (constraintValidatorContext instanceof HibernateConstraintValidatorContext) {
            constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class).addMessageParameter("regexp", escapedRegexp);
        }

        Matcher m = pattern.matcher(value);
        return m.matches();
    }
}
