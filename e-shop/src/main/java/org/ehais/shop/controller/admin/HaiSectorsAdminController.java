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
import org.ehais.shop.model.HaiSectors;
import org.ehais.shop.service.HaiSectorsService;
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

@EPermissionController(name="行业信息管理",value="haiSectorsController")
@Controller
@RequestMapping("/admin")
public class  HaiSectorsAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiSectorsAdminController.class);

	@Autowired
	private HaiSectorsService haiSectorsService;
	
	
	@EPermissionMethod(name="查询",intro="打开行业信息页面",value="haiSectorsView",relation="haiSectorsListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiSectorsView")
	public String haiSectorsView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiSectors> rm = haiSectorsService.sectors_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/sectors/view";
			//return this.view(request, "/sectors/view");
			return this.view(request, "/sectors/sectorsView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("sectors", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回行业信息数据",value="haiSectorsListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiSectorsListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiSectorsListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "sectorsName", required = false) String sectorsName) {
		try{
			ReturnObject<HaiSectors> rm = haiSectorsService.sectors_list_json(request, condition,keySubId,sectorsName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("sectors", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增行业信息",value="haiSectorsAddDetail",relation="haiSectorsAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiSectorsAddDetail",method=RequestMethod.GET)
	public String haiSectorsAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiSectors> rm = haiSectorsService.sectors_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/sectors/detail";
			return this.view(request, "/sectors/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("sectors", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交行业信息",value="haiSectorsAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiSectorsAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiSectorsAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("sectors") HaiSectors sectors,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiSectors> rm = haiSectorsService.sectors_insert_submit(request, sectors);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("sectors", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑行业信息",value="haiSectorsEditDetail",relation="haiSectorsEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiSectorsEditDetail",method=RequestMethod.POST)
	public String haiSectorsEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sectorsId", required = true) Integer sectorsId
			) {
		try{
			ReturnObject<HaiSectors> rm = haiSectorsService.sectors_update(request,sectorsId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("sectors", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑行业信息",value="haiSectorsEditDetail",relation="haiSectorsEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiSectorsEditDetail",method=RequestMethod.GET)
	public String haiSectorsEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sectorsId", required = true) Integer sectorsId
			) {
		try{
			ReturnObject<HaiSectors> rm = haiSectorsService.sectors_update(request,sectorsId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/sectors/detail";
			return this.view(request, "/sectors/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("sectors", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交行业信息",value="haiSectorsEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiSectorsEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiSectorsEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("sectors") HaiSectors sectors,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiSectorsService.sectors_update_submit(request,sectors));
		}catch(Exception e){
			e.printStackTrace();
			log.error("sectors", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除行业信息",value="haiSectorsDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiSectorsDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiSectorsDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sectorsId", required = true) Integer sectorsId
			) {
		try{
			return this.writeJson(haiSectorsService.sectors_delete(request, sectorsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("sectors", e);
			return this.errorJSON(e);
		}
	}

	


}


