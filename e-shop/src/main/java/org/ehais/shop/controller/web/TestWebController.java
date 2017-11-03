package org.ehais.shop.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.util.EHttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestWebController extends CommonController{
	
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;

	@RequestMapping("/validate")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws Exception {
		
		return "/web/test/validate";
	}
	
	
	@RequestMapping("/article_cat")
	public String article_cat(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws Exception {
		try{
			
			
			EHaiArticleCatExample expc = new EHaiArticleCatExample();
			expc.createCriteria().andStoreIdEqualTo(78);
			List<EHaiArticleCat> listc = eHaiArticleCatMapper.selectByExample(expc);
			
			modelMap.addAttribute("listc", listc);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/web/test/article_cat";
	}
	
	
	@RequestMapping("/article_{catId}")
	public String article(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value="catId") Integer catId
			) throws Exception {
		try{
			EHaiArticleExample exp = new EHaiArticleExample();
			exp.createCriteria().andStoreIdEqualTo(78).andCatIdEqualTo(catId);
			List<EHaiArticle> list = eHaiArticleMapper.selectByExample(exp);
			
			modelMap.addAttribute("list", list);
			
			EHaiArticleCat cat = eHaiArticleCatMapper.selectByPrimaryKey(catId);
			modelMap.addAttribute("cat", cat);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/web/test/article";
	}
	
	
	@RequestMapping("/article_detail_{articleId}")
	public String article_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value="articleId") Integer articleId
			) throws Exception {
		
		try{
			EHaiArticle model = eHaiArticleMapper.selectByPrimaryKey(articleId);
			if(StringUtils.isNotBlank(model.getArticleThumb())){
				model.setArticleThumb(model.getArticleThumb().replace("http://lib.gzarts.edu.cn/images/newBookCover/", request.getScheme()+"://"+request.getServerName()+"/eUploads/"));
			}
			modelMap.addAttribute("model", model);
			
			EHaiArticleCat cat = eHaiArticleCatMapper.selectByPrimaryKey(model.getCatId());
			modelMap.addAttribute("cat", cat);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "/web/test/article_detail";
	}
	
	@ResponseBody
	@RequestMapping("/test_ws")
	public String test_ws(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) throws Exception {
		String req = "";
		
		req = EHttpClientUtil.methodGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx722e9a654b5d58cc&secret=63be13ed69ff195b599d4ccd53b8ca93");
		
		return req;
	}
	
}
