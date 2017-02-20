package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.HaiCategory;
import org.ehais.weixin.model.HaiCategoryWithBLOBs;
import org.ehais.weixin.service.action.HaiCategoryService;
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
@RequestMapping("/haicategory")
public class  HaiCategoryController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiCategoryController.class);

	@Autowired
	private HaiCategoryService haicategoryService;
	
	
	@RequestMapping("/haicategory_list")
	public String haicategory_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		try{
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "haicategory_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/haicategory/list";
	}
	
	@ResponseBody
	@RequestMapping("/haicategory_list_json")
	public String haicategory_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			ReturnObject<HaiCategory> rm = haicategoryService.haicategory_list_json(request,cat_code,wx_id, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/haicategory_insert")
	public String haicategory_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "cat_code", required = true) String cat_code
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<HaiCategoryWithBLOBs> rm = haicategoryService.haicategory_insert(request,cat_code,user_id.intValue());
			rm.setAction("haicategory_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/haicategory/info";
	}
	
	@RequestMapping(value="/haicategory_insert_submit",method=RequestMethod.POST)
	public String haicategory_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@ModelAttribute HaiCategoryWithBLOBs haicategory
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			haicategory.setStoreId(user_id.intValue());
			ReturnObject<HaiCategoryWithBLOBs> rm = haicategoryService.haicategory_insert_submit(request,cat_code,haicategory);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "haicategory_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:haicategory_insert";
	}
	
	@RequestMapping("/haicategory_update")
	public String haicategory_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@RequestParam(value = "keyId", required = true) Integer keyId
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<HaiCategoryWithBLOBs> rm = haicategoryService.haicategory_update(request,cat_code,user_id.intValue(), keyId);
			rm.setAction("haicategory_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/haicategory/info";
	}
	
	@RequestMapping(value="/haicategory_update_submit",method=RequestMethod.POST)
	public String haicategory_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@ModelAttribute HaiCategoryWithBLOBs haicategory
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			haicategory.setStoreId(user_id.intValue());
			ReturnObject<HaiCategoryWithBLOBs> rm = haicategoryService.haicategory_update_submit(request,cat_code,haicategory);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "haicategory_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/haicategory/info";
	}
	
	
	@RequestMapping("/haicategory_delete")
	public String haicategory_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = false) Integer keyId,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<HaiCategory> rm = haicategoryService.haicategory_delete(request,cat_code,user_id.intValue(), keyId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "haicategory_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}


