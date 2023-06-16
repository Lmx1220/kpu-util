package cn.lmx.basic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lmx
 * @version 1.0
 * @description: 键值对 通用对象
 * @date 2023/6/16 13:17
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kv implements Serializable {
    private String key;
    private String value;
}
