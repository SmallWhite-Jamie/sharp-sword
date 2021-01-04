package com.sharp.sword.treenode.adapter;

import com.sharp.sword.bean.SysMenus;
import com.sharp.sword.treenode.TreeNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Object> map = new HashMap<>();
        map.put("router", node.getRouter());
        map.put("level", node.getLevel());
        map.put("icon", node.getIcon());
        map.put("code", node.getCode());
        treeNode.setMetaMap(map);
        return treeNode;
    }

    @Override
    public List<SysMenus> getOriData() {
        return sysMenus;
    }

}
