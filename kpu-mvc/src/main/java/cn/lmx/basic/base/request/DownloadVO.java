package cn.lmx.basic.base.request;

import lombok.Builder;
import lombok.Data;

/**
  * @author lmx
 * @version v1.0
 * @date 2024/02/07  02:04 PM
 * @create [2024/02/07  02:04 PM ] [lmx] [初始创建]
 */
@Data
@Builder
public class DownloadVO {
    byte[] data;
    String fileName;
}
