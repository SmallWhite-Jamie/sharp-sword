package com.jamie.framework.log.op;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jamie.framework.log.enumeration.ClientType;
import com.jamie.framework.log.enumeration.OP;
import com.jamie.framework.mybatis.handler.EnumValueTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lizheng
 * @date: 12:30 2020/02/13
 * @Description: OpLog
 */
@Data
@TableName(value = "sys_op_log", autoResultMap = true)
public class OpLog implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String userId;
    private String url;
    private String method;
    @TableField("class_method")
    private String classMethod;
    @TableField("module_name")
    private String moduleName;
    private String description;
    @TableField(value = "client_type", typeHandler = EnumValueTypeHandler.class)
    private ClientType clientType;
    @TableField(value = "op", typeHandler = EnumValueTypeHandler.class)
    private OP op;
    private Date crtTime;
}
