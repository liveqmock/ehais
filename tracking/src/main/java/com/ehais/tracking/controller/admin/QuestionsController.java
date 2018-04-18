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

import com.ehais.tracking.entity.Questions;
import com.ehais.tracking.service.tracking.QuestionsService;

@Controller
@RequestMapping("/admin")
public class QuestionsController extends CommonController {

	private static Logger log = LoggerFactory
			.getLogger(QuestionsController.class);

	@Autowired
	private QuestionsService questionsService;


	@RequestMapping("/questions_insert")
	public String questions_insert(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer user_id = (Integer) request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			ReturnObject<Questions> rm = questionsService.questions_insert(user_id);
			rm.setAction("questions_insert_submit");
			modelMap.addAttribute("bootStrapList", rm.getBootStrapList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/questionnaire/question_info";
	}

	@ResponseBody
	@RequestMapping(value = "/questions_save_submit", method = RequestMethod.POST)
	public String questions_save_submit(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute Questions questions) {
		try {
			Integer user_id = (Integer) request.getSession().getAttribute(
					EConstants.SESSION_USER_ID);
//			questions.setStoreId(user_id);
			ReturnObject<Questions> rm = questionsService
					.questions_save_submit(questions);
			return this.writeJson(rm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@ResponseBody
	@RequestMapping("/questions_delete")
	public String questions_delete(ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "keyId", required = false) Integer keyId,
			@RequestParam(value = "code", required = false) String code) {
		try {
			Integer user_id = (Integer) request.getSession().getAttribute(
					EConstants.SESSION_USER_ID);
			ReturnObject<Questions> rm = questionsService.questions_delete(
					user_id, keyId);
			return this.writeJson(rm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
