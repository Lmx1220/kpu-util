package cn.lmx.basic.log.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 线程变量封装的参数
 *
 * @author lmx
 * @date 2024/02/07  02:04 下午
 */
@Data
public class ThreadLocalParam implements Serializable {
    private Boolean boot;
    private String tenant;
    private Long userid;
    private String name;
    private String account;
}
