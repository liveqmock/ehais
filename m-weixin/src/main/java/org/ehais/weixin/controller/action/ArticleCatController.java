package org.ehais.weixin.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.model.TreeModel;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.HaiArticleCat;
import org.ehais.weixin.service.action.ArticleCatService;
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
@RequestMapping("/member")
public class  ArticleCatController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(ArticleCatController.class);

	@Autowired
	private ArticleCatService articlecatService;
	
	
	@RequestMapping("/articlecat_list")
	public String articlecat_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		try{
			modelMap.addAttribute("wxid", user_id.intValue());
			modelMap.addAttribute("action", "articlecat_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
		}
		return "/action/articlecat/list";
	}
	
	@ResponseBody
	@RequestMapping("/articlecat_list_json")
	public String articlecat_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<TreeModel> rm = articlecatService.articlecat_tree_json(request, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
		}
		return null;
	}
	
	

	@RequestMapping("/articlecat_insert")
	public String articlecat_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiArticleCat> rm = articlecatService.articlecat_insert(request);
			rm.setAction("articlecat_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
		}
		return "/action/articlecat/info";
	}
	
	@RequestMapping(value="/articlecat_insert_submit",method=RequestMethod.POST)
	public String articlecat_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiArticleCat articlecat
			) {
		try{
			ReturnObject<HaiArticleCat> rm = articlecatService.articlecat_insert_submit(request,articlecat);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "articlecat_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
		}
		return "redirect:articlecat_insert";
	}
	
	@RequestMapping("/articlecat_update")
	public String articlecat_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId
			) {
		try{
			ReturnObject<HaiArticleCat> rm = articlecatService.articlecat_update(request, catId);
			rm.setAction("articlecat_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
		}
		return "/action/articlecat/info";
	}
	
	@RequestMapping(value="/articlecat_update_submit",method=RequestMethod.POST)
	public String articlecat_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiArticleCat articlecat
			) {
		try{
			ReturnObject<HaiArticleCat> rm = articlecatService.articlecat_update_submit(request,articlecat);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "articlecat_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
		}
		return "/action/articlecat/info";
	}
	
	
	@RequestMapping("/articlecat_delete")
	public String articlecat_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = false) Integer catId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<HaiArticleCat> rm = articlecatService.articlecat_delete(request, catId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "articlecat_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("articlecat", e);
		}
		return null;
	}
	
	
}


