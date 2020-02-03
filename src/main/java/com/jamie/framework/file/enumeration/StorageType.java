package com.jamie.framework.file.enumeration;

import com.jamie.framework.mybatis.BaseEnum;

/**
 * @author lizheng
 * @date: 20:18 2020/02/01
 * @Description: StorageType
 */
public enum StorageType implements BaseEnum {
    /**
     * 网络（url生效）
     */
    NETWORK(1),

    /**
     * 服务器文件（filepath生效）
     */
    SERVER_PATH(2);



    StorageType(int type) {
        this.type = type;
    }

    private int type;

    @Override
    public int getValue() {
        return this.type;
    }

}
