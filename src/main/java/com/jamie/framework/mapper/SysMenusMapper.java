package com.jamie.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jamie.framework.bean.SysMenus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lizheng
 * @date: 18:13 2019/12/19
 * @Description: SysMenusMapper
 */
public interface SysMenusMapper extends BaseMapper<SysMenus> {
    List<SysMenus> list(@Param("userId") String userId, @Param("menu")SysMenus menu);
}
