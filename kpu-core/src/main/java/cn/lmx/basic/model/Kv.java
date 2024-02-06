package cn.lmx.basic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * 键值对 通用对象
 *
  * @author lmx
 * @version v3.3.0
 * @date 2024/02/07  02:04 下午
 * @create [2024/02/07  02:04 下午 ] [lmx] [初始创建]
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kv implements Serializable {
    private String key;
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kv kv = (Kv) o;
        return Objects.equals(key, kv.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
