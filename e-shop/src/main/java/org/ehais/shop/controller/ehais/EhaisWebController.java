package org.ehais.shop.controller.ehais;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.enums.EArticleRecordEnum;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleCatSimple;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.EHaiArticleSimple;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.shop.mapper.HaiAdMapper;
import org.ehais.shop.mapper.HaiArticleGoodsMapper;
import org.ehais.shop.mapper.HaiArticleRecordMapper;
import org.ehais.shop.mapper.HaiCartMapper;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiCouponsMapper;
import org.ehais.shop.mapper.HaiFavoritesMapper;
import org.ehais.shop.mapper.HaiForumMapper;
import org.ehais.shop.mapper.HaiGoodsGalleryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiUserAddressMapper;
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdExample;
import org.ehais.shop.model.HaiAdSimple;
import org.ehais.shop.model.HaiArticleGoods;
import org.ehais.shop.model.HaiArticleGoodsExample;
import org.ehais.shop.model.HaiArticleRecord;
import org.ehais.shop.model.HaiArticleRecordExample;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCartExample;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiCoupons;
import org.ehais.shop.model.HaiFavorites;
import org.ehais.shop.model.HaiFavoritesExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.model.HaiGoodsGalleryExample;
import org.ehais.shop.model.HaiUserAddress;
import org.ehais.shop.model.HaiUserAddressExample;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.MatrixToImageWriter;
import org.ehais.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/")
public class EhaisWebController extends EhaisCommonController {
	
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiArticleGoodsMapper haiArticleGoodsMapper;
	@Autowired
	private HaiCartMapper haiCartMapper;
	@Autowired
	private HaiUserAddressMapper haiUserAddressMapper;
	@Autowired
	private HaiArticleRecordMapper haiArticleRecordMapper;
	@Autowired
	private HaiForumMapper haiForumMapper;
	@Autowired
	private HaiGoodsGalleryMapper haiGoodsGalleryMapper;
	@Autowired
	private HaiFavoritesMapper haiFavoritesMapper;
	@Autowired
	private HaiAdMapper haiAdMapper;
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	@Autowired
	private HaiCouponsMapper haiCouponsMapper;
	
	
	//http://127.0.0.1/w_shop!afa5890-062c0c01-12b7b002-2c960b1253-37ca2179f7b3b
	//http://8f372c5b.ngrok.io/w_shop!afa5890-062c0c01-12b7b002-2c960b1253-37ca2179f7b3b
	@RequestMapping("/w_shop!{cid}")
	public String shop(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ,
			@PathVariable(value = "cid") String cid,
			@RequestParam(value = "code", required = false) String code
			) {	
		Integer store_id = SignUtil.getUriStoreId(cid);
		if(store_id == 0 || store_id == null){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getCid(cid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/w_shop!"+cid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_shop!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					
					return this.shopData(modelMap, request, response, wp, store, cid, store_id, user_id, map);
					
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_shop!"+cid);
				}else{
					System.out.println(cid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				return this.shopData(modelMap, request, response, wp, store, cid, store_id, user_id, map);
//				return "redirect:"+website;
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		
		return "/ehais/w_shop";
	}
	
	private String shopData(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ,
			WpPublicWithBLOBs wp ,
			EHaiStore store,
			String cid,
			Integer store_id,
			Long user_id ,
			Map<String,Object> map ) throws Exception{
		modelMap.addAttribute("store", store);
		modelMap.addAttribute("cid", cid);
		
		//广告信息
		HaiAdExample adExample = new HaiAdExample();
		adExample.createCriteria()
		.andStoreIdEqualTo(default_store_id)
		.andIsMobileEqualTo(1)
		.andIsVoidEqualTo(1);
		List<HaiAd> adList = haiAdMapper.selectByExample(adExample);
		
		//分类
		HaiCategoryExample categoryExample = new HaiCategoryExample();
		categoryExample.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andIsShowEqualTo(true);
		categoryExample.setOrderByClause("sort_order asc");
		List<HaiCategory> categoryList = haiCategoryMapper.selectByExample(categoryExample);
		
		//热销商品
		HaiGoodsExample goodsExample = new HaiGoodsExample();
		goodsExample.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andIsHotEqualTo(true)
		.andIsOnSaleEqualTo(true);
//		goodsExample.setLimitStart(0);
//		goodsExample.setLimitEnd(20);
		goodsExample.setOrderByClause("sort_order asc");
		List<HaiGoods> goodsList = haiGoodsMapper.selectByExample(goodsExample);
		for (HaiGoods haiGoods : goodsList) {
			haiGoods.setGoodsUrl("w_goods_detail!"+SignUtil.setSid(store_id, Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("userId").toString()), user_id, 0 , haiGoods.getGoodsId(), wp.getToken()));
		}
		modelMap.addAttribute("adList", adList);
		modelMap.addAttribute("categoryList", categoryList);
		modelMap.addAttribute("goodsList", goodsList);
		
		String newCid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user_id , wp.getToken());
		String link = request.getScheme() + "://" + request.getServerName() + "/w_shop!"+newCid;
		
		this.shareWeiXin(modelMap, request, response, wp, store_id, store.getStoreName(), link, store.getDescription(), store.getStoreLogo());
		
		return "/ehais/w_shop";
	}
	
	
	/**
	 * 获取软文与商品的所有信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 * @param map
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private String article_goods(ModelMap modelMap,	HttpServletRequest request,HttpServletResponse response,WpPublicWithBLOBs wp,String sid,Long user_id,Map<String,Object> map,String path) throws NumberFormatException, Exception{
		
		//读取文章信息
		EHaiArticle article = eHaiArticleMapper.selectByPrimaryKey(Integer.valueOf(map.get("articleId").toString()));
		//读取商品信息
		HaiArticleGoodsExample articleGoodsExp = new HaiArticleGoodsExample();
		articleGoodsExp.createCriteria().andArticleIdEqualTo(Integer.valueOf(map.get("articleId").toString()));
		List<HaiArticleGoods> articleGoodsList = haiArticleGoodsMapper.selectByExample(articleGoodsExp);
		Long goodsId = 0L;
		if(articleGoodsList != null && articleGoodsList.size() > 0){
			goodsId = articleGoodsList.get(0).getGoodsId();
			
			//读取微信的分享信息与链接整理
			if(goodsId.longValue() > 0){
				HaiGoods goods = haiGoodsMapper.selectByPrimaryKey(goodsId);
				modelMap.addAttribute("goods", goods);
			}
			sid = SignUtil.setSid(Integer.valueOf(map.get("store_id").toString()), Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("userId").toString()), user_id, Integer.valueOf(map.get("articleId").toString()), goodsId, wp.getToken());
		}
		
		String link = request.getScheme() + "://" + request.getServerName() + "/w_article_goods!"+sid;
		
		modelMap.addAttribute("sid", sid);
		modelMap.addAttribute("article", article);
		
		modelMap.addAttribute("defaultimg", defaultimg);
		modelMap.addAttribute("parentId", map.get("parentId"));
		modelMap.addAttribute("agencyId", map.get("agencyId"));
		modelMap.addAttribute("articleId", map.get("articleId"));
		
		String cid = SignUtil.setCid(Integer.valueOf(map.get("store_id").toString()), Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("userId").toString()), user_id, wp.getToken());
		modelMap.addAttribute("articleIndex", "w_article!"+cid);
		modelMap.addAttribute("shopIndex", "w_shop!"+cid);
		modelMap.addAttribute("storeUnion", "ehaisUnion!"+cid);
		
		
		this.shareWeiXin(modelMap, request, response, wp, Integer.valueOf(map.get("store_id").toString()), article.getTitle(), link, article.getDescription(), article.getArticleImages());
//		WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret(), null);
//		signature.setTitle(article.getTitle());
//		signature.setLink(link);
//		signature.setDesc(StringUtils.isBlank(article.getDescription())?weixin_share_description:article.getDescription());
//		signature.setImgUrl(StringUtils.isBlank(article.getArticleImages())?defaultimg:article.getArticleImages());
//		List<String> jsApiList = new ArrayList<String>();
//		jsApiList.add("onMenuShareTimeline");
//		jsApiList.add("onMenuShareAppMessage");
//		jsApiList.add("onMenuShareQQ");
//		jsApiList.add("onMenuShareWeibo");
//		jsApiList.add("onMenuShareQZone");
//		signature.setJsApiList(jsApiList);
//		modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
		
		
		
		modelMap.addAttribute("w_goods_detail","w_goods_detail!"+sid);
		modelMap.addAttribute("buynow","buynow!"+sid);
		return path;
	}
	
	
	private String goods_detail(ModelMap modelMap,	HttpServletRequest request,HttpServletResponse response,WpPublicWithBLOBs wp,String sid,Long user_id,Map<String,Object> map,String path) throws NumberFormatException, Exception{
		String link = request.getScheme() + "://" + request.getServerName() + "/w_goods_detail!"+sid;
		//读取文章信息
		Long goodsId = Long.valueOf(map.get("goodsId").toString());
		//读取微信的分享信息与链接整理
		if(goodsId == null || goodsId == 0L){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		HaiGoods goods = haiGoodsMapper.selectByPrimaryKey(goodsId);
		
		modelMap.addAttribute("sid", sid);
		modelMap.addAttribute("cid", SignUtil.setCid(Integer.valueOf(map.get("store_id").toString()), Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("userId").toString()), user_id, wp.getToken()));
		modelMap.addAttribute("goods", goods);
		modelMap.addAttribute("parentId", map.get("parentId"));
		modelMap.addAttribute("agencyId", map.get("agencyId"));
		modelMap.addAttribute("articleId", map.get("articleId"));
		
		HaiGoodsGalleryExample galleryExample = new HaiGoodsGalleryExample();
		galleryExample.createCriteria().andGoodsIdEqualTo(goodsId);
		List<HaiGoodsGallery> galleryList = haiGoodsGalleryMapper.selectByExample(galleryExample);
		modelMap.addAttribute("galleryList", galleryList);
		
		if(user_id != null && user_id > 0){
			HaiFavoritesExample fexp = new HaiFavoritesExample();
			fexp.createCriteria().andUserIdEqualTo(user_id).andGoodsIdEqualTo(goodsId);
			List<HaiFavorites> favoritesList = haiFavoritesMapper.selectByExample(fexp);
			if(favoritesList!=null && favoritesList.size()>0)modelMap.addAttribute("favorites", 1);
		}
		
		this.shareWeiXin(modelMap, request, response, wp, Integer.valueOf(map.get("store_id").toString()), goods.getGoodsName(), link, goods.getActDesc(), goods.getGoodsThumb());
		modelMap.addAttribute("w_goods_detail","w_goods_detail!"+sid);
		modelMap.addAttribute("buynow","buynow!"+sid);
		return path;
	}
	
	//http://127.0.0.1/w_article!1ce27200-02bd7b01-1ed30c02-2a6a0103-3760edeb1acce
	@RequestMapping("/w_article!{cid}")
	public String w_article(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") String cid	,
			@RequestParam(value = "code", required = false) String code
			) {
		Integer store_id = SignUtil.getUriStoreId(cid);
		if(store_id == null || store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getCid(cid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/w_article!"+cid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_article!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					
					return this.articleData(modelMap, request, response, wp, store, cid, store_id, user_id, map);
					
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_article!"+cid);
				}else{
					System.out.println(cid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				return this.articleData(modelMap, request, response, wp, store, cid, store_id, user_id, map);
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "redirect:"+website;
	}
	
	/**
	 * 文章列表信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wp
	 * @param store
	 * @param cid
	 * @param store_id
	 * @param user_id
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private String articleData(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ,
			WpPublicWithBLOBs wp ,
			EHaiStore store,
			String cid,
			Integer store_id,
			Long user_id ,
			Map<String,Object> map ) throws Exception{
		modelMap.addAttribute("store", store);
		
		Map<String,Object> mapData = new HashMap<String,Object>();
		
		//读取广告
		HaiAdExample adExample = new HaiAdExample();
		adExample.createCriteria()
		.andStoreIdEqualTo(default_store_id)
		.andIsMobileEqualTo(1)
		.andIsVoidEqualTo(1);
		List<HaiAdSimple> adList = haiAdMapper.mySelectByExample(adExample);
		mapData.put("adList", adList);
		//读取分类
		EHaiArticleCatExample acatExample = new EHaiArticleCatExample();
		acatExample.createCriteria().andStoreIdEqualTo(store_id).andValidEqualTo(true).andModuleEqualTo(EArticleModuleEnum.ARTICLE);
		acatExample.setOrderByClause("sort_order asc");
		List<EHaiArticleCatSimple> articleCatList = eHaiArticleCatMapper.mySelectByExample(acatExample);
		mapData.put("articleCatList", articleCatList);
		//读取资讯列表
		EHaiArticleExample aExample = new EHaiArticleExample();
		aExample.createCriteria().andStoreIdEqualTo(store_id).andIsHotEqualTo(true).andModuleEqualTo(EArticleModuleEnum.ARTICLE);
		aExample.setOrderByClause("article_date desc,article_id desc");
		List<EHaiArticleSimple> articleList = eHaiArticleMapper.mySelectByExample(aExample);
		for (EHaiArticleSimple eHaiArticle : articleList) {
			eHaiArticle.setLink(SignUtil.setSid(store_id, Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("userId").toString()), user_id, eHaiArticle.getArticleId(),0L, wp.getToken()));
		}
		
		mapData.put("hotArticleList", articleList);
		
		JSONObject json = JSONObject.fromObject(mapData,this.getDefaultJsonConfig());
		modelMap.addAttribute("json", json.toString());
		modelMap.addAttribute("cid", cid);
		

//		String newSid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user_id , wp.getToken());
		String link = request.getScheme() + "://" + request.getServerName() + "/w_article!"+cid;
		
		
		this.shareWeiXin(modelMap, request, response, wp, store_id, store.getStoreName(), link, store.getDescription(), store.getStoreLogo());
//		WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret(), null);
//		signature.setTitle(store.getStoreName());
//		signature.setLink(link);
//		signature.setDesc(store.getDescription());
//		signature.setImgUrl(store.getStoreLogo());
//		List<String> jsApiList = new ArrayList<String>();
//		jsApiList.add("onMenuShareTimeline");
//		jsApiList.add("onMenuShareAppMessage");
//		jsApiList.add("onMenuShareQQ");
//		jsApiList.add("onMenuShareWeibo");
//		jsApiList.add("onMenuShareQZone");
//		signature.setJsApiList(jsApiList);
//		modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
		
		return "/ehais/w_article";
	}
	
	
	@ResponseBody
	@RequestMapping("/article_list_cat_id!{cid}")
	public String article_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") String cid	,
			@RequestParam(value = "cat_id", required = true) Integer cat_id,
			@ModelAttribute EConditionObject condition ){
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		ReturnObject<EHaiArticleSimple> rm = new ReturnObject<EHaiArticleSimple>();
		rm.setCode(0);
		try {
//			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getCid(cid,wp.getToken());
			if(map == null){
				rm.setMsg("错误参数");
			    return this.writeJson(rm);
			}
			EHaiArticleExample example = new EHaiArticleExample();
			EHaiArticleExample.Criteria c = example.createCriteria();
			c.andStoreIdEqualTo(store_id);
			c.andModuleEqualTo(EArticleModuleEnum.ARTICLE);
			if(cat_id > 0)c.andCatIdEqualTo(cat_id);
			if(cat_id == 0)c.andIsHotEqualTo(true);
			example.setOrderByClause("article_date desc,article_id desc");
			example.setLimitStart(condition.getStart());
			example.setLimitEnd(condition.getRows());
			List<EHaiArticleSimple> list = eHaiArticleMapper.mySelectByExample(example);
			for (EHaiArticleSimple eHaiArticle : list) {
				eHaiArticle.setLink(SignUtil.setSid(store_id, Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("userId").toString()), user_id, eHaiArticle.getArticleId(),0L, wp.getToken()));
				
			}
			rm.setTotal(eHaiArticleMapper.countByExample(example));
			rm.setRows(list);
			rm.setCode(1);
			return this.writeJson(rm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	/*
	@RequestMapping("/w_article_detail!{aid}")
	public String w_article_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "aid") String aid	,
			@RequestParam(value = "code", required = false) String code
			) {
		Integer store_id = SignUtil.getUriStoreId(aid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getAid(aid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/w_article_detail!"+aid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setAid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), Integer.valueOf(map.get("articleId").toString()),wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_article_detail!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					
					//文章的阅读记录添加
					this.articleRecord(store_id, user_id, map);
					
					return this.article_goods(modelMap, request, response,wp, aid,user_id,map,"/ehais/w_article_goods");//整理此软文与商品所有内容
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);

				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_article_detail!"+aid);
				}else{
					System.out.println(aid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				this.shop_encode(request);
				return this.article_goods(modelMap, request, response,wp, aid,user_id,map,"/ehais/w_article_goods");//整理此软文与商品所有内容
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_article_goods";
	}
	*/
	/**
	 * 文章详情信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wp
	 * @param aid
	 * @param user_id
	 * @param map
	 * @param path
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
//	private String article_detail(ModelMap modelMap,	
//			HttpServletRequest request,
//			HttpServletResponse response,
//			WpPublicWithBLOBs wp,
//			String aid,
//			Long user_id,
//			Map<String,Object> map,
//			String path) throws NumberFormatException, Exception{
//		//读取文章信息
//		EHaiArticle article = eHaiArticleMapper.selectByPrimaryKey(Integer.valueOf(map.get("articleId").toString()));
//		//读取商品信息
//		HaiArticleGoodsExample articleGoodsExp = new HaiArticleGoodsExample();
//		articleGoodsExp.createCriteria().andArticleIdEqualTo(Integer.valueOf(map.get("articleId").toString()));
//		List<HaiArticleGoods> articleGoodsList = haiArticleGoodsMapper.selectByExample(articleGoodsExp);
//		Long goodsId = 0L;
//		if(articleGoodsList != null && articleGoodsList.size() > 0){
//			goodsId = articleGoodsList.get(0).getGoodsId();
//						
//			//读取微信的分享信息与链接整理
//			if(goodsId.longValue() > 0){
//				HaiGoods goods = haiGoodsMapper.selectByPrimaryKey(goodsId);
//				modelMap.addAttribute("goods", goods);
//			}
//			
//		}
//		//为了统一使用文章+商品编码，这里需要转一次Sid
//		aid = SignUtil.setSid(Integer.valueOf(map.get("store_id").toString()), Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("userId").toString()), user_id, Integer.valueOf(map.get("articleId").toString()), goodsId, wp.getToken());
//		String link = request.getScheme() + "://" + request.getServerName() + "/w_article_detail!"+aid;
//		
//		modelMap.addAttribute("sid", aid);//将aid变成sid
//		modelMap.addAttribute("article", article);
//		
//		modelMap.addAttribute("defaultimg", defaultimg);
//		modelMap.addAttribute("parentId", map.get("parentId"));
//		modelMap.addAttribute("agencyId", map.get("agencyId"));
//		modelMap.addAttribute("articleId", map.get("articleId"));
//		
//		WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret(), null);
//		signature.setTitle(article.getTitle());
//		signature.setLink(link);
//		signature.setDesc(article.getDescription());
//		signature.setImgUrl(article.getArticleImages());
//		List<String> jsApiList = new ArrayList<String>();
//		jsApiList.add("onMenuShareTimeline");
//		jsApiList.add("onMenuShareAppMessage");
//		jsApiList.add("onMenuShareQQ");
//		jsApiList.add("onMenuShareWeibo");
//		jsApiList.add("onMenuShareQZone");
//		signature.setJsApiList(jsApiList);
//		modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
//		modelMap.addAttribute("w_goods_detail","w_goods_detail!"+aid);
//		modelMap.addAttribute("buynow","buynow!"+aid);
//		return path;
//	}
		
	/**
	 * http://wx123.9351p.com/w_article_goods!1380e1-7c17608f579081_6ed557049551-2096bf3269
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 * @return
	 */
	@RequestMapping("/w_article_goods!{sid}")
	public String w_article_goods(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid	,
			@RequestParam(value = "code", required = false) String code
			) {
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getSid(sid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/w_article_goods!"+sid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setSid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), Integer.valueOf(map.get("articleId").toString()), Long.valueOf(map.get("goodsId").toString()),wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_article_goods!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					
					//文章的阅读记录添加
					this.articleRecord(store_id, user_id, map);
					
					return this.article_goods(modelMap, request, response,wp, sid,user_id,map,"/ehais/w_article_goods");//整理此软文与商品所有内容
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);

				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_article_goods!"+sid);
				}else{
					System.out.println(sid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
//				this.shop_encode(request);
				return this.article_goods(modelMap, request, response,wp, sid,user_id,map,"/ehais/w_article_goods");//整理此软文与商品所有内容
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_article_goods";
	}
	
	/**
	 * 阅读记录增加
	 * @param store_id
	 * @param user_id
	 * @param map
	 */
	private void articleRecord(Integer store_id,Long user_id,Map<String,Object> map){
		HaiArticleRecordExample arExample = new HaiArticleRecordExample();
		arExample.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andArticleIdEqualTo(Integer.valueOf(map.get("articleId").toString()))
		.andUserIdEqualTo(user_id);
		Long c = haiArticleRecordMapper.countByExample(arExample);
		if(c == 0){
			HaiArticleRecord ar = new HaiArticleRecord();
			ar.setStoreId(store_id);
			ar.setAgencyId(Integer.valueOf(map.get("agencyId").toString()));
			ar.setParentId(Long.valueOf(map.get("parentId").toString()));
			ar.setUserId(Long.valueOf(map.get("userId").toString()));
			ar.setArticleId(Integer.valueOf(map.get("articleId").toString()));
			if(map.get("goodsId")!=null)ar.setGoodsId(Long.valueOf(map.get("goodsId").toString()));
			ar.setRecordTime(new Date());
			ar.setReadPraise(EArticleRecordEnum.READ);
			haiArticleRecordMapper.insert(ar);
			eHaiArticleMapper.plusReadCount(Integer.valueOf(map.get("articleId").toString()));
		}
	}
	
	
	/**
	 * http://localhost/w_goods_detail!7f9999873-2b10bbc4af9081_ec8f91bb8b694-1f18cd3269
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 * @return
	 */
	@RequestMapping("/w_goods_detail!{sid}")
	public String w_goods_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid	,
			@RequestParam(value = "code", required = false) String code) {
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0 || store_id == null){
			System.out.println(sid+" store_id is worng");
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getSid(sid,wp.getToken());
			if(map == null){
				System.out.println(sid+" sid is worng");
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(this.isWeiXin(request)){//微信端登录

				if((user_id == null || user_id == 0) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request,wp.getAppid(), "/w_goods_detail!"+sid);
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setSid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), Integer.valueOf(map.get("articleId").toString()), Long.valueOf(map.get("goodsId").toString()),wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_goods_detail!"+newSid;
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){
					return this.goods_detail(modelMap, request, response,wp, sid,user_id,map,"/ehais/w_goods_detail");//整理此软文与商品所有内容
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);

				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_goods_detail!"+sid);
				}else{
					System.out.println(sid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
				
			}else{
				
//				this.shop_encode(request);
				return this.goods_detail(modelMap, request, response,wp, sid,user_id,map,"/ehais/w_goods_detail");//整理此软文与商品所有内容
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_goods_detail";
	}
	
	
	
	@RequestMapping("/w_cart!{cid}")
	public String w_cart(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") String cid) {
		
		Integer store_id = SignUtil.getUriStoreId(cid);
		if(store_id == 0 || store_id == null){
			System.out.println(cid+" store_id is worng");
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getCid(cid,wp.getToken());
			if(map == null){
				System.out.println(cid+" sid is worng");
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			HaiCartExample example = new HaiCartExample();
			example.createCriteria().andUserIdEqualTo((Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID));
//			String session_shop_encode = (String)request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
//			if(StringUtils.isNotEmpty(session_shop_encode))example.or().andSessionIdEqualTo(session_shop_encode);
			example.setOrderByClause("rec_id desc");
			List<HaiCart> cartList = haiCartMapper.selectByExample(example);
			
			modelMap.addAttribute("cartList", cartList);
			modelMap.addAttribute("cid", cid);
			
			String newCid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user_id, wp.getToken());
			String link = request.getScheme() + "://" + request.getServerName() + "/w_shop!"+newCid;
			modelMap.addAttribute("link", link);
			
			this.shareWeiXin(modelMap, request, response, wp, store_id, store.getStoreName(), link, store.getDescription(), store.getStoreLogo());
//			WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret(), null);
//			signature.setTitle(store.getStoreName());
//			signature.setLink(link);
//			signature.setDesc(store.getDescription());
//			signature.setImgUrl(store.getStoreLogo());
//			List<String> jsApiList = new ArrayList<String>();
//			jsApiList.add("onMenuShareTimeline");
//			jsApiList.add("onMenuShareAppMessage");
//			jsApiList.add("onMenuShareQQ");
//			jsApiList.add("onMenuShareWeibo");
//			jsApiList.add("onMenuShareQZone");
//			signature.setJsApiList(jsApiList);
//			modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_cart";
	}
	
	@RequestMapping("/w_check_order!{cid}")
	public String w_check_order(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") String cid) {
		
		Integer store_id = SignUtil.getUriStoreId(cid);
		if(store_id == 0 || store_id == null){
			System.out.println(cid+" store_id is worng");
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getCid(cid,wp.getToken());
			if(map == null){
				System.out.println(cid+" cid is worng");
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			
			String newCid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user_id, wp.getToken());
			String link = request.getScheme() + "://" + request.getServerName() + "/w_shop!"+newCid;
			modelMap.addAttribute("link", link);
			
			this.shareWeiXin(modelMap, request, response, wp, store_id, store.getStoreName(), link, store.getDescription(), store.getStoreLogo());
			
			modelMap.addAttribute("cid", cid);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_check_order";
	}
	
	
	@RequestMapping("/w_address_list")
	public String w_address_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	) {
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_address_list";
	}
	
	
	@RequestMapping("/w_address_add")
	public String w_address_add(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	) {
		try{
			modelMap.addAttribute("model", new HaiUserAddress());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_address_form";
	}
	
	
	@RequestMapping("/w_address_edit")
	public String w_address_edit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	,
			@RequestParam(value = "addressId", required = true) Long addressId
			) {
		
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			HaiUserAddressExample example = new HaiUserAddressExample();
			example.createCriteria()
			.andUserIdEqualTo((Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID))
			.andAddressIdEqualTo(addressId)	;
			List<HaiUserAddress> userAddressList = haiUserAddressMapper.selectByExample(example);
			if(userAddressList == null || userAddressList.size() == 0){
				return "/ehais/wrong";
			}
			
			modelMap.addAttribute("model", userAddressList.get(0));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_address_form";
	}
	
	
	@RequestMapping(value="/w_write_message!{sid}")
	public String w_write_message(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	,
			@PathVariable(value = "sid") String sid
			) {
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0 || store_id == null){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		try{
			modelMap.addAttribute("sid", sid);
			
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getSid(sid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			//读取文章信息
			EHaiArticle article = eHaiArticleMapper.selectByPrimaryKey(Integer.valueOf(map.get("articleId").toString()));
			modelMap.addAttribute("article", article);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_write_message";
	}
	
	
	/**
	 * 商品二维码
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 */
	@RequestMapping(value="/gqr!{sid}")
	public void gqr(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	,
			@PathVariable(value = "sid") String sid
			) {
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0 || store_id == null){
			return ; //错误的链接，跳转商城
		}
		try{
			String qrcode = request.getScheme()+"://"+request.getServerName()+"/w_goods_detail!"+sid;
			
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			@SuppressWarnings("rawtypes")
	        Map hints = new HashMap();
	        
	        //设置UTF-8， 防止中文乱码
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	        //设置二维码四周白色区域的大小
	        hints.put(EncodeHintType.MARGIN,0);
	        //设置二维码的容错性
	        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); 
	        
	        //width:图片完整的宽;height:图片完整的高
	        //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
	        int width = 120;
	        int height = 120;
	        
	        //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
	        BitMatrix bitMatrix = multiFormatWriter.encode(qrcode, BarcodeFormat.QR_CODE, width, height, hints);
	        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//	         
	        
	        OutputStream stream = response.getOutputStream();
	        ImageIO.write(image, "jpg", stream);
	        
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 商家优惠券
	 * http://127.0.0.1/w_coupons!360e4580-0e976401-1df85c02-2235841253-381d74ad39712
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 * @param code
	 * @return
	 */
	@RequestMapping("/w_coupons!{cid}")
	public String w_coupons(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") String cid	,
			@RequestParam(value = "code", required = false) String code) {
		Integer store_id = SignUtil.getUriStoreId(cid);
		if(store_id == 0 || store_id == null){
			System.out.println(cid+" store_id is worng");
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getCid(cid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			if(this.isWeiXin(request)){//微信端登录

				if((user_id == null || user_id == 0) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request,wp.getAppid(), "/w_coupons!"+cid);
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					request.getSession().setAttribute("subscribe", user.getSubscribe());//关注
					String newSid = SignUtil.setSid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), Integer.valueOf(map.get("articleId").toString()), Long.valueOf(map.get("goodsId").toString()),wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_coupons!"+newSid;
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){
					return this.coupons(modelMap, request, response,wp,store_id, cid,user_id,map,"/ehais/w_coupons");//整理此软文与商品所有内容
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
//					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
					cid = SignUtil.setCid(store_id, Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("parentId").toString()), user_id, wp.getToken());
				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_coupons!"+cid);
				}else{
					System.out.println(cid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
				
			}else{
				
//				this.shop_encode(request);
				return this.coupons(modelMap, request, response,wp,store_id, cid,user_id,map,"/ehais/w_coupons");//整理此软文与商品所有内容
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_coupons";
	}
	
	//商家优惠券数据
	private String coupons(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,WpPublicWithBLOBs wp,Integer store_id,String cid,Long user_id,Map<String,Object> map,String path) throws NumberFormatException, Exception{
		
		EHaiStore store = eStoreService.getEStore(store_id);
		modelMap.addAttribute("store", store);
		
		List<HaiCoupons> list = haiCouponsMapper.selectStoreCoupons(store_id);
		modelMap.addAttribute("list", list);
		System.out.println("list:"+list.size());
		
		cid = SignUtil.setCid(store_id, Integer.valueOf(map.get("agencyId").toString()), user_id, user_id, wp.getToken());
		String link = request.getScheme() + "://" + request.getServerName() + "/w_coupons!"+cid;
		this.shareWeiXin(modelMap, request, response, wp, store_id, 
				store.getStoreName()+weixin_coupons_title, //title 
				link, 
				weixin_coupons_desc, //desc
				defaultimg);//img
		
		return path;
	}
	
	
	public static void main(String[] args) throws Exception {
		String sid = SignUtil.setSid(30, 0, 0L, 0L, 0, 0L, "ehais_wxdev");
		String cid = SignUtil.setCid(30, 0, 0L, 0L, "ehais_wxdev");
		System.out.println(sid);
		System.out.println(cid);
	}
}
