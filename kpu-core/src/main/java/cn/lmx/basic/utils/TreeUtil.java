package cn.lmx.basic.utils;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.lmx.basic.base.entity.TreeEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * list列表 转换成tree列表
 * Created by Ace on 2024/02/07.
 *
 * @author lmx
 */
public final class TreeUtil {
    /**
     * 默认的树节点 分隔符
     */
    public static final String TREE_SPLIT = StrPool.SLASH;
    /**
     * 默认的父id
     */
    public static final Long DEF_PARENT_ID = 0L;
    private static final int TOP_LEVEL = 1;

    private TreeUtil() {
    }

    public static String getTreePath(String parentTreePath, Long parentId) {
        return parentTreePath + parentId + TREE_SPLIT;
    }

    public static String buildTreePath(Long id) {
        return TREE_SPLIT + id + TREE_SPLIT;
    }

    /**
     * 判断id是否为根节点
     *
     * @param id
     * @return
     */
    public static boolean isRoot(Long id) {
        return id == null || DEF_PARENT_ID.equals(id);
    }

    /**
     * 构建Tree结构
     *
     * @param treeList 待转换的集合
     * @return 树结构
     */
    public static <E extends TreeEntity<E, ? extends Serializable>> List<E> buildTree(Collection<E> treeList) {
        if (CollUtil.isEmpty(treeList)) {
            return Collections.emptyList();
        }
        // 找出根节点集合
        List<E> trees = new ArrayList<>();
        //记录自己是自己的父节点的id集合
        List<Serializable> selfIdEqSelfParent = new ArrayList<>();

        // 为每一个节点找到子节点集合
        for (E parent :  treeList) {
            if (parent.getParentId().equals(DEF_PARENT_ID)) {
                trees.add(parent);
            }
            for (E it : treeList) {
                if (it.getParentId().equals(parent.getId())) {
                    if (parent.getChildren() == null) {
                        parent.initChildren();
                    }
                    parent.getChildren().add(it);
                    selfIdEqSelfParent.add(it.getId());
                }
            }
        }

        if(trees.size() == 0){
            trees = treeList.stream().filter(s -> !selfIdEqSelfParent.contains(s.getId())).collect(Collectors.toList());
        }
        return trees;
    }

    public static Long getTopNodeId(String treePath) {
        String[] pathIds = StrUtil.splitToArray(treePath, TREE_SPLIT);
        if (ArrayUtil.isNotEmpty(pathIds)) {
            return Convert.toLong(pathIds[TOP_LEVEL]);
        }
        return null;
    }
}
