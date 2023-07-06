package cn.lmx.basic.database.injector.method;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;

/**
 * @author lmx
 * @version 1.0
 * @description: 修改所有的字段
 * @date 2023/7/4 14:27
 */
@NoArgsConstructor
public class UpdateAllById extends AlwaysUpdateSomeColumnById {

    public UpdateAllById(Predicate<TableFieldInfo> predicate) {
        super(predicate);
    }

    // 怎么替代这个方法？
    public String getMethod(SqlMethod sqlMethod) {
        // 自定义 mapper 方法名
        return "updateAllById";
    }
}
