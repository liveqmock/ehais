package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.mapper.HaiArticleGoodsMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.service.ArticleCatService;
import org.ehais.shop.service.ArticleService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.QiniuUtil;
import org.ehais.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@EPermissionModuleGroup(name="应用模组")

@EPermissionController(name="资讯管理",intro="资讯管理功能",value="articleController")
@Controller
@RequestMapping("/admin")
public class ArticleController extends CommonController{

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
	
	private static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	private static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	private static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	private static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	
	@EPermissionMethod(name="查询",intro="打开软文页面",value="ehaisArticleView",relation="ehaisArticleListJson",type=PermissionProtocol.URL)
	@RequestMapping("/ehaisArticleView")
	public String ehaisArticleView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_list(request,EArticleModuleEnum.ARTICLE);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/article/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="返回软文数据",value="ehaisArticleListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/ehaisArticleListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "title", required = false) String title) {
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_list_json(request,EArticleModuleEnum.ARTICLE, condition , cat_id , title);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增软文",value="ehaisArticleAddDetail",relation="ehaisArticleAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/ehaisArticleAddDetail",method=RequestMethod.GET)
	public String ehaisArticleAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_insert(request,EArticleModuleEnum.ARTICLE);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/"+this.getAdminProjectFolder(request)+"/article/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交软文",value="ehaisArticleAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/ehaisArticleAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = false) Long goodsId,
			@Valid @ModelAttribute("article") EHaiArticle article,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_insert_submit(request,EArticleModuleEnum.ARTICLE, article,goodsId);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	@EPermissionMethod(name="编辑",intro="编辑软文",value="ehaisArticleEditDetail",relation="ehaisArticleEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/ehaisArticleEditDetail",method=RequestMethod.GET)
	public String ehaisArticleEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId
			) {
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_update(request,EArticleModuleEnum.ARTICLE,articleId);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/"+this.getAdminProjectFolder(request)+"/article/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交软文",value="ehaisArticleEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/ehaisArticleEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "goodsId", required = false) Long goodsId,
			@Valid @ModelAttribute("article") EHaiArticle article,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(ehaisArticleService.article_update_submit(request,EArticleModuleEnum.ARTICLE,article,goodsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除软文",value="ehaisArticleDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/ehaisArticleDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(ehaisArticleService.article_delete(request,EArticleModuleEnum.ARTICLE, articleId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	
	@ResponseBody
	@EPermissionMethod(name="查询分类",intro="返回软文数据",value="ehaisArticleCatListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/ehaisArticleCatListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleCatListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "other", required = false) String other) {
		try{
			ReturnObject<EHaiArticleCat> rm = ehaisArticleCatService.articlecat_list_json(request,EArticleModuleEnum.ARTICLE, condition);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(name="新增分类",intro="新增提交软文",value="ehaisArticleCatAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/ehaisArticleCatAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleCatAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticleCat> rm = ehaisArticleCatService.articlecat_insert_submit(request,EArticleModuleEnum.ARTICLE, articlecat);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(name="编辑分类",intro="编辑提交软文",value="ehaisArticleCatEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/ehaisArticleCatEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleCatEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@Valid @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(ehaisArticleCatService.articlecat_update_submit(request,EArticleModuleEnum.ARTICLE,articlecat));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除分类",intro="删除软文",value="ehaisArticleCatDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/ehaisArticleCatDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleCatDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(ehaisArticleCatService.articlecat_delete(request,EArticleModuleEnum.ARTICLE, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	
	@ResponseBody
	@EPermissionMethod(name="群发",intro="微信群发",value="ehaisArticleSendGroupWeixin",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/ehaisArticleSendGroupWeixin",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleSendGroupWeixin(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId
			) {
		try{
			return this.writeJson(ehaisArticleService.ehaisArticleSendGroupWeixin(request, EArticleModuleEnum.ARTICLE, articleId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	

	@EPermissionMethod(name="单页面内容",intro="打开软文页面",value="ehaisArticleDetail",relation="ehaisArticleDetailSubmit",type=PermissionProtocol.URL)
	@RequestMapping("/ehaisArticleDetail")
	public String ehaisArticleDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ,
			@RequestParam(value = "module", required = true) String module) {	
		try{
			
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_module(request,module);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			
			return this.view(request, "/article/module");
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="",value="ehaisArticleDetailSubmit",type=PermissionProtocol.JSON)
	@RequestMapping(value="/ehaisArticleDetailSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisArticleDetailSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@ModelAttribute("article") EHaiArticle article) {
		try{
			return this.writeJson(ehaisArticleService.article_update_submit(request,article.getModule(),article,null));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
}
