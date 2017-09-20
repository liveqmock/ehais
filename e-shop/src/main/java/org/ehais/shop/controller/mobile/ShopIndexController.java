package org.ehais.shop.controller.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.mapper.HaiAdMapper;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdExample;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.WeiXinSignature;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/")
public class ShopIndexController extends EhaisCommonController{

	public static String website = ResourceUtil.getProValue("website");
	public static String defaultimg = ResourceUtil.getProValue("defaultimg");
	
	String themes = "ehais";
	
	@Autowired
	private HaiAdMapper haiAdMapper;
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	
	//http://127.0.0.1/shop!2dcc910-0657d201-121d2202-2cd64c1253-3a55db12b9290
	@RequestMapping("/shop!{sid}")
	public String shop(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ,
			@PathVariable(value = "sid") String sid,
			@RequestParam(value = "code", required = false) String code
			) {	
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			if(StringUtils.isNotBlank(store.getTheme()))themes = store.getTheme();
			Map<String,Object> map = SignUtil.getCid(sid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
//			if(this.isWeiXin(request)){//微信端登录
//				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
//					return this.redirect_wx_authorize(request , wp.getAppid() , "/shop!"+sid);
//				}else if(StringUtils.isNotEmpty(code)){
//					System.out.println(code);
//					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
//					String newSid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), wp.getToken());
//					String link = request.getScheme() + "://" + request.getServerName() + "/shop!"+newSid;
//					System.out.println("code:"+link);
//					return "redirect:"+link;
//				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
//					
//				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
//					System.out.println("user_id != map.userId condition is worng");
//					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
//				    return this.redirect_wx_authorize(request,wp.getAppid(), "/shop!"+sid);
//				}else{
//					System.out.println(sid+" condition is worng");
//					return "redirect:"+website; //错误的链接，跳转商城
//				}
//			}
			
			HaiAdExample adExample = new HaiAdExample();
			adExample.createCriteria().andStoreIdEqualTo(store_id).andIsVoidEqualTo(1);
			List<HaiAd> adList = haiAdMapper.selectByExample(adExample);
			
			HaiCategoryExample categoryExample = new HaiCategoryExample();
			categoryExample.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andIsShowEqualTo(true);
			categoryExample.setOrderByClause("sort_order asc");
			List<HaiCategory> categoryList = haiCategoryMapper.selectByExample(categoryExample);
			
			HaiGoodsExample goodsExample = new HaiGoodsExample();
			goodsExample.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andIsOnSaleEqualTo(true);
			goodsExample.setLimitStart(0);
			goodsExample.setLimitEnd(20);
			goodsExample.setOrderByClause("sort_order asc");
			List<HaiGoods> goodsList = haiGoodsMapper.selectByExample(goodsExample);
			for (HaiGoods haiGoods : goodsList) {
				haiGoods.setGoodsUrl("goods!"+SignUtil.setGid(store_id, Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("userId").toString()), user_id, haiGoods.getGoodsId(), wp.getToken()));
			}
			modelMap.addAttribute("adList", adList);
			modelMap.addAttribute("categoryList", categoryList);
			modelMap.addAttribute("goodsList", goodsList);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/shop";
	}
	
	
	
	@RequestMapping("/goods!{gid}")
	public String goods(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ,
			@PathVariable(value = "gid") String gid,
			@RequestParam(value = "code", required = false) String code
			) {	
		Integer store_id = SignUtil.getUriStoreId(gid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			if(StringUtils.isNotBlank(store.getTheme()))themes = store.getTheme();
			Map<String,Object> map = SignUtil.getGid(gid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
//			if(this.isWeiXin(request)){//微信端登录
//				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
//					return this.redirect_wx_authorize(request , wp.getAppid() , "/shop!"+sid);
//				}else if(StringUtils.isNotEmpty(code)){
//					System.out.println(code);
//					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
//					String newSid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), wp.getToken());
//					String link = request.getScheme() + "://" + request.getServerName() + "/shop!"+newSid;
//					System.out.println("code:"+link);
//					return "redirect:"+link;
//				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
//					
//				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
//					System.out.println("user_id != map.userId condition is worng");
//					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
//				    return this.redirect_wx_authorize(request,wp.getAppid(), "/shop!"+sid);
//				}else{
//					System.out.println(sid+" condition is worng");
//					return "redirect:"+website; //错误的链接，跳转商城
//				}
//			}
			
			
			HaiGoodsExample goodsExample = new HaiGoodsExample();
			goodsExample.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andGoodsIdEqualTo(Long.valueOf(map.get("goodsId").toString()))
			.andIsOnSaleEqualTo(true);
			List<HaiGoods> goodsList = haiGoodsMapper.selectByExample(goodsExample);
			if(goodsList == null || goodsList.size() == 0){
				return "redirect:"+website; //错误的链接，跳转商城
			}
			HaiGoods haiGoods = goodsList.get(0);
			haiGoods.setGoodsUrl("goods!"+SignUtil.setGid(store_id, Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("userId").toString()), user_id, haiGoods.getGoodsId(), wp.getToken()));
			
			modelMap.addAttribute("gid", gid);
			modelMap.addAttribute("goods", haiGoods);
			modelMap.addAttribute("parentId", map.get("parentId"));
			modelMap.addAttribute("agencyId", map.get("agencyId"));
			modelMap.addAttribute("articleId", map.get("articleId"));
			
			
			String link = request.getScheme() + "://" + request.getServerName() + "/goods!"+gid;
			
			WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret(), null);
			signature.setTitle(haiGoods.getGoodsName());
			signature.setLink(link);
			signature.setDesc(haiGoods.getActDesc());
			signature.setImgUrl(haiGoods.getGoodsThumb());
			List<String> jsApiList = new ArrayList<String>();
			jsApiList.add("onMenuShareTimeline");
			jsApiList.add("onMenuShareAppMessage");
			jsApiList.add("onMenuShareQQ");
			jsApiList.add("onMenuShareWeibo");
			jsApiList.add("onMenuShareQZone");
			signature.setJsApiList(jsApiList);
			modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
			modelMap.addAttribute("buynow","buynow!"+gid);
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/goods";
		
		
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(SignUtil.setCid(1, 0, 0L, 125L, "ehais_wxdev"));
	}
	
}
