package cn.lmx.basic.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * @author lmx
 * @version 1.0
 * @description: 字符串帮助类
 * @date 2023/7/4 14:27
 */
@Slf4j
public final class StrHelper {
    private StrHelper() {
    }

    public static String getOrDef(String val, String def) {
        return DefValueHelper.getOrDef(val, def);
    }

    /**
     * 有 任意 一个 Blank
     *
     * @param css CharSequence
     * @return boolean
     */
    public static boolean isAnyBlank(final CharSequence... css) {
        if (ObjectUtil.isEmpty(css)) {
            return true;
        }
        return Stream.of(css).anyMatch(StrUtil::isBlank);
    }

    /**
     * 是否全非 Blank
     *
     * @param css CharSequence
     * @return boolean
     */
    public static boolean isNoneBlank(final CharSequence... css) {
        if (ObjectUtil.isEmpty(css)) {
            return false;
        }
        return Stream.of(css).allMatch(StrUtil::isNotBlank);
    }

    /**
     * mybatis plus like查询转换
     */
    public static String keywordConvert(String value) {
        if (StrUtil.isBlank(value)) {
            return StrPool.EMPTY;
        }
        value = value.replaceAll(StrPool.PERCENT, "\\\\%");
        value = value.replaceAll(StrPool.UNDERSCORE, "\\\\_");
        return value;
    }

    public static Object keywordConvert(Object value) {
        if (value instanceof String) {
            return keywordConvert(String.valueOf(value));
        }
        return value;
    }

    /**
     * 拼接like条件
     *
     * @param value   值
     * @param sqlType 拼接类型
     * @return 拼接后的值
     */
    public static String like(Object value, SqlLike sqlType) {
        return SqlUtils.concatLike(keywordConvert(String.valueOf(value)), sqlType);
    }

    /**
     * 拼接like 模糊条件
     *
     * @param value 值
     * @return 拼接后的值
     */
    public static String fullLike(String value) {
        return like(value, SqlLike.DEFAULT);
    }

    public static String convertToCamelCase(String text) {
        if (StrUtil.isBlank(text)) {
            return StrPool.EMPTY;
        }
        // user_name -> UserName Java实体类驼峰命名
        String[] split = text.split(StrPool.UNDERSCORE);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(StrUtil.upperFirst(s));
        }
        return sb.toString();
    }
    public static String convertUriToCamelCase(String uri) {
        StringBuilder result = new StringBuilder();
        boolean toUpperCase = true;

        for (int i = 0; i < uri.length(); i++) {
            String c = Convert.toStr(uri.charAt(i));

            if (c.equals(StrPool.SLASH) || c.equals(StrPool.DASH) || c.equals(StrPool.UNDERSCORE)) {
                toUpperCase = true;
            } else {
                result.append(toUpperCase ? Character.toUpperCase(Convert.toChar(c)) : Character.toLowerCase(Convert.toChar(c)));
                toUpperCase = false;
            }
        }

        return result.toString();
    }
}
