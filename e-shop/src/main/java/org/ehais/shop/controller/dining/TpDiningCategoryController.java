package org.ehais.shop.controller.dining;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.tp.TpDiningCategory;
import org.ehais.shop.service.TpDiningCategoryService;
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


@EPermissionController(intro="菜品功能",value="tpDiningCategoryController")
@Controller
@RequestMapping("/dining")
public class  TpDiningCategoryController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(TpDiningCategoryController.class);

	@Autowired
	private TpDiningCategoryService tpDiningCategoryService;
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交菜品",value="tpDiningCategoryAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/tpDiningCategoryAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningCategoryAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("diningcategory") TpDiningCategory diningcategory,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<TpDiningCategory> rm = tpDiningCategoryService.diningcategory_insert_submit(request, diningcategory);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningcategory", e);
			return this.errorJSON(e);
		}
	}
	

	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交菜品",value="tpDiningCategoryEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/tpDiningCategoryEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningCategoryEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id,
			@Valid @ModelAttribute("diningcategory") TpDiningCategory diningcategory,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(tpDiningCategoryService.diningcategory_update_submit(request,diningcategory));
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningcategory", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除菜品",value="tpDiningCategoryDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/tpDiningCategoryDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String tpDiningCategoryDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(tpDiningCategoryService.diningcategory_delete(request, id));
		}catch(Exception e){
			e.printStackTrace();
			log.error("diningcategory", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}


