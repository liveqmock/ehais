package org.ehais.shop.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.shop.service.ArticleCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class ArticleCatIController extends CommonController{

	@Autowired
	private ArticleCatService articleCatService;
	
	@ResponseBody
	@RequestMapping("/article_cat_list")
	public String article_cat_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "parent_id", required = true) Integer parent_id){
		
		try {
			return this.writeJson(articleCatService.article_cat_parent_list(EArticleModuleEnum.ARTICLE,store_id, parent_id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
			return null;
	}
	
	
}
