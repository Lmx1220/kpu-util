package cn.lmx.basic.interfaces.echo;

import java.util.Map;

/**
 * 注入VO 父类
 *
 * @author lmx
 * @date 2024/02/07  02:04 下午
 */
public interface EchoVO {

    /**
     * 回显值 集合
     *
     * @return 回显值 集合
     */
    Map<String, Object> getEchoMap();
}
