package org.ehais.weixin.controller.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.service.action.HaiUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/member")
public class  HaiUsersController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiUsersController.class);

	@Autowired
	private HaiUsersService HaiUsersService;
	
	
	

	@RequestMapping("/users/modifyPassword")
	public String HaiUsers_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<EHaiUsers> rm = HaiUsersService.modifyPassword(request,user_id.intValue());
			rm.setAction("modifyPasswordSubmit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/haiusers/modifyPassword";
	}
	
	
	@RequestMapping(value="/users/modifyPasswordSubmit",method=RequestMethod.POST)
	public String modifyPasswordSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "old_password", required = true) String old_password,
			@RequestParam(value = "new_password", required = true) String new_password,
			@RequestParam(value = "confirm_password", required = true) String confirm_password
			) {
		try{
			
			ReturnObject<EHaiUsers> rm = HaiUsersService.modifyPasswordSubmit(request,old_password,new_password,confirm_password);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "modifyPassword");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:modifyPassword";
	}
	
	
	
}


