package cn.lmx.basic.validator.model;

import javax.validation.groups.Default;

/**
 * @author lmx
 * @description: 验证约束
 * @date 2023/6/17 13:24
 */
public class ValidConstraint {

    private final Class<?> target;
    private Class<?>[] groups;

    public ValidConstraint(Class<?> target) {
        this.target = target;
        groups = new Class<?>[]{Default.class};
    }

    public ValidConstraint(Class<?> target, Class<?>[] groups) {
        this.target = target;
        this.groups = groups;
    }

    public Class<?> getTarget() {
        return target;
    }

    public Class<?>[] getGroups() {
        if (groups == null) {
            groups = new Class[0];
        }
        if (groups.length == 0) {
            groups = new Class[]{Default.class};
        }
        return groups;
    }
}
