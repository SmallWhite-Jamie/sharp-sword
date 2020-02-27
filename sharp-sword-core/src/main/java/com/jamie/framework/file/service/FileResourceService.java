package com.jamie.framework.file.service;

import com.jamie.framework.file.bean.SysResource;
import org.springframework.web.multipart.MultipartFile;

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
    void download(String resId) throws Exception;

    /**
     * 上传文件
     * @param file
     * @return
     */
    SysResource upload(MultipartFile file) throws IOException;

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 创建一个资源信息
     * @param resource
     * @return
     */
    SysResource create(SysResource resource);

    /**
     * 断点续传的方式下载
     * @param resId
     */
    void downloadMultiThread(String resId);
}
