package org.ehais.epublic.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiStoreAppkeySecret;
import org.ehais.epublic.service.EStoreAppkeySecretService;
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

//@Controller
//@RequestMapping("/admin")
public class  EStoreAppkeySecretController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(EStoreAppkeySecretController.class);

	@Autowired
	private EStoreAppkeySecretService eStoreAppkeySecretService;
	
	
	@RequestMapping("/e_storeappkeysecret_list")
	public String e_storeappkeysecret_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "e_storeappkeysecret_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("storeappkeysecret", e);
		}
		return "/admin/eStoreAppkeySecret/list";
	}
	
	@ResponseBody
	@RequestMapping("/e_storeappkeysecret_list_json")
	public String e_storeappkeysecret_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<EHaiStoreAppkeySecret> rm = eStoreAppkeySecretService.storeappkeysecret_list_json(request,null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("storeappkeysecret", e);
		}
		return null;
	}
	
	

	@RequestMapping("/e_storeappkeysecret_insert")
	public String e_storeappkeysecret_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiStoreAppkeySecret> rm = eStoreAppkeySecretService.storeappkeysecret_insert(request);
			rm.setAction("e_storeappkeysecret_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("storeappkeysecret", e);
		}
		return "/admin/eStoreAppkeySecret/info";
	}
	
	@RequestMapping(value="/e_storeappkeysecret_insert_submit",method=RequestMethod.POST)
	public String e_storeappkeysecret_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiStoreAppkeySecret storeappkeysecret
			) {
		try{
			ReturnObject<EHaiStoreAppkeySecret> rm = eStoreAppkeySecretService.storeappkeysecret_insert_submit(request,storeappkeysecret);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_storeappkeysecret_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("storeappkeysecret", e);
		}
		return "redirect:e_storeappkeysecret_insert";
	}
	
	@RequestMapping("/e_storeappkeysecret_update")
	public String e_storeappkeysecret_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "appId", required = true) Integer appId
			) {
		try{
			ReturnObject<EHaiStoreAppkeySecret> rm = eStoreAppkeySecretService.storeappkeysecret_update(request, appId);
			rm.setAction("e_storeappkeysecret_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("storeappkeysecret", e);
		}
		return "/admin/eStoreAppkeySecret/info";
	}
	
	@RequestMapping(value="/e_storeappkeysecret_update_submit",method=RequestMethod.POST)
	public String e_storeappkeysecret_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiStoreAppkeySecret storeappkeysecret
			) {
		try{
			ReturnObject<EHaiStoreAppkeySecret> rm = eStoreAppkeySecretService.storeappkeysecret_update_submit(request,storeappkeysecret);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_storeappkeysecret_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("storeappkeysecret", e);
		}
		return "/admin/eStoreAppkeySecret/info";
	}
	
	
	@RequestMapping("/e_storeappkeysecret_delete")
	public String e_storeappkeysecret_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "appId", required = false) Integer appId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<EHaiStoreAppkeySecret> rm = eStoreAppkeySecretService.storeappkeysecret_delete(request, appId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "storeappkeysecret_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("storeappkeysecret", e);
		}
		return null;
	}
	
	/**
	@ResponseBody
	@RequestMapping("/wx{wxid}")
	public String wx(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "wxid") Integer wxid,
			@RequestParam(value = "signature", required = false) String signature){
		
		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
			return null;
	}
	**/
	
}


