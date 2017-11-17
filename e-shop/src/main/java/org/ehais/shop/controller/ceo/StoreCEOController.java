package org.ehais.shop.controller.ceo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.HaiPartnerMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.HaiPartner;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.controller.partner.HaiPartnerStoreAdminController;
import org.ehais.shop.service.HaiStoreService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@EPermissionController(intro="基础数据",value="StoreCEOController")
@Controller
@RequestMapping("/ceo")
public class StoreCEOController extends CommonController{

	private static Logger log = LoggerFactory.getLogger(HaiPartnerStoreAdminController.class);

	@Autowired
	private HaiStoreService haiStoreService;
	@Autowired
	private HaiPartnerMapper haiPartnerMapper;

	@EPermissionMethod(intro="打开商户管理页面",value="haiStoreView",type=PermissionProtocol.URL)
	@RequestMapping("/haiStoreView")
	public String haiStoreView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<EHaiStore> rm = haiStoreService.store_list(request);
			modelMap.addAttribute("rm", rm);
			
			List<HaiPartner> partnerList = 	haiPartnerMapper.selectByExample(null);
			modelMap.addAttribute("partnerList", partnerList);
			
			return "/"+this.getAdminProjectFolder(request)+"/store/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回商户管理数据",value="haiStoreListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiStoreListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "partnerId", required = true) Integer partnerId,
			@RequestParam(value = "storeName", required = false) String storeName) {
		try{
			ReturnObject<EHaiStore> rm = haiStoreService.store_list_json(request, condition,partnerId,storeName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="更新商户管理缓存",value="haiStoreCache",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiStoreCache",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreCache(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "storeId", required = true) Integer storeId) {
		try{
			ReturnObject<EHaiStore> rm = haiStoreService.store_cache(request, storeId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}
