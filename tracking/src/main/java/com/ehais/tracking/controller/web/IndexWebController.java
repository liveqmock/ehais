package com.ehais.tracking.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ehais.tracking.service.web.IndexService;


@Controller
@RequestMapping("/")
public class IndexWebController extends CommonController{

	private String themes = "default";
	
	@Autowired
	private IndexService indexService;	
	
	@RequestMapping("/index.html")
	public String questionnaire_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<Object> rm = indexService.index(request);
			
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/web/"+themes+"/index";
	}
	
	
}
