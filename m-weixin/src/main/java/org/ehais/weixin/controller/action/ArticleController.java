package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.HaiArticle;
import org.ehais.weixin.service.action.ArticleService;
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
@RequestMapping("/article")
public class ArticleController extends WxCommonController {
	private static Logger log = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/article_cat")
	public String article_cat(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/article/list";
	}
	
	@RequestMapping("/article_list")
	public String article_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "article_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/article/list";
	}
	
	
	@ResponseBody
	@RequestMapping("/article_list_json")
	public String article_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<HaiArticle> ro = articleService.article_list(request,wxid ,cat_id, page, len);
			return this.writeJson(ro);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/article_list_v2")
	public String article_list_v2(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("action", "article_list_json_v2");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/article/listv2";
	}
	
	
	@ResponseBody
	@RequestMapping("/article_list_json_v2")
	public String article_list_json_v2(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<HaiArticle> ro = articleService.article_list_json(request,wxid ,cat_id, page, len);
			return this.writeJson(ro);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/article_code_list")
	public String article_code_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {	
		try{
			modelMap.addAttribute("action", "article_code_list_json");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/article/article_code_list";
	}
	
	@ResponseBody
	@RequestMapping("/article_code_list_json")
	public String article_code_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<HaiArticle> ro = articleService.article_code_list(request,wxid , page, len);
			return this.writeJson(ro);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

	@RequestMapping("/article_insert")
	public String article_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "article_id", required = false) Integer article_id
			) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<HaiArticle> ro = articleService.article_insert(request,wxid);
			modelMap.addAttribute("cat_list", ro.getMap().get("cat_list"));
			modelMap.addAttribute("model", new HaiArticle());
			modelMap.addAttribute("action", "article_insert_submit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/article/info";
	}
	
	
	@RequestMapping("/article_insert_v2")
	public String article_insert_v2(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiArticle> rm = articleService.article_insert_v2(request);			
			rm.setAction("article_insert_submit");
			modelMap.addAttribute("rm", rm);
			
			modelMap.addAttribute("version", "v2");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/article/infov2";
	}
	
	@RequestMapping(value="/article_insert_submit",method=RequestMethod.POST)
	public String article_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "version", required = false) String version,
			@ModelAttribute HaiArticle article
			) {
		try{
//			Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
//			article.setStoreId(wxid);
			ReturnObject<HaiArticle> ro = articleService.article_insert_submit(request,article);
			String url = "article_insert";
			if(version != null && version.equals("v2"))url = "article_insert_v2";
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), url);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/article_update")
	public String article_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "article_id", required = true) Integer article_id
			) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<HaiArticle> ro = articleService.article_update(request,wxid, article_id);
			modelMap.addAttribute("cat_list", ro.getMap().get("cat_list"));
			modelMap.addAttribute("model", ro.getModel());
			modelMap.addAttribute("action", "article_update_submit");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/article/info";
	}
	
	
	@RequestMapping("/article_update_v2")
	public String article_update_v2(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "article_id", required = true) Integer article_id
			) {
		try{
			ReturnObject<HaiArticle> rm = articleService.article_update_v2(request, article_id);

			rm.setAction("article_update_submit");
			modelMap.addAttribute("rm", rm);
			
			modelMap.addAttribute("version", "v2");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/article/infov2";
	}
	
	@RequestMapping(value="/article_update_submit",method=RequestMethod.POST)
	public String article_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "version", required = false) String version,
			@ModelAttribute HaiArticle article
			) {
		try{
			
			ReturnObject<HaiArticle> ro = articleService.article_update_submit(request,article);
			String url = this.return_list_url(article.getCode());
			if(version != null && version.equals("v2"))url = "article_list_v2";
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), url);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/action/article/info";
	}
	
	
	@RequestMapping("/article_delete")
	public String article_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "article_id", required = false) Integer article_id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<HaiArticle> ro = articleService.article_delete(request,wxid, article_id);
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), this.return_list_url(code));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	@RequestMapping("/article_delete_v2")
	public String article_delete_v2(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "article_id", required = false) Integer article_id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			Integer wxid = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
			ReturnObject<HaiArticle> ro = articleService.article_delete(request,wxid, article_id);
			return this.ReturnJump(modelMap, ro.getCode(), ro.getMsg(), "article_list_v2");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private String return_list_url(String code){
		String url = "article_list";
		if(code!=null && !code.equals(""))url = "article_code_list";
		return url;
	}
	
}
