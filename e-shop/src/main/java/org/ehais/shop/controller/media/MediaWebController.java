package org.ehais.shop.controller.media;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class MediaWebController extends CommonController{
	
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	
	private Integer store_id = 27;
	private String modal = "web";
	private Integer len  = 4;
	private Integer hot_len  = 9;

	@RequestMapping("/index.do")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			modelMap.addAttribute("currentNav", "index");
			
			if(isWeiXin(request) || JudgeIsMoblie(request)){
				modal = "h5";
				hot_len = 4;
			}
			
			EHaiArticleCatExample ace = new EHaiArticleCatExample();
			ace.createCriteria().andStoreIdEqualTo(store_id);
			ace.setOrderByClause("sort_order asc");
			List<EHaiArticleCat> listArticleCat = eHaiArticleCatMapper.selectByExample(ace);
			modelMap.addAttribute("listArticleCat", listArticleCat);
			
			EHaiArticleExample ae = new EHaiArticleExample();
			
			//推荐的
			ae.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andOpenTypeEqualTo(Short.valueOf("1"));
			ae.setOrderByClause("sort asc");
			ae.setLimitStart(0);
			ae.setLimitEnd(hot_len);
			List<EHaiArticle> listArticleIndex = eHaiArticleMapper.selectByExample(ae);
			modelMap.addAttribute("listArticleIndex", listArticleIndex);
			
			Map<String, List<EHaiArticle>> mapArticle = new HashMap<String, List<EHaiArticle>>();
			//分类
			for (EHaiArticleCat c : listArticleCat) {
				ae.clear();
				ae.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(c.getCatId());
				ae.setOrderByClause("sort asc");
				ae.setLimitStart(0);
				ae.setLimitEnd(len);
				List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(ae);
				mapArticle.put(c.getCatId().toString(), listArticle);
			}
			modelMap.addAttribute("mapArticle", mapArticle);
			
			return "/media/"+modal+"/index";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}		
	}
	
	
	@RequestMapping("/list_{cid}.lv")
	public String list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") Integer cid,
			@RequestParam(value = "p", required = false) Integer p){
		
		modelMap.addAttribute("currentNav", cid.toString());
		
		try{
			if(isWeiXin(request) || JudgeIsMoblie(request))modal = "h5";
			if(p == null)p = 1;
			
			EHaiArticleCatExample ace = new EHaiArticleCatExample();
			ace.createCriteria().andStoreIdEqualTo(store_id);
			ace.setOrderByClause("sort_order asc");
			List<EHaiArticleCat> listArticleCat = eHaiArticleCatMapper.selectByExample(ace);
			modelMap.addAttribute("listArticleCat", listArticleCat);
			
			
			ace.clear();
			ace.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(cid);
			List<EHaiArticleCat> listArticleCat2 = eHaiArticleCatMapper.selectByExample(ace);
			if(listArticleCat2 == null || listArticleCat2.size() == 0){
				return this.errorJump(modelMap, "空数据");
			}
			
			EHaiArticleCat articleCat = listArticleCat2.get(0);
			modelMap.addAttribute("articleCat", articleCat);
			
			
			
			EHaiArticleExample ae = new EHaiArticleExample();
			ae.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(cid);
			ae.setOrderByClause("sort asc");
			ae.setLimitStart(( p - 1 ) * len);
			ae.setLimitEnd(len);
			List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(ae);
			
			modelMap.addAttribute("listArticle", listArticle);
			
			
			ae.clear();
			ae.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andOpenTypeEqualTo(Short.valueOf("1"));
			ae.setOrderByClause("sort asc");
			ae.setLimitStart(0);
			ae.setLimitEnd(10);
			List<EHaiArticle> listArticleHot = eHaiArticleMapper.selectByExample(ae);
			modelMap.addAttribute("listArticleHot", listArticleHot);
			
			
			return "/media/"+modal+"/list";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
	}
	
	@RequestMapping("/play{id}.lv")
	public String play(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "id") Integer id){
		
		try{
			if(isWeiXin(request) || JudgeIsMoblie(request))modal = "h5";
			
			
			EHaiArticleCatExample ace = new EHaiArticleCatExample();
			ace.createCriteria().andStoreIdEqualTo(store_id);
			ace.setOrderByClause("sort_order asc");
			List<EHaiArticleCat> listArticleCat = eHaiArticleCatMapper.selectByExample(ace);
			modelMap.addAttribute("listArticleCat", listArticleCat);
			
			
			EHaiArticleExample ae = new EHaiArticleExample();
			ae.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andArticleIdEqualTo(id);
			ae.setLimitStart(0);
			ae.setLimitEnd(1);
			List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(ae);
			
			if(listArticle == null || listArticle.size() == 0){
				return this.errorJump(modelMap, "空数据");
			}
			
			modelMap.addAttribute("article", listArticle.get(0));
			
			modelMap.addAttribute("currentNav", listArticle.get(0).getCatId().toString());
			
			
			ae.clear();
			ae.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andOpenTypeEqualTo(Short.valueOf("1"));
			ae.setOrderByClause("sort asc");
			ae.setLimitStart(0);
			ae.setLimitEnd(4);
			List<EHaiArticle> listArticleHot = eHaiArticleMapper.selectByExample(ae);
			modelMap.addAttribute("listArticleHot", listArticleHot);
			
			
			return "/media/"+modal+"/play";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
	}
	
}
