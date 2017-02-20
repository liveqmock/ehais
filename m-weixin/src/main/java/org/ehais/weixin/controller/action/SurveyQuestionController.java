package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.WpSurveyQuestion;
import org.ehais.weixin.model.WpSurveyQuestionWithBLOBs;
import org.ehais.weixin.service.action.SurveyQuestionService;
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
public class SurveyQuestionController extends WxCommonController {

	private static Logger log = LoggerFactory.getLogger(SurveyQuestionController.class);

	@Autowired
	private SurveyQuestionService surveyQuestionService;
	
	
	@RequestMapping("/survey_question_list")
	public String survey_question_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "survey_id", required = true) Integer survey_id) {	
		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id.intValue());
			modelMap.addAttribute("survey_id", survey_id);
			modelMap.addAttribute("action", "survey_question_list_json");
			modelMap.addAttribute("action_add", "survey_question_insert_submit");
			modelMap.addAttribute("action_edit", "survey_question_update_submit");
			modelMap.addAttribute("action_find", "survey_question_find");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_question/list";
	}
	
	@ResponseBody
	@RequestMapping("/survey_question_list_json")
	public String survey_question_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "survey_id", required = true) Integer survey_id,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			ReturnObject<WpSurveyQuestion> rm = surveyQuestionService.survey_question_list_json(weiXinService.getWpPublic(wx_id).getToken(),survey_id, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/survey_question_insert")
	public String survey_question_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<WpSurveyQuestionWithBLOBs> rm = surveyQuestionService.survey_question_insert(user_id.intValue());
			rm.setAction("survey_question_insert_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("action", "survey_question_insert_submit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_question/info";
	}
	
	@ResponseBody
	@RequestMapping(value="/survey_question_insert_submit",method=RequestMethod.POST)
	public String survey_question_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpSurveyQuestionWithBLOBs survey_question
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			survey_question.setToken(weiXinService.getWpPublic(wx_id).getToken());
			ReturnObject<WpSurveyQuestionWithBLOBs> rm = surveyQuestionService.survey_question_insert_submit(survey_question);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/survey_question_update")
	public String survey_question_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<WpSurveyQuestionWithBLOBs> rm = surveyQuestionService.survey_question_update(user_id.intValue(), id);
			rm.setAction("survey_question_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_question/info";
	}
	
	@ResponseBody
	@RequestMapping(value="/survey_question_update_submit",method=RequestMethod.POST)
	public String survey_question_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpSurveyQuestionWithBLOBs survey_question
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			survey_question.setToken(weiXinService.getWpPublic(wx_id).getToken());
//			survey_question.setStoreId(user_id);
			ReturnObject<WpSurveyQuestionWithBLOBs> rm = surveyQuestionService.survey_question_update_submit(survey_question);
			return this.writeJson(rm);
//			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_question_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/survey_question_delete")
	public String survey_question_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<WpSurveyQuestion> rm = surveyQuestionService.survey_question_delete(user_id.intValue(), id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "survey_question_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/survey_question_find")
	public String survey_question_find(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<WpSurveyQuestionWithBLOBs> rm = surveyQuestionService.survey_question_find(user_id.intValue(), id);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping("/wx_survey")
	public String wx_survey(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "extend", required = true) Integer extend,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "openid", required = true) String openid
			) {
		try{
			modelMap.addAttribute("action", "wx_survey_json");
			modelMap.addAttribute("survey_id", extend);
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("openid", openid);
			modelMap.addAttribute("wx_survey_answer_action","wx_survey_answer_insert_submit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/survey_question/wx_survey";
	}
	
	
	@ResponseBody
	@RequestMapping("/wx_survey_json")
	public String wx_survey_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "surveyId", required = true) Integer surveyId,
			@RequestParam(value = "wxid", required = true) Integer wxid
			) {
		try{
			ReturnObject<WpSurveyQuestionWithBLOBs> rm = surveyQuestionService.survey_question_blob_list_json(weiXinService.getWpPublic(wxid).getToken(),surveyId, null, null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}

