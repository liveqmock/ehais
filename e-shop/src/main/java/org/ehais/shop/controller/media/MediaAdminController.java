package org.ehais.shop.controller.media;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.service.EHaiAdminUserService;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.mapper.HaiArticleGoodsMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.service.ArticleCatService;
import org.ehais.shop.service.ArticleService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ResourceUtil;
import org.ehais.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@EPermissionModuleGroup(name="模组")

@EPermissionController(name="视频信息管理",value="mediaAdminController")
@Controller
@RequestMapping("/")
public class MediaAdminController extends CommonController{

	//视频保存路径配置
	protected String video_path = ResourceUtil.getProValue("video.path");
	//视频是否中转
	protected boolean video_transfer_bool = Boolean.getBoolean(ResourceUtil.getProValue("video.transfer.bool"));
	//视频中转地址
	protected String video_transfer_posturl = ResourceUtil.getProValue("video.transfer.posturl");
	//视频访问地址
	protected String video_transfer_website = ResourceUtil.getProValue("video.transfer.website");
	//视频上传格式
	protected String video_postfix = ResourceUtil.getProValue("video.postfix");
	
			
	@Autowired
	private ArticleService mediaArticleService;
	@Autowired
	private ArticleCatService mediaArticleCatService;
	@Autowired
	private EHaiAdminUserService eHaiAdminUserService;
	
