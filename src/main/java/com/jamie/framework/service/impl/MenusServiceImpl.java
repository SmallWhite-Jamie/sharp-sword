package com.jamie.framework.service.impl;

import com.jamie.framework.bean.SysMenus;
import com.jamie.framework.idgenerator.IdGenerator;
import com.jamie.framework.mapper.SysMenusMapper;
import com.jamie.framework.service.MenusService;
import com.jamie.framework.treenode.adapter.SysMenusTreeNodeAdapter;
import com.jamie.framework.treenode.TreeNode;
import com.jamie.framework.treenode.TreeNodeBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lizheng
 * @date: 18:46 2019/12/19
 * @Description: MenusServiceImpl
 */
@Service
public class MenusServiceImpl implements MenusService {

    @Autowired
    private SysMenusMapper sysMenusMapper;

    @Autowired
    private AppBaseService appBaseService;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public List<SysMenus> list(SysMenus menu) {
        return sysMenusMapper.list(appBaseService.getUserId(), menu);
    }

    @Override
    public List<TreeNode> tree(SysMenus menu) {
        List<SysMenus> list = this.list(menu);
        if (CollectionUtils.isNotEmpty(list)) {
            TreeNodeBuilder builder = new TreeNodeBuilder(new SysMenusTreeNodeAdapter(list));
            return builder.builTree();
        }
        return new ArrayList<>();
    }


    @Override
    public SysMenus getById(String id) {
        return sysMenusMapper.selectById(id);
    }

    @Override
    public SysMenus save(SysMenus menu) {
        if (StringUtils.isNotBlank(menu.getId())) {
            menu.setUpdateTime(new Date());
            sysMenusMapper.updateById(menu);
        } else {
            menu.setId(idGenerator.nextIdStr());
            menu.setCreateTime(new Date());
            sysMenusMapper.insert(menu);
        }
        return menu;
    }
}
