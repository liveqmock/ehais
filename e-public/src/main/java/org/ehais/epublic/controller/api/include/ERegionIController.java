package org.ehais.epublic.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.epublic.service.ERegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class ERegionIController extends CommonController{

	@Autowired
	private ERegionService eRegionService;
	
	
	@ResponseBody
	@RequestMapping("/region_parent_list")
	public String region_parent_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "parent_id", required = true) Integer parent_id){
		
		try {
			return this.writeJson(eRegionService.region_parent_list(parent_id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/region_id_list")
	public String region_id_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "region_ids", required = true) String region_ids){
		
		try {
			return this.writeJson(eRegionService.region_id_list(region_ids));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/region_parentid_list")
	public String region_parentid_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "region_ids", required = true) String region_ids){
		
		try {
			return this.writeJson(eRegionService.region_parentid_list(region_ids));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	
	
}
