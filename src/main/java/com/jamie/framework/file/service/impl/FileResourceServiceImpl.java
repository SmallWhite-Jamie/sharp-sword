package com.jamie.framework.file.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.ContentType;
import com.alibaba.fastjson.JSON;
import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.file.bean.SysResource;
import com.jamie.framework.file.enumeration.StorageType;
import com.jamie.framework.file.mapper.SysResourceMapper;
import com.jamie.framework.file.service.FileResourceService;
import com.jamie.framework.util.RegExpValidatorUtils;
import com.jamie.framework.util.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author lizheng
 * @date: 12:14 2020/02/02
 * @Description: FileResourceServiceImpl
 */
@Service
@Slf4j
public class FileResourceServiceImpl implements FileResourceService {
    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Autowired
    private AppProperties appProperties;

    @Override
    public SysResource getInfoById(String id) {
        return sysResourceMapper.selectById(id);
    }

    @Override
    public void download(String resId) throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response;
        if (requestAttributes != null && (response = ((ServletRequestAttributes) requestAttributes).getResponse()) != null) {
            SysResource resource = this.getInfoById(resId);
            //设置响应头和客户端保存文件名
            response.setCharacterEncoding("utf-8");
            response.setContentType(ContentType.MULTIPART.toString());
            String fileName = new String(resource.getName().getBytes(CharsetUtil.UTF_8), CharsetUtil.ISO_8859_1);
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + "." + resource.getSuffix());
            if (resource.getStorageType() == StorageType.NETWORK) {
                String url = resource.getUrl();
                if (StringUtils.isNotBlank(url) && RegExpValidatorUtils.isUrl(url)) {
                    response.sendRedirect(url);
                }
            } else if (resource.getStorageType() == StorageType.SERVER_PATH) {
                if (StringUtils.isNotBlank(resource.getFilepath())) {
                    String filePath = appProperties.getFilePath();
                    File file;
                    if (StringUtils.isNotBlank(filePath)) {
                        file = new File(filePath, resource.getFilepath());
                    } else {
                        file = new File(resource.getFilepath());
                    }
                    FileInputStream inputStream = null;
                    ServletOutputStream outputStream = null;
                    try {
                        outputStream = response.getOutputStream();
                        inputStream = new FileInputStream(file);
                        IoUtil.copy(inputStream, outputStream);
                    } catch (Exception e) {
                        response.setContentType(ContentType.JSON.toString());
                        response.setHeader("Content-Disposition", "");
                        if (outputStream != null) {
                            outputStream.write(JSON.toJSONString(ApiResult.fail("下载失败")).getBytes());
                            outputStream.flush();
                        }
                        log.error("文件下载失败", e);
                    } finally {
                        IoUtil.close(inputStream);
                        IoUtil.close(outputStream);
                    }
                }

            }
        }
    }
}
