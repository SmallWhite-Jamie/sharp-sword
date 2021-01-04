package com.sharp.sword.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 14:45
 */
@Data
public class BaseEntity implements Serializable {
    @TableId("id")
    private String id;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除 1 删除
     */
    private int isDeleted;

    /**
     * 版本号 乐观锁
     */
    private Integer revision;
}
