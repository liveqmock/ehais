package org.ehais.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MediaInterceptor extends HandlerInterceptorAdapter{

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		System.out.println("..............media................"+request.getServerName());
		//获取当前请求URL
		response.addHeader("X-XSS-Protection", "1");
		response.addHeader("X-Frame-Options", "deny");
		response.addHeader("X-Content-Type-Options", "nosniff");
		response.addHeader("Content-Security-Policy", "default-src 'self' "+request.getServerName()+" 10.0.10.220 219.222.224.28 219.222.224.45;style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline' 'unsafe-eval';");
		response.addHeader("Set-Cookie", "key=value; HttpOnly");
		response.addHeader("Content-Type", "text/html;charset:utf-8;");
		
		return true;
	}
	
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	

	
}

