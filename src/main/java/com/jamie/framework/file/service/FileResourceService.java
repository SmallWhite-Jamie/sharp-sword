package com.jamie.framework.file.service;

import com.jamie.framework.file.bean.SysResource;

import java.io.IOException;

/**
 * @author lizheng
 * @date: 12:14 2020/02/02
 * @Description: FileResourceService
 */
public interface FileResourceService {

    /**
     * 获取资源信息
     * @param id
     * @return
     */
    SysResource getInfoById(String id);

    /**
     * 根据资源ID下载
     * @param resId
     */
    void download(String resId) throws IOException;
}
