package com.sharp.sword.file.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sharp.sword.file.enumeration.StorageType;
import com.sharp.sword.mybatis.handler.EnumValueTypeHandler;
import lombok.Data;

import java.util.Date;

/**
 * @author lizheng
 * @date: 20:08 2020/02/01
 * @Description: SysResource
 */
@Data
@TableName(value = "sys_resource", autoResultMap = true)
public class SysResource {
    @TableId("id")
    private String id;
    private String name;
    private String suffix;
    private long size;
    @TableField("file_type")
    private String fileType;
    @TableField(value = "storage_type", typeHandler = EnumValueTypeHandler.class)
    private StorageType storageType;
    private int status;
    private String url;
    private String filepath;
    private int sort;
    private String crteater;
    @TableField("update_time")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
