package cn.lmx.basic.model.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.Duration;

/**
 * @author lmx
 * @version 1.0
 * @description: 缓存 key 封装
 * @date 2023/7/4 14:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CacheKey {
    /**
     * redis key
     */
    @NonNull
    private String key;
    /**
     * 超时时间 秒
     */
    private Duration expire;

    public CacheKey(final @NonNull String key) {
        this.key = key;
    }


}
