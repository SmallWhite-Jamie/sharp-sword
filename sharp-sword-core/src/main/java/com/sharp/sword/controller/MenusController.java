package com.sharp.sword.controller;

import com.sharp.sword.bean.SysMenus;
import com.sharp.sword.service.MenusService;
import com.sharp.sword.util.api.ApiResult;
import com.sharp.sword.treenode.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lizheng
 * @date: 19:49 2019/12/19
 * @Description: MenusController
 */
@RestController
@RequestMapping("menus")
public class MenusController {

    @Autowired
    private MenusService menusService;

    @RequestMapping("list")
    public ApiResult list(SysMenus menu) {
        List<SysMenus> list = menusService.list(menu);
        return ApiResult.ok(list);
    }

    @RequestMapping("tree")
    public ApiResult tree(SysMenus menu) {
        List<TreeNode> tree = menusService.tree(menu);
        return ApiResult.ok(tree);
    }

    @RequestMapping("getById/{id}")
    public ApiResult getById(@PathVariable String id) {
        SysMenus menu = menusService.getById(id);
        return ApiResult.ok(menu);
    }

    @RequestMapping("save")
    public ApiResult save(SysMenus menus) {
        SysMenus menu = menusService.save(menus);
        return ApiResult.ok(menu);
    }

    @RequestMapping("delete/{id}")
    public ApiResult delete(@PathVariable String id) {
        menusService.delete(id);
        return ApiResult.ok();
    }
}
