package org.ehais.shop.controller.ehais;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@EPermissionModuleGroup(name="模组")

@EPermissionController(name="优惠券管理",value="haiCouponsController")
@Controller
@RequestMapping("/ehais")
public class  HaiCouponsAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiCouponsAdminController.class);

	@Autowired
	private HaiCouponsService haiCouponsService;
	
	
	@EPermissionMethod(name="查询",intro="打开优惠券页面",value="haiCouponsView",relation="haiCouponsListJson",type=PermissionProtocol.URL)
	@RequestMapping("/manage/haiCouponsView")
	public String haiCouponsView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiCoupons> rm = haiCouponsService.coupons_list(request);
			modelMap.addAttribute("rm", rm);
			return "/ehais/coupons/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回优惠券数据",value="haiCouponsListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/haiCouponsListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCouponsListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "couponsName", required = false) String couponsName) {
		try{
			ReturnObject<HaiCoupons> rm = haiCouponsService.coupons_list_json(request, condition,keySubId,couponsName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增优惠券",value="haiCouponsAddDetail",relation="haiCouponsAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiCouponsAddDetail",method=RequestMethod.GET)
	public String haiCouponsAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiCoupons> rm = haiCouponsService.coupons_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/ehais/coupons/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交优惠券",value="haiCouponsAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiCouponsAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
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
	

	
	@EPermissionMethod(name="编辑",intro="编辑优惠券",value="haiCouponsEditDetail",relation="haiCouponsEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiCouponsEditDetail",method=RequestMethod.GET)
	public String haiCouponsEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "couponsId", required = true) Integer couponsId
			) {
		try{
			ReturnObject<HaiCoupons> rm = haiCouponsService.coupons_update(request,couponsId);
			modelMap.addAttribute("rm", rm);
			return "/ehais/coupons/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交优惠券",value="haiCouponsEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiCouponsEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
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
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除优惠券",value="haiCouponsDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiCouponsDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCouponsDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "couponsId", required = true) Integer couponsId
			) {
		try{
			return this.writeJson(haiCouponsService.coupons_delete(request, couponsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJSON(e);
		}
	}
	
	


}


