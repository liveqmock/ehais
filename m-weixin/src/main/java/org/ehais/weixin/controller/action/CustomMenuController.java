package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.epublic.model.WpPublic;
import org.ehais.model.TreeModel;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.WpCustomMenu;
import org.ehais.weixin.service.wx.CustomMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 项目名称：m-weixin
 * 类描述：CustomMenuController
 * 摘要：微信菜单管理
 * @author lgj628
 */

@Controller
@RequestMapping("/custom_menu")
public class CustomMenuController extends WxCommonController{

	@Autowired
	private CustomMenuService customMenuService;
	
	
	@RequestMapping("/create_menu")
	public String CreateMenu(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response){
		
		try {
			WpPublic p = weiXinService.getWpPublic((Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID));
			customMenuService.CreateMenu(p);
			return this.ReturnJump(modelMap, 1, "同步成功", "custom_menu_list");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "";
	}
	
	
	

	@RequestMapping("/custom_menu_list")
	public String custom_menu_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		try{
			modelMap.addAttribute("wxid", wx_id);
			modelMap.addAttribute("action", "custom_menu_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/custom_menu/list";
	}
	
	@ResponseBody
	@RequestMapping("/custom_menu_list_json")
	public String custom_menu_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<TreeModel> rm = customMenuService.custom_menu_list_json(weiXinService.getWpPublic(wx_id).getToken(), page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/custom_menu_insert")
	public String custom_menu_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<WpCustomMenu> rm = customMenuService.custom_menu_insert(weiXinService.getWpPublic(wx_id).getToken());
			rm.setAction("custom_menu_insert_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("action", "custom_menu_insert_submit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/custom_menu/info";
	}
	
	@RequestMapping(value="/custom_menu_insert_submit",method=RequestMethod.POST)
	public String custom_menu_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpCustomMenu custom_menu
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			custom_menu.setToken(weiXinService.getWpPublic(wx_id).getToken());
			ReturnObject<WpCustomMenu> rm = customMenuService.custom_menu_insert_submit(custom_menu);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "custom_menu_insert");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/custom_menu_update")
	public String custom_menu_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<WpCustomMenu> rm = customMenuService.custom_menu_update(weiXinService.getWpPublic(wx_id).getToken(), id);
			rm.setAction("custom_menu_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/custom_menu/info";
	}
	
	@RequestMapping(value="/custom_menu_update_submit",method=RequestMethod.POST)
	public String custom_menu_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpCustomMenu custom_menu
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			custom_menu.setToken(weiXinService.getWpPublic(wx_id).getToken());
			ReturnObject<WpCustomMenu> rm = customMenuService.custom_menu_update_submit(custom_menu);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "custom_menu_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/custom_menu/info";
	}
	
	
	@RequestMapping("/custom_menu_delete")
	public String custom_menu_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<WpCustomMenu> rm = customMenuService.custom_menu_delete(weiXinService.getWpPublic(wx_id).getToken(), id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "custom_menu_list");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
