package com.sharp.sword.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lizheng
 * @date: 9:35 2019/10/09
 * @Description: Goods
 */
@Data
@TableName("t_goods")
public class Goods {
    private String id;
    private String name;
    @TableField("ms")
    private String ms;
}
