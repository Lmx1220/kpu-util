package cn.lmx.basic.model.cache;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.time.Duration;

import static cn.lmx.basic.utils.StrPool.COLON;


/**
 * @author lmx
 * @version 1.0
 * @description: hash 缓存 key 封装
 * @date 2023/6/16 13:19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
public class CacheHashKey extends CacheKey {
    /**
     * redis hash field
     */
    @NonNull
    private Object field;

    public CacheHashKey(@NonNull String key, final @NonNull Object field) {
        super(key);
        this.field = field;
    }

    public CacheHashKey(@NonNull String key, final @NonNull Object field, Duration expire) {
        super(key, expire);
        this.field = field;
    }

    public CacheKey tran() {
        return new CacheKey(StrUtil.join(COLON, getKey(), getField()), getExpire());
    }
}
