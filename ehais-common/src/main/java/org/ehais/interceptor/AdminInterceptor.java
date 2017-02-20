package org.ehais.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.ehais.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInterceptor extends HandlerInterceptorAdapter{

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
		String account = (String) request.getSession().getAttribute(Constants.SESSION_ADMIN_NAME);
		
		//如果session中用户名为空，则跳转到登录页面
		if (account == null || "".equals(account)) {
			//如果是ajax请求
			if (request.getHeader("X-Requested-With") != null) {
//				response.getWriter().write(JsonResult.buildException("未登录，或已退出").toString());
			} else {
				response.sendRedirect(loginUrl);
			}
			return false;
		}
		
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
