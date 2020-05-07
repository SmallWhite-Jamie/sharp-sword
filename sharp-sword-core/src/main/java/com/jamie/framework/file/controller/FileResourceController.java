package com.jamie.framework.file.controller;

import com.jamie.framework.file.bean.SysResource;
import com.jamie.framework.file.service.FileResourceService;
import com.jamie.framework.shiro.support.ShiroAnon;
import com.jamie.framework.util.api.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizheng
 * @date: 12:13 2020/02/02
 * @Description: FileResourceController
 */
@ShiroAnon
@RestController
@RequestMapping("file")
@Api(value = "文件资源管理", tags = { "文件资源管理"})
public class FileResourceController {

    @Autowired
    private FileResourceService fileResource;

    @PostMapping("create")
    @ApiOperation(value = "创建一个资源", notes = "非HTTP上传的方式创建一个资源信息", response = SysResource.class)
    public ApiResult create(SysResource resource) {
        SysResource sysResource = fileResource.create(resource);
        return ApiResult.ok(sysResource);
    }

    @PostMapping("delete/{id}")
    @ApiOperation(value = "删除", notes = "根据资源ID, 删除对应资源")
    public ApiResult delete(@PathVariable String id) {
        fileResource.delete(id);
        return ApiResult.ok();
    }

    @GetMapping("info/{id}")
    @ApiOperation(value = "详情", notes = "根据资源ID, 获取资源详情", response = SysResource.class)
    public ApiResult info(@PathVariable String id) {
        SysResource resource = fileResource.getInfoById(id);
        return ApiResult.ok(resource);
    }

    @PostMapping("downloadMultiThread/{resId}")
    @ApiOperation(value = "断点续传方式下载", notes = "根据资源ID, 下载资源文件")
    public void downloadMultiThread(@PathVariable String resId) {
        fileResource.downloadMultiThread(resId);
    }

    @PostMapping("download/{resId}")
    @ApiOperation(value = "下载", notes = "根据资源ID, 下载资源文件")
    public void download(@PathVariable String resId) throws Exception {
        fileResource.download(resId);
    }

    @PostMapping("upload")
    @ApiOperation(value = "上传", notes = "上传一个资源文件", response = SysResource.class)
    public ApiResult upload(MultipartFile file) throws IOException {
        SysResource resource = fileResource.upload(file);
        return ApiResult.ok(resource);
    }



}
