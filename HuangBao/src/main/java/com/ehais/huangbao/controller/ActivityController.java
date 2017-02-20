package com.ehais.huangbao.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;

import com.ehais.huangbao.model.HaiActivity;
import com.ehais.huangbao.model.HaiActivityApply;
import com.ehais.huangbao.model.HaiActivityWithBLOBs;
import com.ehais.huangbao.service.ActivityApplyService;
import com.ehais.huangbao.service.ActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixin/activity")
public class ActivityController extends WxCommonController{

	@Autowired
	private ActivityService activityService;
	@Autowired
	private ActivityApplyService activityApplyService;
	
	@RequestMapping("/wx_list")
	public String wx_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "act_type_id" , required = false) Integer act_type_id
			) {
		try{
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("act_type_id", act_type_id);
			modelMap.addAttribute("action", "wx_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/activity/wx_list";
	}
	
	
	@ResponseBody
	@RequestMapping("/wx_list_json")
	public String wx_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "act_type_id" , required = false) Integer act_type_id,
			@RequestParam(value = "page" , required = true) Integer page,
			@RequestParam(value = "len" , required = true) Integer len
			) {
		try{
			ReturnObject<HaiActivity> rm = activityService.activity_list_json(wxid, page, len);
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
			@RequestParam(value = "extend" , required = true) Integer extend,
			@RequestParam(value = "openid" , required = true) String openid
			) {
		try{
			
			ReturnObject<HaiActivityWithBLOBs> rm = activityService.activity_info(wxid, extend);
			modelMap.addAttribute("model", rm.getModel());
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "wx_apply");
			modelMap.addAttribute("openid", openid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/activity/wx_info";
	}
	
	@ResponseBody
	@RequestMapping("/wx_apply")
	public String wx_apply(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "act_id" , required = true) Integer act_id,
			@RequestParam(value = "realname" , required = true) String realname,
			@RequestParam(value = "mobile" , required = true) String mobile,
			@RequestParam(value = "openid" , required = true) String openid,
			@RequestParam(value = "company" , required = true) String company,
			@RequestParam(value = "remark" , required = true) String remark
			) {
		try{
			return this.writeJson(activityService.apply(wxid, act_id, realname, mobile, openid,company,remark));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	//////////////////////////////////////////////
	
	

	@RequestMapping("/activity_list")
	public String activity_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "activity_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/activity/list";
	}
	
	@ResponseBody
	@RequestMapping("/activity_list_json")
	public String activity_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
		    
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<HaiActivity> rm = activityService.activity_list_json(user_id.intValue(), page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	


	@RequestMapping("/activity_insert")
	public String activity_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "activity_id", required = false) Integer activity_id
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<HaiActivityWithBLOBs> rm = activityService.activity_insert(user_id.intValue());
			rm.setAction("activity_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/activity/info";
	}
	
	@RequestMapping(value="/activity_insert_submit",method=RequestMethod.POST)
	public String activity_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiActivityWithBLOBs activity
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			activity.setWxid(user_id.intValue());
			ReturnObject<HaiActivityWithBLOBs> rm = activityService.activity_insert_submit(activity);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "activity_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/activity_update")
	public String activity_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "actId", required = true) Integer actId
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<HaiActivityWithBLOBs> rm = activityService.activity_update(user_id.intValue(), actId);
			rm.setAction("activity_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/activity/info";
	}
	
	@RequestMapping(value="/activity_update_submit",method=RequestMethod.POST)
	public String activity_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiActivityWithBLOBs activity
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			activity.setWxid(user_id.intValue());
			ReturnObject<HaiActivityWithBLOBs> rm = activityService.activity_update_submit(activity);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "activity_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/activity/info";
	}
	
	
	@RequestMapping("/activity_delete")
	public String activity_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "actId", required = false) Integer actId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<HaiActivity> rm = activityService.activity_delete(user_id.intValue(), actId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "activity_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	////////////////////////////////////////////////////////
	

	@RequestMapping("/activity_apply_list")
	public String activity_apply_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "actId", required = true) Integer actId ) {	
		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id.intValue());
			modelMap.addAttribute("actId", actId);
			modelMap.addAttribute("action", "activity_apply_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/activity/list_apply";
	}
	
	@ResponseBody
	@RequestMapping("/activity_apply_list_json")
	public String activity_apply_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "actId", required = true) Integer actId,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<HaiActivityApply> rm = activityApplyService.activityApply_list_json(user_id.intValue() , actId , page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
