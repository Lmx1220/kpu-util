package cn.lmx.basic.validator.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author lmx
 * @description: 字段校验规则信息
 * @date 2023/7/4 14:27
 */
@Data
@ToString
@Accessors(chain = true)
public class FieldValidatorDesc {
    /**
     * 字段名称
     */
    private String field;
    /**
     * 字段的类型
     */
    private String fieldType;
    /**
     * 约束集合
     */
    private List<ConstraintInfo> constraints;
}
