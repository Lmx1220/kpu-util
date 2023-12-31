package cn.lmx.basic.interfaces;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author lmx
 * @version 1.0
 * @description: 枚举类型基类
 * @date 2023/7/4 14:27
 */
public interface BaseEnum extends IEnum<String> {

    /**
     * 编码
     *
     * @return 编码
     */
    default String getCode() {
        return toString();
    }

    /**
     * 描述
     *
     * @return 描述
     */
    String getDesc();

    /**
     * 扩展参数
     *
     * @return 扩展参数
     */
    default String getExtra() {
        return "";
    }

    /**
     * 判断val是否跟当前枚举相等
     *
     * @param val 值
     * @return 是否等于
     */
    default boolean eq(String val) {
        return this.getCode().equalsIgnoreCase(val);
    }

    /**
     * 枚举值
     *
     * @return 数据库中的值
     */
    @Override
    default String getValue() {
        return getCode();
    }
}
