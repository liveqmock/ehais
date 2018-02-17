package org.ehais.shop.controller.ceo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.HaiPartner;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@EPermissionModuleGroup(name="模组")

@EPermissionController(intro="对帐功能",value="PartnerCEOController")
@Controller
@RequestMapping("/ceo")
public class BillCEOController extends CommonController{
	private static Logger log = LoggerFactory.getLogger(BillCEOController.class);

	
	
	@EPermissionMethod(name="查询",intro="打开对帐页面",value="haiBillView",relation="haiBillListJson",type=PermissionProtocol.URL)
	@RequestMapping("/haiBillView")
	public String haiPartnerView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			
			return "/ceo/bill/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	
}
