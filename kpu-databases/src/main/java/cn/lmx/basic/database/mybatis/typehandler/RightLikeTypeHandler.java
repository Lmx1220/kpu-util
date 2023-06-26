package cn.lmx.basic.database.mybatis.typehandler;


import com.baomidou.mybatisplus.core.enums.SqlLike;
import org.apache.ibatis.type.Alias;

/**
 * @author lmx
 * @version 1.0
 * @description: 仅仅用于like查询
 * @date 2023/7/4 14:27
 */
@Alias("rightLike")
public class RightLikeTypeHandler extends BaseLikeTypeHandler {
    public RightLikeTypeHandler() {
        super(SqlLike.RIGHT);
    }
}

