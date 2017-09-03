package org.ehais.shop.controller.wine;

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
import org.ehais.shop.mapper.HaiCartMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiUserAddressMapper;
import org.ehais.shop.model.HaiArticleGoods;
import org.ehais.shop.model.HaiArticleGoodsExample;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCartExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiUserAddress;
import org.ehais.shop.model.HaiUserAddressExample;
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
@RequestMapping("/wine")
public class WineWebController extends WineCommonController {
	//store_id在winecommoncontroller设置
	
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
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
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
			user.setStoreId(store_id);
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
		request.getSession(true).setAttribute(EConstants.SESSION_OPEN_ID, open.getOpenid());
		
		return user;
	}
	
		
	/**
	 * http://wx123.9351p.com/wine/w_article_detail!1380e1-7c17608f579081_6ed557049551-2096bf3269
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
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		Map<String,Object> map = this.getSid(sid);
		if(map == null){
			System.out.println(sid+" sid is worng");
		    return "redirect:"+website; //错误的链接，跳转商城
		}
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);

		try{
			
			if((user_id == null || user_id == 0 || StringUtils.isEmpty(openid)) && StringUtils.isEmpty(code)){
				return this.redirect_wx_authorize(request, "/wine/w_article_detail!"+sid);
			}else if(StringUtils.isNotEmpty(code)){
				System.out.println(code);
				EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
				String newSid = this.setSid(Long.valueOf(map.get("agencyId").toString()), Integer.valueOf(map.get("articleId").toString()), user.getUserId(), Long.valueOf(map.get("goodsId").toString()));
				String link = request.getScheme() + "://" + request.getServerName() + "/wine/w_article_detail!"+newSid;
				System.out.println("code:"+link);
				return "redirect:"+link;
			}else if(user_id > 0 && StringUtils.isNotEmpty(openid) && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){
				String link = request.getScheme() + "://" + request.getServerName() + "/wine/w_article_detail!"+sid;
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
				modelMap.addAttribute("parentUserId", map.get("userId"));
				modelMap.addAttribute("agencyId", map.get("agencyId"));
				modelMap.addAttribute("articleId", map.get("articleId"));
				
				WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
				WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, store_id, wp.getAppid(), wp.getSecret(), null);
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
				return "/wine/w_article_detail";
			}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
				System.out.println("user_id != map.userId condition is worng");
				request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				request.getSession().removeAttribute(EConstants.SESSION_OPEN_ID);

			    return this.redirect_wx_authorize(request, "/wine/w_article_detail!"+sid);
			}else{
				System.out.println(sid+" condition is worng");
				return "redirect:"+website; //错误的链接，跳转商城
			}


			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/w_article_detail";
	}
	
	
	/**
	 * http://localhost/wine/w_goods_detail!7f9999873-2b10bbc4af9081_ec8f91bb8b694-1f18cd3269
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
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		Map<String,Object> map = this.getSid(sid);
		if(map == null){
			System.out.println(sid+" sid is worng");
		    return "redirect:"+website; //错误的链接，跳转商城
		}
		
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);

		
		try{
			
			if((user_id == null || user_id == 0 || StringUtils.isEmpty(openid)) && StringUtils.isEmpty(code)){
				return this.redirect_wx_authorize(request, "/wine/w_goods_detail!"+sid);
			}else if(StringUtils.isNotEmpty(code)){
				System.out.println(code);
				EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
				String newSid = this.setSid(Long.valueOf(map.get("agencyId").toString()), Integer.valueOf(map.get("articleId").toString()), user.getUserId(), Long.valueOf(map.get("goodsId").toString()));
				String link = request.getScheme() + "://" + request.getServerName() + "/wine/w_goods_detail!"+newSid;
				System.out.println("code:"+link);
				return "redirect:"+link;
			}else if(user_id > 0 && StringUtils.isNotEmpty(openid) && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){
				String link = request.getScheme() + "://" + request.getServerName() + "/wine/w_goods_detail!"+sid;
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
				modelMap.addAttribute("parentUserId", map.get("userId"));
				modelMap.addAttribute("agencyId", map.get("agencyId"));
				modelMap.addAttribute("articleId", map.get("articleId"));
				WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
				WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, store_id, wp.getAppid(), wp.getSecret(), null);
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
				return "/wine/w_goods_detail";
			}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
				System.out.println("user_id != map.userId condition is worng");
				request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				request.getSession().removeAttribute(EConstants.SESSION_OPEN_ID);

			    return this.redirect_wx_authorize(request, "/wine/w_goods_detail!"+sid);
			}else{
				System.out.println(sid+" condition is worng");
				return "redirect:"+website; //错误的链接，跳转商城
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/w_goods_detail";
	}
	
	
	
	@RequestMapping("/w_cart")
	public String w_cart(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	) {
		
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			HaiCartExample example = new HaiCartExample();
			example.createCriteria().andUserIdEqualTo((Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID));
			example.setOrderByClause("rec_id desc");
			List<HaiCart> cartList = haiCartMapper.selectByExample(example);
			
			modelMap.addAttribute("cartList", cartList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/w_cart";
	}
	
	@RequestMapping("/w_check_order")
	public String w_check_order(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	) {
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		if(this.isLocalHost(request)){
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, 55L);			
		}
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/w_check_order";
	}
	
	
	@RequestMapping("/w_address_list")
	public String w_address_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	) {
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			HaiUserAddressExample example = new HaiUserAddressExample();
			example.createCriteria().andUserIdEqualTo((Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID));
			example.setOrderByClause("is_default desc,address_id desc");
			List<HaiUserAddress> userAddressList = haiUserAddressMapper.selectByExample(example);
			
			modelMap.addAttribute("userAddressList", userAddressList);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/w_address_list";
	}
	
	
	@RequestMapping("/w_address_add")
	public String w_address_add(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	) {
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			modelMap.addAttribute("model", new HaiUserAddress());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/w_address_form";
	}
	
	
	@RequestMapping("/w_address_edit")
	public String w_address_edit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	,
			@RequestParam(value = "addressId", required = true) Long addressId
			) {
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			HaiUserAddressExample example = new HaiUserAddressExample();
			example.createCriteria()
			.andUserIdEqualTo((Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID))
			.andAddressIdEqualTo(addressId)	;
			List<HaiUserAddress> userAddressList = haiUserAddressMapper.selectByExample(example);
			if(userAddressList == null || userAddressList.size() == 0){
				return "/wine/wrong";
			}
			
			modelMap.addAttribute("model", userAddressList.get(0));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/w_address_form";
	}
	
	
	
}
