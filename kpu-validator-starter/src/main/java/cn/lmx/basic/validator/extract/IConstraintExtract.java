package cn.lmx.basic.validator.extract;

import cn.lmx.basic.validator.model.FieldValidatorDesc;
import cn.lmx.basic.validator.model.ValidConstraint;

import java.util.Collection;
import java.util.List;


/**
 * @author lmx
 * @description: 提取指定表单验证规则
 * @date 2023/7/4 14:27
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
