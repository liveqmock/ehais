package org.ehais.shop.controller.mall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.ENavClassifyEnum;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.mapper.HaiAdMapper;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiNavMapper;
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdExample;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiNav;
import org.ehais.shop.model.HaiNavExample;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;


@Controller
@RequestMapping("/")
public class EhaisMallController extends EhaisCommonController {

	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiAdMapper haiAdMapper;
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	@Autowired
	private HaiNavMapper haiNavMapper;
	
	

	private String mallData(ModelMap modelMap,
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
		
		HaiAdExample adExample = new HaiAdExample();
		adExample.createCriteria()
		.andStoreIdEqualTo(store.getStoreId())
		.andIsMobileEqualTo(1)
		.andIsVoidEqualTo(1);
		adExample.setOrderByClause("sort asc");
		List<HaiAd> adList = haiAdMapper.selectByExample(adExample);
		
		HaiNavExample navExample = new HaiNavExample();
		navExample.createCriteria()
		.andStoreIdEqualTo(store.getStoreId())
		.andIsMobileEqualTo(true)
		.andIsValidEqualTo(true)
		.andClassifyEqualTo(ENavClassifyEnum.MOBILE_MALL_INDEX);
		navExample.setOrderByClause("sort asc");
		List<HaiNav> navList = haiNavMapper.selectByExample(navExample);
		
		
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
		modelMap.addAttribute("navList", navList);
		
		modelMap.addAttribute("goodsList", goodsList);
		
		String newCid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user_id , wp.getToken());
		String link = request.getScheme() + "://" + request.getServerName() + "/w_shop!"+newCid;
		
		this.shareWeiXin(modelMap, request, response, wp, store_id, store.getStoreName(), link, store.getDescription(), store.getStoreLogo());
		
