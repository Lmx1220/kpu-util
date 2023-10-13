package cn.lmx.basic.database.injector;

import cn.hutool.core.util.ArrayUtil;
import cn.lmx.basic.base.entity.SuperEntity;
import cn.lmx.basic.database.injector.method.UpdateAllById;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * @author lmx
 * @version 1.0
 * @description: 自定义sql 注入器
 * @date 2023/7/4 14:27
 */
public class KpuSqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);

        //增加自定义方法
        methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.UPDATE));
        methodList.add(new UpdateAllById(field -> !ArrayUtil.containsAny(new String[]{
                SuperEntity.CREATED_TIME_FIELD, SuperEntity.CREATED_BY_FIELD
        }, field.getColumn())));
        return methodList;
    }
}
