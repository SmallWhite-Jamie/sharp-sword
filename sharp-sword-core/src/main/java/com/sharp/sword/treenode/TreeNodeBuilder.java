package com.sharp.sword.treenode;

import com.sharp.sword.treenode.adapter.TreeNodeAdapter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizheng
 * @date: 19:48 2020/01/07
 * @Description: TreeNodeBuilder
 */
public class TreeNodeBuilder {

    private List<TreeNode> data;

    public TreeNodeBuilder(TreeNodeAdapter adapter) {
        this.data = adapter.getList();
    }

    public List<TreeNode> builTree() {
        List<TreeNode> nodes = new ArrayList<>();
        data.stream().filter(item -> StringUtils.isBlank(item.getParentId())).forEach(item -> nodes.add(buildChildTree(item)));
        return nodes;
    }

    private TreeNode buildChildTree(TreeNode pNode) {
        List<TreeNode> childNodes =new ArrayList<>();
        for (TreeNode node : data) {
            if (pNode.getId().equals(node.getParentId())) {
                childNodes.add(buildChildTree(node));
            }
        }
        pNode.setChildren(childNodes);
        return pNode;
    }

}
