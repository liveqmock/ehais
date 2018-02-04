package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryWithBLOBs;
import org.ehais.shop.service.CategoryService;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class  CategoryController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;
	
	
	@RequestMapping("/category_list")
	public String category_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "category_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
		}
		return "/admin/category/list";
	}
	
	@ResponseBody
	@RequestMapping("/category_list_json")
	public String category_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<HaiCategory> rm = categoryService.category_list_json(request, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
		}
		return null;
	}
	
	

	@RequestMapping("/category_insert")
	public String category_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiCategoryWithBLOBs> rm = categoryService.category_insert(request);
			rm.setAction("category_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
		}
		return "/admin/category/info";
	}
	
	@RequestMapping(value="/category_insert_submit",method=RequestMethod.POST)
	public String category_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiCategoryWithBLOBs category
			) {
		try{
			ReturnObject<HaiCategoryWithBLOBs> rm = categoryService.category_insert_submit(request,category);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "category_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
		}
		return "redirect:category_insert";
	}
	
	@RequestMapping("/category_update")
	public String category_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId
			) {
		try{
			ReturnObject<HaiCategoryWithBLOBs> rm = categoryService.category_update(request, catId);
			rm.setAction("category_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
		}
		return "/admin/category/info";
	}
	
	@RequestMapping(value="/category_update_submit",method=RequestMethod.POST)
	public String category_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiCategoryWithBLOBs category
			) {
		try{
			ReturnObject<HaiCategoryWithBLOBs> rm = categoryService.category_update_submit(request,category);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "category_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
		}
		return "/admin/category/info";
	}
	
	
	@RequestMapping("/category_delete")
	public String category_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = false) Integer catId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<HaiCategory> rm = categoryService.category_delete(request, catId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "category_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
		}
		return null;
	}
	
	
}


