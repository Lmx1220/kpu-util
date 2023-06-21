package cn.lmx.basic.validator.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author lmx
 * @description: 检验约束信息
 * @date 2023/6/21 19:43
 */
@Data
@ToString
@Accessors(chain = true)
public class ConstraintInfo {
    /**
     * 约束对象的类型
     */
    private String type;
    /**
     * 约束属性
     */
    private Map<String, Object> attrs;
}
