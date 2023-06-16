package cn.lmx.basic.converter;

import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.interfaces.BaseEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author lmx
 * @version 1.0
 * @description: 继承了BaseEnum接口的枚举值，将会统一按照以下格式序列化
 * <pre>
 * {
 * "code": "XX",
 * "desc": "xxx"
 * }
 * </pre>
 * @date 2023/6/16 12:59
 */
public class EnumSerializer extends StdSerializer<BaseEnum> {
    public static final EnumSerializer INSTANCE = new EnumSerializer();
    public static final String ALL_ENUM_KEY_FIELD = "code";
    public static final String ALL_ENUM_EXTRA_FIELD = "extra";
    public static final String ALL_ENUM_DESC_FIELD = "desc";

    public EnumSerializer() {
        super(BaseEnum.class);
    }

    @Override
    public void serialize(BaseEnum distance, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        generator.writeStartObject();
        generator.writeFieldName(ALL_ENUM_KEY_FIELD);
        generator.writeString(distance.getCode());
        generator.writeFieldName(ALL_ENUM_DESC_FIELD);
        generator.writeString(distance.getDesc());
        if (StrUtil.isNotEmpty(distance.getExtra())) {
            generator.writeFieldName(ALL_ENUM_EXTRA_FIELD);
            generator.writeString(distance.getExtra());
        }
        generator.writeEndObject();
    }
}
