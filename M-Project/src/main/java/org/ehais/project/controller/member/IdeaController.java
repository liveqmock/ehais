package org.ehais.project.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.project.model.ProIdea;
import org.ehais.project.model.ProIdeaWithBLOBs;
import org.ehais.project.service.IdeaService;
import org.ehais.tools.ReturnObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/member")
public class IdeaController  extends CommonController{


	@Autowired
	private IdeaService ideaService;
	
	private Integer proId ;
	
	
	@RequestMapping(value="/ideaList",method = RequestMethod.GET)
	public String ideaList(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log list", e);
		}
		
		return "/admin/idea/list";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/ideaListJson",method = RequestMethod.POST , produces = "text/html;charset=UTF-8")
	public String ideaListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows
			) {
		
		try{
			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<ProIdea> ro = ideaService.ListIdea(userId ,  page, rows);
			JSONObject json = new JSONObject(ro);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log list json", e);
		}
		
		return null;
	}
	
	
	
	
	@RequestMapping(value="/ideaInsert",method = RequestMethod.GET)
	public String ideaInsert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		ProIdea idea = new ProIdea();
		
		modelMap.addAttribute("model", idea);
		modelMap.addAttribute("action", "ideaInsertSubmit");
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log insert", e);
		}
		
		return "/admin/idea/info";
	}
	
	@RequestMapping(value="/ideaInsertSubmit",method = RequestMethod.POST)
	public String ideaInsertSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProIdeaWithBLOBs idea
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			idea.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			ReturnObject<ProIdea> ro = ideaService.InsertIdea(idea);
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "ideaInsert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log insert submit", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	
	@RequestMapping(value="/ideaUpdate",method = RequestMethod.GET)
	public String ideaUpdate(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProIdea idea
			) {

		try{
			idea.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			
			ReturnObject<ProIdea> ro = ideaService.FindIdea(idea.getUserId() ,idea.getIdeaId());
			modelMap.addAttribute("model", ro.getModel());
			modelMap.addAttribute("action", "ideaUpdateSubmit");
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log update", e);
		}
		
		return "/admin/idea/info";
	}
	
	@RequestMapping(value="/ideaUpdateSubmit",method = RequestMethod.POST)
	public String ideaUpdateSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProIdeaWithBLOBs idea
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			idea.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));
			
			ReturnObject<ProIdea> ro = ideaService.UpdateIdea(idea);
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "ideaList");
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log update submit", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	
	@RequestMapping(value="/ideaRecover",method = RequestMethod.GET)
	public String ideaRecover(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ProIdea idea
			) {

		proId = (Integer)request.getSession().getAttribute(Constants.SESSION_PROJECT_ID);
		
		try{
			idea.setUserId((Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID));

			ReturnObject<ProIdea> ro = ideaService.RecoverIdea(idea);
			
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "ideaList");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("work log recover", e);
		}
		
		return this.errorJump(modelMap);
	}
	
	
	
	
}
