package org.ehais.shop.controller.admin;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.mapper.HaiBusinessLinkmanMapper;
import org.ehais.shop.model.HaiBusiness;
import org.ehais.shop.model.HaiBusinessLinkman;
import org.ehais.shop.model.HaiBusinessLinkmanExample;
import org.ehais.shop.model.HaiBusinessWithBLOBs;
import org.ehais.shop.service.HaiBusinessService;
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

//@EPermissionController(name="往来单位信息管理",value="haiBusinessController")
@Controller
@RequestMapping("/admin")
public class  HaiBusinessAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiBusinessAdminController.class);

	@Autowired
	private HaiBusinessService haiBusinessService;
	@Autowired
	private HaiBusinessLinkmanMapper haiBusinessLinkmanMapper;
	
	
	@ResponseBody
//	@EPermissionMethod(name="查询",intro="打开往来单位信息页面",value="haiBusinessLinkManView",relation="haiBusinessListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping(value="/haiBusinessLinkManJson",method=RequestMethod.POST)
	public String haiBusinessLinkManJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "action", required = true) String action,
			@RequestParam(value = "businessId", required = true) Integer businessId) {	
		ReturnObject<HaiBusinessLinkman> rm = new ReturnObject<HaiBusinessLinkman>();
		List<HaiBusinessLinkman> list = null;
		try {
			if(action.equals("add")) {
				list = new ArrayList<HaiBusinessLinkman>();
				for(int i = 0 ; i < 5 ; i++) {
					HaiBusinessLinkman b = new HaiBusinessLinkman();
					list.add(b);
				}
				
			}else if(action.equals("edit")) {
				HaiBusinessLinkmanExample exp = new HaiBusinessLinkmanExample();
				exp.createCriteria().andBusinessIdEqualTo(businessId);
				list = haiBusinessLinkmanMapper.selectByExample(exp);
			}
			rm.setRows(list);
			rm.setCode(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	
	@EPermissionMethod(name="查询",intro="打开往来单位信息页面",value="haiBusinessView",relation="haiBusinessListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiBusinessView")
	public String haiBusinessView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiBusiness> rm = haiBusinessService.business_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/business/view";
			//return this.view(request, "/business/view");
			return this.view(request, "/business/businessView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回往来单位信息数据",value="haiBusinessListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiBusinessListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiBusinessListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "businessName", required = false) String businessName) {
		try{
			ReturnObject<HaiBusiness> rm = haiBusinessService.business_list_json(request, condition,keySubId,businessName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增往来单位信息",value="haiBusinessAddDetail",relation="haiBusinessAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiBusinessAddDetail",method=RequestMethod.GET)
	public String haiBusinessAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiBusinessWithBLOBs> rm = haiBusinessService.business_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/business/detail";
			return this.view(request, "/business/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交往来单位信息",value="haiBusinessAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiBusinessAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiBusinessAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("business") HaiBusinessWithBLOBs business,
			BindingResult result,
			@RequestParam(value = "linkManJson", required = true) String linkManJson
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiBusinessWithBLOBs> rm = haiBusinessService.business_insert_submit(request, business,"",linkManJson);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑往来单位信息",value="haiBusinessEditDetail",relation="haiBusinessEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiBusinessEditDetail",method=RequestMethod.POST)
	public String haiBusinessEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "businessId", required = true) Integer businessId
			) {
		try{
			ReturnObject<HaiBusinessWithBLOBs> rm = haiBusinessService.business_update(request,businessId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑往来单位信息",value="haiBusinessEditDetail",relation="haiBusinessEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiBusinessEditDetail",method=RequestMethod.GET)
	public String haiBusinessEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "businessId", required = true) Integer businessId
			) {
		try{
			ReturnObject<HaiBusinessWithBLOBs> rm = haiBusinessService.business_update(request,businessId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/business/detail";
			return this.view(request, "/business/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交往来单位信息",value="haiBusinessEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiBusinessEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiBusinessEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("business") HaiBusinessWithBLOBs business,
			BindingResult result,
			@RequestParam(value = "linkManJson", required = true) String linkManJson
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiBusinessService.business_update_submit(request,business,"",linkManJson));
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除往来单位信息",value="haiBusinessDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiBusinessDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiBusinessDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "businessId", required = true) Integer businessId
			) {
		try{
			return this.writeJson(haiBusinessService.business_delete(request, businessId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("business", e);
			return this.errorJSON(e);
		}
	}

	


}


