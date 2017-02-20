package com.ehais.huangbao.controller;

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

import com.ehais.huangbao.model.HaiContribution;
import com.ehais.huangbao.service.HaiContributionService;


@Controller
@RequestMapping("/member")
public class  HaiContributionController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiContributionController.class);

	@Autowired
	private HaiContributionService haicontributionService;
	
	
	@RequestMapping("/haicontribution_list")
	public String haicontribution_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			modelMap.addAttribute("action", "haicontribution_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/haicontribution/list";
	}
	
	@ResponseBody
	@RequestMapping("/haicontribution_list_json")
	public String haicontribution_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			ReturnObject<HaiContribution> rm = haicontributionService.haicontribution_list_json(request, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/haicontribution_insert")
	public String haicontribution_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			ReturnObject<HaiContribution> rm = haicontributionService.haicontribution_insert(request);
			rm.setAction("haicontribution_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/haicontribution/info";
	}
	
	@RequestMapping(value="/haicontribution_insert_submit",method=RequestMethod.POST)
	public String haicontribution_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiContribution haicontribution
			) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			haicontribution.setStoreId(wxid);
			ReturnObject<HaiContribution> rm = haicontributionService.haicontribution_insert_submit(request,haicontribution);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "haicontribution_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:haicontribution_insert";
	}
	
	@RequestMapping("/haicontribution_update")
	public String haicontribution_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = true) Integer keyId
			) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			ReturnObject<HaiContribution> rm = haicontributionService.haicontribution_update(request, keyId);
			rm.setAction("haicontribution_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/haicontribution/info";
	}
	
	@RequestMapping(value="/haicontribution_update_submit",method=RequestMethod.POST)
	public String haicontribution_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiContribution haicontribution
			) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			haicontribution.setStoreId(wxid);
			ReturnObject<HaiContribution> rm = haicontributionService.haicontribution_update_submit(request,haicontribution);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "haicontribution_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/haicontribution/info";
	}
	
	
	@RequestMapping("/haicontribution_delete")
	public String haicontribution_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "keyId", required = false) Integer keyId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			ReturnObject<HaiContribution> rm = haicontributionService.haicontribution_delete(request, keyId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "haicontribution_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}


