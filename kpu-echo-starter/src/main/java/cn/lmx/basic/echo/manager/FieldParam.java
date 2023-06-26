package cn.lmx.basic.echo.manager;

import cn.lmx.basic.annotation.echo.Echo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装字段上标记了 Echo 注解的字段
 *
 * @author lmx
 * @date 2023/7/4 14:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldParam {
    /**
     * 当前字段上的注解
     */
    private Echo echo;
    /**
     * 从当前字段的值构造出的调用api#method方法的参数
     */
    private Serializable actualValue;
    /**
     * 当前字段的具体值
     */
    private Object originalValue;

    /**
     * 当前 字段名
     */
    private String fieldName;

    private LoadKey loadKey;
}
