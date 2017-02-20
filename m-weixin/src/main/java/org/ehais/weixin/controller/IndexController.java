package org.ehais.weixin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.cache.BaseOSCache;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.service.wx.CustomMenuService;
import org.ehais.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opensymphony.oscache.base.NeedsRefreshException;

@Controller
@RequestMapping("/")
public class IndexController {
	
	private static Logger log = LoggerFactory.getLogger(IndexController.class);

	
	/*
	@RequestMapping("/index")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
//		BaseOSCache osc = new BaseOSCache("wx",1800);
//		osc.put("ehais", "fuckyou");
		
		try {
//			System.out.println(osc.get("ehais"));
//			AccessToken token = WeiXinUtil.getAccessToken(1);
//			log.info("token.getToken():"+token.getToken());
			
//			customMenuService.CreateMenu();
	        
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/web/index";
	}
	*/

	
}
