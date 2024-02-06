package cn.lmx.basic.validator.component.extract;

import cn.lmx.basic.validator.model.FieldValidatorDesc;
import cn.lmx.basic.validator.model.ValidConstraint;

import java.util.Collection;
import java.util.List;


/**
 * 提取指定表单验证规则
 *
 * @author lmx
 * @date 2024/02/07
 */
public interface IConstraintExtract {

    /**
     * 提取指定表单验证规则
     *
     * @param constraints 限制条件
     * @return 验证规则
     * @throws Exception 异常
     */
    Collection<FieldValidatorDesc> extract(List<ValidConstraint> constraints) throws Exception;
}
