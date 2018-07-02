package org.ehais.shop.controller.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.ehais.controller.CommonController;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.shop.mapper.HaiAdMapper;
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdExample;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/weixinapp")
public class WXAppController extends CommonController{

	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private HaiAdMapper haiAdMapper;
	
	
	@ResponseBody
	@RequestMapping("/wxapp.v1")
	public String wxappv1(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "parent_id", required = true) Integer parent_id){
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		try {
			Map<String,Object> map = new HashedMap<String,Object>();
			
			EHaiArticleCatExample expCat = new EHaiArticleCatExample();
			expCat.createCriteria().andStoreIdEqualTo(store_id).andParentIdEqualTo(parent_id).andModuleEqualTo(EArticleModuleEnum.ARTICLE);
			List<EHaiArticleCat> catList = eHaiArticleCatMapper.selectByExample(expCat);
			map.put("catList", catList);
			if(catList!=null && catList.size() > 0) {
				EHaiArticleExample expArt = new EHaiArticleExample();
				expArt.createCriteria().andCatIdEqualTo(catList.get(0).getCatId()).andStoreIdEqualTo(store_id).andModuleEqualTo(EArticleModuleEnum.ARTICLE);
				expArt.setLimitStart(0);
				expArt.setLimitEnd(5);
				List<EHaiArticle> articleList = eHaiArticleMapper.selectByExample(expArt);
				map.put("articleList", articleList);
			}else {
				map.put("articleList", null);
			}
			
			HaiAdExample expAd = new HaiAdExample();
			expAd.createCriteria().andStoreIdEqualTo(store_id).andAdPicIsNotNull();
			List<HaiAd> adList = haiAdMapper.selectByExample(expAd);
			map.put("adList", adList);
			
			rm.setMap(map);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return this.writeJson(rm);
	}
	
	
	@ResponseBody
	@RequestMapping("/wxapp.article.v1")
	public String wxapp_article_v1(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@ModelAttribute EConditionObject condition){
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		try {
			System.out.println(condition.getStart()+"--"+condition.getRows());
			EHaiArticleExample expArt = new EHaiArticleExample();
			expArt.createCriteria().andCatIdEqualTo(catId).andStoreIdEqualTo(condition.getStore_id()).andModuleEqualTo(EArticleModuleEnum.ARTICLE);
			expArt.setLimitStart(condition.getStart());
			expArt.setLimitEnd(condition.getRows());
			List<EHaiArticle> articleList = eHaiArticleMapper.selectByExample(expArt);
			rm.setRows(articleList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return this.writeJson(rm);
	}
	
	@ResponseBody
	@RequestMapping("/wxapp.article.view.v1")
	public String wxapp_article_view_v1(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "articleId", required = true) Integer articleId){
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		try {
			EHaiArticleExample expArt = new EHaiArticleExample();
			expArt.createCriteria().andArticleIdEqualTo(articleId).andStoreIdEqualTo(store_id).andModuleEqualTo(EArticleModuleEnum.ARTICLE);
			List<EHaiArticle> articleList = eHaiArticleMapper.selectByExampleWithBLOBs(expArt);
			if(articleList.size()>0) {
				rm.setModel(articleList.get(0));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return this.writeJson(rm);
	}
	
	
}
