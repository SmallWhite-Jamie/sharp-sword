package com.jamie.framework.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jamie.framework.login.entity.SysLoginErrorEntity;
import org.apache.ibatis.annotations.Update;

/**
 * SysLoginErrorMapper
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 19:19
 */
public interface SysLoginErrorMapper extends BaseMapper<SysLoginErrorEntity> {
    void deleteLogicByUserId(String userId);
}