	@EPermissionMethod(name="查询",intro="打开视频管理页面",value="login.me",relation="mediaArticleListJson",type=PermissionProtocol.URL)
	@RequestMapping("/login.me")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			modelMap.addAttribute("submit", "mediaLoginSubmitAjax.json");
			modelMap.addAttribute("redirect", "index.mg");
			return "/media/admin/login";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/mediaLoginSubmitAjax.json", method = RequestMethod.POST)
	public String mediaLoginSubmitAjax(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "verificationcode", required = true) String verificationcode
			) {
		try {
			ReturnObject<EHaiAdminUser> rm = eHaiAdminUserService.hai_login_submit(request, username, password,verificationcode,true);
			
			return this.writeJson(rm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 0);this.put("msg", "登录失败");}});
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/mediaLogout.json", method = RequestMethod.POST)
	public String mediaLogout(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			session.removeAttribute(EConstants.SESSION_ADMIN_ID);
			session.removeAttribute(EConstants.SESSION_ADMIN_NAME);
			session.removeAttribute(EConstants.SESSION_STORE_ID);
			session.removeAttribute(EConstants.SESSION_STORE_NAME);
			session.removeAttribute(EConstants.SESSION_ADMIN_CLASSIFY);
			session.removeAttribute(EConstants.SESSION_ADMIN_PROJECT_FOLDER);
			session.removeAttribute(EConstants.SESSION_ROLE_ID_ARRAY);
			session.removeAttribute(EConstants.SESSION_PARTNER_ID);
			session.removeAttribute(EConstants.SESSION_PARTNER_NAME);
			session.removeAttribute(EConstants.SESSION_PARTNER_THEME);
			session.removeAttribute(EConstants.SESSION_STORE_THEME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 1);this.put("msg", "退出登录");}});
	}
	
	
	
	@EPermissionMethod(name="查询",intro="打开视频管理页面",value="index.mg",relation="mediaArticleListJson",type=PermissionProtocol.URL)
	@RequestMapping("/index.mg")
	public String mediaArticleView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<EHaiArticle> rm = mediaArticleService.article_list(request,EArticleModuleEnum.ARTICLE);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("today", DateUtil.formatDate(new Date(), DateUtil.FORMATSTR_3));
			return "/media/admin/article/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="返回视频数据",value="mediaArticleListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/mediaArticleListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String mediaArticleListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "title", required = false) String title) {
		try{
			ReturnObject<EHaiArticle> rm = mediaArticleService.article_list_json(request,EArticleModuleEnum.ARTICLE, condition , cat_id , title);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交视频",value="mediaArticleAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/mediaArticleAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String mediaArticleAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = false) Long goodsId,
			@Valid @ModelAttribute("article") EHaiArticle article,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticle> rm = mediaArticleService.article_insert_submit(request,EArticleModuleEnum.ARTICLE, article,goodsId);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑视频",value="mediaArticleEditDetail",relation="mediaArticleEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/mediaArticleEditDetail",method=RequestMethod.POST)
	public String mediaArticleEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId
			) {
		try{
			ReturnObject<EHaiArticle> rm = mediaArticleService.article_update(request,EArticleModuleEnum.ARTICLE,articleId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.writeJsonObject(e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交视频",value="mediaArticleEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/mediaArticleEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String mediaArticleEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "goodsId", required = false) Long goodsId,
			@Valid @ModelAttribute("article") EHaiArticle article,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(mediaArticleService.article_update_submit(request,EArticleModuleEnum.ARTICLE,article,goodsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除视频",value="mediaArticleDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/mediaArticleDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String mediaArticleDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(mediaArticleService.article_delete(request,EArticleModuleEnum.ARTICLE, articleId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	
	@ResponseBody
	@EPermissionMethod(name="查询分类",intro="返回视频数据",value="mediaArticleCatListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/mediaArticleCatListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String mediaArticleCatListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "other", required = false) String other) {
		try{
			ReturnObject<EHaiArticleCat> rm = mediaArticleCatService.articlecat_list_json(request,EArticleModuleEnum.ARTICLE, condition);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(name="新增分类",intro="新增提交视频",value="mediaArticleCatAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/mediaArticleCatAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String mediaArticleCatAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<EHaiArticleCat> rm = mediaArticleCatService.articlecat_insert_submit(request,EArticleModuleEnum.ARTICLE, articlecat);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="编辑分类",intro="编辑提交视频",value="mediaArticleCatEdit",relation="mediaArticleCatEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/mediaArticleCatEdit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String mediaArticleCatEdit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId
			) {
		try{
			return this.writeJson(mediaArticleCatService.articlecat_info(request, EArticleModuleEnum.ARTICLE, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="编辑分类",intro="编辑提交视频",value="mediaArticleCatEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/mediaArticleCatEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String mediaArticleCatEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@Valid @ModelAttribute("articlecat") EHaiArticleCat articlecat,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(mediaArticleCatService.articlecat_update_submit(request,EArticleModuleEnum.ARTICLE,articlecat));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除分类",intro="删除视频",value="mediaArticleCatDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/mediaArticleCatDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String mediaArticleCatDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(mediaArticleCatService.articlecat_delete(request,EArticleModuleEnum.ARTICLE, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
			return this.errorJSON(e);
		}
	}
	
	

	@EPermissionMethod(name="单页面内容",intro="打开视频页面",value="mediaArticleDetail",relation="mediaArticleDetailSubmit",type=PermissionProtocol.URL)
	@RequestMapping("/mediaArticleDetail")
	public String mediaArticleDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ,
			@RequestParam(value = "module", required = true) String module) {	
		try{
			
			ReturnObject<EHaiArticle> rm = mediaArticleService.article_module(request,module);
			modelMap.addAttribute("rm", rm);
			
			return this.view(request, "/article/module");
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="",value="mediaArticleDetailSubmit",type=PermissionProtocol.JSON)
	@RequestMapping(value="/mediaArticleDetailSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String mediaArticleDetailSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@ModelAttribute("article") EHaiArticle article) {
		try{
			return this.writeJson(mediaArticleService.article_update_submit(request,article.getModule(),article,null));
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/video.mg", method = RequestMethod.POST)
	public String videoUpload(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response
	) {


		try {
			
			return UploadUtils.upload_video(request, response,video_path,true,video_transfer_posturl,video_postfix,video_transfer_website);

		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}
	
	
	
}
