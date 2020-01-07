package com.jamie.framework.treenode.adapter;

import com.jamie.framework.treenode.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizheng
 * @date: 19:39 2020/01/07
 * @Description: TreeNodeConverter
 */
public interface TreeNodeAdapter<T> {
    /**
     * 转换目标对象
     * @param node
     * @return
     */
    TreeNode convert(T node);

    /**
     * 提供待转换的数据
     */
    List<T> getOriData();

    /**
     * 返回列表数据,一般不需要重写
     * @return TreeNodes
     */
    default List<TreeNode> getList() {
        List<T> data = getOriData();
        if (data == null || data.size() == 0) {
            return new ArrayList<>(0);
        }
        List<TreeNode> treeNodes = new ArrayList<>(data.size());
        for (T node : data) {
            treeNodes.add(convert(node));
        }
        return treeNodes;
    }

}
