package org.ehais.project.controller.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.project.model.ApiModel;
import org.ehais.project.service.HaiUserService;
import org.ehais.project.util.Environments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class WSController extends CommonController{

	@Autowired
	private HaiUserService haiUserService;
	
	private ApiModel api;
	
	@Autowired
	private Environments evn;
	
	
	@ResponseBody
	@RequestMapping(value="/login")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		System.out.println("jdbcUrl:"+evn.getJdbcUrl());
		return "fuck:"+evn.getJdbcUrl();
	}


	public ApiModel getApi() {
		return api;
	}

	public void setApi(ApiModel api) {
		this.api = api;
	}


	
	
}
