package com.ehais.tracking.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
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

import com.ehais.tracking.entity.Message;
import com.ehais.tracking.service.tracking.MessageService;


@Controller
@RequestMapping("/admin")
public class  MessageController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageService messageService;
	
	
	@RequestMapping("/message_list")
	public String message_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "message_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/message/list";
	}
	
	@ResponseBody
	@RequestMapping("/message_list_json")
	public String message_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
			ReturnObject<Message> rm = messageService.message_list_json(request,user_id, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/message_insert")
	public String message_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
			ReturnObject<Message> rm = messageService.message_insert(request,user_id);
			rm.setAction("message_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/message/info";
	}
	
	@RequestMapping(value="/message_insert_submit",method=RequestMethod.POST)
	public String message_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Message message
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
			message.setSchoolId(user_id);
			ReturnObject<Message> rm = messageService.message_insert_submit(request,message);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "message_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:message_insert";
	}
	
	@RequestMapping("/message_update")
	public String message_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = true) Integer keyId
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
			ReturnObject<Message> rm = messageService.message_update(request,user_id, keyId);
			rm.setAction("message_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/message/info";
	}
	
	@RequestMapping(value="/message_update_submit",method=RequestMethod.POST)
	public String message_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Message message
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
			message.setSchoolId(user_id);
			ReturnObject<Message> rm = messageService.message_update_submit(request,message);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "message_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/message/info";
	}
	
	
	@RequestMapping("/message_delete")
	public String message_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = false) Integer keyId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_SCHOOL_ID);
			ReturnObject<Message> rm = messageService.message_delete(request,user_id, keyId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "message_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}


