package org.ehais.weixin.controller.wx;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.service.EUsersService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ECommon;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.model.WeiXinNotityXml;
import org.ehais.weixin.model.WeiXinSignature;
import org.ehais.weixin.model.WeiXinUserInfo;
import org.ehais.weixin.model.WpPublic;
import org.ehais.weixin.service.wx.WeiXinService;
import org.ehais.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/weixin")
public class WeixinController extends WxCommonController{

	private static Logger log = LoggerFactory.getLogger(WeixinController.class);

	@Autowired
	private WeiXinService weiXinService;
	
	@Autowired
	private EUsersService eUsersService;
	
	/**
	 * 微信绑定地址
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wxid
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/wx{wxid}")
	public String wx(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxid") Integer wxid,
			@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "timestamp", required = false) String timestamp,
			@RequestParam(value = "nonce", required = false) String nonce,
			@RequestParam(value = "echostr", required = false) String echostr) {

		try {
			if(signature != null && !signature.equals("")){
				log.info("wxid:"+wxid+" | signature: " + signature + " | timestamp: " + timestamp + " | nonce : " + nonce + " | echostr : " + echostr);
				if(WeiXinUtil.checkSignature(timestamp, nonce, signature)){
					if(echostr != null && !echostr.equals(""))return echostr;
					
					String inputLine = null;
					String notityXml = "";
					while ((inputLine = request.getReader().readLine()) != null) {
						notityXml += inputLine;
					}
					log.info("notityXml:"+notityXml);
					if(notityXml!=null && !notityXml.equals("")){
						WeiXinNotityXml notity = WeiXinUtil.toNotityXml(notityXml);
						
						String reqXml = weiXinService.WeiXinNotityEvent(request ,wxid , notity);
						
						return reqXml;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 微信支付回调地址
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
/*	@ResponseBody
	@RequestMapping("/notify_pay")
	public String notify_pay(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response){
		String inputLine;
		String notityXml = "";
		
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			log.info("notityPayBackXml:"+notityXml);
			request.getReader().close();
			
			
			if(notityXml==null || notityXml.equals("")){
				WeiXinNotifyPay notifyPay = new WeiXinNotifyPay();
				notifyPay.setReturn_code("FAIL");
				notifyPay.setReturn_msg("接收数据为空");				
				String xml = WeiXinUtil.fromNotifyPayXml(notifyPay);
				
				System.out.println("notifyPay:"+xml);
				
				return xml;
			}
			
			
	        
			WeiXinNotifyPay notifyPay = WeiXinUtil.toNotifyPayXml(notityXml);
			
			if(notifyPay != null){
				//自己的逻辑处理
				ReturnObject<WeiXinNotifyPay> ro = weiXinService.notifyPay(notifyPay);
				
//				if(ro.getCode() == 1){					
//					notifyPay.setReturn_code("SUCCESS");
//					notifyPay.setReturn_msg("OK");					
//				}else{					
//					notifyPay.setReturn_code("FAIL");
//					notifyPay.setReturn_msg(ro.getMsg());
//				}
				
				String xml = WeiXinUtil.fromNotifyPayXml(ro.getModel());
				
				System.out.println("notifyPay:"+xml);
				
				return xml;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}*/
	
