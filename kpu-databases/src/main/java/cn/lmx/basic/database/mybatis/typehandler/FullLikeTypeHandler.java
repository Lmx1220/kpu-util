package cn.lmx.basic.database.mybatis.typehandler;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import org.apache.ibatis.type.Alias;

/**
 * @author lmx
 * @version 1.0
 * @description: 仅仅用于like查询
 * @date 2023/6/16 15:47
 */
@Alias("fullLike")
public class FullLikeTypeHandler extends BaseLikeTypeHandler {
    public FullLikeTypeHandler() {
        super(SqlLike.DEFAULT);
    }
}
