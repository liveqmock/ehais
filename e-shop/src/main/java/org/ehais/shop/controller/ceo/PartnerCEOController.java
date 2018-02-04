package org.ehais.shop.controller.ceo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.HaiPartner;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.service.HaiPartnerService;
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

@EPermissionController(intro="基础数据",value="PartnerCEOController")
@Controller
@RequestMapping("/ceo")
public class PartnerCEOController extends CommonController{

	private static Logger log = LoggerFactory.getLogger(PartnerCEOController.class);

	@Autowired
	private HaiPartnerService haiPartnerService;
	
	
	@EPermissionMethod(name="查询",intro="打开代理管理页面",value="haiPartnerView",relation="haiPartnerListJson",type=PermissionProtocol.URL)
	@RequestMapping("/haiPartnerView")
	public String haiPartnerView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiPartner> rm = haiPartnerService.partner_list(request);
			modelMap.addAttribute("rm", rm);
			return "/ceo/partner/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回代理管理数据",value="haiPartnerListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiPartnerListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPartnerListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "partnerName", required = false) String partnerName) {
		try{
			ReturnObject<HaiPartner> rm = haiPartnerService.partner_list_json(request, condition,keySubId,partnerName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增代理管理",value="haiPartnerAddDetail",relation="haiPartnerAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiPartnerAddDetail",method=RequestMethod.GET)
	public String haiPartnerAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiPartner> rm = haiPartnerService.partner_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/partner/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交代理管理",value="haiPartnerAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiPartnerAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPartnerAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("partner") HaiPartner partner,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiPartner> rm = haiPartnerService.partner_insert_submit(request, partner);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑代理管理",value="haiPartnerEditDetail",relation="haiPartnerEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiPartnerEditDetail",method=RequestMethod.GET)
	public String haiPartnerEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "partnerId", required = true) Integer partnerId
			) {
		try{
			ReturnObject<HaiPartner> rm = haiPartnerService.partner_update(request,partnerId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/partner/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交代理管理",value="haiPartnerEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiPartnerEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPartnerEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("partner") HaiPartner partner,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiPartnerService.partner_update_submit(request,partner));
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除代理管理",value="haiPartnerDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiPartnerDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiPartnerDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "partnerId", required = true) Integer partnerId
			) {
		try{
			return this.writeJson(haiPartnerService.partner_delete(request, partnerId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}
