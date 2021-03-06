package org.ehais.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.util.ECommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DiningInterceptor extends HandlerInterceptorAdapter{

	private String loginUrl;
	private String[] ignoreUrls;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//获取当前请求URL
		String url = request.getRequestURI().toString()+(request.getQueryString() != null?("?"+request.getQueryString().toString()):"");
//		System.out.println(url);
		
		String s_encode = (String) request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		if(s_encode == null || s_encode.equals("")){
			s_encode = ECommon.nonceStrUpper(32);
			System.out.println("商码："+s_encode);
			request.getSession().setAttribute(EConstants.SESSION_SHOP_ENCODE,s_encode);
		}
		
		if(url.equals(loginUrl)){
			return true;
		}
		//判断当前URL是不是登录页面，如果是，则不拦截
		for (String s : ignoreUrls) {
			if (url.indexOf(s) >= 0) {
				return true;
			}
		}
		
		
		
		Long admin_id = (Long) request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		
		//如果session中用户名为空，则跳转到登录页面
		if (admin_id == null || admin_id == 0) {
			//如果是ajax请求
			if (request.getHeader("X-Requested-With") != null) {
				response.getWriter().write("{'code':2,'msg':'请登录再请求'}");
			} else {
				request.getSession().setAttribute("BACK-SHOP-URL",url);
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

