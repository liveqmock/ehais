package org.ehais.epublic.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.service.EHaiAdminUserService;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
//@RequestMapping("/admin")
public class  EHaiAdminUserController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(EHaiAdminUserController.class);

	@Autowired
	private EHaiAdminUserService eHaiAdminUserService;
	
	
//	@RequestMapping("/EHaiAdminUser_list")
//	public String EHaiAdminUser_list(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response ) {	
//		Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//		try{
//			modelMap.addAttribute("wxid", user_id);
//			modelMap.addAttribute("action", "EHaiAdminUser_list_json");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return "/admin/adminuser/list";
//	}
	
//	@ResponseBody
//	@RequestMapping("/EHaiAdminUser_list_json")
//	public String EHaiAdminUser_list_json(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "page", required = true) Integer page,
//			@RequestParam(value = "len", required = true) Integer len) {
//		try{
//			ReturnObject<EHaiAdminUser> rm = eHaiAdminUserService.EHaiAdminUser_list_json(request, page, len);
//			return this.writeJson(rm);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	

//	@RequestMapping("/EHaiAdminUser_insert")
//	public String EHaiAdminUser_insert(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response
//			) {
//		try{
//			ReturnObject<EHaiAdminUserWithBLOBs> rm = eHaiAdminUserService.EHaiAdminUser_insert(request);
//			rm.setAction("EHaiAdminUser_insert_submit");
//			modelMap.addAttribute("rm", rm);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return "/admin/adminuser/info";
//	}
	
//	@RequestMapping(value="/EHaiAdminUser_insert_submit",method=RequestMethod.POST)
//	public String EHaiAdminUser_insert_submit(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@ModelAttribute EHaiAdminUserWithBLOBs EHaiAdminUser
//			) {
//		try{
//			ReturnObject<EHaiAdminUserWithBLOBs> rm = eHaiAdminUserService.EHaiAdminUser_insert_submit(request,EHaiAdminUser);
//			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "EHaiAdminUser_insert");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return "redirect:EHaiAdminUser_insert";
//	}
	
//	@RequestMapping("/EHaiAdminUser_update")
//	public String EHaiAdminUser_update(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "id", required = true) Integer id
//			) {
//		try{
//			ReturnObject<EHaiAdminUserWithBLOBs> rm = eHaiAdminUserService.EHaiAdminUser_update(request, id);
//			rm.setAction("EHaiAdminUser_update_submit");
//			modelMap.addAttribute("rm", rm);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return "/admin/adminuser/info";
//	}
	
//	@RequestMapping(value="/EHaiAdminUser_update_submit",method=RequestMethod.POST)
//	public String EHaiAdminUser_update_submit(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@ModelAttribute EHaiAdminUserWithBLOBs EHaiAdminUser
//			) {
//		try{
//			ReturnObject<EHaiAdminUserWithBLOBs> rm = eHaiAdminUserService.EHaiAdminUser_update_submit(request,EHaiAdminUser);
//			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "EHaiAdminUser_list");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return "/admin/adminuser/info";
//	}
	
	
//	@RequestMapping("/EHaiAdminUser_delete")
//	public String EHaiAdminUser_delete(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "id", required = false) Integer id,
//			@RequestParam(value = "code", required = false) String code
//			) {
//		try{
//			ReturnObject<EHaiAdminUser> rm = eHaiAdminUserService.EHaiAdminUser_delete(request, id);
//			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "EHaiAdminUser_list");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	
}


