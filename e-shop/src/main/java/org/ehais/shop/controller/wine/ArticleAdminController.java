package org.ehais.shop.controller.wine;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.service.ArticleCatService;
import org.ehais.shop.service.ArticleService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@EPermissionController(intro="红酒软文功能",value="wineArticleController")
@Controller
@RequestMapping("/wine")
public class  ArticleAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(ArticleAdminController.class);

	@Autowired
	private ArticleService wineArticleService;
	@Autowired
	private ArticleCatService wineArticleCatService;
	
	
	@EPermissionMethod(intro="打开红酒软文页面",value="wineArticleView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/wineArticleView")
	public String wineArticleView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<EHaiArticle> rm = wineArticleService.article_list(request);
			modelMap.addAttribute("rm", rm);
			return "/wine/article/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回红酒软文数据",value="wineArticleListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/wineArticleListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineArticleListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "title", required = false) String title) {
		try{
			ReturnObject<EHaiArticle> rm = wineArticleService.article_list_json(request, condition , cat_id , title);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增红酒软文",value="wineArticleAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/wineArticleAddDetail",method=RequestMethod.GET)
	public String wineArticleAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiArticle> rm = wineArticleService.article_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/wine/article/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交红酒软文",value="wineArticleAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/wineArticleAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineArticleAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@Valid @ModelAttribute("article") EHaiArticle article,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticle> rm = wineArticleService.article_insert_submit(request, article,goodsId);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑红酒软文",value="wineArticleEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/wineArticleEditDetail",method=RequestMethod.GET)
	public String wineArticleEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId
			) {
		try{
			ReturnObject<EHaiArticle> rm = wineArticleService.article_update(request,articleId);
			modelMap.addAttribute("rm", rm);
			return "/wine/article/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交红酒软文",value="wineArticleEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/wineArticleEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineArticleEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@Valid @ModelAttribute("article") EHaiArticle article,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(wineArticleService.article_update_submit(request,article,goodsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除红酒软文",value="wineArticleDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/wineArticleDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineArticleDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(wineArticleService.article_delete(request, articleId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="返回红酒软文数据",value="wineArticleCatListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/wineArticleCatListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineArticleCatListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "other", required = false) String other) {
		try{
			ReturnObject<EHaiArticleCat> rm = wineArticleCatService.articlecat_list_json(request, condition);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交红酒软文",value="wineArticleCatAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/wineArticleCatAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineArticleCatAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticleCat> rm = wineArticleCatService.articlecat_insert_submit(request, articlecat);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交红酒软文",value="wineArticleCatEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/wineArticleCatEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineArticleCatEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@Valid @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(wineArticleCatService.articlecat_update_submit(request,articlecat));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除红酒软文",value="wineArticleCatDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/wineArticleCatDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineArticleCatDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(wineArticleCatService.articlecat_delete(request, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}


