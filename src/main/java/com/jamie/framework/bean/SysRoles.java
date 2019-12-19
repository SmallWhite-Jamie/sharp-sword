package com.jamie.framework.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lizheng
 * @date: 15:07 2019/10/21
 * @Description: SysRoles
 */
@Data
@TableName("sys_roles")
public class SysRoles implements Serializable {
    @TableId("ROLEID")
    private String roleId;
    @TableField("ROLECODE")
    private String roleCode;
    @TableField("ROLETYPE")
    private String roleType;
    @TableField("ROLENAME")
    private String roleName;
    @TableField("ROLESTATUS")
    private String roleStatus;
    @TableField("REMARK")
    private String remark;
    @TableField("PERMISSION")
    private String permission;
    @TableField("PARENTID")
    private String parentId;
    @TableField("SQL")
    private String sql;
}
