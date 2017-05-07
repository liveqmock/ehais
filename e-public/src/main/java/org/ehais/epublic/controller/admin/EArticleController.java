package org.ehais.epublic.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.service.EArticleService;
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
			@RequestParam(value = "isCode", required = true) Integer isCode ) {
		try{
			modelMap.addAttribute("isCode", isCode);
			modelMap.addAttribute("action", "e_article_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return "/admin/eArticle/list";
	}
	
	@ResponseBody
	@RequestMapping("/e_article_list_json")
	public String e_article_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "isCode", required = true) Integer isCode,//0：变量，1：常量
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		
		boolean is_code = false;
		if(isCode!=null && isCode == 1)is_code = true;
		
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_list_json(request,null , is_code, page, len);
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
			@RequestParam(value = "isCode", required = true) Integer isCode
			) {
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_insert(request, isCode);
			rm.setAction("e_article_insert_submit");
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("isCode", isCode);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return "/admin/eArticle/info";
	}
	
	@RequestMapping(value="/e_article_insert_submit",method=RequestMethod.POST)
	public String article_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiArticle article,
			@RequestParam(value = "isCode", required = true) Integer isCode
			) {
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_insert_submit(request,article);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_article_insert?isCode="+isCode);
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
			@RequestParam(value = "isCode", required = true) Integer isCode
			) {
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_update(request, articleId,isCode);
			rm.setAction("e_article_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return "/admin/eArticle/info";
	}
	
	@RequestMapping(value="/e_article_update_submit",method=RequestMethod.POST)
	public String article_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiArticle article,
			@RequestParam(value = "isCode", required = true) Integer isCode
			) {
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_update_submit(request,article);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_article_list?isCode="+isCode);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return "/admin/eArticle/info";
	}
	
	
	@RequestMapping("/e_article_delete")
	public String article_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = false) Integer articleId,
			@RequestParam(value = "isCode", required = true) Integer isCode
			) {
		try{
			ReturnObject<EHaiArticle> rm = eArticleService.article_delete(request, articleId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "e_article_list?isCode="+isCode);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
		}
		return null;
	}
	
	
}


