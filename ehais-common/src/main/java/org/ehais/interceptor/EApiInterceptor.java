package org.ehais.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;









import org.ehais.cache.EApiCacheManager;
import org.ehais.cache.EStoreAppKeySecretCacheManager;
import org.ehais.common.Constants;
import org.ehais.util.ECommon;
import org.ehais.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class EApiInterceptor extends HandlerInterceptorAdapter{

//	@Autowired
//	private String apiUrl;
//	@Autowired
//	private String[] ignoreUrls;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		System.out.println("request.getServletPath():"+request.getServletPath());
		String url = request.getRequestURI().toString();
//		System.out.println("url:"+url);
		//判断当前URL是不是登录页面，如果是，则不拦截
//		for (String s : ignoreUrls) {
//			if (url.equals(s)) {
//				return true;
//			}
//		}
		
		if(request.getParameter("appkey") == null || request.getParameter("appkey").toString().trim().equals("")){
			response.getWriter().print("{'code':0,'msg':'参数不全a1001'}");
			return false;
		}
		
		if(request.getParameter("version") == null || request.getParameter("version").toString().trim().equals("")){
			response.getWriter().print("{'code':0,'msg':'参数不全v1002'}");
			return false;
		}
		
		if(request.getParameter("timestamp") == null || request.getParameter("timestamp").toString().trim().equals("")){
			response.getWriter().print("{'code':0,'msg':'参数不全t1003'}");
			return false;
		}
		
		if(request.getParameter("sign") == null || request.getParameter("sign").toString().trim().equals("")){
			response.getWriter().print("{'code':0,'msg':'参数不全s1004'}");
			return false;
		}
				
		if(!ECommon.isNumeric(request.getParameter("timestamp").toString().trim())){
			response.getWriter().print("{'code':0,'msg':'参数错误t1005'}");
			return false;
		}
		
		//10 * 60 * 1000 10分钟转化成豪秒数比较，误差不得超过10分钟
		if(Math.abs(System.currentTimeMillis() - Long.parseLong(request.getParameter("timestamp").toString().trim())) > (600000)){
			response.getWriter().print("{'code':0,'msg':'请求超时t1006'}");
			return false;
		}
		
		
		//必须参数
		String[] mustParameter = EApiCacheManager.getInstance(request).getApiParamterMap(url);
		if ( mustParameter != null){
			for (String string : mustParameter) {
				if(request.getParameter(string) == null || request.getParameter(string).toString().trim().equals("")){
					response.getWriter().print("{'code':0,'msg':'参数不全'}");
					return false;
				}
			}
		}
		
		String secret = EStoreAppKeySecretCacheManager.getInstance().getStoreSecret(request.getParameter("appkey").toString().trim());
		if(secret == null || secret.equals("")){
			response.getWriter().print("{'code':0,'msg':'不存在的应用'}");
			return false;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		for(Enumeration e = request.getParameterNames() ; e.hasMoreElements();){
			String thisName=e.nextElement().toString();
			String thisValue=request.getParameter(thisName);
//			System.out.println(thisName + " ======= "+ thisValue);
			if(thisName.equals("sign"))continue;
			map.put(thisName, thisValue);
		}
		
		String signWS = SignUtil.getSignWS(map, secret);
		if(!request.getParameter("sign").toString().trim().toUpperCase().equals(signWS)){
			response.getWriter().print("{'code':0,'msg':'数字签名错误'}");
			return false;
		}
		
		return true;
	}
	
	

	
}
