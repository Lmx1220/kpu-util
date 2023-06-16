package cn.lmx.basic.model.database;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;

/**
 * @author lmx
 * @version 1.0
 * @description: 数据权限查询参数
 * @date 2023/6/16 13:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataScope extends HashMap {
    /**
     * 限制范围的字段名称 （除个人外）
     */
    private String scopeName = "created_org_id";
    /**
     * 限制范围为个人时的字段名称
     */
    private String selfScopeName = "created_by";
    /**
     * 当前用户ID
     */
    private Long userId;

    /**
     * 具体的数据范围
     */
    private List<Long> orgIds;

}
