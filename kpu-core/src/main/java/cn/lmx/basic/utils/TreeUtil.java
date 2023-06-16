package cn.lmx.basic.utils;


import cn.hutool.core.collection.CollUtil;
import cn.lmx.basic.base.entity.TreeEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lmx
 * @version 1.0
 * @description: list列表 转换成tree列表
 * @date 2023/6/16 13:31
 */
public final class TreeUtil {
    private TreeUtil() {
    }


    /**
     * 判断id是否为根节点
     *
     * @param id
     * @return
     */
    public static boolean isRoot(Long id) {
        return id == null || Long.valueOf(StrPool.DEF_PARENT_ID).equals(id);
    }


    public static String getTreePath(String parentTreePath, Long parentId) {
        return StrPool.COMMA + parentId + parentTreePath;
    }

    /**
     * 构建Tree结构
     *
     * @param treeList 待转换的集合
     * @return 树结构
     */
    public static <E extends TreeEntity<E, ? extends Serializable>> List<E> buildTree(List<E> treeList) {
        if (CollUtil.isEmpty(treeList)) {
            return treeList;
        }
        //记录自己是自己的父节点的id集合
        List<Serializable> selfIdEqSelfParent = new ArrayList<>();
        // 为每一个节点找到子节点集合
        for (E parent : treeList) {
            Serializable id = parent.getId();
            for (E children : treeList) {
                if (parent != children) {
                    //parent != children 这个来判断自己的孩子不允许是自己，因为有时候，根节点的parent会被设置成为自己
                    if (id.equals(children.getParentId())) {
                        parent.initChildren();
                        parent.getChildren().add(children);
                    }
                } else if (id.equals(parent.getParentId())) {
                    selfIdEqSelfParent.add(id);
                }
            }
        }
        // 找出根节点集合
        List<E> trees = new ArrayList<>();

        List<? extends Serializable> allIds = treeList.stream().map(node -> node.getId()).collect(Collectors.toList());
        for (E baseNode : treeList) {
            if (!allIds.contains(baseNode.getParentId()) || selfIdEqSelfParent.contains(baseNode.getParentId())) {
                trees.add(baseNode);
            }
        }
        return trees;
    }
}
