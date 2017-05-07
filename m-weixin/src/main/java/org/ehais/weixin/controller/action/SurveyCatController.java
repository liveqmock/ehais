package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.WpSurveyCat;
import org.ehais.weixin.service.action.SurveyCatService;
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
@RequestMapping("/survey_cat")
public class SurveyCatController extends WxCommonController {

	private static Logger log = LoggerFactory.getLogger(SurveyCatController.class);

	@Autowired
	private SurveyCatService surveyCatService;
	
	
	@RequestMapping("/survey_cat_list")
	public String survey_cat_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id.intValue());
			modelMap.addAttribute("action", "survey_cat_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_cat/list";
	}
	
	@ResponseBody
	@RequestMapping("/survey_cat_list_json")
	public String survey_cat_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<WpSurveyCat> rm = surveyCatService.survey_cat_list_json(user_id.intValue(), page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/survey_cat_insert")
	public String survey_cat_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<WpSurveyCat> rm = surveyCatService.survey_cat_insert(user_id.intValue());
			rm.setAction("survey_cat_insert_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("action", "survey_cat_insert_submit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_cat/info";
	}
	
	@RequestMapping(value="/survey_cat_insert_submit",method=RequestMethod.POST)
	public String survey_cat_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpSurveyCat survey_cat
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//			survey_cat.setStoreId(user_id);
			ReturnObject<WpSurveyCat> rm = surveyCatService.survey_cat_insert_submit(survey_cat);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_cat_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/survey_cat_update")
	public String survey_cat_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = true) Integer keyId
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<WpSurveyCat> rm = surveyCatService.survey_cat_update(user_id.intValue(), keyId);
			rm.setAction("survey_cat_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_cat/info";
	}
	
	@RequestMapping(value="/survey_cat_update_submit",method=RequestMethod.POST)
	public String survey_cat_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpSurveyCat survey_cat
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//			survey_cat.setStoreId(user_id);
			ReturnObject<WpSurveyCat> rm = surveyCatService.survey_cat_update_submit(survey_cat);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_cat_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_cat/info";
	}
	
	
	@RequestMapping("/survey_cat_delete")
	public String survey_cat_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = false) Integer keyId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<WpSurveyCat> rm = surveyCatService.survey_cat_delete(user_id.intValue(), keyId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_cat_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
