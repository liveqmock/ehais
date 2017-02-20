package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.HaiArticle;
import org.ehais.weixin.service.action.ExtendsFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/extendsFields")
public class ExtendsFieldsController extends CommonController{

	@Autowired
	private ExtendsFieldsService extendsFieldsService;
	
	@RequestMapping("/extends_fields_info")
	public String extends_fields_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "table", required = true) String table,
			@RequestParam(value = "table_id", required = false) Integer table_id
			) {
		try{
			ReturnObject<Object> rm = extendsFieldsService.ExtendsFieldsInfo(request, code, table, table_id);
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/extend/info";
	}
	
	
}
