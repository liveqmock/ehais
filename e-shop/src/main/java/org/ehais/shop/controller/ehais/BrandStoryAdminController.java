package org.ehais.shop.controller.ehais;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
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

@Controller
@RequestMapping("/ehais")
public class BrandStoryAdminController extends EhaisCommonController {
	private static Logger log = LoggerFactory.getLogger(BrandStoryAdminController.class);

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
	
	
	@EPermissionMethod(intro="打开品牌故事页面",value="ehaisBrandStoryView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/ehaisBrandStoryView")
	public String ehaisBrandStoryView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_list(request,EArticleModuleEnum.BRANDSTORY);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getStoreTheme(request)+"/brand_story/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="返回红酒软文数据",value="ehaisBrandStoryListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/ehaisBrandStoryListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisBrandStoryListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "title", required = false) String title) {
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_list_json(request,EArticleModuleEnum.BRANDSTORY, condition , cat_id , title);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	

	@EPermissionMethod(name="新增",intro="新增红酒软文",value="ehaisBrandStoryAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisBrandStoryAddDetail",method=RequestMethod.GET)
	public String ehaisBrandStoryAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_insert(request,EArticleModuleEnum.BRANDSTORY);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/"+this.getStoreTheme(request)+"/brand_story/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交红酒软文",value="ehaisBrandStoryAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisBrandStoryAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisBrandStoryAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("article") EHaiArticle article,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_insert_submit(request,EArticleModuleEnum.BRANDSTORY, article,0l);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑红酒软文",value="ehaisBrandStoryEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisBrandStoryEditDetail",method=RequestMethod.GET)
	public String ehaisBrandStoryEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId
			) {
		try{
			ReturnObject<EHaiArticle> rm = ehaisArticleService.article_update(request,EArticleModuleEnum.BRANDSTORY,articleId);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			modelMap.addAttribute("domain", domain);
			return "/"+this.getStoreTheme(request)+"/brand_story/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交红酒软文",value="ehaisBrandStoryEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisBrandStoryEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisBrandStoryEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@Valid @ModelAttribute("article") EHaiArticle article,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(ehaisArticleService.article_update_submit(request,EArticleModuleEnum.BRANDSTORY,article,0l));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除红酒软文",value="ehaisBrandStoryDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisBrandStoryDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisBrandStoryDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(ehaisArticleService.article_delete(request,EArticleModuleEnum.BRANDSTORY, articleId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="返回红酒软文数据",value="ehaisBrandStoryCatListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/ehaisBrandStoryCatListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisBrandStoryCatListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "other", required = false) String other) {
		try{
			ReturnObject<EHaiArticleCat> rm = ehaisArticleCatService.articlecat_list_json(request,EArticleModuleEnum.BRANDSTORY, condition);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交红酒软文",value="ehaisBrandStoryCatAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisBrandStoryCatAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisBrandStoryCatAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticleCat> rm = ehaisArticleCatService.articlecat_insert_submit(request,EArticleModuleEnum.BRANDSTORY, articlecat);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交红酒软文",value="ehaisBrandStoryCatEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/ehaisBrandStoryCatEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisBrandStoryCatEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@Valid @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(ehaisArticleCatService.articlecat_update_submit(request,EArticleModuleEnum.BRANDSTORY,articlecat));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除红酒软文",value="ehaisBrandStoryCatDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/ehaisBrandStoryCatDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisBrandStoryCatDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(ehaisArticleCatService.articlecat_delete(request,EArticleModuleEnum.BRANDSTORY, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@RequestMapping(value="/manage/brand_story_qrcode",method=RequestMethod.POST)
	public void manage_brand_story_qrcode(ModelMap modelMap,
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
