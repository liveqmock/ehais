package org.ehais.epublic.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class NotPermissionController {

	@RequestMapping("/notpermission")
	public String notpermission(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		
		return "/system/notpermission";
	}
	
}
