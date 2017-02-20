package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.PublicAddonConfig;
import org.ehais.weixin.model.Wecome;
import org.ehais.weixin.model.WpPublic;
import org.ehais.weixin.model.WpPublicWithBLOBs;
import org.ehais.weixin.service.wx.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


@Controller
@RequestMapping("/public")
public class WpPublicController extends WxCommonController{

	@Autowired
	private PublicService publicService;
	
	@RequestMapping("/wecome")
	public String wecome(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response){
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			WpPublicWithBLOBs wpPublic = weiXinService.getWpPublic(wx_id);
			String addonConfig = wpPublic.getAddonConfig();
			System.out.println(addonConfig);
			
			if(addonConfig!=null && !addonConfig.equals("")){
				Gson gson = new Gson();
				PublicAddonConfig pac = gson.fromJson(addonConfig, PublicAddonConfig.class);
				String description = pac.getWecome().getDescription();
				modelMap.addAttribute("description", description);				
			}else{
				modelMap.addAttribute("description", "");
			}
			
			modelMap.addAttribute("action", "wecome_submit");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "/action/public/wecome";
	}
	
	@ResponseBody
	@RequestMapping("/wecome_submit")
	public String wecome_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wecome", required = true) String wecome
			){
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			WpPublicWithBLOBs wpPublic = weiXinService.getWpPublic(wx_id);
			String addonConfig = wpPublic.getAddonConfig();
			System.out.println(addonConfig);
			Gson gson = new Gson();
			PublicAddonConfig pac = null;
			if(addonConfig!=null && !addonConfig.equals("")){
				
				pac = gson.fromJson(addonConfig, PublicAddonConfig.class);
				pac.getWecome().setDescription(wecome);
			}else{
				pac = new PublicAddonConfig();
				pac.setWecome(new Wecome(1,wecome,0));
			}
			addonConfig = gson.toJson(pac);
			System.out.println(addonConfig);
			wpPublic.setAddonConfig(addonConfig);
			ReturnObject<WpPublicWithBLOBs> rm = publicService.public_update_submit(wpPublic);
			weiXinService.setWpPublic(wx_id, wpPublic);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	

	@RequestMapping("/public_update")
	public String public_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			Integer wx_id = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			ReturnObject<WpPublicWithBLOBs> rm = publicService.public_update(null, wx_id);
			rm.setAction("public_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/public/info";
	}
	
	@RequestMapping(value="/public_update_submit",method=RequestMethod.POST)
	public String public_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpPublicWithBLOBs wpPublic
			) {
		try{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			wpPublic.setUid(user_id);
			Integer wx_id = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
			wpPublic.setId(wx_id);
			ReturnObject<WpPublicWithBLOBs> rm = publicService.public_update_submit(wpPublic);
			weiXinService.setWpPublic(wx_id, wpPublic);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "public_update");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/public/info";
	}
	
	
}