		return "/mall/index_fresh";
	}
	
	//无需要签名进入的商城界面,default_store_id
	@RequestMapping("/e_mall")
	public String e_mall(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ,
			@RequestParam(value = "code", required = false) String code
			) {	
		
		return "/mall/index_fresh";
	}
	
	
	
	//http://127.0.0.1/w_mall!afa5890-062c0c01-12b7b002-2c960b1253-37ca2179f7b3b
	@RequestMapping("/w_mall!{cid}")
	public String w_mall(ModelMap modelMap,
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
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/w_mall!"+cid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_mall!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					
					return this.mallData(modelMap, request, response, wp, store, cid, store_id, user_id, map);
					
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_mall!"+cid);
				}else{
					System.out.println(cid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				return this.mallData(modelMap, request, response, wp, store, cid, store_id, user_id, map);
				
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		
		return "redirect:"+website;
		
	}
	
	
	

	private String categoryData(ModelMap modelMap,
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
		modelMap.addAttribute("defaultimg", defaultimg);
		
		HaiCategoryExample categoryExample = new HaiCategoryExample();
		categoryExample.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andIsShowEqualTo(true);
		categoryExample.setOrderByClause("sort_order asc");
		List<HaiCategory> category_list = haiCategoryMapper.selectByExample(categoryExample);
		List<Map<String,Object>> categoryList = new ArrayList<Map<String,Object>>();
		//整理返回一二层的分类
		for (HaiCategory haiCategory : category_list) {
			if(haiCategory.getParentId().intValue() == 0){
				categoryList.add(this.dealCategory(wp,store_id,user_id,map,haiCategory));//加载一级分类
				for (HaiCategory haiCategory2 : category_list) {
					if(haiCategory2.getParentId().intValue() == haiCategory.getCatId().intValue()){
						categoryList.add(this.dealCategory(wp,store_id,user_id,map,haiCategory2));//加载二级分类
					}
				}
			}
		}
		
		
		modelMap.addAttribute("categoryList", JSONArray.fromObject(categoryList).toString());
		
		String newCid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user_id , wp.getToken());
		String link = request.getScheme() + "://" + request.getServerName() + "/w_shop!"+newCid;
		
		this.shareWeiXin(modelMap, request, response, wp, store_id, store.getStoreName(), link, store.getDescription(), store.getStoreLogo());
		
		
		return "/mall/category_fresh";
	}
	
	
	private Map<String,Object> dealCategory(
			WpPublicWithBLOBs wp ,
			Integer store_id,
			Long user_id ,
			Map<String,Object> map,
			HaiCategory haiCategory) throws Exception{

		Map<String,Object> catMap = new HashMap<String,Object>();
		catMap.put("catId",haiCategory.getCatId());
		catMap.put("parentId",haiCategory.getParentId());
		catMap.put("catName",haiCategory.getCatName());
		catMap.put("thumb",haiCategory.getThumb());
		
		catMap.put("cid", SignUtil.setAid(store_id, Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user_id, haiCategory.getCatId(), wp.getToken()));
	

		return catMap;
	}
	
	
	//http://127.0.0.1/w_category!afa5890-062c0c01-12b7b002-2c960b1253-37ca2179f7b3b
	@RequestMapping("/w_category!{cid}")
	public String w_category(ModelMap modelMap,
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
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/w_category!"+cid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_category!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					
					return this.categoryData(modelMap, request, response, wp, store, cid, store_id, user_id, map);
					
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_category!"+cid);
				}else{
					System.out.println(cid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				return this.categoryData(modelMap, request, response, wp, store, cid, store_id, user_id, map);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:"+website;
	}
	
	
	private String goodsListData(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ,
			WpPublicWithBLOBs wp ,
			EHaiStore store,
			String aid,
			Integer store_id,
			Long user_id ,
			Map<String,Object> map ) throws Exception{
		modelMap.addAttribute("store", store);
		modelMap.addAttribute("aid", aid);
		modelMap.addAttribute("defaultimg", defaultimg);
		
		//获取当前二级分类的所有三级分类
		Integer catId = Integer.valueOf(map.get("articleId").toString());
		modelMap.addAttribute("catId", catId);
		
		HaiCategoryExample cateExp = new HaiCategoryExample();
		cateExp.createCriteria().andParentIdEqualTo(catId);
		cateExp.or().andCatIdEqualTo(catId);
		List<HaiCategory> cateList = haiCategoryMapper.selectByExample(cateExp);
		modelMap.addAttribute("cateList", JSONArray.fromObject(cateList).toString());
		
		
		String newCid = SignUtil.setAid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user_id ,Integer.valueOf(map.get("articleId").toString()), wp.getToken());
		String link = request.getScheme() + "://" + request.getServerName() + "/w_goods_list!"+newCid;
		
		this.shareWeiXin(modelMap, request, response, wp, store_id, store.getStoreName(), link, store.getDescription(), store.getStoreLogo());
		
		
		
		return "/mall/goods_list_fresh";
	}
	
	private Map<String,Object> dealGoods(WpPublicWithBLOBs wp ,
			Integer store_id,
			Long user_id ,
			Map<String,Object> map,
			HaiGoods goods) throws Exception{
		Map<String,Object> gMap = new HashMap<String,Object>();
		gMap.put("goodsName", goods.getGoodsName());
		gMap.put("actDesc", goods.getActDesc());
		gMap.put("goodsThumb", goods.getGoodsThumb());
		gMap.put("shopPrice", goods.getShopPrice());
		gMap.put("marketPrice", goods.getMarketPrice());
		gMap.put("goodsUrl", SignUtil.setSid(store_id, Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("userId").toString()), user_id, 0 , goods.getGoodsId(), wp.getToken()));
		return gMap;
	}
	
	
	@RequestMapping("/w_goods_list!{aid}")
	public String w_goods_list(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ,
			@PathVariable(value = "aid") String aid,
			@RequestParam(value = "code", required = false) String code
			) {	
		Integer store_id = SignUtil.getUriStoreId(aid);
		if(store_id == 0 || store_id == null){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getAid(aid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/w_goods_list!"+aid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setAid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(),Integer.valueOf(map.get("articleId").toString()), wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_goods_list!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					
					return this.goodsListData(modelMap, request, response, wp, store, aid, store_id, user_id, map);
					
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_goods_list!"+aid);
				}else{
					System.out.println(aid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				return this.goodsListData(modelMap, request, response, wp, store, aid, store_id, user_id, map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "redirect:"+website;
	}
	
	/**
	 * 适应三级分类的第二与第三级ID调用
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param condition
	 * @param catId 第二或第三级的分类
	 * @param sort 排序方式：price,quantity,is_new
	 * @param adsc 升降级 asc desc
	 * @param keyword 关键字
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/goods_list_cat_id!{aid}")
	public String goods_list_cat_id(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ,
			@PathVariable(value = "aid") String aid,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "adsc", required = false) String adsc,
			@RequestParam(value = "keyword", required = false) String keyword){
		ReturnObject<Map<String,Object>> rm = new ReturnObject<Map<String,Object>>();
		rm.setCode(0);
		Integer store_id = SignUtil.getUriStoreId(aid);
		if(store_id == 0 || store_id == null){
			rm.setMsg("错误链接：1001");
			return this.writeJson(rm);
		}
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getAid(aid,wp.getToken());
			if(map == null){
				rm.setMsg("错误链接：1002");
				return this.writeJson(rm);
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			//根据当前ID获取下级分类
			HaiCategoryExample cateExp = new HaiCategoryExample();
			cateExp.createCriteria().andParentIdEqualTo(catId);
			List<HaiCategory> cateList = haiCategoryMapper.selectByExample(cateExp);
			List<Integer> catIds = new ArrayList<Integer>();
			catIds.add(catId);
			for (HaiCategory haiCategory : cateList) {
				catIds.add(haiCategory.getCatId());
			}
			if(StringUtils.isBlank(adsc))adsc = "desc";
			HaiGoodsExample goodsExp = new HaiGoodsExample();
			HaiGoodsExample.Criteria c = goodsExp.createCriteria();
			c.andCatIdIn(catIds);
			
			
			
			String orderByClause = "update_date "+adsc;
			Integer is_new = null;
			if(sort!=null && sort.equals("price")){
				orderByClause = "shop_price "+adsc;
			}else if(sort!=null && sort.equals("salecount")){
				orderByClause = "sale_count "+adsc;
			}else if(sort!=null && sort.equals("isnew")){
				is_new = 1;
			}
			
			
			List<HaiGoods> listGoods = haiGoodsMapper.selectFilterCateKeyword(store_id, 
					StringUtils.join(catIds.toArray(), ","), 
					is_new,
					keyword, 
					orderByClause, 
					condition.getStart(), 
					condition.getRows());
			
			List<Map<String,Object>> gMapList = new ArrayList<Map<String,Object>>();
			for (HaiGoods haiGoods : listGoods) {
				gMapList.add(this.dealGoods(wp, store_id, user_id, map, haiGoods));
			}
			Long total = haiGoodsMapper.countFilterCateKeyword(store_id, 
					StringUtils.join(catIds.toArray(), ","), 
					is_new,
					keyword);
			
			rm.setTotal(total);
			rm.setRows(gMapList);
			rm.setMsg("success");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
//		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 0);this.put("msg", "网络延时");}});
		return this.writeJson(rm);
	}
	
	
}
