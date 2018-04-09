package org.ehais.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * ehais business management system
 * @author Administrator
 *
 */
public class EBMSInterceptor extends HandlerInterceptorAdapter{

	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		System.out.println("request.getServletPath():"+request.getServletPath());
		String url = request.getRequestURI().toString();
		System.out.println("url:"+url);
		//判断当前URL是不是登录页面，如果是，则不拦截

		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, 97);
		
		Integer store_id = (Integer) request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		String open_id = (String) request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
	    response.addHeader("Access-Control-Allow-Origin","*");
	    response.addHeader("Access-Control-Allow-Methods","*");
	    response.addHeader("Access-Control-Max-Age","100");
	    response.addHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.addHeader("Access-Control-Allow-Credentials","false");
	    return super.preHandle(request, response, handler);
//		return true;
	}
	
	
}
