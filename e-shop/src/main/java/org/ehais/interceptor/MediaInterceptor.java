package org.ehais.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.util.ECommon;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MediaInterceptor extends HandlerInterceptorAdapter{

	private String securityPolicy;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		System.out.println("..............media................"+request.getServerName());
		
		
		
		//获取当前请求URL
//		response.addHeader("X-XSS-Protection", "1");
//		response.addHeader("X-Frame-Options", "deny");
//		response.addHeader("X-Content-Type-Options", "nosniff");
//		response.addHeader("Content-Security-Policy", "default-src 'self' "+request.getServerName()+" "+securityPolicy+";style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline' 'unsafe-eval';");
//		response.addHeader("Set-Cookie", "key=value; HttpOnly");
//		response.addHeader("Content-Type", "text/html;charset:utf-8;");
		
		
		String s_encode = (String) request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		if(StringUtils.isBlank(s_encode)){
			s_encode = ECommon.nonceStrUpper(32);
			System.out.println("商码："+s_encode);
			request.getSession().setAttribute(EConstants.SESSION_SHOP_ENCODE,s_encode);
		}
		
		
		return true;
	}
	
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	public String getSecurityPolicy() {
		return securityPolicy;
	}

	public void setSecurityPolicy(String securityPolicy) {
		this.securityPolicy = securityPolicy;
	}
	

	
}

