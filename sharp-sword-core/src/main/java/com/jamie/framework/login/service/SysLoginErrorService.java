package com.jamie.framework.login.service;

import com.jamie.framework.login.entity.SysLoginErrorEntity;
import com.jamie.framework.service.BaseService;

/**
 * SysLoginErrorService
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 19:17
 */
public interface SysLoginErrorService extends BaseService<SysLoginErrorEntity> {
    /**
     * 验证登录错误次数
     * @param userId
     * @return
     */
    boolean validateLoginErrorNum(String userId);

    /**
     * 用过有户名进行逻辑删除
     * @param userId
     */
    void deleteLogicByUserId(String userId);
}
