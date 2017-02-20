package org.ehais.weixin.controller.wx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.HaiArticle;
import org.ehais.weixin.model.WpPublic;
import org.ehais.weixin.service.action.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/weixin")
public class ArticleWXController extends WxCommonController{

	private static Logger log = LoggerFactory.getLogger(ArticleWXController.class);
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/article_list-{wxid}-{cid}")
	public String article_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxid") Integer wxid,
			@PathVariable("cid") Integer cid){
		try{
			modelMap.addAttribute("wxid", wxid);
			modelMap.addAttribute("cid", cid);
			modelMap.addAttribute("articleCat", articleService.article_cat(request,cid));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "weixin/article/list";
	}
	
	@ResponseBody
	@RequestMapping("/article_list_json")
	public String article_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "cid", required = true) Integer cid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "len", required = false) Integer len){
		try{
			Integer user_id = weiXinService.wxStoreId(wxid);
			ReturnObject<HaiArticle> ro = articleService.article_list(request,user_id ,cid, page, len);
			JSONObject json = JSONObject.fromObject(ro);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/article_code_list_json")
	public String article_code_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "len", required = false) Integer len){
		try{
			ReturnObject<HaiArticle> ro = articleService.article_list_by_catcode(request,wxid ,cat_code, page, len);
			JSONObject json = JSONObject.fromObject(ro);
			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/article_info")
	public String article_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "aid", required = true) Integer aid){
		try {
			modelMap.addAttribute("article", articleService.article(request,aid));			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "weixin/article/info";
	}
	
	@RequestMapping("/ai-{wxid}-{aid}.html")
	public String ai(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxid") Integer wxid,
			@PathVariable("aid") Integer aid){
		try {
			modelMap.addAttribute("article", articleService.article(request,aid));			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "weixin/article/info";
	}
	
	@RequestMapping("/article_code_info")
	public String article_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "wxid", required = true) Integer wxid,
			@RequestParam(value = "code", required = true) String code){
		try {
			WpPublic wpPublic = this.getWpPublic(wxid);
			modelMap.addAttribute("article", articleService.article_code(request,wpPublic.getUid().intValue(),code));			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "weixin/article/info";
	}
	
	
	
}
