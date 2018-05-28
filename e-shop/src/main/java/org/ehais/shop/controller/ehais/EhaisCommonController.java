package org.ehais.shop.controller.ehais;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.shop.mapper.HaiArticleGoodsMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiArticleGoods;
import org.ehais.shop.model.HaiArticleGoodsExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.util.MatrixToImageWriter;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.model.WeiXinSignature;
import org.ehais.weixin.model.WeiXinUserInfo;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.sf.json.JSONObject;

public class EhaisCommonController extends CommonController{
	//三级分销的主页面，可以将代理，与三个分销关系的ID组成商城主页，从而确定到三层关系与分销员的唯一商城地址

	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	
	
	@Autowired
	protected EWPPublicService eWPPublicService;
	@Autowired
	protected EStoreService eStoreService;
	
	protected Integer default_store_id = Integer.valueOf(ResourceUtil.getProValue("default_store_id"));
	protected Integer default_public_id = Integer.valueOf(ResourceUtil.getProValue("default_public_id"));
	protected String website = ResourceUtil.getProValue("website");
	protected String defaultimg = ResourceUtil.getProValue("defaultimg");
	protected String weixin_appid = ResourceUtil.getProValue("weixin_appid");
	protected String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
	protected String weixin_token = ResourceUtil.getProValue("weixin_token");
	protected String weixin_mch_id = ResourceUtil.getProValue("weixin_mch_id");
	protected String weixin_mch_secret = ResourceUtil.getProValue("weixin_mch_secret");
	protected String weixin_share_description = ResourceUtil.getProValue("weixin.share.description");
	protected String weixin_coupons_title = ResourceUtil.getProValue("weixin.coupons.title");//商家优惠券标题
	protected String weixin_coupons_desc = ResourceUtil.getProValue("weixin.coupons.desc");//商家优惠券描述
	
	
	protected static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	protected static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	protected static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	protected static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	/**
	 * 1.判断session的userid,openid随便一个不存在，即走微信网络请求链接
	 * 2.如果userid与openid同时存在，则判断链接的userid与session的userid是否一样，如果不一样，则清空session同时走微信网络请求链接
	 * @param request
	 * @param code
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private EHaiUsers saveUserOpen(HttpServletRequest request,OpenidInfo open,WeiXinUserInfo wxUser,Map<String ,Object> map){
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
			if(map!=null){
				user.setParentId(map.get("userId") != null ? Long.valueOf(map.get("userId").toString()) : 0);
				user.setAgencyId(map.get("agencyId") != null ? Integer.valueOf(map.get("agencyId").toString()) : 0);
				user.setStoreId(map.get("store_id") != null ? Integer.valueOf(map.get("store_id").toString()) : 0);
				user.setPartnerId(map.get("partnerId")!=null ? Integer.valueOf(map.get("partnerId").toString()) : 0 );
				
			}
			
			user.setEmail("");
			user.setUserName(open.getOpenid());
			user.setPassword("");					
			user.setRegTime(date);
			user.setLastLogin(date);
			
			if(wxUser != null){
				user.setSubscribe(wxUser.getSubscribe());
				if(wxUser.getNickname()!=null)user.setNickname(wxUser.getNickname());
				if(wxUser.getHeadimgurl()!=null)user.setFaceImage(wxUser.getHeadimgurl());
			}
			
			
			eHaiUsersMapper.insert(user);
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
		}else{
			user = list.get(0);
			if(wxUser != null){
				user.setSubscribe(wxUser.getSubscribe());
				if(wxUser.getNickname()!=null)user.setNickname(wxUser.getNickname());
				if(wxUser.getHeadimgurl()!=null)user.setFaceImage(wxUser.getHeadimgurl());
			}
			user.setLastLogin(date);
			eHaiUsersMapper.updateByPrimaryKeyWithBLOBs(user);
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
		}
		
		return user;
	}
	/**
	 * 适用于商家加盟的注册
	 * @param request
	 * @param code
	 * @param map
	 * @return
	 * @throws Exception
	 */
	protected EHaiUsers saveUserByOpenIdInfo(HttpServletRequest request,String code,Map<String ,Object> map) throws Exception{
		OpenidInfo open = WeiXinUtil.getOpenid(code,weixin_appid,weixin_appsecret);
		if(open == null || open.getOpenid() == null) return null;
		WeiXinUserInfo wxUser = null;
		AccessToken accesstoken = WeiXinUtil.getAccessToken(default_store_id, weixin_appid, weixin_appsecret);
		wxUser = WeiXinUtil.getUserInfo(accesstoken.getAccess_token(), open.getOpenid());
		return this.saveUserOpen(request, open , wxUser , map);
	}
	
