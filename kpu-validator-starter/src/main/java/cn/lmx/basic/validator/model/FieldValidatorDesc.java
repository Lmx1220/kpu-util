package cn.lmx.basic.validator.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 字段校验规则信息
 *
 * @author lmx
 * @date 2024/02/07  02:04
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
