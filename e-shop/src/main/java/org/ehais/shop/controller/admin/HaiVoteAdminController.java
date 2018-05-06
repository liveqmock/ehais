package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiVote;
import org.ehais.shop.service.HaiVoteService;
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

@EPermissionModuleGroup(name="模组")

@EPermissionController(name="投票管理",value="haiVoteController")
@Controller
@RequestMapping("/admin")
public class  HaiVoteAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiVoteAdminController.class);

	@Autowired
	private HaiVoteService haiVoteService;
	
	
	@EPermissionMethod(name="查询",intro="打开投票页面",value="haiVoteView",relation="haiVoteListJson",type=PermissionProtocol.URL)
	@RequestMapping("/haiVoteView")
	public String haiVoteView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiVote> rm = haiVoteService.vote_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/vote/view";
			return this.view(request, "/vote/view");
		}catch(Exception e){
			e.printStackTrace();
			log.error("vote", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回投票数据",value="haiVoteListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiVoteListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiVoteListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "vodeName", required = false) String vodeName) {
		try{
			ReturnObject<HaiVote> rm = haiVoteService.vote_list_json(request, condition,keySubId,vodeName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("vote", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增投票",value="haiVoteAddDetail",relation="haiVoteAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiVoteAddDetail",method=RequestMethod.GET)
	public String haiVoteAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiVote> rm = haiVoteService.vote_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/vote/detail";
			return this.view(request, "/vote/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("vote", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交投票",value="haiVoteAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiVoteAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiVoteAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("vote") HaiVote vote,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiVote> rm = haiVoteService.vote_insert_submit(request, vote);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("vote", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑投票",value="haiVoteEditDetail",relation="haiVoteEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiVoteEditDetail",method=RequestMethod.GET)
	public String haiVoteEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "vodeId", required = true) Integer vodeId
			) {
		try{
			ReturnObject<HaiVote> rm = haiVoteService.vote_update(request,vodeId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/vote/detail";
			return this.view(request, "/vote/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("vote", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交投票",value="haiVoteEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiVoteEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiVoteEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("vote") HaiVote vote,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiVoteService.vote_update_submit(request,vote));
		}catch(Exception e){
			e.printStackTrace();
			log.error("vote", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除投票",value="haiVoteDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/haiVoteDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiVoteDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "vodeId", required = true) Integer vodeId
			) {
		try{
			return this.writeJson(haiVoteService.vote_delete(request, vodeId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("vote", e);
			return this.errorJSON(e);
		}
	}
	
	


}


