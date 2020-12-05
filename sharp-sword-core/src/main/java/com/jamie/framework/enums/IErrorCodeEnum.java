package com.jamie.framework.enums;

/**
 * IErrorCodeEnum
 *
 * @author lizheng 日撸代码三千行，不识加班累，只缘bug狂。
 * @version 1.0
 * @date 2020/10/13 11:35
 */
public interface IErrorCodeEnum {
    /**
     * 获取code
     * @return
     */
    int getErrCode();

    /**
     * 获取desc
     * @return
     */
    String getErrDesc();
}
