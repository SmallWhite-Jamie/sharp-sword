package com.jamie.framework.file.service.impl;

import com.jamie.framework.file.bean.SysResource;
import com.jamie.framework.file.mapper.SysResourceMapper;
import com.jamie.framework.file.service.FileResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lizheng
 * @date: 12:14 2020/02/02
 * @Description: FileResourceServiceImpl
 */
@Service
public class FileResourceServiceImpl implements FileResourceService {
    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    public SysResource getInfoById(String id) {
        return sysResourceMapper.selectById(id);
    }
}
