package cn.lmx.basic.annotation.user;

import java.lang.annotation.*;

/**
 * @author lmx
 * @version 1.0
 * @description: 请求的方法参数SysUser上添加该注解，则注入当前登录人信息
 * <p>
 * 例1：public void test(@LoginUser SysUser user) // 取BaseContextHandler中的 用户id、账号、姓名、组织id、岗位id等信息
 * <p>
 * 例2：public void test(@LoginUser(isRoles = true) SysUser user) //能获取SysUser对象的实时的用户信息和角色信息
 * <p>
 * 例3：public void test(@LoginUser(isOrg = true) SysUser user) //能获取SysUser对象的实时的用户信息和组织信息
 * <p>
 * 例4：public void test(@LoginUser(isStation = true) SysUser user) //能获取SysUser对象的实时的用户信息和岗位信息
 * <p>
 * 例5：public void test(@LoginUser(isFull = true) SysUser user) //能获取SysUser对象的所有信息
 * <p>
 * 例6：public void test(@LoginUser(isResource = true) SysUser user) //能获取SysUser对象的实时的用户信息和资源信息
 * @date 2023/7/4 14:27
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {
    /**
     * 是否查询SysUser对象所有信息，true则通过rpc接口查询
     */
    boolean isFull() default false;

    /**
     * 是否只查询角色信息，true则通过rpc接口查询
     */
    boolean isRoles() default false;

    /**
     * 是否只查询 资源 信息，true则通过rpc接口查询
     */
    boolean isResource() default false;

    /**
     * 是否只查询组织信息，true则通过rpc接口查询
     */
    boolean isOrg() default false;

    /**
     * 是否只查询岗位信息，true则通过rpc接口查询
     */
    boolean isStation() default false;
}
