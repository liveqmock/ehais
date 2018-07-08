package org.ehais.shop.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.enums.EAdMediaTypeEnum;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.enums.ECouponsTypeEnum;
import org.ehais.enums.EOrderClassifyEnum;
import org.ehais.enums.EOrderSourceEnum;
import org.ehais.enums.EOrderStatusEnum;
import org.ehais.enums.EPayEnum;
import org.ehais.enums.EPayStatusEnum;
import org.ehais.enums.EUserTypeEnum;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.mapper.HaiStoreStatisticsMapper;
import org.ehais.epublic.model.EHaiAdExample;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.epublic.model.HaiStoreStatistics;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.epublic.service.WeiXinPayService;
import org.ehais.shop.mapper.HaiAdMapper;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiCouponsMapper;
import org.ehais.shop.mapper.HaiGoodsAttrMapper;
import org.ehais.shop.mapper.HaiGoodsGalleryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiOrderGoodsMapper;
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdExample;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiCoupons;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.model.HaiGoodsGalleryExample;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.service.OrderInfoService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.Bean2Utils;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.model.WeiXinTemplateMessage;
import org.ehais.weixin.model.WeiXinWCPay;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

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
	private HaiCouponsMapper haiCouponsMapper;
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;
	@Autowired
	private HaiStoreStatisticsMapper haiStoreStatisticsMapper;
	@Autowired
	private EWPPublicService eWPPublicService;
	@Autowired
	private EStoreService eStoreService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private WeiXinPayService weiXinPayService;
	
	private String prefix_order_dining = ResourceUtil.getProValue("prefix.order.dining");
	
	
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
	
	
	
	
	@ResponseBody
	@RequestMapping("/wxapp.eshop.vue.jroll.v1")
	public String e_shop_vue_jroll_v1(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id){
		
		
		
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(0);
		Map<String, Object> map = new HashMap<String,Object>();
		
		try {
//			Integer session_store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//			if (session_store_id != store_id) {
//				rm.setMsg("session wrong");
//				return this.writeJson(rm);
//			}
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			
			map.put("payModule", store.getPayModule());
			
			HaiAdExample adExample = new HaiAdExample();
			HaiAdExample.Criteria c = adExample.createCriteria();
			c.andIsMobileEqualTo(1)
			.andIsVoidEqualTo(1)
			.andMediaTypeEqualTo(EAdMediaTypeEnum.carousel_mobile);
			//如果存在代理有广告，就使用代理的广告
			if(store.getPartnerId() != null && store.getPartnerId() > 0){
				c.andPartnerIdEqualTo(store.getPartnerId());
			}else{
				c.andStoreIdEqualTo(store_id);
			}
			List<HaiAd> ad_List = haiAdMapper.selectByExample(adExample);
			List<Map<String,String>> listAd = new ArrayList<Map<String,String>>();
			for (HaiAd ad : ad_List) {
				Map<String,String> mapAd = new HashMap<String,String>();
				mapAd.put("adName",ad.getAdName());
				mapAd.put("adPic",ad.getAdPic());
				mapAd.put("adLink",ad.getAdLink());
				listAd.add(mapAd);
			}
			map.put("listAd", listAd);
			
			HaiCategoryExample cExp = new HaiCategoryExample();
			cExp.createCriteria().andStoreIdEqualTo(store_id);
			cExp.setOrderByClause("sort_order asc");
			List<HaiCategory> list_Category = haiCategoryMapper.selectByExample(cExp);
			List<Map<String,String>> listCategory = new ArrayList<Map<String,String>>();
			for (HaiCategory cg : list_Category) {
				Map<String,String> mapCategory = new HashMap<String,String>();
				mapCategory.put("catId", cg.getCatId().toString());
				mapCategory.put("catName", cg.getCatName());
				listCategory.add(mapCategory);
			}
			map.put("listCategory", listCategory);
			
			HaiGoodsExample gExp = new HaiGoodsExample();
			gExp.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andIsDeleteEqualTo(false)
			.andIsOnSaleEqualTo(true);
			gExp.setOrderByClause("sort_order asc");
			List<HaiGoods> list_Goods = haiGoodsMapper.selectByExample(gExp);
			List<Map<String,Object>> listGoods = new ArrayList<Map<String,Object>>();
			for (HaiGoods haiGoods : list_Goods) {
				String goodsUrl = SignUtil.setGid(store_id, 0, 0L, 0L, haiGoods.getGoodsId(), wp.getToken());
				
				Map<String,Object> mapGoods = new HashMap<String,Object>();
				mapGoods.put("goodsId", haiGoods.getGoodsId().toString());
				mapGoods.put("goodsName", haiGoods.getGoodsName());
				mapGoods.put("goodsThumb", haiGoods.getGoodsThumb());
				mapGoods.put("marketPrice", haiGoods.getMarketPrice());
				mapGoods.put("shopPrice", haiGoods.getShopPrice());
				mapGoods.put("goodsUrl", goodsUrl);
				mapGoods.put("isHot", haiGoods.getIsHot());
//				mapGoods.put("badge", 0);
				listGoods.add(mapGoods);
			}
			
			map.put("listGoods", listGoods);
			
			//获取此商家是否有优惠券
			long countCoupons = haiCouponsMapper.countStoreCoupons(store_id);
			map.put("countCoupons", countCoupons);//是否有可用优惠券
			
			rm.setCode(1);
			rm.setMap(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return this.writeJson(rm);
	}
	
	@ResponseBody
	@RequestMapping("/wxapp.eshop.vue.submit.order.v1")
	public String e_shop_vue_submit_order_v1(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "cid", required = true) String cid,
			@RequestParam(value = "couponsId", required = true) Integer couponsId,//优惠券ID
			@RequestParam(value = "tPay", required = true) String tPay,
			@RequestParam(value = "cart", required = true) String cart,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "consignee", required = false) String consignee,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "wTotal", required = true) Integer wTotal,//总价格，经后台运算与前端的匹配
			@RequestParam(value = "wPayAmount", required = true) Integer wPayAmount//支付价格，经后台运算与前端的匹配
			){
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		try {
			//判断购物车是否有值
			if(StringUtils.isEmpty(cart)){
				rm.setMsg("cart is wrong");
				return this.writeJson(rm);
			}
			Integer storeid = SignUtil.getUriStoreId(cid);
			if(storeid == 0 || storeid != store_id){
				rm.setMsg("store is wrong");
				return this.writeJson(rm);
			}
			//效验地址商号与session商号是否一致
//			Integer sStoreId = (Integer) request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//			if(store_id.intValue() != sStoreId.intValue()){
//				rm.setMsg("ses store is wrong");
//				return this.writeJson(rm);
//			}
			EHaiStore store = eStoreService.getEStore(store_id);
			if(store == null){
				rm.setMsg("store info is empty");
				return this.writeJson(rm);
			}
			
			//解析地址效验
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getCid(cid,wp.getToken());
//			Long sUserId = (Long) request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//			if(sUserId.longValue() != Long.valueOf(map.get("userId").toString()).longValue()){
//				rm.setMsg("ses user is wrong");
//				return this.writeJson(rm);
//			}
			
			//获取用户编号效验
			EHaiUsers users = eHaiUsersMapper.selectByPrimaryKey(Long.valueOf(map.get("userId").toString()));
			if(users == null){
				rm.setMsg("user is wrong");
				return this.writeJson(rm);
			}
			
			//效验购物车数量
			JSONObject cartObj = JSONObject.fromObject(cart);
			if(cartObj.size() == 0){
				rm.setMsg("cart is empty");
				return this.writeJson(rm);
			}
			Iterator iterator = cartObj.keys();
			List<Long> goodsIds = new ArrayList<Long>();
			Integer total = 0;
			Integer badge = 0;
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				JSONObject goodsObj = cartObj.getJSONObject(key);
				goodsIds.add(Long.valueOf(key));
				
				total += goodsObj.getInt("price") * goodsObj.getInt("badge");
				badge += goodsObj.getInt("badge");
			}
			
			//参数中的商品数组价格与传递价格不一致的情况v2...........
			if(total.intValue() != wTotal.intValue()){
				rm.setMsg("you are black person");
				return this.writeJson(rm);
			}
			
			//读取商品信息
			HaiGoodsExample goodsExample = new HaiGoodsExample();
			goodsExample.createCriteria().andStoreIdEqualTo(store_id).andGoodsIdIn(goodsIds);
			List<HaiGoods> listGoods = haiGoodsMapper.selectByExample(goodsExample);
			//数量效验
			if(cartObj.size()!=listGoods.size()){
				rm.setMsg("cart quantity is wrong");
				return this.writeJson(rm);
			}
			Integer amount = 0;
			StringBuffer sb = new StringBuffer();
			for (HaiGoods haiGoods : listGoods) {
				JSONObject goodsObj = cartObj.getJSONObject(haiGoods.getGoodsId().toString());
				amount += haiGoods.getShopPrice() * goodsObj.getInt("badge");
				sb.append(haiGoods.getGoodsName()+"【"+goodsObj.getInt("badge")+"】 ");
			}
			
			if(total.intValue() != amount.intValue()){
				rm.setMsg("you are black person");
				return this.writeJson(rm);
			}
			
			if(couponsId != null && couponsId > 0){
				HaiCoupons haiCoupons = haiCouponsMapper.selectByPrimaryKey(couponsId);
				if(haiCoupons == null ){
					rm.setMsg("优惠券无效");
					return this.writeJson(rm);
				}
				if(haiCoupons.getUseCount() != null && haiCoupons.getCouponsQuantity() != null && haiCoupons.getCouponsQuantity() > 0 && haiCoupons.getUseCount().intValue() >= haiCoupons.getCouponsQuantity().intValue()){
					rm.setMsg("优惠券已使用满额，请刷新重新下单");
					return this.writeJson(rm);
				}
				
				//带优惠券的价格效验v2版本.......................
				//获取优惠券，判断优惠券ID是否正确
				List<HaiCoupons> list = haiCouponsMapper.selectStoreCoupons(store_id);
				
				Integer _amount = 0;//优惠的金额，以分为单位判断
				Integer _couponsId = 0;
				for (HaiCoupons hcp : list) {
					if(total.intValue() >= hcp.getQuota().intValue()){
						if(hcp.getCouponsType().equals(ECouponsTypeEnum.reduce)){//立减优惠
							if(hcp.getDiscounts().intValue() * 100 > _amount.intValue()){
								_amount = hcp.getDiscounts() * 100;
								_couponsId = hcp.getCouponsId();
							}
						}else if(hcp.getCouponsType().equals(ECouponsTypeEnum.rebate)){//折扣优惠
							if(total.intValue() * (100 - hcp.getDiscounts().intValue()) / 100 > _amount.intValue()){
								_amount = total.intValue() * (100 - hcp.getDiscounts().intValue()) / 100;
								_couponsId = hcp.getCouponsId();
							}
						}
					}
				}
				//判断优惠券的价格是否正确
				if((wTotal.intValue() - wPayAmount.intValue()) != _amount.intValue() ){
					rm.setMsg("优惠券金额不正确");
					return this.writeJson(rm);
				}
				if(_couponsId.intValue() != couponsId.intValue()){
					rm.setMsg("优惠券使用有误");
					return this.writeJson(rm);
				}
				//更新支付金额为优惠后的金额
				amount = amount -_amount;
			}
			
			Date date = new Date();
			//插入订单主表
			HaiOrderInfoWithBLOBs orderInfo = new HaiOrderInfoWithBLOBs();
			orderInfoService.setDefaultOrder(orderInfo,date,store_id);//公共设置订单的默认值
			
			String orderSn = prefix_order_dining + DateUtil.formatDate(date, DateUtil.FORMATSTR_4) + map.get("userId").toString() + ECommon.nonceInt(4);
			orderInfo.setOrderSn(orderSn);
			orderInfo.setUserId(Long.valueOf(map.get("userId").toString()));
			orderInfo.setOrderStatus(EOrderStatusEnum.init);
			if(tPay.equals(EPayEnum.cash)){
				orderInfo.setOrderStatus(EOrderStatusEnum.success);//现金支付，不需要理订单状态
				orderInfo.setPayTime(System.currentTimeMillis());
				orderInfo.setPayStatus(EPayStatusEnum.cash);
				orderInfo.setPayName("现金支付");
				
				//流水号+1
				int daySerialCount = haiOrderInfoMapper.daySerialNumber(store_id);
				orderInfo.setDaySerialNumber(daySerialCount+1);
				
			}else if(tPay.equals(EPayEnum.weixin)){
				orderInfo.setPayName("微信支付");
			}
			
			orderInfo.setPostscript(message!=null?message:"");//订单附言
			orderInfo.setGoodsAmount(total);//商品价格
			orderInfo.setOrderAmount(amount);//订单价格
			orderInfo.setAddTime(date);
			orderInfo.setOrderSource(EOrderSourceEnum.dining_take_out);
			orderInfo.setGoodsDesc(sb.toString());
			orderInfo.setClassify(EOrderClassifyEnum.shop);
			orderInfo.setSid(cid);
			orderInfo.setConsignee(consignee);
			orderInfo.setMobile(mobile);
			orderInfo.setAddress(address);
			//保存优惠券编号
			if(couponsId != null && couponsId > 0){
				orderInfo.setCouponsId(couponsId);
				orderInfo.setCouponsUserId(0L);
				orderInfo.setDiscount(wTotal.intValue() - wPayAmount.intValue());
			}
			
			haiOrderInfoMapper.insert(orderInfo);
			Long order_id = orderInfo.getOrderId();
			
			if(couponsId != null && couponsId > 0){//更新优惠券记录使用数量				
				haiOrderInfoMapper.updateCouponsUseCount(store_id, couponsId);
			}
			
			List<HaiOrderGoods> orderGoodsList = new ArrayList<HaiOrderGoods>();
			for (HaiGoods haiGoods : listGoods) {
				HaiOrderGoods orderGoods = new HaiOrderGoods();
				JSONObject goodsObj = cartObj.getJSONObject(haiGoods.getGoodsId().toString());
				orderGoods.setOrderId(order_id);
				orderGoods.setGoodsId(haiGoods.getGoodsId());
				orderGoods.setGoodsName(haiGoods.getGoodsName());
				orderGoods.setGoodsSn(haiGoods.getGoodsSn());
				orderGoods.setGoodsNumber(goodsObj.getInt("badge"));
				orderGoods.setMarketPrice(haiGoods.getMarketPrice());
				orderGoods.setGoodsPrice(haiGoods.getShopPrice());
				orderGoods.setGoodsThumb(haiGoods.getOriginalImg());
				orderGoods.setProductId(0L);
				orderGoods.setParentId(0L);
				orderGoods.setIsGift(Short.valueOf("0"));
				orderGoods.setGoodsAttrId("");
				orderGoods.setGoodsAttr("");
				orderGoods.setExtensionCode("");
				orderGoods.setIsReal(true);
				orderGoods.setSendNumber(0);
				orderGoods.setAgencyId(Integer.valueOf(map.get("agencyId").toString()));
				orderGoods.setArticleId(0);
				orderGoods.setSellerUserId(Long.valueOf(map.get("parentId").toString()));
				orderGoodsList.add(orderGoods);
			}
			
			int codeBatch = haiOrderGoodsMapper.insertBatch(orderGoodsList);
			
			Map<String,Object> rsMap = new HashMap<String,Object>();
			
			if(tPay.equals(EPayEnum.weixin)){
				
				//跳转支付信息返回
				WeiXinWCPay cpay = weiXinPayService.WeiXinPayApi(request,cid, 
						users.getOpenid(), 
						orderSn, 
						amount, 
						"易海司微信支付订单", 
						"hai_order", 
						order_id,EOrderClassifyEnum.shop);
				
				rsMap.put("WeiXinWCPay", cpay);
				
			}else{
				
				HaiStoreStatistics storeStatistics = haiStoreStatisticsMapper.selectByPrimaryKey(store_id);
				if(storeStatistics == null){
					storeStatistics = new HaiStoreStatistics();
					storeStatistics.setStoreId(store_id);
				}
				storeStatistics.setCashAmount((storeStatistics.getCashAmount() == null ? 0 : storeStatistics.getCashAmount()) + amount);
				storeStatistics.setCashQuantity((storeStatistics.getCashQuantity() == null ? 0 : storeStatistics.getCashQuantity()) + 1);
				haiStoreStatisticsMapper.updateByPrimaryKey(storeStatistics);
				
				//推送消息
				//给用户推送消息
				String diningUserTemp = this.diningUserTemplateMessage(request, wp, store, orderInfo, users, map, store_id, date, sb);
				
				EHaiUsersExample userExample = new EHaiUsersExample();
				userExample.createCriteria().andStoreIdEqualTo(store_id).andUserTypeEqualTo(EUserTypeEnum.dining);
				List<EHaiUsers> listUsers = eHaiUsersMapper.selectByExample(userExample);
				if(listUsers != null && listUsers.size() > 0){
					//给商家发信息
					String diningStoreTemp = this.diningStoreTemplateMessage(request, wp, store, orderInfo, listUsers.get(0), map, store_id, date, sb);
					System.out.println(diningStoreTemp);
				}
			}
			
			//读取广告
			HaiAdExample adExample = new HaiAdExample();
			HaiAdExample.Criteria c = adExample.createCriteria();
			c.andIsMobileEqualTo(1)
			.andIsVoidEqualTo(1)
			.andMediaTypeEqualTo(EAdMediaTypeEnum.order_mobile);
			//如果存在代理有广告，就使用代理的广告
			if(store.getPartnerId() != null && store.getPartnerId() > 0){
				c.andPartnerIdEqualTo(store.getPartnerId());
			}else{
				c.andStoreIdEqualTo(store_id);
			}
			List<HaiAd> ad_List = haiAdMapper.selectByExample(adExample);
//			List<Map<String,String>> listAd = new ArrayList<Map<String,String>>();
//			for (HaiAd ad : ad_List) {
//				Map<String,String> mapAd = new HashMap<String,String>();
//				mapAd.put("adName",ad.getAdName());
//				mapAd.put("adPic",ad.getAdPic());
//				mapAd.put("adLink",ad.getAdLink());
//				listAd.add(mapAd);
//			}
//			rsMap.put("listAd", listAd);
			
			if(ad_List.size() > 0) {
				map.put("omap", ad_List.get(0).getAdPic());//order_mobile_ad_pic
			}
			
			rm.setMap(rsMap);
			
			rm.setCode(1);
			rm.setModel(orderInfo);
			rm.setMsg("订单提交成功");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return this.writeJson(rm);
	}
	
	
	
	/**
	 * 点餐模式给用户发送消息
	 * @param request
	 * @param notifyPay
	 * @param orderInfo
	 * @param wpPublic
	 * @param store_id
	 * @throws Exception 
	 */
	private String diningUserTemplateMessage(HttpServletRequest request,
			WpPublicWithBLOBs wp,
			EHaiStore store,
			HaiOrderInfoWithBLOBs orderInfo,
			EHaiUsers users,
			Map<String,Object> map,
			Integer store_id,
			Date date,
			StringBuffer sb
			) throws Exception{
		WeiXinTemplateMessage template = new WeiXinTemplateMessage();
		template.setTemplate_id("LFdLrMKmvqCgJ3sbIB2cbaZsEChQmFwfnvpn1VrbhOI");//订单支付成功通知
		template.setTouser(users.getOpenid());
		template.setUrl(request.getScheme()+"://"+request.getServerName()+"/dining_user_order_detail!"+SignUtil.setOid(store_id, orderInfo.getOrderId(), orderInfo.getOrderSn(), orderInfo.getUserId(), users.getOpenid(), wp.getToken()));
		template.setTopcolor("#FF0000");
		
		Map<String,Object> mapTemplateUser = new HashMap<String,Object>();
        
		Map<String,String> first = new HashMap<String,String>();
		first.put("value", "客官您好，你刚下的订单已成功交结掌柜，请等待送餐！");
		first.put("color", "#173177");
		mapTemplateUser.put("first", first);
		
		Map<String,String> keyword1 = new HashMap<String,String>();
		keyword1.put("value", store.getStoreName());
		keyword1.put("color", "#173177");
		mapTemplateUser.put("keyword1", keyword1);
		
		Map<String,String> keyword2 = new HashMap<String,String>();
		keyword2.put("value", DateUtil.formatDate(date, DateUtil.FORMATSTR_2));
		keyword2.put("color", "#173177");
		mapTemplateUser.put("keyword2", keyword2);
		
		Map<String,String> keyword3 = new HashMap<String,String>();
		keyword3.put("value", String.format("%.2f", orderInfo.getGoodsAmount().doubleValue() / 100));
		keyword3.put("color", "#173177");
		mapTemplateUser.put("keyword3", keyword3);
		
		Map<String,String> remark = new HashMap<String,String>();
		remark.put("value", "陛下，您点了"+sb.toString()+"，掌柜已吩咐厨房开火了，请稍等！");
		remark.put("color", "#173177");
		mapTemplateUser.put("remark", remark);
		
		template.setData(mapTemplateUser);
		
		return WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(store_id, wp.getAppid(), wp.getSecret()).getAccess_token(), template);
		
	}
	
	/**
	 * 点餐模式给商户发送消息
	 * @throws Exception 
	 */
	private String diningStoreTemplateMessage(HttpServletRequest request,
			WpPublicWithBLOBs wp,
			EHaiStore store,
			HaiOrderInfoWithBLOBs orderInfo,
			EHaiUsers users,
			Map<String,Object> map,
			Integer store_id,
			Date date,
			StringBuffer sb) throws Exception{
		WeiXinTemplateMessage templateStore = new WeiXinTemplateMessage();
		templateStore.setTemplate_id("Vlmhl4el_dW2Zcq_5cf9KkgRlDx7XI5G_XLuJQ4f2gM");//订单支付成功通知
		templateStore.setTouser(users.getOpenid());
		templateStore.setUrl(request.getScheme()+"://"+request.getServerName()+"/dining_store_order_detail!"+SignUtil.setOid(store_id, orderInfo.getOrderId(), orderInfo.getOrderSn(), orderInfo.getUserId(), users.getOpenid(), wp.getToken()));
		templateStore.setTopcolor("#FF0000");
		
		Map<String,Object> mapTemplateStore = new HashMap<String,Object>();
        
		Map<String,String> firstStore = new HashMap<String,String>();
		firstStore.put("value", map.get("tableNo").toString() + "餐台/房的客官已下了订单");
		firstStore.put("color", "#173177");
		mapTemplateStore.put("first", firstStore);
		
		Map<String,String> keyword1Store = new HashMap<String,String>();
		keyword1Store.put("value", orderInfo.getOrderSn());
		keyword1Store.put("color", "#173177");
		mapTemplateStore.put("keyword1", keyword1Store);
		
		Map<String,String> keyword2Store = new HashMap<String,String>();
		keyword2Store.put("value", DateUtil.formatDate(date, DateUtil.FORMATSTR_2));
		keyword2Store.put("color", "#173177");
		mapTemplateStore.put("keyword2", keyword2Store);
		
		Map<String,String> keyword3Store = new HashMap<String,String>();
		keyword3Store.put("value", String.format("%.2f", orderInfo.getGoodsAmount().doubleValue() / 100));
		keyword3Store.put("color", "#173177");
		mapTemplateStore.put("keyword3", keyword3Store);
		
		Map<String,String> keyword4Store = new HashMap<String,String>();
		keyword4Store.put("value", "现金付款");
		keyword4Store.put("color", "#173177");
		mapTemplateStore.put("keyword4", keyword4Store);
		
		Map<String,String> remarkStore = new HashMap<String,String>();
		remarkStore.put("value", "客官点了"+sb.toString()+"，掌柜您可以为客官出菜了！");
		remarkStore.put("color", "#173177");
		mapTemplateStore.put("remark", remarkStore);
		
		templateStore.setData(mapTemplateStore);
		
		return WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(store_id, wp.getAppid(), wp.getSecret()).getAccess_token(), templateStore);
		
	}
	
	
	
}
