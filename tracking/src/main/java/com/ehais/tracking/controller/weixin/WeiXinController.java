package com.ehais.tracking.controller.weixin;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/weixin")
public class WeiXinController extends CommonController{

	private static Logger log = LoggerFactory.getLogger(WeiXinController.class);


	Locale locale = Locale.getDefault();  
	ResourceBundle localResource = ResourceBundle.getBundle("config/config", locale);  
	String wxdev_token = localResource.getString("wxdev_token");  
	String weixin_appid = localResource.getString("weixin_appid");  
	String weixin_appsecret = localResource.getString("weixin_appsecret");  
	

	
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
					
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	
}
