package com.jamie.framework.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lizheng
 * @date: 10:01 2019/10/14
 * @Description: SysUser
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
    @TableId("id")
    private String id;
    private String userid;
    private String username;
    private String password;
    @TableField("pw_hash")
    private String pwHash;
    private String enable;
    @TableField("usertype")
    private String userType;
}
