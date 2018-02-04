package org.ehais.shop.controller.ceo;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.ehais.annotation.EPermissionController;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.epublic.model.WpPublic;
import org.ehais.util.DateUtil;
import org.ehais.util.SignUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@EPermissionController(intro="基础数据",value="MainCEOController")
@Controller
@RequestMapping("/ceo")
public class MainCEOController extends CommonController{

	
	@RequestMapping("/main")
	public String main(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/"+this.getAdminProjectFolder(request)+"/main";
	}
	
	
}
