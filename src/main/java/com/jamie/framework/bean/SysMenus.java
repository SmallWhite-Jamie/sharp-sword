package com.jamie.framework.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lizheng
 * @date: 18:07 2019/12/19
 * @Description: SysMenus
 */
@TableName("sys_menus")
@Data
public class SysMenus implements Serializable {
    @TableId("id")
    private String id;
    private String name;
    private String code;
    private String desc;
    private String router;
    private String enable;
    private String pid;
    private int level;
    private int sort;
    private String icon;
    private int type;
    @TableField(exist = false)
    private List<SysMenus> children;
}
