package com.sharp.sword.controller;

import com.sharp.sword.bean.SysPermission;
import com.sharp.sword.bean.SysRoles;
import com.sharp.sword.service.PermissionService;
import com.sharp.sword.service.impl.AppBaseService;
import com.sharp.sword.util.api.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizheng
 * @date: 16:33 2019/10/21
 * @Description: PermissionController
 */
@RestController
public class PermissionController {
    @Autowired
    private AppBaseService appBaseService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/permission")
    public ApiResult permission() {
        String userId = appBaseService.getUserId();
        List<SysPermission> permission = permissionService.getSysPermissionByUserId(userId);
        List<SysRoles> roles = permissionService.getRolesByUserId(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("permissions", permission);
        data.put("roles", roles);
        return ApiResult.ok(data);
    }
}
