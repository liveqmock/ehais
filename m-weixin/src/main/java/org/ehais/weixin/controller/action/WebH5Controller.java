package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.weixin.controller.WxCommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class WebH5Controller extends WxCommonController{

	@RequestMapping("/h5")
	public String h5(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		return "/web/h5";
	}
}
