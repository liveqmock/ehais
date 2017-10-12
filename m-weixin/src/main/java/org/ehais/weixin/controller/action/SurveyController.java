package org.ehais.weixin.controller.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.WpSurvey;
import org.ehais.weixin.model.WpSurveyWithBLOBs;
import org.ehais.weixin.service.action.SurveyService;
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
@RequestMapping("/weixin/survey")
public class SurveyController extends WxCommonController {

	private static Logger log = LoggerFactory.getLogger(SurveyController.class);

	@Autowired
	private SurveyService surveyService;
	
	
	@RequestMapping("/survey_list")
	public String survey_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id.intValue());
			modelMap.addAttribute("action", "survey_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey/list";
	}
	
	@ResponseBody
	@RequestMapping("/survey_list_json")
	public String survey_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			
			ReturnObject<WpSurvey> rm = surveyService.survey_list_json(eWPPublicService.getWpPublic(wx_id).getToken(), page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/survey_insert")
	public String survey_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<WpSurveyWithBLOBs> rm = surveyService.survey_insert(eWPPublicService.getWpPublic(wx_id).getToken());
			rm.setAction("survey_insert_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("action", "survey_insert_submit");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey/info";
	}
	
	@RequestMapping(value="/survey_insert_submit",method=RequestMethod.POST)
	public String survey_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpSurveyWithBLOBs survey
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			survey.setToken(eWPPublicService.getWpPublic(wx_id).getToken());
			ReturnObject<WpSurveyWithBLOBs> rm = surveyService.survey_insert_submit(survey);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/survey_update")
	public String survey_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			
			ReturnObject<WpSurveyWithBLOBs> rm = surveyService.survey_update(eWPPublicService.getWpPublic(wx_id).getToken(), id);
			rm.setAction("survey_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey/info";
	}
	
	@RequestMapping(value="/survey_update_submit",method=RequestMethod.POST)
	public String survey_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpSurveyWithBLOBs survey
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			survey.setToken(eWPPublicService.getWpPublic(wx_id).getToken());
			ReturnObject<WpSurveyWithBLOBs> rm = surveyService.survey_update_submit(survey);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey/info";
	}
	
	
	@RequestMapping("/survey_delete")
	public String survey_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<WpSurvey> rm = surveyService.survey_delete(eWPPublicService.getWpPublic(wx_id).getToken(), id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/wx_list")
	public String wx_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid) {	
		
		try{
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "survey_wx_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey/wx_list";
	}
	
	@ResponseBody
	@RequestMapping("/survey_wx_list_json")
	public String survey_wx_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			
			ReturnObject<WpSurvey> rm = surveyService.survey_list_json(eWPPublicService.getWpPublic(wxid).getToken(), page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/wx_info")
	public String wx_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			
			ReturnObject<WpSurveyWithBLOBs> rm = surveyService.survey_update(eWPPublicService.getWpPublic(wxid).getToken(), id);
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey/wx_info";
	}
	
	
}
