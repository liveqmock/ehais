package org.ehais.shop.controller.wine;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.shop.mapper.HaiArticleGoodsMapper;
import org.ehais.shop.mapper.HaiCartMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiArticleGoods;
import org.ehais.shop.model.HaiArticleGoodsExample;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCartExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.util.EachMapUtils;
import org.ehais.util.EncryptUtils;
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
public class WineWebController extends CommonController {

	Integer store_id = 56;
	
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
	
	public static String weixin_appid = ResourceUtil.getProValue("weixin_appid");
	public static String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
	public static String wxdev_token = ResourceUtil.getProValue("wxdev_token");
	public static String website = ResourceUtil.getProValue("website");
	
	//sid 32位md5[{5}{agencyId}-{15}{articleId}_{26}{userId}-{6}{goodsId}]
	public static void main(String[] args)  {
		Integer agencyId = 9873;
		Integer articleId = 9081;
		Long userId = 94L;
		Long goodsId = 3269L;
		
		try {
			WineWebController w = new WineWebController();
			String sid = w.setSid(agencyId, articleId, userId, goodsId);
			Map<String,Object> map = w.getSid(sid);
			if(map!=null)EachMapUtils.printMap(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String setSid(Integer agencyId,Integer articleId,Long userId,Long goodsId) throws UnsupportedEncodingException{
		String md5 = EncryptUtils.md5(agencyId.toString()+articleId.toString()+userId.toString()+goodsId.toString()+wxdev_token);
		String sid = md5.substring(0, 5)+agencyId.toString()+"-"+md5.substring(5,15)+articleId.toString()+"_"+md5.substring(15,26)+userId.toString()+"-"+md5.substring(26,32)+goodsId.toString();
		return sid;
	}
	
	/**
	 * 解析参数
	 * @param sid
	 * @return
	 */
	private Map<String,Object> getSid(String sid){
		if(StringUtils.isEmpty(sid))return null;
		Map<String,Object> map = null;
		try{
			String s1 = sid.substring(0, 5);if(StringUtils.isEmpty(s1))return null;
			String sAgencyId = sid.substring(5,5 + sid.substring(5).indexOf("-"));if(StringUtils.isEmpty(sAgencyId))return null;
			String s2 = sid.substring(sid.indexOf("-") + 1 ,sid.indexOf("-") + 11);if(StringUtils.isEmpty(s2))return null;
			String sArticleId = sid.substring(sid.indexOf("-") + 11 ,sid.indexOf("_"));if(StringUtils.isEmpty(sArticleId))return null;
			String s3 = sid.substring(sid.indexOf("_")+1,sid.indexOf("_") +12);if(StringUtils.isEmpty(s3))return null;
			String sUserId = sid.substring(sid.indexOf("_") +12,sid.indexOf("_") +sid.substring(sid.indexOf("_")).indexOf("-"));if(StringUtils.isEmpty(sUserId))return null;
			String s4 = sid.substring(sid.lastIndexOf("-")+1,sid.lastIndexOf("-")+7);if(StringUtils.isEmpty(s4))return null;
			String sGoodsId = sid.substring(sid.lastIndexOf("-")+7);if(StringUtils.isEmpty(sGoodsId))return null;
			if(EncryptUtils.md5(sAgencyId+sArticleId+sUserId+sGoodsId+wxdev_token).equals(s1+s2+s3+s4)){
				map = new HashMap<String,Object>();
				map.put("agencyId", sAgencyId);
				map.put("articleId", sArticleId);
				map.put("userId", sUserId);
				map.put("goodsId", sGoodsId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return map;
	}
	
	private void saveUserByOpenIdInfo(HttpServletRequest request,String code,Map<String ,Object> map) throws Exception{
		OpenidInfo open = WeiXinUtil.getOpenid(code,weixin_appid,weixin_appsecret);
		
		EHaiUsersExample userExp = new EHaiUsersExample();
		EHaiUsersExample.Criteria userC = userExp.createCriteria();
		userC.andOpenidEqualTo(open.getOpenid());
		
		List<EHaiUsers> list = eHaiUsersMapper.selectByExample(userExp);
		Date date = new Date();
		if(list == null || list.size() == 0){//用户不存在，入库
			EHaiUsers user = new EHaiUsers();
			user.setOpenid(open.getOpenid());
			user.setParentId(Long.valueOf(map.get("userId").toString()));
			user.setStoreId(store_id);
			user.setEmail("");
			user.setUserName(open.getOpenid());
			user.setPassword("");					
			user.setRegTime(date);
			user.setLastLogin(date);
			eHaiUsersMapper.insert(user);
			request.getSession(true).setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
		}else{
			EHaiUsers user = list.get(0);
			user.setLastLogin(date);
			eHaiUsersMapper.updateByPrimaryKeyWithBLOBs(user);
			request.getSession(true).setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
		}
	}
	
	
	/**
	 * http://wx123.9351p.com/wine/wxarticle!7f9999873-2b10bbc4af9081_ec8f91bb8b694-1f18cd3269
	 * 微信分享资讯跳转
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 * @return
	 */
	@RequestMapping("/wxarticle!{sid}")
	public String wxgo_article_detail(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid,
			@RequestParam(value = "code", required = false) String code
			){
		Map<String ,Object> map = this.getSid(sid);
		if(map == null){
			System.out.println(sid+" sid is worng");
		    return "redirect://"+website; //错误的链接，跳转商城
		}
		try{

			if(StringUtils.isEmpty(code)){//跳转微信的链接
				String REDIRECT_URI = request.getScheme()+"://"+request.getServerName()+"/wine/wxarticle!"+sid;
				REDIRECT_URI = java.net.URLEncoder.encode(REDIRECT_URI, "utf-8");
				
				return "redirect:"+WeiXinUtil.authorize_snsapi(weixin_appid, "snsapi_base", REDIRECT_URI);
			}else{//根据code获取openid，判断此用户是否存在库，如果不存在，把此用户入库，同时记录推荐人的user_id
				this.saveUserByOpenIdInfo(request, code,map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return "redirect:/wine/w_article_detail!"+sid;		
	}
	
	
	
	/**
	 * http://localhost/wine/w_article_detail!7f9999873-2b10bbc4af9081_ec8f91bb8b694-1f18cd3269
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 * @return
	 */
	@RequestMapping("/w_article_detail!{sid}")
	public String w_article_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid	) {
		Map<String,Object> map = this.getSid(sid);
		if(map == null){
			System.out.println(sid+" sid is worng");
		    return "redirect://"+website; //错误的链接，跳转商城
		}
		String link = request.getScheme()+"://"+request.getServerName()+"/wine/wxarticle!"+sid;
		if(StringUtils.isEmpty((String)request.getSession(true).getAttribute(EConstants.SESSION_OPEN_ID)) && !this.isLocalHost(request)){
			return "redirect://"+link;//无登录情况要跳回重新登录
		}
		
		try{
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
					return "redirect://"+website; //错误的链接，跳转商城
				}
			}
			//读取微信的分享信息与链接整理
			if(goodsId == null || goodsId == 0L){
				return "redirect://"+website; //错误的链接，跳转商城
			}
			HaiGoods goods = haiGoodsMapper.selectByPrimaryKey(goodsId);
			
			modelMap.addAttribute("article", article);
			modelMap.addAttribute("goods", goods);
			
			WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, store_id, weixin_appid, weixin_appsecret, null);
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
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/w_article_detail";
	}
	
	
	
	@RequestMapping("/wxgoods!{sid}")
	public String wxgo_goods_detail(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid,
			@RequestParam(value = "code", required = false) String code
			){
		Map<String ,Object> map = this.getSid(sid);
		if(map == null){
			System.out.println(sid+" sid is worng");
		    return "redirect://"+website; //错误的链接，跳转商城
		}
		try{

			if(StringUtils.isEmpty(code)){//跳转微信的链接
				String REDIRECT_URI = request.getScheme()+"://"+request.getServerName()+"/wine/wxgoods!"+sid;
				REDIRECT_URI = java.net.URLEncoder.encode(REDIRECT_URI, "utf-8");
				
				return "redirect:"+WeiXinUtil.authorize_snsapi(weixin_appid, "snsapi_base", REDIRECT_URI);
			}else{//根据code获取openid，判断此用户是否存在库，如果不存在，把此用户入库，同时记录推荐人的user_id
				this.saveUserByOpenIdInfo(request, code,map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return "redirect:/wine/w_goods_detail!"+sid;		
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
			@PathVariable(value = "sid") String sid	) {
		Map<String,Object> map = this.getSid(sid);
		if(map == null){
			System.out.println(sid+" sid is worng");
		    return "redirect://"+website; //错误的链接，跳转商城
		}
		String link = request.getScheme()+"://"+request.getServerName()+"/wine/wxarticle!"+sid;
		if(StringUtils.isEmpty((String)request.getSession(true).getAttribute(EConstants.SESSION_OPEN_ID)) && !this.isLocalHost(request)){
			return "redirect://"+link;//无登录情况要跳回重新登录
		}
		
		try{
			
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
					return "redirect://"+website; //错误的链接，跳转商城
				}
			}
			//读取微信的分享信息与链接整理
			if(goodsId == null || goodsId == 0L){
				return "redirect://"+website; //错误的链接，跳转商城
			}
			HaiGoods goods = haiGoodsMapper.selectByPrimaryKey(goodsId);
			
			modelMap.addAttribute("article", article);
			modelMap.addAttribute("goods", goods);
			modelMap.addAttribute("parentUserId", map.get("userId"));
			modelMap.addAttribute("agencyId", map.get("agencyId"));
			modelMap.addAttribute("articleId", map.get("articleId"));
			
			WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, store_id, weixin_appid, weixin_appsecret, null);
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
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/w_goods_detail";
	}
	
	
	
	@RequestMapping("/w_cart")
	public String w_cart(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response	) {
		
		request.getSession().setAttribute(EConstants.SESSION_USER_ID, 920L);
		
		
		try{
			HaiCartExample example = new HaiCartExample();
			example.createCriteria().andUserIdEqualTo((Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID));
			example.setOrderByClause("rec_id desc");
			List<HaiCart> cartList = haiCartMapper.selectByExample(example);
			
			
			modelMap.addAttribute("cartList", cartList);
			
			//购物车限制转发
//			WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, store_id, weixin_appid, weixin_appsecret, null);
//			signature.setTitle(article.getTitle());
//			signature.setLink(link);
//			signature.setDesc(article.getDescription());
//			signature.setImgUrl(article.getArticleImages());
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
		return "/wine/w_cart";
	}
	
	
	
}
