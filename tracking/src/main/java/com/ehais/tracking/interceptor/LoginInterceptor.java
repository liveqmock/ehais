package com.ehais.tracking.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import org.ehais.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{

	private String loginUrl;
	private String[] ignoreUrls;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		
		//获取当前请求URL
		String url = request.getRequestURI().toString();
		//判断当前URL是不是登录页面，如果是，则不拦截
		for (String s : ignoreUrls) {
			if (url.equals(s)) {
				return true;
			}
		}
		//登录类型：admin,teacher,student,school
		String login_type = (String) request.getSession().getAttribute(Constants.SESSION_ROLE_TYPE);
		
		//如果session中用户名为空，则跳转到登录页面
		if (login_type == null || "".equals(login_type)) {
			//如果是ajax请求
			response.sendRedirect(loginUrl);
			return false;
		}
		
//		String login_type = (String) request.getSession().getAttribute(Constants.SESSION_ROLE_TYPE);
		
		
		return true;
	}
	
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	


	public String getLoginUrl() {
		return loginUrl;
	}
	@Autowired
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String[] getIgnoreUrls() {
		return ignoreUrls;
	}
	@Autowired
	public void setIgnoreUrls(String[] ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}
	
}
