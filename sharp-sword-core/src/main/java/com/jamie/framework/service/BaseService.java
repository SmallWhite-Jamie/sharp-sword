package com.jamie.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * BaseService
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 15:30
 */
public interface BaseService<T> extends IService<T> {
    /**
     * 逻辑删除
     * @param ids
     * @return
     */
    void deleteLogic(@NotEmpty List<String> ids);
}
