package org.ehais.shop.controller.weixin;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublic;
import org.ehais.epublic.service.EUsersService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ECommon;
import org.ehais.util.ResourceUtil;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.model.WeiXinNotityXml;
import org.ehais.weixin.model.WeiXinUserInfo;
import org.ehais.weixin.utils.WeiXinUtil;
import org.ehais.weixin.utils.aes.WXBizMsgCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/")
public class WeixinController extends CommonController{

	protected String website = ResourceUtil.getProValue("website");
	
	@Autowired
	protected EWPPublicService eWPPublicService;
	@Autowired
	private EUsersService eUsersService;
	
	/**
	 * 微信通信接口对接
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param store_id
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/wx{store_id}")
	public String wx(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("store_id") Integer store_id,
			@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "timestamp", required = false) String timestamp,
			@RequestParam(value = "nonce", required = false) String nonce,
			@RequestParam(value = "echostr", required = false) String echostr) {

		try {
			if(signature != null && !signature.equals("")){
				log.info("store_id:"+store_id+" | signature: " + signature + " | timestamp: " + timestamp + " | nonce : " + nonce + " | echostr : " + echostr);
				if(WeiXinUtil.checkSignature(timestamp, nonce, signature)){
					if(echostr != null && !echostr.equals(""))return echostr;
					
					String inputLine = null;
					String notityXml = "";
					while ((inputLine = request.getReader().readLine()) != null) {
						notityXml += inputLine;
					}
					System.out.println("notityXml:"+notityXml);
					if(notityXml!=null && !notityXml.equals("")){
						WeiXinNotityXml notity = WeiXinUtil.toNotityXml(notityXml);
//						
//						log.info("notity.getEncrypt():"+notity.getEncrypt());
						
//						WXBizMsgCrypt wmc = new WXBizMsgCrypt("ehais_wxdev","ohilBTsxcO7ydwH9y36C9KyRfQsxaAPRDKDIdqd8CjZ","wxb7e05d362dab27b1");
//						String content = wmc.decrypt(notity.getEncrypt());
//						System.out.println("content:"+content);
						
								
						
						
//						String reqXml = weiXinService.WeiXinNotityEvent(request ,wxid , notity);
//						
//						return reqXml;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	
	
	/**
	 * @描述 获取用户openid信息进入业务界面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param store_id
	 * @param scope
	 * @param controller
	 * @param postfix
	 * @return
	 * @作者 lgj628
	 * @日期 2016年10月29日
	 * @返回 String
	 */
	@RequestMapping("/gowx/{store_id}/{scope}/{controller}.{postfix}")
	public String gowx(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "store_id", required = true) Integer store_id,
			@PathVariable(value = "scope", required = true) String scope,//snsapi_base , snsapi_userinfo
			@PathVariable(value = "controller", required = true) String controller,
			@PathVariable("postfix") String postfix
			){
		System.out.println("========"+request.getScheme()+"://"+request.getServerName()+"/"+request.getRequestURI()+";"+request.getServletPath());
		//微信类型错误
		if(StringUtils.isBlank(scope) || (!scope.equals("snsapi_base") && !scope.equals("snsapi_userinfo")) ) {
			return "redirect:"+website;
		}
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			if(request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1")){
				//测试使用
				String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/weixin/openid/"+store_id+"/"+controller+"."+postfix;
				url+="?code="+ECommon.nonceStr(32);
						
				log.info("url:"+url);
				response.sendRedirect( url );
			}else{//正式使用
				WpPublic wpPublic = eWPPublicService.getWpPublic(store_id);
				//整理要回调给自己的地址
				String url = request.getScheme()+"://"+request.getServerName()+"/openid/"+store_id+"/"+controller+"."+postfix;
				log.info("url:"+url);
				url = URLEncoder.encode(url, "utf-8");
				//整理请求获取openid的微信链接
				String wxurl = WeiXinUtil.authorize_snsapi(wpPublic.getAppid(), scope, url);
				log.info("wxurl:"+wxurl);
				//滚去请求，即response.sendR***(wxurl);
				response.sendRedirect( wxurl );
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("go web page", e);
		}
		return null;
	}
	
	
	@RequestMapping("/openid/{store_id}/{controller}.{postfix}")
	public String getOpenid(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "code", required = true) String code,
			@PathVariable(value = "controller", required = true) String controller,
			@PathVariable("postfix") String postfix
			){
		log.info("openid:==controller:"+controller+"/postfix="+postfix+"/code="+code);
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		//验证session store_id是否正确
		Integer session_store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		if(store_id != session_store_id) {
			return "redirect:"+website;
		}
		
		try{
			if(request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1")){
				String controllurl = "/"+controller.replaceAll("-", "/")+"."+postfix;
				log.info("localhost log openid controllurl "+controllurl);
				String openid = "o5eMcv6IGgGFswvaK8a_tCVA-qHw";
				Long userId = 10L;
				String userName = "lgj628";
				request.getSession().setAttribute(EConstants.SESSION_OPEN_ID, openid);
				request.getSession().setAttribute(EConstants.SESSION_USER_ID, userId);
				request.getSession().setAttribute(EConstants.SESSION_USER_NAME, userName);
				
				response.sendRedirect( controllurl );
			}else{
				
				WpPublic wpPublic = eWPPublicService.getWpPublic(store_id);
				//根据code获取openid信息，一样的请求方式
				OpenidInfo open = WeiXinUtil.getOpenid(code, wpPublic.getAppid(), wpPublic.getSecret());
				String openid = open.getOpenid();	
				log.info( "openid:" + openid );
				System.out.println("system openid : " + openid);
				AccessToken accessToken = WeiXinUtil.getAccessToken(store_id,wpPublic.getAppid(), wpPublic.getSecret());
				WeiXinUserInfo userInfo = WeiXinUtil.getUserInfo(accessToken.getAccess_token(),openid);
				
				System.out.println("返回的微信用户信息："+this.writeJsonObject(userInfo));
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("userInfo", userInfo);		
				if(userInfo != null){
					//保存用户信息于我们的数据库中
					ReturnObject<EHaiUsers> rm = eUsersService.wx_user_save(
							request,
							store_id, 
							"", 
//							(userInfo.getNickname() == null ? "" : userInfo.getNickname()),
							"",
							"", 
							userInfo.getNickname(), 
							"", 
							userInfo.getSex(), 
							userInfo.getSubscribe(), 
							openid, 
							userInfo.getCity(), 
							userInfo.getCountry(), 
							userInfo.getProvince(), 
							userInfo.getLanguage(), 
							userInfo.getHeadimgurl(), 
							userInfo.getSubscribe_time(), 
							userInfo.getUnionid(), 
							userInfo.getRemark(), 
							userInfo.getGroupid()
							);
					Long userId = null;
					if(rm.getCode() == 1 && rm.getModel() != null){
						userId = rm.getModel().getUserId();
						request.getSession().setAttribute(EConstants.SESSION_OPEN_ID, openid);
						request.getSession().setAttribute(EConstants.SESSION_USER_ID, userId);
						request.getSession().setAttribute(EConstants.SESSION_USER_NAME, rm.getModel().getUserName());
					}
					//保存用户信息于session中				
					map.put("userId", userId);
					
				}
				
				JSONObject json = JSONObject.fromObject(map);		
				request.getSession().setAttribute("userInfo", json.toString());
				log.info("userInfo session:"+json.toString());
				System.out.println("userInfo session:"+json.toString());
				
				String controllurl = "/"+controller.replaceAll("-", "/")+"."+postfix;
				log.info(wpPublic.getAppid()+"  log openid controllurl   "+controllurl);
				
				response.sendRedirect( controllurl );
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("getOpenid web page", e);
			
		}	
		
		return null;		
	}
	
}
