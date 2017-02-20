package com.ehais.tracking.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ehais.tracking.entity.Leader;
import com.ehais.tracking.service.tracking.LeaderService;



@Controller
@RequestMapping("/admin")
public class  LeaderController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(LeaderController.class);

	@Autowired
	private LeaderService leaderService;
	
	
	@RequestMapping("/leader_list")
	public String leader_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			modelMap.addAttribute("action", "leader_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/leader/list";
	}
	
	@ResponseBody
	@RequestMapping("/leader_list_json")
	public String leader_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<Leader> rm = leaderService.leader_list_json(request, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/leader_insert")
	public String leader_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<Leader> rm = leaderService.leader_insert(request);
			rm.setAction("leader_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/leader/info";
	}
	
	@RequestMapping(value="/leader_insert_submit",method=RequestMethod.POST)
	public String leader_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Leader leader
			) {
		try{
			ReturnObject<Leader> rm = leaderService.leader_insert_submit(request,leader);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "leader_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:leader_insert";
	}
	
	@RequestMapping("/leader_update")
	public String leader_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = true) Integer keyId
			) {
		try{
			ReturnObject<Leader> rm = leaderService.leader_update(request, keyId);
			rm.setAction("leader_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/leader/info";
	}
	
	@RequestMapping(value="/leader_update_submit",method=RequestMethod.POST)
	public String leader_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute Leader leader
			) {
		try{
			ReturnObject<Leader> rm = leaderService.leader_update_submit(request,leader);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "leader_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/leader/info";
	}
	
	
	@RequestMapping("/leader_delete")
	public String leader_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = false) Integer keyId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<Leader> rm = leaderService.leader_delete( request,keyId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "leader_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
