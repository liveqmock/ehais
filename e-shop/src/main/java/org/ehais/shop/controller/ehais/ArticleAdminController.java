package org.ehais.shop.controller.ehais;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.mapper.HaiArticleGoodsMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.WArticleGoodsMapper;
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


@EPermissionController(intro="红酒软文功能",value="ehaisArticleController")
@Controller
@RequestMapping("/ehais")
public class  ArticleAdminController extends EhaisCommonController {

	private static Logger log = LoggerFactory.getLogger(ArticleAdminController.class);

	@Autowired
	private ArticleService ehaisArticleService;
	@Autowired
	private ArticleCatService ehaisArticleCatService;
	@Autowired
	private HaiArticleGoodsMapper haiArticleGoodsMapper;
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	
	
	@EPermissionMethod(intro="打开红酒软文页面",value="ehaisArticleView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/ehaisArticleView")
	public String ehaisArticleView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/article/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回红酒软文数据",value="ehaisArticleListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/ehaisArticleListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "title", required = false) String title) {
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_list_json(request, condition , cat_id , title);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增红酒软文",value="ehaisArticleAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisArticleAddDetail",method=RequestMethod.GET)
	public String ehaisArticleAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/article/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交红酒软文",value="ehaisArticleAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisArticleAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@Valid @ModelAttribute("article") EHaiArticle article,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_insert_submit(request, article,goodsId);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑红酒软文",value="ehaisArticleEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisArticleEditDetail",method=RequestMethod.GET)
	public String ehaisArticleEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId
			) {
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_update(request,articleId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/article/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交红酒软文",value="ehaisArticleEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisArticleEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@Valid @ModelAttribute("article") EHaiArticle article,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(ehaisArticleService.article_update_submit(request,article,goodsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除红酒软文",value="ehaisArticleDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisArticleDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(ehaisArticleService.article_delete(request, articleId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="返回红酒软文数据",value="ehaisArticleCatListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/ehaisArticleCatListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleCatListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "other", required = false) String other) {
		try{
			ReturnObject<EHaiArticleCat> rm = ehaisArticleCatService.articlecat_list_json(request, condition);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交红酒软文",value="ehaisArticleCatAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisArticleCatAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleCatAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticleCat> rm = ehaisArticleCatService.articlecat_insert_submit(request, articlecat);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交红酒软文",value="ehaisArticleCatEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisArticleCatEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleCatEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@Valid @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(ehaisArticleCatService.articlecat_update_submit(request,articlecat));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除红酒软文",value="ehaisArticleCatDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisArticleCatDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleCatDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(ehaisArticleCatService.articlecat_delete(request, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@RequestMapping(value="/manage/article_qrcode",method=RequestMethod.POST)
	public void manage_article_qrcode(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "download", required = false) Integer download
			
			) {	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try{
			
			this.article_qrcode(request, response, eHaiArticleMapper, haiGoodsMapper,haiArticleGoodsMapper, store_id, 0, 0L, 0L, articleId, download);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
}


