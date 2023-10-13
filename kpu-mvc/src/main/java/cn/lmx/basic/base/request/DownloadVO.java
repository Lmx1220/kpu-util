package cn.lmx.basic.base.request;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author lmx
 * @version v1.0.0
 * @date 2023/10/04  18:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "DownloadVO", description = "下载")
public class DownloadVO {
    private String fileName;
    private byte[] data;
}
