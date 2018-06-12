package org.ehais.shop.controller.admin;



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
import org.ehais.shop.model.HaiUnit;
import org.ehais.shop.service.HaiUnitService;
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

@EPermissionController(name="计量单位信息管理",value="haiUnitController")
@Controller
@RequestMapping("/admin")
public class  HaiUnitAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiUnitAdminController.class);

	@Autowired
	private HaiUnitService haiUnitService;
	
	
	@EPermissionMethod(name="查询",intro="打开计量单位信息页面",value="haiUnitView",relation="haiUnitListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiUnitView")
	public String haiUnitView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiUnit> rm = haiUnitService.unit_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/unit/view";
			//return this.view(request, "/unit/view");
			return this.view(request, "/unit/unitView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("unit", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回计量单位信息数据",value="haiUnitListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiUnitListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiUnitListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "unitName", required = false) String unitName) {
		try{
			ReturnObject<HaiUnit> rm = haiUnitService.unit_list_json(request, condition,keySubId,unitName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("unit", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增计量单位信息",value="haiUnitAddDetail",relation="haiUnitAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiUnitAddDetail",method=RequestMethod.GET)
	public String haiUnitAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiUnit> rm = haiUnitService.unit_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/unit/detail";
			return this.view(request, "/unit/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("unit", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交计量单位信息",value="haiUnitAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiUnitAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiUnitAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("unit") HaiUnit unit,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiUnit> rm = haiUnitService.unit_insert_submit(request, unit);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("unit", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑计量单位信息",value="haiUnitEditDetail",relation="haiUnitEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiUnitEditDetail",method=RequestMethod.POST)
	public String haiUnitEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "unitId", required = true) Integer unitId
			) {
		try{
			ReturnObject<HaiUnit> rm = haiUnitService.unit_update(request,unitId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("unit", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑计量单位信息",value="haiUnitEditDetail",relation="haiUnitEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiUnitEditDetail",method=RequestMethod.GET)
	public String haiUnitEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "unitId", required = true) Integer unitId
			) {
		try{
			ReturnObject<HaiUnit> rm = haiUnitService.unit_update(request,unitId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/unit/detail";
			return this.view(request, "/unit/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("unit", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交计量单位信息",value="haiUnitEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiUnitEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiUnitEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("unit") HaiUnit unit,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiUnitService.unit_update_submit(request,unit));
		}catch(Exception e){
			e.printStackTrace();
			log.error("unit", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除计量单位信息",value="haiUnitDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiUnitDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiUnitDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "unitId", required = true) Integer unitId
			) {
		try{
			return this.writeJson(haiUnitService.unit_delete(request, unitId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("unit", e);
			return this.errorJSON(e);
		}
	}

	


}


