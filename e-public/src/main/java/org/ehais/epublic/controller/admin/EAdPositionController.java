package org.ehais.epublic.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiAdPosition;
import org.ehais.epublic.service.EAdPositionService;
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
public class  EAdPositionController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(EAdPositionController.class);

	@Autowired
	private EAdPositionService eAdPositionService;
	
	
	@RequestMapping("/e_adposition_list")
	public String e_adposition_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "e_adposition_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
		}
		return "/admin/eAdposition/list";
	}
	
	@ResponseBody
	@RequestMapping("/e_adposition_list_json")
	public String e_adposition_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<EHaiAdPosition> rm = eAdPositionService.adposition_list_json(request,null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
		}
		return null;
	}
	
	

	@RequestMapping("/e_adposition_insert")
	public String e_adposition_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiAdPosition> rm = eAdPositionService.adposition_insert(request);
			rm.setAction("e_adposition_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
		}
		return "/admin/eAdposition/info";
	}
	
	@RequestMapping(value="/e_adposition_insert_submit",method=RequestMethod.POST)
	public String e_adposition_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiAdPosition adposition
			) {
		try{
			ReturnObject<EHaiAdPosition> rm = eAdPositionService.adposition_insert_submit(request,adposition);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_adposition_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
		}
		return "redirect:e_adposition_insert";
	}
	
	@RequestMapping("/e_adposition_update")
	public String e_adposition_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "positionId", required = true) Integer positionId
			) {
		try{
			ReturnObject<EHaiAdPosition> rm = eAdPositionService.adposition_update(request, positionId);
			rm.setAction("e_adposition_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
		}
		return "/admin/eAdposition/info";
	}
	
	@RequestMapping(value="/e_adposition_update_submit",method=RequestMethod.POST)
	public String e_adposition_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiAdPosition adposition
			) {
		try{
			ReturnObject<EHaiAdPosition> rm = eAdPositionService.adposition_update_submit(request,adposition);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_adposition_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
		}
		return "/admin/eAdposition/info";
	}
	
	
	@RequestMapping("/e_adposition_delete")
	public String e_adposition_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "positionId", required = false) Integer positionId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<EHaiAdPosition> rm = eAdPositionService.adposition_delete(request, positionId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_adposition_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("adposition", e);
		}
		return null;
	}
	
	
}


