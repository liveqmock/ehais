package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WpKeyword;
import org.ehais.weixin.service.action.KeywordService;
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
public class  KeywordController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(KeywordController.class);

	@Autowired
	private KeywordService keywordService;
	
	
	@RequestMapping("/keyword_list")
	public String keyword_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id.intValue());
			modelMap.addAttribute("action", "keyword_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("keyword", e);
		}
		return "/action/keyword/list";
	}
	
	@ResponseBody
	@RequestMapping("/keyword_list_json")
	public String keyword_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<WpKeyword> rm = keywordService.keyword_list_json(request,null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("keyword", e);
		}
		return null;
	}
	
	

	@RequestMapping("/keyword_insert")
	public String keyword_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<WpKeyword> rm = keywordService.keyword_insert(request);
			rm.setAction("keyword_insert_submit");
			modelMap.addAttribute("action", "/article/article_list_json_v2");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("bootstrap", rm.getBootStrapList().get(1));
		}catch(Exception e){
			e.printStackTrace();
			log.error("keyword", e);
		}
		return "/action/keyword/info";
	}
	
	@RequestMapping(value="/keyword_insert_submit",method=RequestMethod.POST)
	public String keyword_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpKeyword keyword
			) {
		try{
			ReturnObject<WpKeyword> rm = keywordService.keyword_insert_submit(request,keyword);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "keyword_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("keyword", e);
		}
		return "redirect:keyword_insert";
	}
	
	@RequestMapping("/keyword_update")
	public String keyword_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id
			) {
		try{
			ReturnObject<WpKeyword> rm = keywordService.keyword_update(request, id);
			rm.setAction("keyword_update_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("action", "/article/article_list_json_v2");
			modelMap.addAttribute("bootstrap", rm.getBootStrapList().get(1));
		}catch(Exception e){
			e.printStackTrace();
			log.error("keyword", e);
		}
		return "/action/keyword/info";
	}
	
	@RequestMapping(value="/keyword_update_submit",method=RequestMethod.POST)
	public String keyword_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute WpKeyword keyword
			) {
		try{
			ReturnObject<WpKeyword> rm = keywordService.keyword_update_submit(request,keyword);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "keyword_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("keyword", e);
		}
		return "/action/keyword/info";
	}
	
	
	@RequestMapping("/keyword_delete")
	public String keyword_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<WpKeyword> rm = keywordService.keyword_delete(request, id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "keyword_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("keyword", e);
		}
		return null;
	}
	
	
}


