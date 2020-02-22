package com.jamie.framework.log.enumeration;

import com.jamie.framework.mybatis.BaseEnum;

/**
 * @author lizheng
 * @date: 12:36 2020/02/16
 * @Description: RequestMethod
 */
public enum RequestMethod implements BaseEnum {
    /**
     * get、head、post、put、patch、options、trace
     */
    GET(0),
    HEAD(1),
    POST(2),
    PUT(3),
    PATCH(4),
    DELETE(5),
    OPTIONS(6),
    TRACE(7);

    private int type;
    RequestMethod(int type) {
        this.type = type;
    }
    @Override
    public int getValue() {
        return this.type;
    }
}
