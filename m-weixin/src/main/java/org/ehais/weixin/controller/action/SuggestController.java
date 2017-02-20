package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.HaiSuggest;
import org.ehais.weixin.service.action.SuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixin/suggest")
public class SuggestController extends WxCommonController {

	@Autowired
	private SuggestService suggestService;
	
	@RequestMapping("/form")
	public String form(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "openid", required = true) String openid) {
		try{
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("openid", this.getOpenId(wxid,code,openid));
			modelMap.addAttribute("action", "submit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/suggest/form";
	}
	
	@ResponseBody
	@RequestMapping("/submit")
	public String submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiSuggest suggest) {
		try{
			ReturnObject<HaiSuggest> ro = suggestService.suggest_insert(suggest);
			return this.writeJson(ro);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/suggest_list")
	public String suggest_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id.intValue());
			modelMap.addAttribute("action", "suggest_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/suggest/list";
	}
	
	@ResponseBody
	@RequestMapping("/suggest_list_json")
	public String suggest_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<HaiSuggest> rm = suggestService.suggest_list_json(user_id.intValue(), page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping("/suggest_find")
	public String suggest_find(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sugId", required = true) Integer sugId
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			ReturnObject<HaiSuggest> rm = suggestService.suggest_find(user_id.intValue(), sugId);
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/suggest/info";
	}
	
	
	
}
