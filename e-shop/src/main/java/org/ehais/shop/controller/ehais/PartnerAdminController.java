package org.ehais.shop.controller.ehais;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ehais")
public class PartnerAdminController extends EhaisCommonController {
	private static Logger log = LoggerFactory.getLogger(PartnerAdminController.class);
	public static String weixin_token = ResourceUtil.getProValue("weixin_token");
	
	
	@RequestMapping("/manage/partner_dining")
	public String partner_dining(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
			String qrcode = request.getScheme()+"://"+request.getServerName()+"/diningUnion!"+SignUtil.setPartnerId(partner_id, 0L, 0L, weixin_token);
			modelMap.addAttribute("qrcode", qrcode);
			return "/"+this.getStoreTheme(request)+"/qrcode/dining";
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner_dining", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	

}
