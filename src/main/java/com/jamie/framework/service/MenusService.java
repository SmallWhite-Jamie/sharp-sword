package com.jamie.framework.service;

import com.jamie.framework.bean.SysMenus;

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
    List<SysMenus> tree(SysMenus menu);

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
}
