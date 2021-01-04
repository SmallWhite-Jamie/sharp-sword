package com.sharp.sword.log.enumeration;

import com.sharp.sword.mybatis.BaseEnum;

/**
 * @author lizheng
 * @date: 12:18 2020/02/13
 * @Description: StorageType
 */
public enum OP implements BaseEnum {
    /**
     * 新增
     */
    ADD(0),
    /**
     * 删除
     */
    DELETE(1),

    /**
     * 更新
     */
    UPDATE(2),

    /**
     * 查询
     */
    QUERY(3),

    /**
     * 导入
     */
    IMPORT(4),

    /**
     * 导出
     */
    EXPORT(5),

    /**
     * 同步
     */
    SYNC(6);

    private int value;
    OP(int value) {
        this.value = value;
    }
    @Override
    public int getValue() {
        return this.value;
    }
}