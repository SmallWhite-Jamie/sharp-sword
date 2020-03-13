package com.jamie.framework.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jamie.framework.login.entity.SysLoginErrorEntity;
import com.jamie.framework.login.LoginValidatorProperties;
import com.jamie.framework.login.mapper.SysLoginErrorMapper;
import com.jamie.framework.login.service.SysLoginErrorService;
import com.jamie.framework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * SysLoginErrorServiceImpl
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 19:18
 */
@Service
public class SysLoginErrorServiceImpl extends BaseServiceImpl<SysLoginErrorMapper, SysLoginErrorEntity> implements SysLoginErrorService {

    @Autowired
    private LoginValidatorProperties loginValidatorProperties;

    @Autowired
    private SysLoginErrorMapper sysLoginErrorMapper;

    @Override
    public boolean validateLoginErrorNum(String userId) {
        if (loginValidatorProperties.isVerifyPasswordErrorNum()) {
            SysLoginErrorEntity errorEntity = new SysLoginErrorEntity();
            errorEntity.setUserId(userId);
            errorEntity.setIsDeleted(0);
            QueryWrapper<SysLoginErrorEntity> wrapper = new QueryWrapper<>(errorEntity);
            // 默认10分钟后恢复登录
            wrapper.ge("login_time", LocalDateTime.now().minusSeconds(loginValidatorProperties.getTime()));
            if (super.count(wrapper) >= loginValidatorProperties.getNum()) {
                return false;
            } else {
                // 默认5分钟内允许出错5次
                wrapper.ge("login_time", LocalDateTime.now().minusSeconds(loginValidatorProperties.getInterval()));
                return super.count(wrapper) < loginValidatorProperties.getNum();
            }
        }
        return true;
    }

    @Override
    public void deleteLogicByUserId(String userId) {
        sysLoginErrorMapper.deleteLogicByUserId(userId);
    }
}
