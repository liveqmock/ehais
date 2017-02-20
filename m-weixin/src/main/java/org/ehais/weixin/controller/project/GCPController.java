package org.ehais.weixin.controller.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.epublic.model.EHaiAd;
import org.ehais.epublic.service.EAdService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.controller.WxCommonController;
import org.ehais.weixin.model.HaiArticle;
import org.ehais.weixin.model.HaiArticleCat;
import org.ehais.weixin.service.action.ArticleCatService;
import org.ehais.weixin.service.action.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/gcp")
public class GCPController extends WxCommonController{
	private static Logger log = LoggerFactory.getLogger(GCPController.class);

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCatService articleCatService;
	@Autowired
	private EAdService eAdService;
	
	@RequestMapping("/index.html")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
//			,@PathVariable(value = "wxid") Integer wxid
			) {	
		int wxid = 7;
		try{
//			ReturnObject<EHaiAd> rm = eAdService.ad_list_json(wxid, 1, 10);
//			modelMap.addAttribute("ad_list", rm.getRows());
			modelMap.addAttribute("wxid", wxid);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gcp/index";
	}
	
	@RequestMapping("/contactus.html")
	public String contactus(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "联系我们");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gcp/contactus";
	}
	
	
	@RequestMapping("/cpzx.html")
	public String cpzx(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "产品中心");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gcp/cpzx";
	}
	
	
	@RequestMapping("/fwzc.html")
	public String fwzc(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "服务支持");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gcp/fwzc";
	}
	
	
	@RequestMapping("/gywm.html")
	public String gywm(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "关于我们");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gcp/gywm";
	}
	
	@RequestMapping("/hzjm.html")
	public String hzjm(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "合作加盟");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gcp/hzjm";
	}
	
	
	@RequestMapping("/job.html")
	public String job(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "人才招聘");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gcp/job";
	}
	
	@RequestMapping("/xswl.html")
	public String xswl(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "销售网络");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gcp/xswl";
	}
	
	@RequestMapping("/xwzx.html")
	public String xwzx(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {		
		try{
			modelMap.addAttribute("title", "新闻中心");
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gcp/xwzx";
	}
	
	
	@RequestMapping("/ai-{wxid}-{id}.html")
	public String article_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxid") Integer wxid,
			@PathVariable("id") Integer id){
		modelMap.addAttribute("title", "资讯信息");
		try {
			modelMap.addAttribute("model", articleService.article(request,id));			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "project/gcp/article";
	}
	
	@RequestMapping("/ac-{wxid}-{code}.html")
	public String article_code(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxid") Integer wxid,
			@PathVariable("code") String code){
		modelMap.addAttribute("title", "资讯信息");
		try {
			modelMap.addAttribute("model", articleService.article_code(request,wxid,code));			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "project/gcp/article";
	}
	
	
	@RequestMapping("/yxjs-{code}.html")
	public String yxjs(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("code") String code) {		
		modelMap.addAttribute("title", "院系介绍");
		try{
			ReturnObject<HaiArticle> rm = articleService.article_list_by_catcode(request,7, code, 1, 50);
			modelMap.addAttribute("rm", rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("gcp", e);
		}		
		return "project/gcp/yxjs";
	}
	
	/**
	 * 根据分类编码去获取相应的资讯列表
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wxid
	 * @param code
	 * @return
	 */
	@RequestMapping("/al-{wxid}-{code}.html")
	public String article_cat_code_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxid") Integer wxid,
			@PathVariable("code") String code){
		
		try {
			ReturnObject<HaiArticleCat> rac = articleCatService.articlecatcode(request, 7, code);
			if(rac.getCode() != 1){
				return this.wrongJump(modelMap, "记录不存在");
			}
			modelMap.addAttribute("title", rac.getModel().getCatName());
			ReturnObject<HaiArticle> rm = articleService.article_list_by_catcode(request,7, code, 1, 50);
			modelMap.addAttribute("rm", rm);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "project/gcp/article_list";
	}
	
	
}
