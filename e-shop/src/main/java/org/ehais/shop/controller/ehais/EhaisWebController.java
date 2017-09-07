package org.ehais.shop.controller.ehais;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.shop.mapper.HaiArticleGoodsMapper;
import org.ehais.shop.mapper.HaiArticleRecordMapper;
import org.ehais.shop.mapper.HaiCartMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiUserAddressMapper;
import org.ehais.shop.model.HaiArticleGoods;
import org.ehais.shop.model.HaiArticleGoodsExample;
import org.ehais.shop.model.HaiArticleRecord;
import org.ehais.shop.model.HaiArticleRecordExample;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCartExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiUserAddress;
import org.ehais.shop.model.HaiUserAddressExample;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.OpenidInfo;
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
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private HaiCartMapper haiCartMapper;
	@Autowired
	private HaiUserAddressMapper haiUserAddressMapper;
	@Autowired
	private HaiArticleRecordMapper haiArticleRecordMapper;
	
	
	public static String website = ResourceUtil.getProValue("website");
	
	//sid 32位md5[{5}{agencyId}-{15}{articleId}_{26}{userId}-{6}{goodsId}]

	/**
	 * 1.判断session的userid,openid随便一个不存在，即走微信网络请求链接
	 * 2.如果userid与openid同时存在，则判断链接的userid与session的userid是否一样，如果不一样，则清空session同时走微信网络请求链接
	 * @param request
	 * @param code
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private EHaiUsers saveUserByOpenIdInfo(HttpServletRequest request,String code,Map<String ,Object> map) throws Exception{
		//获取openid
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(Integer.valueOf(map.get("store_id").toString()));
		OpenidInfo open = WeiXinUtil.getOpenid(code,wp.getAppid(),wp.getSecret());
		//根据openid获取用户是否存在
		EHaiUsersExample userExp = new EHaiUsersExample();
		EHaiUsersExample.Criteria userC = userExp.createCriteria();
		userC.andOpenidEqualTo(open.getOpenid());
		System.out.println("openid:"+open.getOpenid());
		List<EHaiUsers> list = eHaiUsersMapper.selectByExample(userExp);
		Date date = new Date();
		EHaiUsers user = null;
		if(list == null || list.size() == 0){//用户不存在，入库
			user = new EHaiUsers();
			user.setOpenid(open.getOpenid());
			user.setParentId(Long.valueOf(map.get("userId").toString()));
			user.setAgencyId(Integer.valueOf(map.get("agencyId").toString()));
			user.setStoreId(Integer.valueOf(map.get("store_id").toString()));
			user.setEmail("");
			user.setUserName(open.getOpenid());
			user.setPassword("");					
			user.setRegTime(date);
			user.setLastLogin(date);
			eHaiUsersMapper.insert(user);
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
		}else{
			user = list.get(0);
			user.setLastLogin(date);
			eHaiUsersMapper.updateByPrimaryKeyWithBLOBs(user);
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
		}
//		request.getSession(true).setAttribute(EConstants.SESSION_OPEN_ID, open.getOpenid());
		
		return user;
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
	private String article_goods(ModelMap modelMap,	HttpServletRequest request,HttpServletResponse response,WpPublicWithBLOBs wp,String sid,Map<String,Object> map,String path) throws NumberFormatException, Exception{
		String link = request.getScheme() + "://" + request.getServerName() + "/w_article_detail!"+sid;
		//读取文章信息
		EHaiArticle article = eHaiArticleMapper.selectByPrimaryKey(Integer.valueOf(map.get("articleId").toString()));
		//读取商品信息
		HaiArticleGoodsExample articleGoodsExp = new HaiArticleGoodsExample();
		articleGoodsExp.createCriteria().andArticleIdEqualTo(Integer.valueOf(map.get("articleId").toString()));
		List<HaiArticleGoods> articleGoodsList = haiArticleGoodsMapper.selectByExample(articleGoodsExp);
		Long goodsId = 0L;
		if(articleGoodsList != null && articleGoodsList.size() > 0){
			goodsId = articleGoodsList.get(0).getGoodsId();
			if(goodsId.longValue() !=Long.valueOf(map.get("goodsId").toString()).longValue()){
				return "redirect:"+website; //错误的链接，跳转商城
			}
		}
		//读取微信的分享信息与链接整理
		if(goodsId == null || goodsId == 0L){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		HaiGoods goods = haiGoodsMapper.selectByPrimaryKey(goodsId);
		
		modelMap.addAttribute("article", article);
		modelMap.addAttribute("goods", goods);
		modelMap.addAttribute("parentId", map.get("parentId"));
		modelMap.addAttribute("agencyId", map.get("agencyId"));
		modelMap.addAttribute("articleId", map.get("articleId"));
		
		WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret(), null);
		signature.setTitle(article.getTitle());
		signature.setLink(link);
		signature.setDesc(article.getDescription());
		signature.setImgUrl(article.getArticleImages());
		List<String> jsApiList = new ArrayList<String>();
		jsApiList.add("onMenuShareTimeline");
		jsApiList.add("onMenuShareAppMessage");
		jsApiList.add("onMenuShareQQ");
		jsApiList.add("onMenuShareWeibo");
		jsApiList.add("onMenuShareQZone");
		signature.setJsApiList(jsApiList);
		modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
		modelMap.addAttribute("w_goods_detail","w_goods_detail!"+sid);
		modelMap.addAttribute("buynow","buynow!"+sid);
		return path;
	}
	
	
	private String goods_detail(ModelMap modelMap,	HttpServletRequest request,HttpServletResponse response,WpPublicWithBLOBs wp,String sid,Map<String,Object> map,String path) throws NumberFormatException, Exception{
		String link = request.getScheme() + "://" + request.getServerName() + "/w_goods_detail!"+sid;
		//读取文章信息
		Long goodsId = Long.valueOf(map.get("goodsId").toString());
		//读取微信的分享信息与链接整理
		if(goodsId == null || goodsId == 0L){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		HaiGoods goods = haiGoodsMapper.selectByPrimaryKey(goodsId);
		
		modelMap.addAttribute("goods", goods);
		modelMap.addAttribute("parentId", map.get("parentId"));
		modelMap.addAttribute("agencyId", map.get("agencyId"));
		modelMap.addAttribute("articleId", map.get("articleId"));
		
		WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret(), null);
		signature.setTitle(goods.getGoodsName());
		signature.setLink(link);
		signature.setDesc(goods.getGoodsBrief());
		signature.setImgUrl(goods.getGoodsThumb());
		List<String> jsApiList = new ArrayList<String>();
		jsApiList.add("onMenuShareTimeline");
		jsApiList.add("onMenuShareAppMessage");
		jsApiList.add("onMenuShareQQ");
		jsApiList.add("onMenuShareWeibo");
		jsApiList.add("onMenuShareQZone");
		signature.setJsApiList(jsApiList);
		modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
		modelMap.addAttribute("w_goods_detail","w_goods_detail!"+sid);
		modelMap.addAttribute("buynow","buynow!"+sid);
		return path;
	}
		
	/**
	 * http://wx123.9351p.com/w_article_detail!1380e1-7c17608f579081_6ed557049551-2096bf3269
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 * @return
	 */
	@RequestMapping("/w_article_detail!{sid}")
	public String w_article_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid	,
			@RequestParam(value = "code", required = false) String code
			) {
		Integer store_id = this.getUriStoreId(sid);
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
					return this.redirect_wx_authorize(request , wp.getAppid() , "/w_article_detail!"+sid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					String newSid = SignUtil.setSid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), Integer.valueOf(map.get("articleId").toString()), Long.valueOf(map.get("goodsId").toString()),wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_article_detail!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					
					//文章的阅读记录添加
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
						ar.setGoodsId(Long.valueOf(map.get("goodsId").toString()));
						ar.setReadTime(new Date());
						haiArticleRecordMapper.insert(ar);
						eHaiArticleMapper.plusReadCount(Integer.valueOf(map.get("articleId").toString()));
					}
					
					
					return this.article_goods(modelMap, request, response,wp, sid,map,"/ehais/w_article_detail");//整理此软文与商品所有内容
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);

				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_article_detail!"+sid);
				}else{
					System.out.println(sid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				this.shop_encode(request);
				return this.article_goods(modelMap, request, response,wp, sid,map,"/ehais/w_article_detail");//整理此软文与商品所有内容
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_article_detail";
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
		Integer store_id = this.getUriStoreId(sid);
		if(store_id == 0){
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
			
			if(this.isWeiXin(request)){//微信端登录

				Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
//				String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);

				if((user_id == null || user_id == 0) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request,wp.getAppid(), "/w_goods_detail!"+sid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					String newSid = SignUtil.setSid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), Integer.valueOf(map.get("articleId").toString()), Long.valueOf(map.get("goodsId").toString()),wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_goods_detail!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){
					return this.goods_detail(modelMap, request, response,wp, sid,map,"/ehais/w_goods_detail");//整理此软文与商品所有内容
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
//					request.getSession().removeAttribute(EConstants.SESSION_OPEN_ID);

				    return this.redirect_wx_authorize(request,wp.getAppid(), "/w_goods_detail!"+sid);
				}else{
					System.out.println(sid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
				
			}else{
				this.shop_encode(request);
				return this.goods_detail(modelMap, request, response,wp, sid,map,"/ehais/w_goods_detail");//整理此软文与商品所有内容
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_goods_detail";
	}
	
	
	
	@RequestMapping("/w_cart")
	public String w_cart(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	) {
		
		try{
			HaiCartExample example = new HaiCartExample();
			example.createCriteria().andUserIdEqualTo((Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID));
			example.setOrderByClause("rec_id desc");
			List<HaiCart> cartList = haiCartMapper.selectByExample(example);
			
			modelMap.addAttribute("cartList", cartList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_cart";
	}
	
	@RequestMapping("/w_check_order")
	public String w_check_order(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	) {
		
		
		if(this.isLocalHost(request)){
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, 55L);			
		}
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/w_check_order";
	}
	
	
	@RequestMapping("/w_address_list")
	public String w_address_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	) {
		
		
		try{
			HaiUserAddressExample example = new HaiUserAddressExample();
			example.createCriteria().andUserIdEqualTo((Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID));
			example.setOrderByClause("is_default desc,address_id desc");
			List<HaiUserAddress> userAddressList = haiUserAddressMapper.selectByExample(example);
			
			modelMap.addAttribute("userAddressList", userAddressList);
			
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
	
	
	
}