	/**
	 * 用适于文章，商品等普通页面的用户获取
	 * @param request
	 * @param code
	 * @param map
	 * @param subscribe
	 * @return
	 * @throws Exception
	 */
	protected EHaiUsers saveUserByOpenIdInfo(HttpServletRequest request,String code,Map<String ,Object> map,boolean subscribe) throws Exception{
		//获取openid
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(Integer.valueOf(map.get("store_id").toString()));
		OpenidInfo open = WeiXinUtil.getOpenid(code,wp.getAppid(),wp.getSecret());
		if(open == null || open.getOpenid() == null) return null;
		WeiXinUserInfo wxUser = null;
		if(subscribe){
			AccessToken token = WeiXinUtil.getAccessToken(default_store_id, weixin_appid, weixin_appsecret);
			wxUser = WeiXinUtil.getUserInfo(token.getAccess_token(), open.getOpenid());
		}
		//根据openid获取用户是否存在
		return this.saveUserOpen(request, open , wxUser , map);
	}
	
	/**
	 * 适用于个人中心或公众号进入的用户
	 * @param request
	 * @param code
	 * @param store_id
	 * @return
	 * @throws Exception
	 */
	protected EHaiUsers saveUserByOpenIdInfo(HttpServletRequest request,String code,Integer store_id) throws Exception{
		//获取openid
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		OpenidInfo open = WeiXinUtil.getOpenid(code,wp.getAppid(),wp.getSecret());
		if(open == null || open.getOpenid() == null) return null;
		WeiXinUserInfo wxUser = null;
		AccessToken token = WeiXinUtil.getAccessToken(store_id, wp.getAppid(), wp.getSecret());
		wxUser = WeiXinUtil.getUserInfo(token.getAccess_token(), open.getOpenid());
		//根据openid获取用户是否存在
		return this.saveUserOpen(request, open , wxUser , null);
	}
	
	protected EHaiUsers getUserByOpenIdInfo(HttpServletRequest request,String code,Integer store_id) throws Exception{
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		OpenidInfo open = WeiXinUtil.getOpenid(code,wp.getAppid(),wp.getSecret());
		if(open == null || open.getOpenid() == null) return null;
		
		EHaiUsersExample userExp = new EHaiUsersExample();
		EHaiUsersExample.Criteria userC = userExp.createCriteria();
		userC.andOpenidEqualTo(open.getOpenid());
		List<EHaiUsers> list = eHaiUsersMapper.selectByExample(userExp);

		if(list != null && list.size() > 0){//用户不存在，入库
			return list.get(0);
		}
		return null;
	}
	
	
	//跳转微信认证
	protected String redirect_wx_authorize(HttpServletRequest request ,String appid , String path ) throws Exception {
		String REDIRECT_URI = request.getScheme()+"://"+request.getServerName() + path;
		REDIRECT_URI = java.net.URLEncoder.encode(REDIRECT_URI, "utf-8");
		return "redirect:"+WeiXinUtil.authorize_snsapi(appid, "snsapi_base", REDIRECT_URI);
	}
	
	/**
	 * 
	 * @param request
	 * @param appid
	 * @param path
	 * @param scope  snsapi_base  snsapi_userinfo 
	 * @return
	 * @throws Exception
	 */
	protected String redirect_wx_authorize(HttpServletRequest request ,String appid , String path ,String scope) throws Exception {
		String REDIRECT_URI = request.getScheme()+"://"+request.getServerName() + path;
		REDIRECT_URI = java.net.URLEncoder.encode(REDIRECT_URI, "utf-8");
		return "redirect:"+WeiXinUtil.authorize_snsapi(appid, scope, REDIRECT_URI);
	}
	
	
	
