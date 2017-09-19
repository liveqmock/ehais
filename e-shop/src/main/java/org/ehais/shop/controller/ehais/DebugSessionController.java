package org.ehais.shop.controller.ehais;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ehais")
public class DebugSessionController extends CommonController {

	@ResponseBody
	@RequestMapping("/debug")
	public String debug(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {
		
		if(this.isLocalHost(request)){
			request.getSession().setAttribute(EConstants.SESSION_STORE_ID, 5);
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, 124L);
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, 5);
		request.getSession().setAttribute(EConstants.SESSION_USER_ID, 124L);
		return "我们是做互联网的微商";
	}
	
	@RequestMapping("/debuggo")
	public String debuggo(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {
		
		if(this.isLocalHost(request)){
			
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, 5);
		request.getSession().setAttribute(EConstants.SESSION_USER_ID, 124L);
		return "redirect:/vtu_sign!5ab1650-0f864c01-1aa90b02-26ccab03-3a166089fc253";
//		return "我们是做互联网的微商";
	}
	
}
