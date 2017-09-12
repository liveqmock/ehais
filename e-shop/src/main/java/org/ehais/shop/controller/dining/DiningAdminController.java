package org.ehais.shop.controller.dining;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.shop.model.tp.TpAdmin;
import org.ehais.shop.service.AdminUserService;
import org.ehais.shop.service.ArticleCatService;
import org.ehais.shop.service.ArticleService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 作废。。。。。。。。。。。。。
 * @author lgj628
 *
 */
@Controller
@RequestMapping("/dining")
public class DiningAdminController extends CommonController{

	@Autowired
	private ArticleCatService articleCatService;
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 验证码
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/AuthImage")
	public void AuthImage(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws IOException {	
		
		response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
           
        //生成随机字串 
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4); 
        //存入会话session 
        HttpSession session = request.getSession(true); 
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase()); 
        //生成图片 
        int w = 100, h = 30; 
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode); 
        
	}
	
	
	@RequestMapping("/login")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/dining/login";
	}
	
	@ResponseBody
	@RequestMapping("/login_submit")
	public String login_submit(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "verificationcode", required = true) String verificationcode
			) {		
		try{
			ReturnObject<TpAdmin> rm = adminUserService.login_submit(request , username, password , verificationcode);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "{'code':-1,'msg':'wrong'}";
	}
	
	@RequestMapping("/logout")
	public String logout(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			HttpSession session = request.getSession(true);
			session.removeAttribute(EConstants.SESSION_ADMIN_ID);
			session.removeAttribute(EConstants.SESSION_SUPPLIERS_ID);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:login";
	}
	
	@RequestMapping("/main")
	public String main(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/dining/main";
	}
	
	
	@RequestMapping("/m_article_list")
	public String m_article_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/dining/m_article_list";
	}
	
	@RequestMapping("/m_article_add")
	public String m_article_add(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Integer store_id = (Integer)request.getSession().getAttribute("store_id");
		if(store_id == null)store_id = 55;
		try{
			
			ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
			EHaiArticle model = new EHaiArticle();
			rm.setModel(model);
			rm.setAction("add");
			modelMap.addAttribute("rm", rm);
			
			List<EHaiArticleCat> articleCatList = articleCatService.articleCatList(store_id, 0);
			modelMap.addAttribute("articleCatList", articleCatList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/dining/m_article_info";
	}
	
	
	@ResponseBody
	@RequestMapping("/m_article_info_insert_submit")
	public String m_article_info_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ,
			@ModelAttribute EHaiArticle article) {	
		
		try{
			
			ReturnObject<EHaiArticle> rm = articleCatService.article_insert_submit(request,article);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/dining/m_article_info";
	}
	
	
	
	@RequestMapping("/m_article_edit")
	public String m_article_edit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id) {	
		
		Integer store_id = (Integer)request.getSession().getAttribute("store_id");
		try{
			ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
			EHaiArticle model = new EHaiArticle();
			rm.setModel(model);
			rm.setAction("add");
			modelMap.addAttribute("rm", rm);
			
			List<EHaiArticleCat> articleCatList = articleCatService.articleCatList(store_id, 0);
			modelMap.addAttribute("articleCatList", articleCatList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/dining/m_article_info";
	}
	
	
	
}
