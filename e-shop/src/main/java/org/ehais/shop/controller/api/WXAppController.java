package org.ehais.shop.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ehais.controller.CommonController;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.shop.mapper.HaiAdMapper;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiGoodsAttrMapper;
import org.ehais.shop.mapper.HaiGoodsGalleryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdExample;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.model.HaiGoodsGalleryExample;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.Bean2Utils;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.utils.WeiXinUtil;
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
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiGoodsGalleryMapper haiGoodsGalleryMapper;
	@Autowired
	private HaiGoodsAttrMapper haiGoodsAttrMapper;
	@Autowired
	protected EWPPublicService eWPPublicService;
	@Autowired
	protected EStoreService eStoreService;
	
	@ResponseBody
	@RequestMapping("/wxapp.jscode2session")
	public String wxapp_jscode2session(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "code", required = true) String code){
		System.out.println(code);
		ReturnObject<Object> rm = new ReturnObject<Object>();
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			OpenidInfo openInfo = WeiXinUtil.getJsCode2SessionOpenid(code, wp.getAppid(), wp.getSecret());
			Bean2Utils.printEntity(openInfo);
			HttpSession session = request.getSession();
			map.put("sessionId", session.getId());
			session.setAttribute("openid", openInfo.getOpenid());
			session.setAttribute("key3rd", openInfo.getSession_key()+"|"+openInfo.getOpenid());
			String token = ECommon.nonceStr(4);
			
			System.out.println(token);
			
			session.setAttribute("token", token);
			rm.setMap(map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
	}
	
	@ResponseBody
	@RequestMapping("/wxapp.article.v1")
	public String wxapp_article_v1(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "parent_id", required = true) Integer parent_id){
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			System.out.println("token:"+request.getSession().getAttribute("token"));
			EHaiArticleCatExample expCat = new EHaiArticleCatExample();
			expCat.createCriteria().andStoreIdEqualTo(store_id).andParentIdEqualTo(parent_id).andModuleEqualTo(EArticleModuleEnum.ARTICLE);
			List<EHaiArticleCat> catList = eHaiArticleCatMapper.selectByExample(expCat);
			map.put("catList", catList);
			if(catList!=null && catList.size() > 0) {
				EHaiArticleExample expArt = new EHaiArticleExample();
				expArt.createCriteria().andCatIdEqualTo(catList.get(0).getCatId()).andStoreIdEqualTo(store_id).andModuleEqualTo(EArticleModuleEnum.ARTICLE);
				expArt.setLimitStart(0);
				expArt.setLimitEnd(10);
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
	@RequestMapping("/wxapp.article.list.v1")
	public String wxapp_article_list_v1(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@ModelAttribute EConditionObject condition){
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		System.out.println("token:"+request.getSession().getAttribute("token"));
		try {
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
		System.out.println("token:"+request.getSession().getAttribute("token"));
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			EHaiArticleExample expArt = new EHaiArticleExample();
			expArt.createCriteria().andArticleIdEqualTo(articleId).andStoreIdEqualTo(store_id).andModuleEqualTo(EArticleModuleEnum.ARTICLE);
			List<EHaiArticle> articleList = eHaiArticleMapper.selectByExampleWithBLOBs(expArt);
			if(articleList.size()>0) {
				
				map.put("title", articleList.get(0).getTitle());
				map.put("content", articleList.get(0).getContent());
				map.put("date", articleList.get(0).getArticleDate()!=null ? DateUtil.formatDate(articleList.get(0).getArticleDate(), DateUtil.FORMATSTR_3):"");
				
				Integer catId = articleList.get(0).getCatId();
				EHaiArticleCatExample expCat = new EHaiArticleCatExample();
				expCat.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(catId).andModuleEqualTo(EArticleModuleEnum.ARTICLE);
				List<EHaiArticleCat> catList = eHaiArticleCatMapper.selectByExample(expCat);
				if(catList.size() > 0) {
					map.put("catName", catList.get(0).getCatName());
				}
				
			}
			rm.setMap(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return this.writeJson(rm);
	}
	
	
	
	@ResponseBody
	@RequestMapping("/wxapp.goods.v1")
	public String wxapp_goods_v1(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "parent_id", required = true) Integer parent_id){
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			
			HaiCategoryExample expCat = new HaiCategoryExample();
			expCat.createCriteria().andStoreIdEqualTo(store_id).andParentIdEqualTo(parent_id);
			List<HaiCategory> catList = haiCategoryMapper.selectByExample(expCat);
			map.put("catList", catList);
			
			if(catList!=null && catList.size() > 0) {
				HaiGoodsExample expGoods = new HaiGoodsExample();
				expGoods.createCriteria().andCatIdEqualTo(catList.get(0).getCatId()).andStoreIdEqualTo(store_id);
				expGoods.setLimitStart(0);
				expGoods.setLimitEnd(10);
				List<HaiGoods> goodsList = haiGoodsMapper.selectByExample(expGoods);
				map.put("goodsList", goodsList);
			}else {
				map.put("goodsList", null);
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
	@RequestMapping("/wxapp.goods.list.v1")
	public String wxapp_goods_list_v1(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@ModelAttribute EConditionObject condition){
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		try {
			HaiGoodsExample expGoods = new HaiGoodsExample();
			expGoods.createCriteria().andCatIdEqualTo(catId).andStoreIdEqualTo(condition.getStore_id());
			expGoods.setLimitStart(condition.getStart());
			expGoods.setLimitEnd(condition.getRows());
			List<HaiGoods> goodsList = haiGoodsMapper.selectByExample(expGoods);
			rm.setRows(goodsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return this.writeJson(rm);
	}
	
	
	
	@ResponseBody
	@RequestMapping("/wxapp.goods.view.v1")
	public String wxapp_goods_view_v1(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "goodsId", required = true) Long goodsId){
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			HaiGoodsExample expGoods = new HaiGoodsExample();
			expGoods.createCriteria().andGoodsIdEqualTo(goodsId).andStoreIdEqualTo(store_id);
			List<HaiGoodsWithBLOBs> goodsList = haiGoodsMapper.selectByExampleWithBLOBs(expGoods);
			if(goodsList.size()>0) {
				rm.setModel(goodsList.get(0));
				map.put("goodsName", goodsList.get(0).getGoodsName());
				map.put("shopPrice", goodsList.get(0).getShopPrice());
				map.put("goodsDesc", goodsList.get(0).getGoodsDesc());
				
				Integer catId = goodsList.get(0).getCatId();
				HaiCategoryExample expCat = new HaiCategoryExample();
				expCat.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(catId);
				List<HaiCategory> catList = haiCategoryMapper.selectByExample(expCat);
				if(catList.size() > 0) {
					map.put("catName", catList.get(0).getCatName());
				}
				
				
				HaiGoodsGalleryExample gexp = new HaiGoodsGalleryExample();
				gexp.createCriteria().andGoodsIdEqualTo(goodsList.get(0).getGoodsId());
				List<HaiGoodsGallery> gList = haiGoodsGalleryMapper.selectByExample(gexp);
				List<String> galleryList = new ArrayList<String>();
				for (HaiGoodsGallery g : gList) {
					galleryList.add(g.getThumbUrl());
				}
				map.put("galleryList", galleryList);
				
			}
			rm.setMap(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return this.writeJson(rm);
	}
	
	
	
}