	/**
	 * 微信演示虚拟地址
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/index")
	public String wx_index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id){
		try {
			ReturnObject<Object> ro = weiXinService.wxIndex(id);
			modelMap.addAttribute("menu_list", ro.getMap().get("menu_list"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "weixin/index";
	}
	
	
	/**
	 * 微信演示虚拟地址
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/index-{id}")
	public String wx_index_id(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable Integer id){
		try {
			ReturnObject<Object> ro = weiXinService.wxIndex(id);
			modelMap.addAttribute("menu_list", ro.getMap().get("menu_list"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "weixin/index";
	}
	
	
	
	/**
	 * @描述 获取用户openid信息进入业务界面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wxid
	 * @param controller
	 * @param postfix
	 * @return
	 * @作者 lgj628
	 * @日期 2016年10月29日
	 * @返回 String
	 */
	@RequestMapping("/gowx/{wxid}/{controller}.{postfix}")
	public String gowx(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxid") Integer wxid,
			@PathVariable("controller") String controller,
			@PathVariable("postfix") String postfix
			){
		try{
			if(request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1")){
				//测试使用
				String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/weixin/openid/"+wxid+"/"+controller+"."+postfix;
				url+="?code="+ECommon.nonceStr(32);
						
				log.info("url:"+url);
				response.sendRedirect( url );
			}else{//正式使用
				WpPublic wpPublic = this.getWpPublic(wxid);
				//整理要回调给自己的地址
				String url = request.getScheme()+"://"+request.getServerName()+"/weixin/openid/"+wxid+"/"+controller+"."+postfix;
				log.info("url:"+url);
				url = URLEncoder.encode(url, "utf-8");
				//整理请求获取openid的微信链接
				String wxurl = WeiXinUtil.authorize_snsapi(wpPublic.getAppid(), "snsapi_base", url);
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
	
	
	@RequestMapping("/openid/{wxid}/{controller}.{postfix}")
	public String getOpenid(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxid") Integer wxid,
			@RequestParam(value = "code", required = true) String code,
			@PathVariable("controller") String controller,
			@PathVariable("postfix") String postfix
			){
		log.info("openid:==controller:"+controller+"/postfix="+postfix+"/code="+code);
		
		try{
			if(request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1")){
				String controllurl = "/"+controller.replaceAll("-", "/")+"."+postfix;
				log.info("localhost log openid controllurl "+controllurl);
				String openid = "o5eMcv6IGgGFswvaK8a_tCVA-qHw";
				Long userId = 10L;
				String userName = "lgj628";
				request.getSession().setAttribute(Constants.SESSION_OPEN_ID, openid);
				request.getSession().setAttribute(Constants.SESSION_USER_ID, userId);
				request.getSession().setAttribute(Constants.SESSION_USER_NAME, userName);
				
				response.sendRedirect( controllurl );
			}else{
				
				WpPublic wpPublic = this.getWpPublic(wxid);
				//根据code获取openid信息，一样的请求方式
				OpenidInfo open = WeiXinUtil.getOpenid(code, wpPublic.getAppid(), wpPublic.getSecret());
				String openid = open.getOpenid();	
				log.info( "openid:" + openid );
				System.out.println("system openid : " + openid);
				AccessToken accessToken = WeiXinUtil.getAccessToken(wxid,wpPublic.getAppid(), wpPublic.getSecret());
				WeiXinUserInfo userInfo = WeiXinUtil.getUserInfo(accessToken.getToken(),openid);
				
				System.out.println("返回的微信用户信息："+this.writeJsonObject(userInfo));
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("userInfo", userInfo);		
				if(userInfo != null){
					//保存用户信息于我们的数据库中
					ReturnObject<EHaiUsers> rm = eUsersService.wx_user_save(
							request,
							wxid, 
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
						request.getSession().setAttribute(Constants.SESSION_OPEN_ID, openid);
						request.getSession().setAttribute(Constants.SESSION_USER_ID, userId);
						request.getSession().setAttribute(Constants.SESSION_USER_NAME, rm.getModel().getUserName());
					}
					//保存用户信息于session中				
					map.put("userId", userId);
					
				}
				
				JSONObject json = JSONObject.fromObject(map);		
				request.getSession().setAttribute("userInfo", json.toString());
				log.info("userInfo session:"+json.toString());
				System.out.println("userInfo session:"+json.toString());
				
				String controllurl = "/"+controller.replaceAll("-", "/")+"."+postfix;
				log.info(wpPublic.getAppid()+"log openid controllurl "+controllurl);
				
				response.sendRedirect( controllurl );
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("getOpenid web page", e);
			
		}	
		
		return null;		
	}
	
	
	/**
	 * @描述 通用js-sdk签字类
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wxid
	 * @param url
	 * @return
	 * @throws Exception
	 * @作者 lgj628
	 * @日期 2016年10月29日
	 * @返回 String
	 */
	@ResponseBody
	@RequestMapping("/wxConfig")
	public String wxConfig(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "url", required = true) String url) throws Exception {
		WpPublic wpPublic = this.getWpPublic(wxid);
		ReturnObject<WeiXinSignature> rm = new ReturnObject<WeiXinSignature>();		
		rm.setModel(WeiXinUtil.SignatureJSSDK(request,wxid,wpPublic.getAppid(), wpPublic.getSecret(), url));
		rm.setCode(1);
		return this.writeJson(rm);
	}
	
	
}
