package org.ehais.shop.controller.dining;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.tp.TpDiningTable;
import org.ehais.shop.service.TpDiningTableService;
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



@EPermissionController(intro="通用资讯框架功能",value="tpDiningTableController")
@Controller
@RequestMapping("/dining")
public class  TpDiningTableController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(TpDiningTableController.class);

	@Autowired
	private TpDiningTableService tpDiningTableService;
	
	
	@EPermissionMethod(intro="打开通用资讯框架页面",value="tpDiningTableView",type=PermissionProtocol.URL)
	@RequestMapping("/tpDiningTableView")
	public String tpDiningTableView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<TpDiningTable> rm = tpDiningTableService.diningtable_list(request);
			modelMap.addAttribute("rm", rm);
			return "/dining/diningtable/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningtable", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回通用资讯框架数据",value="tpDiningTableListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/tpDiningTableListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningTableListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "tablename", required = false) String tablename) {
		try{
			ReturnObject<TpDiningTable> rm = tpDiningTableService.diningtable_list_json(request, condition,tablename);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningtable", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增通用资讯框架",value="tpDiningTableAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/tpDiningTableAddDetail",method=RequestMethod.GET)
	public String tpDiningTableAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<TpDiningTable> rm = tpDiningTableService.diningtable_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/dining/diningtable/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningtable", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交通用资讯框架",value="tpDiningTableAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/tpDiningTableAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningTableAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("diningtable") TpDiningTable diningtable,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<TpDiningTable> rm = tpDiningTableService.diningtable_insert_submit(request, diningtable);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningtable", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑通用资讯框架",value="tpDiningTableEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/tpDiningTableEditDetail",method=RequestMethod.GET)
	public String tpDiningTableEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "dtId", required = true) Long dtId
			) {
		try{
			ReturnObject<TpDiningTable> rm = tpDiningTableService.diningtable_update(request,dtId);
			modelMap.addAttribute("rm", rm);
			return "/dining/diningtable/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningtable", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交通用资讯框架",value="tpDiningTableEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/tpDiningTableEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningTableEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("diningtable") TpDiningTable diningtable,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(tpDiningTableService.diningtable_update_submit(request,diningtable));
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningtable", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除通用资讯框架",value="tpDiningTableDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/tpDiningTableDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningTableDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "dtId", required = true) Long dtId
			) {
		try{
			return this.writeJson(tpDiningTableService.diningtable_delete(request, dtId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningtable", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="打开通用资讯框架页面",value="tpDiningTableView",type=PermissionProtocol.URL)
	@RequestMapping("/tpDiningTableQRCodeExport")
	public void tpDiningTableQRCodeExport(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "dtId", required = true) Long dtId,
			@RequestParam(value = "download", required = false) Integer download,			
			@RequestParam(value = "preview", required = false) Integer preview) {	
		try{
			tpDiningTableService.diningtable_export(request,response,dtId,download,preview);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningtable", e);
		}
		
	}

	
}


