package org.ehais.shop.controller.admin;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/cache")
public class CacheController extends CommonController{

	private static Logger log = LoggerFactory.getLogger(CacheController.class);
	
	private String web_url = "http://shop.ehais.com";
	private String timestamp = String.valueOf(System.currentTimeMillis());
	private String appkey = "ifuckehais";
	private String secret = "ufuckehais";
	
	
	@RequestMapping("/clear")
	public String clear(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws UnsupportedEncodingException {
		
		
		return "/admin/cache/clear";
	}
	
	
	@RequestMapping("/clear-submit")
	public String clearSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ,
			@RequestParam(value = "url", required = true) String url
			) throws UnsupportedEncodingException {
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", appkey); paramsMap.put("ver", "v1.0"); paramsMap.put("timestamp", timestamp);
		paramsMap.put("store_id", store_id.toString()); 
    	String sign = SignUtil.getSignWS(paramsMap,secret);
    	paramsMap.put("sign", sign.toLowerCase());
    	Map<String,String> mapHeader = new HashMap<String, String>();
    	mapHeader.put("User-Agent", "ehais");
		String req = EHttpClientUtil.httpPost("http://"+url+"/Cache-Clear.api", paramsMap);
		modelMap.addAttribute("req", req);
		return "/admin/cache/clear";
	}
	
	
}
