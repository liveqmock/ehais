package org.ehais.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.cache.ERolePermissionCacheManager;
import org.ehais.common.EConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInterceptor extends HandlerInterceptorAdapter{

	private String loginUrl;
	private String notpermissionUrl;
	private String[] ignoreUrls;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("X-XSS-Protection", "1");
		response.addHeader("X-Frame-Options", "deny");
		response.addHeader("X-Content-Type-Options", "nosniff");
		response.addHeader("Content-Security-Policy", "default-src 'self' "+request.getServerName()+" ;style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline' 'unsafe-eval';");
		response.addHeader("Set-Cookie", "key=value; HttpOnly");
		response.addHeader("Content-Type", "text/html;charset:utf-8;");
		
		//获取当前请求URL
		String url = request.getRequestURI().toString();
//		System.out.println(url);
		if (url.equals(loginUrl)) {
			return true;
		}
		String account = (String) request.getSession().getAttribute(EConstants.SESSION_ADMIN_NAME);
		
		//如果session中用户名为空，则跳转到登录页面
		if (StringUtils.isBlank(account)) {
			//如果是ajax请求
			if (request.getHeader("X-Requested-With") != null) {
//				response.getWriter().write(JsonResult.buildException("未登录，或已退出").toString());
			} else {
				response.sendRedirect(loginUrl);
			}
			return false;
		}
		
		
		
		// 从 HTTP 头中取得 Referer 值
		String referer=request.getHeader("Referer");
		// 判断 Referer 是否以 bank.example 开头

		if(referer==null || !referer.trim().startsWith(request.getScheme() + "://" + request.getServerName())){
			response.sendRedirect(notpermissionUrl);
			return false;
		} 
		 
		 
		//判断当前URL是不是登录页面，如果是，则不拦截
		for (String s : ignoreUrls) {
			if (url.equals(s)) {
				return true;
			}
		}
				
		//权限判断
		String role = (String) request.getSession().getAttribute(EConstants.SESSION_ROLE_ID_ARRAY);
		if(StringUtils.isBlank(role)){
			response.sendRedirect(notpermissionUrl);
			return false;
		}
//		System.out.println("role==== : "+ role);
		List<String> result = Arrays.asList(StringUtils.split(role,","));  
		for (String string : result) {
//			System.out.println("role : "+ string);
			if(ERolePermissionCacheManager.checkRolePermission(Integer.valueOf(string), url)){
				return true;
			}
		}
		
		response.sendRedirect(notpermissionUrl);
		return false;
	}
	
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	


	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public String[] getIgnoreUrls() {
		return ignoreUrls;
	}
	public void setIgnoreUrls(String[] ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}
	public String getNotpermissionUrl() {
		return notpermissionUrl;
	}
	public void setNotpermissionUrl(String notpermissionUrl) {
		this.notpermissionUrl = notpermissionUrl;
	}
	
}
