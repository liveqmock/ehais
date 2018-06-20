package org.ehais.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.util.ECommon;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class OpenApiInterceptor extends HandlerInterceptorAdapter{

	private String securityPolicy;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

        String store_id = request.getParameter("store_id");
        if(StringUtils.isEmpty(store_id) || StringUtils.isBlank(store_id)) {
        	response.getWriter().write("{'code':2,'msg':'你认为你是帅哥吗?'}");
        	return false;
        }
		
	    response.addHeader("Access-Control-Allow-Origin","*");
	    response.addHeader("Access-Control-Allow-Methods","*");
	    response.addHeader("Access-Control-Max-Age","100");
	    response.addHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.addHeader("Access-Control-Allow-Credentials","false");
	    return super.preHandle(request, response, handler);
        
//		return true;
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

