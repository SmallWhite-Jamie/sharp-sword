package com.jamie.framework.jobadmin.controller.interceptor;

import com.jamie.framework.jobadmin.controller.annotation.PermissionLimit;
import com.jamie.framework.jobadmin.core.model.XxlJobUser;
import com.jamie.framework.jobadmin.core.util.I18nUtil;
import com.jamie.framework.jobadmin.service.XxlJobAdminLoginService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截
 *
 * @author xuxueli 2015-12-12 18:09:04
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {


	@Resource
	private XxlJobAdminLoginService xxlJobAdminLoginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return super.preHandle(request, response, handler);
		}

		// if need login
		boolean needLogin = true;
		boolean needAdminuser = false;
		HandlerMethod method = (HandlerMethod)handler;
		PermissionLimit permission = method.getMethodAnnotation(PermissionLimit.class);
		if (permission!=null) {
			needLogin = permission.limit();
			needAdminuser = permission.adminuser();
		}

		if (needLogin) {
			XxlJobUser loginUser = xxlJobAdminLoginService.ifLogin(request, response);
			if (loginUser == null) {
				response.sendRedirect(request.getContextPath() + "/xxl-job-admin/toLogin");
				//request.getRequestDispatcher("/toLogin").forward(request, response);
				return false;
			}
			if (needAdminuser && loginUser.getRole()!=1) {
				throw new RuntimeException(I18nUtil.getString("system_permission_limit"));
			}
			request.setAttribute(XxlJobAdminLoginService.LOGIN_IDENTITY_KEY, loginUser);
		}

		return super.preHandle(request, response, handler);
	}
	
}
