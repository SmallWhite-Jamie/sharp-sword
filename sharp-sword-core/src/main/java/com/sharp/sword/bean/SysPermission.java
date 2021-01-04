package com.sharp.sword.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lizheng
 * @date: 15:20 2019/10/21
 * @Description: sys_permission
 */
@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {
    @TableId("id")
    private String id;
    @TableField("code")
    private String code;
    @TableField("name")
    private String name;
    @TableField("status")
    private String status;
    @TableField("sort")
    private int sort;
}
