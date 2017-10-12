package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.WpSurveyAnswer;
import org.ehais.weixin.service.action.SurveyAnswerService;
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
public class SurveyAnswerController extends WxCommonController {

	private static Logger log = LoggerFactory.getLogger(SurveyAnswerController.class);

	@Autowired
	private SurveyAnswerService surveyAnswerService;
	
	
	@RequestMapping("/survey_answer_list")
	public String survey_answer_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id.intValue());
			modelMap.addAttribute("action", "survey_answer_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_answer/list";
	}
	
	@ResponseBody
	@RequestMapping("/survey_answer_list_json")
	public String survey_answer_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<WpSurveyAnswer> rm = surveyAnswerService.survey_answer_list_json(user_id.intValue(), page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/survey_answer_insert")
	public String survey_answer_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<WpSurveyAnswer> rm = surveyAnswerService.survey_answer_insert(user_id.intValue());
			rm.setAction("survey_answer_insert_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("action", "survey_answer_insert_submit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_answer/info";
	}
	
	@RequestMapping(value="/survey_answer_insert_submit",method=RequestMethod.POST)
	public String survey_answer_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpSurveyAnswer survey_answer
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//			survey_answer.setStoreId(user_id);
			ReturnObject<WpSurveyAnswer> rm = surveyAnswerService.survey_answer_insert_submit(survey_answer);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_answer_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/survey_answer_update")
	public String survey_answer_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<WpSurveyAnswer> rm = surveyAnswerService.survey_answer_update(user_id.intValue(), id);
			rm.setAction("survey_answer_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_answer/info";
	}
	
	@RequestMapping(value="/survey_answer_update_submit",method=RequestMethod.POST)
	public String survey_answer_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpSurveyAnswer survey_answer
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//			survey_answer.setStoreId(user_id);
			ReturnObject<WpSurveyAnswer> rm = surveyAnswerService.survey_answer_update_submit(survey_answer);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_answer_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_answer/info";
	}
	
	
	@RequestMapping("/survey_answer_delete")
	public String survey_answer_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<WpSurveyAnswer> rm = surveyAnswerService.survey_answer_delete(user_id.intValue(), id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_answer_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/wx_survey_answer_insert_submit",method=RequestMethod.POST)
	public String wx_survey_answer_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "surveyId", required = true) Integer surveyId,
			@RequestParam(value = "openid", required = true) String openid,
			@RequestParam(value = "extend", required = true) String extend
			) {
		try{
			ReturnObject<WpSurveyAnswer> rm = surveyAnswerService.survey_answer_insert_submit(
					eWPPublicService.getWpPublic(wxid).getToken(),
					openid,
					surveyId,
					extend
					);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
