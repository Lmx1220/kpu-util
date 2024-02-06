package cn.lmx.basic.validator.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 检验约束信息
 *
 * @author lmx
 * @date 2024/02/07  02:04
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
