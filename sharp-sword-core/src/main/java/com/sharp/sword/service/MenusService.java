package com.sharp.sword.service;

import com.sharp.sword.bean.SysMenus;
import com.sharp.sword.treenode.TreeNode;

import java.util.List;

/**
 * @author lizheng
 * @date: 18:15 2019/12/19
 * @Description: MenusService
 */
public interface MenusService {
    /**
     * 根据条件列表方式获取菜单
     * @param menu
     * @return
     */
    List<SysMenus> list(SysMenus menu);

    /**
     * 根据条件树形方式获取菜单
     * @param menu
     * @return
     */
    List<TreeNode> tree(SysMenus menu);

    /**
     * 根据ID获取菜单详情
     * @param id
     * @return
     */
    SysMenus getById(String id);

    /**
     * 新增或修改菜单
     * @param menu
     * @return
     */
    SysMenus save(SysMenus menu);

    void delete(String id);
}
