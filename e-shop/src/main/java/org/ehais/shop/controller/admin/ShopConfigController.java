package org.ehais.shop.controller.admin;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.epublic.controller.admin.EShopConfigController;
import org.ehais.epublic.model.HaiShopConfig;
import org.ehais.epublic.service.ShopConfigService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.QiniuUtil;
import org.ehais.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ShopConfigController extends EShopConfigController {
	private static Logger log = LoggerFactory.getLogger(EShopConfigController.class);

	@Autowired
	private ShopConfigService shopconfigService;
	
	private static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	private static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	private static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	private static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	@RequestMapping("/setting")
	public String shopconfig(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			ReturnObject<HaiShopConfig> rm = shopconfigService.shopconfig_info(request);
			rm.setAction("shopconfig_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("shopconfig", e);
		}
		return this.view(request, "/shopconfig/info");
	}
	
	
}
