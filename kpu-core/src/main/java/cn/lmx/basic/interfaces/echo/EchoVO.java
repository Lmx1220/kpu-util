package cn.lmx.basic.interfaces.echo;

import java.util.Map;


/**
 * @author lmx
 * @version 1.0
 * @description: 注入VO 父类
 * @date 2023/7/4 14:27
 */
public interface EchoVO {

    /**
     * 回显值 集合
     *
     * @return 回显值 集合
     */
    Map<String, Object> getEchoMap();
}
