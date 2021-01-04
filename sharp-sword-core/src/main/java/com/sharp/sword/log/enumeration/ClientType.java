package com.sharp.sword.log.enumeration;

import com.sharp.sword.mybatis.BaseEnum;

/**
 * @author lizheng
 * @date: 12:20 2020/02/13
 * @Description: StorageType
 */
public enum ClientType implements BaseEnum {
    /**
     * PC端
     */
    PC(0),

    /**
     * 移动手机端
     */
    MOBILE(1),

    /**
     * 其他服务调用
     */
    SERVICES(2);

    private int value;
    ClientType(int value) {
        this.value = value;
    }
    @Override
    public int getValue() {
        return this.value;
    }
}