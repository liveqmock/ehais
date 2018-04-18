package com.ehais.tracking.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
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

import com.ehais.tracking.entity.QuestionGroup;
import com.ehais.tracking.entity.Questionnaire;
import com.ehais.tracking.entity.QuestionnaireGather;
import com.ehais.tracking.service.tracking.QuestionnaireService;


@Controller
@RequestMapping("/admin")
public class QuestionnaireController extends CommonController{

	private static Logger log = LoggerFactory.getLogger(QuestionnaireController.class);

	@Autowired
	private QuestionnaireService questionnaireService;
	
	
	@RequestMapping("/questionnaire_list")
	public String questionnaire_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/questionnaire/list";
	}
	
	@ResponseBody
	@RequestMapping("/questionnaire_list_json")
	public String questionnaire_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<Questionnaire> rm = questionnaireService.questionnaire_list_json(request,user_id, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/questionnaire_insert")
	public String questionnaire_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<Questionnaire> rm = questionnaireService.questionnaire_insert(null);
			rm.setAction("questionnaire_save_submit");
			modelMap.addAttribute("className","main");
			modelMap.addAttribute("rm", rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/questionnaire/info";
	}
	
	

	
	
	@RequestMapping("/questionnaire_update")
	public String questionnaire_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<Questionnaire> rm = questionnaireService.questionnaire_update(user_id, id);
			modelMap.addAttribute("className","main");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("questions_group_list", "questions_group_list();");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/questionnaire/info";
	}
	
	@RequestMapping("/questionnaire_report")
	public String questionnaire_report(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<Questionnaire> rm = questionnaireService.questionnaire_update(user_id, id);
			modelMap.addAttribute("className","mainReport");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("questions_group_list", "questions_group_list();");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/questionnaire/info";
	}
	

	@ResponseBody
	@RequestMapping(value="/questionnaire_save_submit",method=RequestMethod.POST)
	public String questionnaire_save_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Questionnaire questionnaire
			) {
		try{
//			Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
//			questionnaire.setStoreId(user_id);
			ReturnObject<Questionnaire> rm = questionnaireService.questionnaire_save_submit(questionnaire);
			return this.writeJson(rm);
			//return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "questionnaire_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/questionnaire_delete")
	public String questionnaire_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<Questionnaire> rm = questionnaireService.questionnaire_delete(user_id, id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "questionnaire_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/questions_group_list")
	public String questions_group_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<QuestionGroup> rm = questionnaireService.question_group_list(user_id, id);
			
			modelMap.addAttribute("questionsBSML", rm.getModel().getQuestionsBSML());
			modelMap.addAttribute("answerBSML", rm.getModel().getAnswerBSML());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/questionnaire/question_group_list";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/questionnaire_file.upd", method = RequestMethod.POST)
	public String questionnaire_file(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

		try {
			
			ReturnObject<Questionnaire> rm = questionnaireService.questionnaire_file(request, response);
		    return this.writeJson(rm);
		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/questions_gather_list_json")
	public String questions_gather_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "questionnaireId", required = true) Integer questionnaireId) {
		try{
			ReturnObject<QuestionnaireGather> rm = questionnaireService.questionnaire_gather_list_json(request,questionnaireId, null, null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
