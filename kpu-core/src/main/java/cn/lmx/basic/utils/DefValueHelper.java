package cn.lmx.basic.utils;

import cn.hutool.core.util.StrUtil;

import java.io.Serializable;

/**
 * @author lmx
 * @version 1.0
 * @description: 默认值
 * @date 2023/7/4 14:27
 */
public final class DefValueHelper {
    private DefValueHelper() {
    }

    public static String getOrDef(String val, String def) {
        return StrUtil.isEmpty(val) ? def : val;
    }

    public static <T extends Serializable> T getOrDef(T val, T def) {
        return val == null ? def : val;
    }

}
