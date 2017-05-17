package org.ehais.epublic.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.service.EArticleService;
import org.ehais.tools.EConditionObject;
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

//@Controller
//@RequestMapping("/admin")
public class  EArticleController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(EArticleController.class);

	@Autowired
	private EArticleService eArticleService;
	
	
	@RequestMapping("/e_article_list")
	public String article_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module ) {
		try{
			modelMap.addAttribute("module", module);
			modelMap.addAttribute("action", "e_article_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return "/admin/article/list";
	}
	
	@ResponseBody
	@RequestMapping("/e_article_list_json")
	public String e_article_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module,
			@RequestParam(value = "catId", required = false) Integer catId,
			@ModelAttribute EConditionObject condition) {
		
		
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_list_json(request,null ,catId, module,condition);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return null;
	}
	
	

	@RequestMapping("/e_article_insert")
	public String article_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_insert(request, module);
			rm.setAction("e_article_insert_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("module", module);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return "/admin/article/info";
	}
	
	@RequestMapping(value="/e_article_insert_submit",method=RequestMethod.POST)
	public String article_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiArticle article,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_insert_submit(request,article);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_article_insert?module="+module);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return "redirect:e_article_insert";
	}
	
	@RequestMapping("/e_article_update")
	public String article_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_update(request,module, articleId);
			rm.setAction("e_article_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return "/admin/article/info";
	}
	
	@RequestMapping(value="/e_article_update_submit",method=RequestMethod.POST)
	public String article_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiArticle article,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_update_submit(request,article);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_article_list?module="+module);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return "/admin/article/info";
	}
	
	
	@RequestMapping("/e_article_delete")
	public String article_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = false) Integer articleId,
			@RequestParam(value = "module", required = true) String module
			) {
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_delete(request,module, articleId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_article_list?module="+module);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return null;
	}
	
	
}


