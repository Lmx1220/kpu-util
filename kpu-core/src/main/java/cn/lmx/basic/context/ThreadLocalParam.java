package cn.lmx.basic.context;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lmx
 * @version 1.0
 * @description: 线程变量封装的参数
 * @date 2023/6/16 13:24
 */
@Data
public class ThreadLocalParam implements Serializable {
    private Boolean boot;
    private String tenant;
    private Long userid;
    private String name;
    private String account;
}
