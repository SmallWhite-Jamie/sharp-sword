package com.jamie.framework.file.controller;

import com.jamie.framework.file.bean.SysResource;
import com.jamie.framework.file.service.FileResourceService;
import com.jamie.framework.util.api.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author lizheng
 * @date: 12:13 2020/02/02
 * @Description: FileResourceController
 */
@RestController
@RequestMapping("file")
public class FileResourceController {

    @Autowired
    private FileResourceService fileResource;

    @RequestMapping("create")
    public ApiResult create(SysResource resource) {
        fileResource.create(resource);
        return ApiResult.ok();
    }

    @RequestMapping("delete/{id}")
    public ApiResult delete(@PathVariable String id) {
        fileResource.delete(id);
        return ApiResult.ok();
    }

    @RequestMapping("info/{id}")
    public ApiResult info(@PathVariable String id) {
        SysResource resource = fileResource.getInfoById(id);
        return ApiResult.ok(resource);
    }

    @RequestMapping("downloadMultiThread/{resId}")
    public void downloadMultiThread(@PathVariable String resId) {
        fileResource.downloadMultiThread(resId);
    }

    @RequestMapping("download/{resId}")
    public void download(@PathVariable String resId) throws IOException {
        fileResource.download(resId);
    }

    @RequestMapping("upload")
    public ApiResult upload(MultipartFile file) throws IOException {
        SysResource resource = fileResource.upload(file);
        return ApiResult.ok(resource);
    }



}
