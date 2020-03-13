package com.jamie.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jamie.framework.constant.AppConstant;
import com.jamie.framework.entity.BaseEntity;
import com.jamie.framework.service.BaseService;
import com.jamie.framework.util.ApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * BaseServiceImpl
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 15:32
 */
public class BaseServiceImpl <M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public boolean save(T entity) {
        this.fullEntity(entity);
        return super.save(entity);
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        if (StringUtils.isBlank(entity.getId())) {
            this.fullEntity(entity);
        } else {
            this.updateEntity(entity);
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        String userId = ApplicationContextUtil.getAppBaseService().getUserId();
        entityList.forEach(item -> {
            if (StringUtils.isBlank(item.getId())) {
                this.fullEntity(item, userId);
            } else {
                this.updateEntity(item, userId);
            }
        });
        return super.saveOrUpdateBatch(entityList, batchSize);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        String userId = ApplicationContextUtil.getAppBaseService().getUserId();
        entityList.forEach(item -> this.fullEntity(item, userId));
        return super.saveBatch(entityList, batchSize);
    }

    @Override
    public boolean updateById(T entity) {
        this.updateEntity(entity);
        return super.updateById(entity);
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        this.updateEntity(entity);
        return super.update(entity, updateWrapper);
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        String userId = ApplicationContextUtil.getAppBaseService().getUserId();
        entityList.forEach(item -> this.updateEntity(item, userId));
        return super.updateBatchById(entityList, batchSize);
    }

    /**
     * 填充更新字段数据
     * @param entity
     * @param userId
     */
    private void updateEntity(T entity, String ...userId) {
        String id;
        if (userId.length == 0) {
            id = ApplicationContextUtil.getAppBaseService().getUserId();
        } else {
            id = userId[0];
        }
        entity.setUpdateUser(id);
        entity.setUpdateTime(new Date());
        entity.setIsDeleted(AppConstant.INT_ZERO);
        entity.setRevision(AppConstant.INT_ZERO);
    }

    /**
     * 填充创建更新字段数据
     * @param entity
     * @param userId
     */
    private void fullEntity(T entity, String ...userId) {
        String id;
        if (userId.length == 0) {
            id = ApplicationContextUtil.getAppBaseService().getUserId();
        } else {
            id = userId[0];
        }
        entity.setCreateUser(id);
        entity.setCreateTime(new Date());
        entity.setUpdateUser(id);
        entity.setUpdateTime(new Date());
        entity.setIsDeleted(AppConstant.INT_ZERO);
        entity.setRevision(AppConstant.INT_ZERO);
    }
}
