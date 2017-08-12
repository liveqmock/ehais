package org.ehais.shop.controller.wine;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiAgency;
import org.ehais.shop.service.HaiAgencyService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@EPermissionController(intro="代理等级信息功能",value="haiAgencyController")
@Controller
@RequestMapping("/wine")
public class  AgencyAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(AgencyAdminController.class);

	@Autowired
	private HaiAgencyService haiAgencyService;
	
	
	@EPermissionMethod(intro="打开代理等级信息页面",value="haiAgencyView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/haiAgencyView")
	public String haiAgencyView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiAgency> rm = haiAgencyService.agency_list(request);
			modelMap.addAttribute("rm", rm);
			return "/wine/agency/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("agency", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回代理等级信息数据",value="haiAgencyListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/haiAgencyListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAgencyListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "agencyName", required = false) String agencyName) {
		try{
			ReturnObject<HaiAgency> rm = haiAgencyService.agency_list_json(request, condition , agencyName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("agency", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增代理等级信息",value="haiAgencyAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiAgencyAddDetail",method=RequestMethod.GET)
	public String haiAgencyAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiAgency> rm = haiAgencyService.agency_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/wine/agency/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("agency", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交代理等级信息",value="haiAgencyAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiAgencyAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAgencyAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("agency") HaiAgency agency,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiAgency> rm = haiAgencyService.agency_insert_submit(request, agency);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("agency", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑代理等级信息",value="haiAgencyEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiAgencyEditDetail",method=RequestMethod.GET)
	public String haiAgencyEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "agencyId", required = true) Integer agencyId
			) {
		try{
			ReturnObject<HaiAgency> rm = haiAgencyService.agency_update(request,agencyId);
			modelMap.addAttribute("rm", rm);
			return "/wine/agency/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("agency", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交代理等级信息",value="haiAgencyEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiAgencyEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAgencyEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "agencyId", required = true) Integer agencyId,
			@Valid @ModelAttribute("agency") HaiAgency agency,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiAgencyService.agency_update_submit(request,agency));
		}catch(Exception e){
			e.printStackTrace();
			log.error("agency", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除代理等级信息",value="haiAgencyDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiAgencyDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiAgencyDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "agencyId", required = true) Integer agencyId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(haiAgencyService.agency_delete(request, agencyId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("agency", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}


