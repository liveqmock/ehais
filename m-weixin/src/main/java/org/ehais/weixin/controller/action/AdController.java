package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiAd;
import org.ehais.epublic.service.EAdService;
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

@Controller
@RequestMapping("/member")
public class  AdController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(AdController.class);

	@Autowired
	private EAdService eAdService;
	
	
	@RequestMapping("/ad_list")
	public String ad_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id.intValue());
			modelMap.addAttribute("action", "ad_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
		}
		return "/action/ad/list";
	}
	
	@ResponseBody
	@RequestMapping("/ad_list_json")
	public String ad_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
//			ReturnObject<EHaiAd> rm = eAdService.ad_list_json(request, page, len);
//			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
		}
		return null;
	}
	
	

	@RequestMapping("/ad_insert")
	public String ad_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiAd> rm = eAdService.ad_insert(request);
			rm.setAction("ad_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
		}
		return "/action/ad/info";
	}
	
	@RequestMapping(value="/ad_insert_submit",method=RequestMethod.POST)
	public String ad_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiAd ad
			) {
		try{
			ReturnObject<EHaiAd> rm = eAdService.ad_insert_submit(request,ad);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "ad_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
		}
		return "redirect:ad_insert";
	}
	
	@RequestMapping("/ad_update")
	public String ad_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "adId", required = true) Integer adId
			) {
		try{
			ReturnObject<EHaiAd> rm = eAdService.ad_update(request, adId);
			rm.setAction("ad_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
		}
		return "/action/ad/info";
	}
	
	@RequestMapping(value="/ad_update_submit",method=RequestMethod.POST)
	public String ad_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiAd ad
			) {
		try{
			ReturnObject<EHaiAd> rm = eAdService.ad_update_submit(request,ad);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "ad_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
		}
		return "/action/ad/info";
	}
	
	
	@RequestMapping("/ad_delete")
	public String ad_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "adId", required = false) Integer adId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<EHaiAd> rm = eAdService.ad_delete(request, adId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "ad_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("ad", e);
		}
		return null;
	}
	
	
}


