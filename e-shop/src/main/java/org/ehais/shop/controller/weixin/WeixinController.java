package org.ehais.shop.controller.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.weixin.model.WeiXinNotityXml;
import org.ehais.weixin.utils.WeiXinUtil;
import org.ehais.weixin.utils.aes.WXBizMsgCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class WeixinController extends CommonController{

	@Autowired
	protected EWPPublicService eWPPublicService;
	
	
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
	
}
