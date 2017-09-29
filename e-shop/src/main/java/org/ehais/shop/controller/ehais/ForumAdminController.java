package org.ehais.shop.controller.ehais;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiForum;
import org.ehais.shop.service.HaiForumService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@EPermissionController(intro="评论管理功能",value="haiForumController")
@Controller
@RequestMapping("/ehais")
public class  ForumAdminController extends CommonController {

	private  Logger log = LoggerFactory.getLogger(ForumAdminController.class);

	@Autowired
	private HaiForumService haiForumService;
	
	
	@EPermissionMethod(intro="打开评论管理页面",value="haiForumView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/haiForumView")
	public String haiForumView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiForum> rm = haiForumService.forum_list(request);
			modelMap.addAttribute("rm", rm);
			log.info("打开评论管理页面");
			return "/"+this.getStoreTheme(request)+"/forum/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("forum", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回评论管理数据",value="haiForumListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/haiForumListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiForumListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "tablename", required = true) String tablename) {
		try{
			if(log.isInfoEnabled()){
				System.out.println("s返回评论管理数据");
				log.info("i返回评论管理数据");
			}
			ReturnObject<HaiForum> rm = haiForumService.forum_list_json(request, condition,tablename);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("forum", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增评论管理",value="haiForumAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiForumAddDetail",method=RequestMethod.GET)
	public String haiForumAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiForum> rm = haiForumService.forum_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/forum/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("forum", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交评论管理",value="haiForumAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiForumAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiForumAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("forum") HaiForum forum,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiForum> rm = haiForumService.forum_insert_submit(request, forum);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("forum", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑评论管理",value="haiForumEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiForumEditDetail",method=RequestMethod.GET)
	public String haiForumEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "forumId", required = true) Long forumId
			) {
		try{
			ReturnObject<HaiForum> rm = haiForumService.forum_update(request,forumId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/forum/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("forum", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交评论管理",value="haiForumEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/haiForumEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiForumEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("forum") HaiForum forum,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiForumService.forum_update_submit(request,forum));
		}catch(Exception e){
			e.printStackTrace();
			log.error("forum", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除评论管理",value="haiForumDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/haiForumDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiForumDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "forumId", required = true) Long forumId
			) {
		try{
			return this.writeJson(haiForumService.forum_delete(request, forumId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("forum", e);
			return this.errorJSON(e);
		}
	}
	
	
}


