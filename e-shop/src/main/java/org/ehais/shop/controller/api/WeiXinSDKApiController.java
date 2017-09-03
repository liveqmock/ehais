package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.JsApiTicket;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class WeiXinSDKApiController extends CommonController{
	
	@Autowired
	protected EWPPublicService eWPPublicService;
	
	/**
	 * 微信认证登录跳转
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param appid
	 * @param redirect_uri
	 * @param scope
	 * @return
	 */
	@RequestMapping("/connect/oauth2/authorize")
	public String authorize(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "appid", required = true) String appid,
			@RequestParam(value = "redirect_uri", required = true) String redirect_uri,
			@RequestParam(value = "scope", required = true) String scope) {
		try{
			System.out.println("");
			System.out.println("authorize=========================");
			
			System.out.println(redirect_uri);
			redirect_uri = java.net.URLEncoder.encode(redirect_uri, "utf-8");
			System.out.println(redirect_uri);
			
			
			String redirect = request.getScheme() + "://" + request.getServerName()+"/connect/oauth2/authorize_code?redirect_uri_back="+redirect_uri;
			System.out.println(redirect);
			redirect = java.net.URLEncoder.encode(redirect, "utf-8");
			System.out.println(redirect);
			
			
			String authorize_redirect = WeiXinUtil.authorize_snsapi(appid, scope, redirect);
			System.out.println("authorize_redirect:"+authorize_redirect);
			
			
			return "redirect:"+authorize_redirect;
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
//	wx123.9351p.com/test_authorize
	
	@RequestMapping("/test_authorize")
	public String test_authorize(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(2);
			String redirect_uri = request.getScheme() + "://" + request.getServerName()+"/xxauthorize_back?redirect_uri=abbccdd&tyler=fuckyou";
			redirect_uri = java.net.URLEncoder.encode(redirect_uri, "utf-8");
			System.out.println(redirect_uri);
			
			
			String redirect = request.getScheme() + "://" + request.getServerName()+"/connect/oauth2/authorize?appid="+wp.getAppid()+
					"&scope=snsapi_base&redirect_uri="+redirect_uri;
			
			
			return "redirect:"+redirect;
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
	
	
	/**
	 * 微信认证登录回调code
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param redirect_uri
	 * @param code
	 * @return
	 */
	@RequestMapping("/connect/oauth2/authorize_code")
	public String authorize_code(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "redirect_uri_back", required = true) String redirect_uri_back,
			@RequestParam(value = "code", required = true) String code){
		try{
			System.out.println("");
			System.out.println("authorize_code......................");
			redirect_uri_back = java.net.URLDecoder.decode(redirect_uri_back, "utf-8");
			if(redirect_uri_back.indexOf("?")>0){
				redirect_uri_back += "&code="+code;
			}else{
				redirect_uri_back += "?code="+code;
			}
			System.out.println("redirect_uri_back:"+redirect_uri_back);
			return "redirect:"+redirect_uri_back;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String uri = "http://mg.ehais.com/aa/dd?s=ss&f=sd";
		if(uri.indexOf("?")>0){
			System.out.println("AA");
		}else{
			System.out.println("BBB");
		}
	}
	
	@ResponseBody
	@RequestMapping("/cgi-bin/token")
	public String token(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "appid", required = true) String appid,
			@RequestParam(value = "secret", required = true) String secret
			){
		try{
			AccessToken accessToken = WeiXinUtil.getAccessToken(store_id, appid, secret);
			return this.writeJsonObject(accessToken);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/cgi-bin/ticket/getticket")
	public String getticket(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "access_token", required = true) String access_token
			){
		try{
			JsApiTicket jsApiTicket = WeiXinUtil.getJsApiTicket(store_id, access_token);
			return this.writeJsonObject(jsApiTicket);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
}
