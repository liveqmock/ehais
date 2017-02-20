package org.ehais.shop.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestWebController extends CommonController{

	@RequestMapping("/validate")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws Exception {
		
		return "/web/test/validate";
	}
}
