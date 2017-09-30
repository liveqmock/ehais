package org.ehais.shop.controller.ehais;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.model.HaiShippingWithBLOBs;
import org.ehais.shop.service.HaiShippingService;
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



@EPermissionController(intro="支付管理功能",value="haiShippingController")
@Controller
@RequestMapping("/ehais")
public class  HaiShippingAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiShippingAdminController.class);

	@Autowired
	private HaiShippingService haiShippingService;
	
	
	@EPermissionMethod(intro="打开支付管理页面",value="haiShippingView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/haiShippingView")
	public String haiShippingView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiShipping> rm = haiShippingService.shipping_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/shipping/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回支付管理数据",value="haiShippingListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/haiShippingListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiShippingListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "shippingName", required = false) String shippingName) {
		try{
			ReturnObject<HaiShipping> rm = haiShippingService.shipping_list_json(request, condition,keySubId,shippingName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增支付管理",value="haiShippingAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiShippingAddDetail",method=RequestMethod.GET)
	public String haiShippingAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiShippingWithBLOBs> rm = haiShippingService.shipping_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/shipping/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交支付管理",value="haiShippingAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiShippingAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiShippingAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("shipping") HaiShippingWithBLOBs shipping,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiShippingWithBLOBs> rm = haiShippingService.shipping_insert_submit(request, shipping);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑支付管理",value="haiShippingEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiShippingEditDetail",method=RequestMethod.GET)
	public String haiShippingEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "shippingId", required = true) Integer shippingId
			) {
		try{
			ReturnObject<HaiShippingWithBLOBs> rm = haiShippingService.shipping_update(request,shippingId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/shipping/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交支付管理",value="haiShippingEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiShippingEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiShippingEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("shipping") HaiShippingWithBLOBs shipping,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiShippingService.shipping_update_submit(request,shipping));
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除支付管理",value="haiShippingDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiShippingDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiShippingDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "shippingId", required = true) Integer shippingId
			) {
		try{
			return this.writeJson(haiShippingService.shipping_delete(request, shippingId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("shipping", e);
			return this.errorJSON(e);
		}
	}
	
	


}


