package com.jamie.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jamie.framework.bean.SysMenus;
import com.jamie.framework.mapper.SysMenusMapper;
import com.jamie.framework.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<SysMenus> list(SysMenus menu) {
        return sysMenusMapper.list(appBaseService.getUserId(), menu);
    }

    @Override
    public List<SysMenus> tree(SysMenus menu) {
        return null;
    }

    @Override
    public SysMenus getById(String id) {
        return null;
    }

    @Override
    public SysMenus save(SysMenus menu) {
        return null;
    }
}
