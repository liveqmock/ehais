package org.ehais.shop.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.shop.service.ArticleService;
import org.ehais.tools.EConditionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class ArticleIController extends CommonController{

	@Autowired
	private ArticleService articleService;
	
	
	@ResponseBody
	@RequestMapping("/article_list_cid")
	public String article_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = false) Integer store_id,
			@RequestParam(value = "cat_id", required = true) Integer cat_id,
			@ModelAttribute EConditionObject condition ){
		
		try {
			return this.writeJson(articleService.article_list_cid(request,EArticleModuleEnum.ARTICLE,store_id, cat_id, condition));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/article_list_code")
	public String article_list_code(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		
		try {
			return this.writeJson(articleService.article_list_code(EArticleModuleEnum.ARTICLE,store_id, cat_code, page, len));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/article_info")
	public String article_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "article_id", required = true) Integer article_id){
		
		try {
			return this.writeJson(articleService.article_info(EArticleModuleEnum.ARTICLE,store_id, article_id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/article_extends_list_json")
	public String article_extends_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sid", required = true) String sid
			){
		
		try {
			return this.writeJson(articleService.article_extends_list_json(request, sid));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	
}
