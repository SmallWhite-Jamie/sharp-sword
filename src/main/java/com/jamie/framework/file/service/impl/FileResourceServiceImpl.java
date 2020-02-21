package com.jamie.framework.file.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.ContentType;
import cn.hutool.system.OsInfo;
import com.alibaba.fastjson.JSON;
import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.file.bean.SysResource;
import com.jamie.framework.file.enumeration.StorageType;
import com.jamie.framework.file.mapper.SysResourceMapper;
import com.jamie.framework.file.service.FileResourceService;
import com.jamie.framework.service.impl.AppBaseService;
import com.jamie.framework.util.ApplicationContextUtil;
import com.jamie.framework.util.DateTimeUtils;
import com.jamie.framework.util.RegExpValidatorUtils;
import com.jamie.framework.util.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

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

    @Autowired
    private AppBaseService baseService;

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
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + resource.getSuffix());
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

    @Override
    public void downloadMultiThread(String resId) {
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        SysResource resource;
//        if (requestAttributes == null || (resource = this.getInfoById(resId)) == null) {
//            return;
//        }
//        requestAttributes.getRequest().getHeader("");
    }

    @Override
    public SysResource upload(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String newName = ApplicationContextUtil.getIdGenerator().nextIdStr();
            StringBuilder builder = new StringBuilder();
            // 系统配置路径
            String filePath = ApplicationContextUtil.getAppProperties().getFilePath();
            if (filePath == null) {
                OsInfo osInfo = new OsInfo();
                if (osInfo.isWindows()) {
                    // Windows
                    builder.append("C:");
                    builder.append(File.separatorChar);
                    builder.append("resource");
                } else if (osInfo.isLinux()) {
                    // linux
                    builder.append("/data");
                }
            } else {
                builder.append(filePath);
            }
            // 系统分隔符
            builder.append(File.separatorChar);
            // 当前日期文件夹
            builder.append(DateTimeUtils.getDateStr());
            File dir = new File(builder.toString());
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    log.error("文件夹创建失败");
                    throw new RuntimeException("文件夹创建失败");
                }
            }
            // 系统分隔符
            builder.append(File.separatorChar);
            // 文件夹
            builder.append(newName);
            String path =  builder.toString();
            file.transferTo(new File(builder.toString()));
            SysResource resource = new SysResource();
            resource.setName(getFilename(originalFilename));
            resource.setSize(file.getSize());
            resource.setCrteater(baseService.getUserId());
            resource.setSuffix(getSuffix(originalFilename));
            resource.setStatus(1);
            resource.setStorageType(StorageType.SERVER_PATH);
            resource.setUpdateTime(new Date());
            resource.setFilepath(path);
            sysResourceMapper.insert(resource);
            return resource;
        }
        return null;
    }

    @Override
    public void delete(String id) {
        SysResource resource = this.getInfoById(id);
        if (resource != null && StringUtils.isNotBlank(resource.getFilepath())) {
            File file = new File(resource.getFilepath());
            if (file.exists()) {
                if (file.delete()) {
                    sysResourceMapper.deleteById(id);
                }
            }
        }
    }

    @Override
    public SysResource create(SysResource resource) {
        resource.setStatus(1);
        resource.setCrteater(baseService.getUserId());
        resource.setUpdateTime(new Date());
        sysResourceMapper.insert(resource);
        return resource;
    }

    private String getFilename(String originalFilename) {
        if (StringUtils.isBlank(originalFilename)) {
            return "";
        }
        return originalFilename.substring(0, originalFilename.lastIndexOf("."));
    }

    private String getSuffix(String originalFilename) {
        if (StringUtils.isBlank(originalFilename)) {
            return "";
        }
        return originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
    }
}
