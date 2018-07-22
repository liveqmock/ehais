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
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryWithBLOBs;
import org.ehais.shop.service.HaiCategoryService;
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

@EPermissionController(name="商品分类管理",value="haiCategoryController")
@Controller
@RequestMapping("/admin")
public class  HaiCategoryAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiCategoryAdminController.class);

	@Autowired
	private HaiCategoryService haiCategoryService;
	
	
	@EPermissionMethod(name="查询",intro="打开商品分类页面",value="haiCategoryView",relation="haiCategoryListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiCategoryView")
	public String haiCategoryView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiCategory> rm = haiCategoryService.category_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/category/view";
			return this.view(request, "/category/view");
//			return this.view(request, "/category/categoryView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回商品分类数据",value="haiCategoryListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiCategoryListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCategoryListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "catName", required = false) String catName) {
		try{
			ReturnObject<HaiCategory> rm = haiCategoryService.category_list_json(request, condition,keySubId,catName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增商品分类",value="haiCategoryAddDetail",relation="haiCategoryAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiCategoryAddDetail",method=RequestMethod.GET)
	public String haiCategoryAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiCategoryWithBLOBs> rm = haiCategoryService.category_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/category/detail";
			return this.view(request, "/category/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交商品分类",value="haiCategoryAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiCategoryAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCategoryAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("category") HaiCategoryWithBLOBs category,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiCategoryWithBLOBs> rm = haiCategoryService.category_insert_submit(request, category);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑商品分类",value="haiCategoryEditDetail",relation="haiCategoryEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiCategoryEditDetail",method=RequestMethod.POST)
	public String haiCategoryEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId
			) {
		try{
			ReturnObject<HaiCategoryWithBLOBs> rm = haiCategoryService.category_update(request,catId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	
	@EPermissionMethod(name="编辑",intro="编辑商品分类",value="haiCategoryEditDetail",relation="haiCategoryEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/getHaiCategoryEditDetail",method=RequestMethod.GET)
	public String getHaiCategoryEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId
			) {
		try{
			ReturnObject<HaiCategoryWithBLOBs> rm = haiCategoryService.category_update(request,catId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/category/detail";
			return this.view(request, "/category/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交商品分类",value="haiCategoryEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiCategoryEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCategoryEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("category") HaiCategoryWithBLOBs category,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiCategoryService.category_update_submit(request,category));
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除商品分类",value="haiCategoryDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiCategoryDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiCategoryDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId
			) {
		try{
			return this.writeJson(haiCategoryService.category_delete(request, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}

	


}