	protected void article_qrcode(HttpServletRequest request,
			HttpServletResponse response,
			EHaiArticleMapper eHaiArticleMapper,
			HaiGoodsMapper haiGoodsMapper,
			HaiArticleGoodsMapper haiArticleGoodsMapper,
			Integer store_id,
			Integer agencyId,
			Long parentId,
			Long userId,
			Integer articleId,
			Integer download) throws Exception{
		EHaiArticleExample articleExample = new EHaiArticleExample();
		articleExample.createCriteria().andArticleIdEqualTo(articleId).andStoreIdEqualTo(store_id);
		List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(articleExample);
		if(listArticle == null || listArticle.size() == 0){
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().print("错误信息");
			return ;
		}
		EHaiArticle article = listArticle.get(0);
		Long goodsId = 0l;
		
		HaiArticleGoodsExample agExample = new HaiArticleGoodsExample();
		agExample.createCriteria().andArticleIdEqualTo(articleId);
		List<HaiArticleGoods> listArticleGoods = haiArticleGoodsMapper.selectByExample(agExample);
		if(listArticleGoods != null && listArticleGoods.size() > 0){
			HaiArticleGoods articleGoods = listArticleGoods.get(0);
			goodsId = articleGoods.getGoodsId();
			if(goodsId.longValue() != 0){
				HaiGoodsExample goodsExample = new HaiGoodsExample();
				goodsExample.createCriteria().andGoodsIdEqualTo(goodsId).andStoreIdEqualTo(store_id);
				List<HaiGoods> listGoods = haiGoodsMapper.selectByExample(goodsExample);
				if(listGoods == null || listGoods.size() == 0){
					response.setHeader("Content-type", "text/html;charset=UTF-8");
					response.getWriter().print("关联商品错误信息");
					return ;
				}
			}
			
		}
		
		
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		String content = request.getScheme()+"://"+request.getServerName()+"/w_article_goods!"+SignUtil.setSid(store_id,agencyId,parentId,userId,articleId,goodsId,wp.getToken());
		System.out.println(content);
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		@SuppressWarnings("rawtypes")
        Map hints = new HashMap();
        
        //设置UTF-8， 防止中文乱码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN,1);
        //设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
        
        //width:图片完整的宽;height:图片完整的高
        //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
        int width = 400;
        int height = 500;
        
        //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//        
        Graphics2D g = image.createGraphics();
        String pressText = article.getTitle();
        int fontSize = 25; //字体大小
        int fontStyle = 1; //字体风格
        
        int startX = (width-(fontSize*pressText.length()))/2;
        //y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height-(height-width)/2 + 5; 
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, fontStyle, fontSize));
        g.drawString(pressText, startX, startY);
        
        
        fontSize = 12; //字体大小
        g.setFont(new Font(null, fontStyle, fontSize));
        pressText = "广州易海司信息科技有限公司";
        startX = (width-(fontSize*pressText.length()))/2;
        g.drawString(pressText, startX, startY + 30);
        
        fontSize = 10; //字体大小
        g.setFont(new Font(null, fontStyle, fontSize));
        pressText = "www.ehais.com";
        startX = (width-(fontSize*pressText.length()) / 2 )/2;
        g.drawString(pressText, startX, startY + 40);
        
        g.dispose();
        
        if(download != null && download == 1){//下载
        	response.setContentType("application/octet-stream");  
            response.setHeader("Accept-Ranges", "bytes");  
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(article.getTitle().getBytes("utf-8"), "ISO8859-1")+".jpg");  
            
        }
         
        
        OutputStream stream = response.getOutputStream();
        ImageIO.write(image, "jpg", stream);
        
	}

	protected void goods_qrcode(HttpServletRequest request,
			HttpServletResponse response,
			HaiGoodsMapper haiGoodsMapper,
			Integer store_id,
			Integer agencyId,
			Long parentId,
			Long userId,
			Long goodsId,
			Integer download) throws Exception{
		
		Integer articleId=0;
		HaiGoodsExample goodsExample = new HaiGoodsExample();
		goodsExample.createCriteria()
		.andGoodsIdEqualTo(goodsId)
		.andStoreIdEqualTo(store_id);
		List<HaiGoods> listGoods = haiGoodsMapper.selectByExample(goodsExample);
		if(listGoods == null || listGoods.size() == 0){
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().print("商品错误信息");
			return ;
		}
		HaiGoods goods = listGoods.get(0);
		
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		String content = request.getScheme()+"://"+request.getServerName()+"/w_goods_detail!"+SignUtil.setSid(store_id,agencyId,parentId,userId,articleId,goodsId,wp.getToken());
		System.out.println(content);
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		@SuppressWarnings("rawtypes")
        Map hints = new HashMap();
        
        //设置UTF-8， 防止中文乱码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN,1);
        //设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
        
        //width:图片完整的宽;height:图片完整的高
        //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
        int width = 400;
        int height = 500;
        
        //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//        
        Graphics2D g = image.createGraphics();
        String pressText = goods.getGoodsName();
        int fontSize = 25; //字体大小
        int fontStyle = 1; //字体风格
        
        int startX = (width-(fontSize*pressText.length()))/2;
        //y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height-(height-width)/2 + 5; 
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, fontStyle, fontSize));
        g.drawString(pressText, startX, startY);
        
        
        fontSize = 12; //字体大小
        g.setFont(new Font(null, fontStyle, fontSize));
        pressText = "广州易海司信息科技有限公司";
        startX = (width-(fontSize*pressText.length()))/2;
        g.drawString(pressText, startX, startY + 30);
        
        fontSize = 10; //字体大小
        g.setFont(new Font(null, fontStyle, fontSize));
        pressText = "www.ehais.com";
        startX = (width-(fontSize*pressText.length()) / 2 )/2;
        g.drawString(pressText, startX, startY + 40);
        
        g.dispose();
        
        if(download != null && download == 1){//下载
        	response.setContentType("application/octet-stream");  
            response.setHeader("Accept-Ranges", "bytes");  
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(goods.getGoodsName().getBytes("utf-8"), "ISO8859-1")+".jpg");  
            
        }
         
        
        OutputStream stream = response.getOutputStream();
        ImageIO.write(image, "jpg", stream);
        
	}
	
	
	/**
	 * 
	@RequestMapping("/shop!{sid}")
	public String index_simple(ModelMap modelMap,
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
			
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/shop!"+sid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					String newSid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/shop!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				    return this.redirect_wx_authorize(request,wp.getAppid(), "/shop!"+sid);
				}else{
					System.out.println(sid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/wxshop/"+themes+"/index_simple";
	}
	 */
	
	
	/*
	public String common_wx_redirect(ModelMap modelMap,
			HttpServletRequest request,
			String sid,
			String code,
			String signGMethod,
			String signSMethod,
			String url){
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == null || store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Class clazz = Class.forName("org.ehais.util.SignUtil"); 
			Method m = clazz.getDeclaredMethod("hi",new Class[]{int.class,String.class}); 
			Map<String,Object> map = (Map<String, Object>) m.invoke(clazz.newInstance(),sid,wp);
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/"+url+"!"+sid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					String newSid = SignUtil.setSid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), Integer.valueOf(map.get("articleId").toString()), Long.valueOf(map.get("goodsId").toString()),wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/w_article_goods!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "redirect:"+website;
	}
	
	*/
	
	/**
	 * 微信分享公共方法
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param wp
	 * @param store_id
	 * @param title
	 * @param link
	 * @param desc
	 * @param imgurl
	 * @throws Exception
	 */
	protected void shareWeiXin(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			WpPublicWithBLOBs wp ,
			Integer store_id,
			String title,
			String link,
			String desc,
			String imgurl)throws Exception{
		WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, store_id, wp.getAppid(), wp.getSecret(), null);
		signature.setTitle(title);
		signature.setLink(link);
		signature.setDesc(StringUtils.isBlank(desc)?weixin_share_description:desc);
		signature.setImgUrl(StringUtils.isBlank(imgurl)?defaultimg:imgurl);
		List<String> jsApiList = new ArrayList<String>();
		jsApiList.add("onMenuShareTimeline");
		jsApiList.add("onMenuShareAppMessage");
		jsApiList.add("onMenuShareQQ");
		jsApiList.add("onMenuShareWeibo");
		jsApiList.add("onMenuShareQZone");
		signature.setJsApiList(jsApiList);
		modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
		
	}
	
	/**
	 * 返回前端的模板
	 * @param store_id
	 * @param defaultTheme
	 * @return
	 */
	protected String webThemes(Integer store_id,String defaultTheme){
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			if(store == null)return defaultTheme;
			return StringUtils.isBlank(store.getWebTheme())?defaultTheme:store.getWebTheme();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return defaultTheme;
		
	}
	
	protected String webThemes(EHaiStore store,String defaultTheme){
		try{
			if(store == null)return defaultTheme;
			return StringUtils.isBlank(store.getWebTheme())?defaultTheme:store.getWebTheme();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return defaultTheme;
		
	}
	
	

}
