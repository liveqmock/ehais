package org.ehais.epublic.controller.pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.epublic.model.HaiOrderPayRecordAndUsers;
import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixin/pay")
public class WeiXinPayController {

	
	@ResponseBody
	@RequestMapping("/notify_url")
	public String notify_url(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
}
