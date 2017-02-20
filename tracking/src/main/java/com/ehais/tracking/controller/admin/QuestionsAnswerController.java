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

import com.ehais.tracking.entity.QuestionsAnswer;
import com.ehais.tracking.service.tracking.QuestionsAnswerService;



@Controller
@RequestMapping("/admin")
public class  QuestionsAnswerController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(QuestionsAnswerController.class);

	@Autowired
	private QuestionsAnswerService questionsAnswerService;
	
	
	

	@RequestMapping("/questions_answer_insert")
	public String questions_answer_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<QuestionsAnswer> rm = questionsAnswerService.questions_answer_insert(user_id);
			modelMap.addAttribute("bootStrapList", rm.getBootStrapList());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/questionnaire/answer_info";
	}
	
	@ResponseBody
	@RequestMapping(value="/questions_answer_save_submit",method=RequestMethod.POST)
	public String questions_answer_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "extend", required = true) String extend
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
//			questions_answer.setStoreId(user_id);
			ReturnObject<QuestionsAnswer> rm = questionsAnswerService.questions_answer_save_submit(user_id,extend);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/questions_answer_delete")
	public String questions_answer_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = false) Integer keyId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Integer user_id = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<QuestionsAnswer> rm = questionsAnswerService.questions_answer_delete(user_id, keyId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "questions_answer_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}


