package com.jamie.framework.file.controller;

import com.jamie.framework.file.bean.SysResource;
import com.jamie.framework.file.service.FileResourceService;
import com.jamie.framework.util.api.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("info/{id}")
    public ApiResult info(@PathVariable String id) {
        SysResource resource = fileResource.getInfoById(id);
        return ApiResult.ok(resource);
    }


}
