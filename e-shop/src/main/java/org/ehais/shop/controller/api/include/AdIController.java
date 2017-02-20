package org.ehais.shop.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiAd;
import org.ehais.shop.service.AdService;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



public class AdIController extends CommonController{

	protected static Logger log = LoggerFactory.getLogger(AdIController.class); 
	
	@Autowired
	private AdService adService;
	
	
	@ResponseBody
	@RequestMapping("/ad-list-{store_id}-{position_id}-{enabled}-{is_mobile}-{page}-{len}.api")
	public String ad_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "store_id") Integer store_id,
			@PathVariable(value = "position_id") Integer position_id,
			@PathVariable(value = "enabled") Integer enabled,
			@PathVariable(value = "is_mobile") Integer is_mobile,
			@PathVariable(value = "page") Integer page,
			@PathVariable(value = "len") Integer len){
		try {
			ReturnObject<EHaiAd> rm = adService.hai_ad_list(store_id, position_id, enabled, is_mobile, page, len);
			return this.writeJson(rm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("api ad list error :", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/adlist")
	public String adlist(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "position_id", required = true) Integer position_id,
			@RequestParam(value = "enabled", required = true) Integer enabled,
			@RequestParam(value = "is_mobile", required = true) Integer is_mobile,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		try {
			ReturnObject<EHaiAd> rm = adService.hai_ad_list(store_id, position_id, enabled, is_mobile, page, len);
			return this.writeJson(rm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("api ad list error :", e);
		}
		return null;
	}
	
	
}
