package cn.lmx.basic.interfaces.validator;

/**
 * 实现了此接口，表示此类将会支持验证框架。
 *
 * @author lmx
 * @date 2024/02/07  02:04
 */
public interface IValidatable {

    /**
     * 此类需要检验什么值
     * 支持length长度检验。也可以看情况实现支持类似于email，正则等等校验
     *
     * @return 需要验证的值
     */
    Object value();
}
