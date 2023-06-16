package cn.lmx.basic.database.mybatis.auth;


import cn.lmx.basic.model.database.DataScope;

/**
 * @author lmx
 * @version 1.0
 * @description: 数据权限保证类
 * @date 2023/6/16 15:43
 */
public class DataScopeHelper {
    protected static final ThreadLocal<DataScope> LOCAL_DATA_SCOPE = new ThreadLocal<>();

    /**
     * 获取 数据权限字段配置 参数
     *
     * @return
     */
    public static DataScope getLocalDataScope() {
        return LOCAL_DATA_SCOPE.get();
    }

    /**
     * 设置 数据权限字段配置
     *
     * @param dataScope
     */
    protected static void setLocalDataScope(DataScope dataScope) {
        LOCAL_DATA_SCOPE.set(dataScope);
    }

    /**
     * 移除本地变量
     */
    public static void clearDataScope() {
        LOCAL_DATA_SCOPE.remove();
    }

    /**
     * @param dataScope sql中需要动态拼接条件的表的别名
     * @description: 开启数据权限
     * @author lmx
     * @date 2023/6/16 15:56
     * @version 1.0
     */
    public static void startDataScope(DataScope dataScope) {
        if (dataScope == null) {
            return;
        }
        setLocalDataScope(dataScope);
    }
}
