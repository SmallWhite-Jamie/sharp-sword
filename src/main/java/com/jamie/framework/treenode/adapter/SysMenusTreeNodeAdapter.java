package com.jamie.framework.treenode.adapter;

import com.jamie.framework.bean.SysMenus;
import com.jamie.framework.treenode.TreeNode;

import java.util.List;

/**
 * @author lizheng
 * @date: 19:47 2020/01/07
 * @Description: SysMenusTreeNodeConverter
 */
public class SysMenusTreeNodeAdapter implements TreeNodeAdapter<SysMenus> {

    private List<SysMenus> sysMenus;

    public SysMenusTreeNodeAdapter(List<SysMenus> sysMenus) {
        this.sysMenus = sysMenus;
    }

    @Override
    public TreeNode convert(SysMenus node) {
        TreeNode treeNode = new TreeNode();
        treeNode.setId(node.getId());
        treeNode.setText(node.getName());
        treeNode.setParentId(node.getPid());
        return treeNode;
    }

    @Override
    public List<SysMenus> getOriData() {
        return sysMenus;
    }

}
