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
import org.ehais.shop.service.HaiStoreStatisticsService;
import org.ehais.util.EHttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class TestWebController extends CommonController{
	
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	private HaiStoreStatisticsService haiStoreStatisticsService;
	
//	private Integer store_id = 87;//78[美院],87[岭南]
	
	@RequestMapping("/test/validate")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws Exception {
		haiStoreStatisticsService.dayStoreStatistics();
		return "/web/test/validate";
	}
	
	
	@RequestMapping("/test/article_cat_{store_id}")
	public String article_cat(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "store_id") Integer store_id) throws Exception {
		try{
			
			
			EHaiArticleCatExample expc = new EHaiArticleCatExample();
			expc.createCriteria()
			.andStoreIdEqualTo(store_id);
			List<EHaiArticleCat> listc = eHaiArticleCatMapper.selectByExample(expc);
			
			modelMap.addAttribute("listc", listc);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/web/test/article_cat";
	}
	
	
	@RequestMapping("/test/article_{catId}")
	public String article(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value="catId") Integer catId
			) throws Exception {
		try{
			EHaiArticleExample exp = new EHaiArticleExample();
			exp.createCriteria()
//			.andStoreIdEqualTo(store_id)
			.andCatIdEqualTo(catId);
			List<EHaiArticle> list = eHaiArticleMapper.selectByExample(exp);
			
			modelMap.addAttribute("list", list);
			
			EHaiArticleCat cat = eHaiArticleCatMapper.selectByPrimaryKey(catId);
			modelMap.addAttribute("cat", cat);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/web/test/article";
	}
	
	
	@RequestMapping("/test/article_detail_{articleId}")
	public String article_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value="articleId") Integer articleId
			) throws Exception {
		
		try{
			EHaiArticle model = eHaiArticleMapper.selectByPrimaryKey(articleId);
			if(StringUtils.isNotBlank(model.getArticleThumb())){
//				model.setArticleThumb(model.getArticleThumb().replace("http://lib.gzarts.edu.cn/images/newBookCover/", request.getScheme()+"://"+request.getServerName()+"/eUploads/"));
			}
			modelMap.addAttribute("model", model);
			
			EHaiArticleCat cat = eHaiArticleCatMapper.selectByPrimaryKey(model.getCatId());
			modelMap.addAttribute("cat", cat);
			
			if(model.getModule().equals("exhibit")){
				return "/web/test/article_detail_exhibit";
			}else if(model.getModule().equals("book")){
				return "/web/test/article_detail_book";
			}else if(model.getModule().equals("collection")){
				return "/web/test/article_detail_collection";
			}else if(model.getModule().equals("author")){
				return "/web/test/article_detail_author";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "/web/test/article_detail";
	}
	
	@ResponseBody
	@RequestMapping("/test/test_ws")
	public String test_ws(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) throws Exception {
		String req = "";
		
		req = EHttpClientUtil.methodGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx722e9a654b5d58cc&secret=63be13ed69ff195b599d4ccd53b8ca93");
		
		return req;
	}
	
	
	@ResponseBody
	@RequestMapping("/test/chrome")
	public String chrome(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "str", required = true) String str
			) throws Exception {
		String req = "chrom";
		System.out.println("网络请求的参数 ："+str);
		return req;
	}
	
	@RequestMapping("/cn/newsview.asp")
	public String newsview(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "Id", required = false) Integer Id
			) throws Exception {
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "/temp/newsview";
	}
	
	
}
