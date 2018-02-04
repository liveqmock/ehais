package org.ehais.shop.controller.ehais;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class StoreManagerController extends EhaisCommonController{

	private static Logger log = LoggerFactory.getLogger(StoreManagerController.class);

	//http://8937e8ed.ngrok.io/wx_store_manager
	@RequestMapping("/wx_store_manager")
	public String wx_store_manager(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = false) String code ) {
		
		try{
//			EHaiStore store = eStoreService.getEStore(default_store_id);
			
			
			
			if(this.isWeiXin(request)){//微信端登录
				WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(default_store_id);
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/wx_store_manager");
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, default_store_id);
					String cid = SignUtil.setCid(default_store_id, 0, user.getUserId(), user.getUserId(), wp.getToken());
					modelMap.addAttribute("cid", cid);
//					验证用户是否注册的商家，以前商家类型
					if(user.getUserType() == null || user.getUserType() == 0 || user.getStoreId() == null || user.getStoreId() == 0){
						//非商家用户，则跳转到商家注册去
						String link = request.getScheme() + "://" + request.getServerName() + "/ehaisUnion!"+cid;
						return "redirect:"+link;
					}
					
					return "/ehais/store_manager";
				}
			}else{
				return "redirect:"+website;
//				String cid = SignUtil.setCid(default_store_id, 0, 0L, 0L, wp.getToken());
//				modelMap.addAttribute("cid", cid);
//				return "/ehais/store_manager";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:"+website;
	}
}
