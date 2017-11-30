package org.ehais.shop.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiCoupons;
import org.ehais.shop.service.HaiCouponsService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class CouponsIController extends CommonController{

	private static Logger log = LoggerFactory.getLogger(CouponsIController.class);
	
	@Autowired
	private HaiCouponsService haiCouponsService;
	

	@ResponseBody
	@RequestMapping(value="/haiCouponsListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCouponsListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition) {
		try{
			ReturnObject<HaiCoupons> rm = haiCouponsService.coupons_list_json(request, condition,null,null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/haiCouponsAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCouponsAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("coupons") HaiCoupons coupons,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiCoupons> rm = haiCouponsService.coupons_insert_submit(request, coupons);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/haiCouponsEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCouponsEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("coupons") HaiCoupons coupons,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiCouponsService.coupons_update_submit(request,coupons));
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJSON(e);
		}
	}
	


}
