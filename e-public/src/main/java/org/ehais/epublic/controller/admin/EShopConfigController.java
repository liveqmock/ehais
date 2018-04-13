package org.ehais.epublic.controller.admin;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.HaiShopConfig;
import org.ehais.epublic.service.ShopConfigService;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class  EShopConfigController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(EShopConfigController.class);

	@Autowired
	private ShopConfigService shopconfigService;
	
	
	
	@RequestMapping("/shopconfig")
	public String shopconfig(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			
			String ROLE = (String) request.getSession().getAttribute(EConstants.SESSION_ROLE_TYPE);
			
			if(ROLE!=null && !ROLE.equals("")) {
				ROLE="_"+ROLE;
			}else{
				ROLE = "";
			}
			
			//当此权限用户的菜单不存在，则用统一的菜单
			String store_config_path = this.config_file(request,"StoreConfig"+ROLE+".xml");
			File file = new File(store_config_path);
			if (!file.exists() || file.isDirectory()){
				store_config_path = this.config_file(request,"StoreConfig.xml");
				file = new File(store_config_path);
			}
			
//			String store_config_path = this.config_file(request,"StoreConfig.xml");
			ReturnObject<HaiShopConfig> rm = shopconfigService.shopconfig_find(request,store_config_path);
			rm.setAction("shopconfig_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("shopconfig", e);
		}
		return "/admin/shopconfig/info";
	}
	
	@RequestMapping(value="/shopconfig_update_submit",method=RequestMethod.POST)
	public String shopconfig_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			
			ReturnObject<HaiShopConfig> rm = shopconfigService.shopconfig_update_submit(request,true);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "setting");
		}catch(Exception e){
			e.printStackTrace();
			log.error("shopconfig", e);
		}
		return this.ReturnJump(modelMap, 0, "保存出错", "setting");
	}
	
	
	
}


