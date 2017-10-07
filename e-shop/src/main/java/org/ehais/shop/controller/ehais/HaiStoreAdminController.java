package org.ehais.shop.controller.ehais;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.service.HaiStoreService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.QiniuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@EPermissionController(intro="商户管理功能",value="HaiStoreAdminController")
@Controller
@RequestMapping("/ehais")
public class  HaiStoreAdminController extends EhaisCommonController {

	private static Logger log = LoggerFactory.getLogger(HaiStoreAdminController.class);
	@Autowired
	private HaiStoreService haiStoreService;
	
	/////个体商户编辑

	@EPermissionMethod(name="编辑",intro="编辑商户管理",value="haiStoreEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiStoreDetail",method=RequestMethod.GET)
	public String haiStoreDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		Integer storeId = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try{
			ReturnObject<EHaiStore> rm = haiStoreService.store_update(request,storeId);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/ehais/store/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交商户管理",value="haiStoreSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiStoreSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiStoreSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("store") EHaiStore store,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
			Integer storeId = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			store.setStoreId(storeId);
		try{
			return this.writeJson(haiStoreService.store_update_submit(request,store));
		}catch(Exception e){
			e.printStackTrace();
			log.error("store", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}


