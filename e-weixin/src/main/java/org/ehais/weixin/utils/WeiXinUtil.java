package org.ehais.weixin.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.ehais.util.ECommon;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.EmojiFilterUtils;
import org.ehais.util.EncryptUtils;
import org.ehais.util.SignUtil;
import org.ehais.util.StringUtil;
import org.ehais.util.XStreamUtil;
import org.ehais.weixin.WXConstants;
import org.ehais.weixin.cache.AccessTokenCacheManager;
import org.ehais.weixin.cache.JsApiTicketCacheManager;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.JsApiTicket;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.model.WeiXinArticlesItem;
import org.ehais.weixin.model.WeiXinDownloadBill;
import org.ehais.weixin.model.WeiXinGetSignKey;
import org.ehais.weixin.model.WeiXinImage;
import org.ehais.weixin.model.WeiXinMPNews;
import org.ehais.weixin.model.WeiXinNotifyPay;
import org.ehais.weixin.model.WeiXinNotityXml;
import org.ehais.weixin.model.WeiXinSignature;
import org.ehais.weixin.model.WeiXinTemplateMessage;
import org.ehais.weixin.model.WeiXinUnifiedOrder;
import org.ehais.weixin.model.WeiXinUnifiedOrderResult;
import org.ehais.weixin.model.WeiXinUserInfo;
import org.ehais.weixin.model.WeiXinUserInfoBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import net.sf.json.JSONObject;


public class WeiXinUtil {
	private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);
	
	public static String signature(String wxdev_token ,String timestamp,String nonce)throws Exception{
		String[] arr = new String[] { wxdev_token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		
		String tmpStr = EncryptUtils.sha1(content.toString());
		content = null;
		return tmpStr;
	}
	
	public static String wxSignature(String wx_token,String timestamp,String nonce)throws Exception{
		String[] arr = new String[] { wx_token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		System.out.println(content.toString());
		String tmpStr = EncryptUtils.sha1(content.toString());
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr ;
	}
	
	public static boolean checkSignature(String timestamp,String nonce,String signature)throws Exception{
		String[] arr = new String[] { WXConstants.wxdev_token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		
		String tmpStr = EncryptUtils.sha1(content.toString());
		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toLowerCase()) : false;
	}
	
	/**将xml转化成对象
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static WeiXinNotityXml toNotityXml(String content) throws Exception{
		XStream xStream = new XStream();
		streamAlias(xStream);
		WeiXinNotityXml notity = (WeiXinNotityXml) xStream.fromXML(content);
		return notity;
	}
	
	public static String fromNotityXml(WeiXinNotityXml notity) throws Exception{
		XStream xStream = new XStream();
		streamAlias(xStream);
		return xStream.toXML(notity);
	}
	
	private static void streamAlias(XStream xStream){
		xStream.alias("xml",WeiXinNotityXml.class);
		xStream.alias("Image",WeiXinImage.class);
		xStream.alias("item",WeiXinArticlesItem.class);
	}
	
//	public static String getEhaisToken() throws Exception{
//		String request = EHttpClientUtil.methodGet(WXConstants.ehais_access_token);
//		return request;
//	}
	
//	public static String getJsApiTicket() throws Exception{
//		String request = EHttpClientUtil.methodGet(WXConstants.ehais_jsapiticket);
//		return request;
//	}

	/**
	 * 获取access token
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public static AccessToken getAccessToken(int storeId,String weixin_appid,String weixin_appsecret) throws Exception {
		log.info("getAccessToken==storeId:"+storeId+";weixin_appid:"+weixin_appid+";weixin_appsecret:"+weixin_appsecret);
		AccessToken accessToken = (AccessToken)AccessTokenCacheManager.getInstance().getAccessToken(storeId);
//		accessToken.setExpire_time(System.currentTimeMillis());
//		AccessTokenCacheManager.getInstance().putAccessToken(accessToken);
		if(accessToken!=null){
			log.info("111==oscache--accesstoken---time:"+System.currentTimeMillis()+"---"+accessToken.getExpire_time()+"======"+(accessToken.getExpire_time() - System.currentTimeMillis()));
			if(System.currentTimeMillis() < accessToken.getExpire_time()){
				log.info("oscache缓存accesstoken:"+accessToken.getAccess_token());
				return accessToken;
			}				
		}

		String requestUrl = WXConstants.access_token_url
				.replace("APPID", weixin_appid)
				.replace("APPSECRET", weixin_appsecret)
				.replace("STORE_ID", storeId+"");
		log.info("请求AccessToken地址:"+requestUrl);
		
		String request = EHttpClientUtil.methodGet(requestUrl);
		log.info("返回AccessToken的信息:"+request);
		JSONObject jsonObject = JSONObject.fromObject(request);
		
		if (null != jsonObject) {
			if(jsonObject.has("errcode")){//如果报错，强制重新获取一次
				log.info("retry access token");
				requestUrl = WXConstants.access_token_url
						.replace("APPID", weixin_appid)
						.replace("APPSECRET", weixin_appsecret);
				request = EHttpClientUtil.methodGet(requestUrl);
				jsonObject = JSONObject.fromObject(request);				
			}
			accessToken = new AccessToken();
			accessToken.setId(storeId);
			accessToken.setAccess_token(jsonObject.getString("access_token"));
//			accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
//			accessToken.setExpires_in(jsonObject.getInt("expires_in"));
			if(jsonObject.has("expiresIn"))accessToken.setExpires_in(jsonObject.getInt("expiresIn"));
			if(jsonObject.has("expires_in"))accessToken.setExpires_in(jsonObject.getInt("expires_in"));
			accessToken.setExpire_time(System.currentTimeMillis() + accessToken.getExpires_in() * 1000);//毫秒数
			AccessTokenCacheManager.getInstance().putAccessToken(accessToken);//保存内存中，不需要经常读接口
		}
		
		return accessToken;
	}
	
	public static AccessToken getAccessToken(int storeId,String weixin_appid,String weixin_appsecret,boolean isconstraint) throws Exception{
		AccessToken accessToken = (AccessToken)AccessTokenCacheManager.getInstance().getAccessToken(storeId);
		
		String requestUrl = WXConstants.access_token_url
				.replace("APPID", weixin_appid)
				.replace("APPSECRET", weixin_appsecret);
		log.info("强制请求AccessToken地址:"+requestUrl);
		
		String request = EHttpClientUtil.methodGet(requestUrl);
		JSONObject jsonObject = JSONObject.fromObject(request);
		log.info("强制返回AccessToken的信息:"+jsonObject.toString());
		if (null != jsonObject) {
			if(jsonObject.has("errcode")){//如果报错，强制重新获取一次
				requestUrl = WXConstants.access_token_url
						.replace("APPID", weixin_appid)
						.replace("APPSECRET", weixin_appsecret);
				request = EHttpClientUtil.methodGet(requestUrl);
				jsonObject = JSONObject.fromObject(request);				
			}
			accessToken = new AccessToken();
			accessToken.setId(storeId);
			accessToken.setAccess_token(jsonObject.getString("access_token"));
//			accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			if(jsonObject.has("expiresIn"))accessToken.setExpires_in(jsonObject.getInt("expiresIn"));
			if(jsonObject.has("expires_in"))accessToken.setExpires_in(jsonObject.getInt("expires_in"));
			accessToken.setExpire_time(System.currentTimeMillis() + accessToken.getExpires_in() * 1000);//毫秒数
			AccessTokenCacheManager.getInstance().putAccessToken(accessToken);//保存内存中，不需要经常读接口
		}
		
		return accessToken;
	}


	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static JsApiTicket getJsApiTicket(int storeId,String access_token,String weixin_appid,String weixin_appsecret) throws Exception {
		log.info("getJsApiTicket==storeId:"+storeId+";access_token:"+access_token+";weixin_appid:"+weixin_appid+";weixin_appsecret:"+weixin_appsecret);
		JsApiTicket jsapiticket = (JsApiTicket)JsApiTicketCacheManager.getInstance().getJsApiTicket(storeId);
//		jsapiticket.setExpire_time(System.currentTimeMillis());
//		JsApiTicketCacheManager.getInstance().putJsApiTicket(jsapiticket);
		if(jsapiticket!=null){
			log.info("111111111 oscache--jsapiticket---time:"+System.currentTimeMillis()+"---"+jsapiticket.getExpire_time()+"=========="+(jsapiticket.getExpire_time() - System.currentTimeMillis() ));
			if(System.currentTimeMillis() < jsapiticket.getExpire_time()){
				log.info("oscache缓存jsapiticket:"+jsapiticket.getTicket());
				return jsapiticket;
			}
				
		}
		
		
		String requestUrl = WXConstants.get_jsapi_url.replace("ACCESS_TOKEN", access_token).replace("STORE_ID", storeId+"");
		System.out.println("JsApiTicket:"+requestUrl);
		String request = EHttpClientUtil.methodGet(requestUrl);
		JSONObject jsonObject = JSONObject.fromObject(request);
		
		System.out.println("request JsApiTicket:"+request);
		// 如果请求成功
		if (null != jsonObject) {
			if(jsonObject.has("errcode") && jsonObject.getInt("errcode") > 0){//如果报错，强制重新获取一次
				log.info("retry api ticket");
				AccessToken accessToken = getAccessToken(storeId, weixin_appid, weixin_appsecret, true);
				requestUrl = WXConstants.get_jsapi_url.replace("ACCESS_TOKEN", accessToken.getAccess_token());
				request = EHttpClientUtil.methodGet(requestUrl);
				jsonObject = JSONObject.fromObject(request);
			}
			jsapiticket = new JsApiTicket();
			jsapiticket.setId(storeId);
			jsapiticket.setTicket(jsonObject.getString("ticket"));
			jsapiticket.setErrcode(jsonObject.getInt("errcode"));
			jsapiticket.setErrmsg(jsonObject.getString("errmsg"));
			jsapiticket.setExpires_in(jsonObject.getInt("expires_in"));
			jsapiticket.setExpire_time(System.currentTimeMillis() + jsonObject.getInt("expires_in") * 1000);
			JsApiTicketCacheManager.getInstance().putJsApiTicket(jsapiticket);
			
		}
		
		
		return jsapiticket;
	}
	
	
	public static WeiXinSignature SignatureJSSDK(HttpServletRequest request,int storeId,String weixin_appid,String weixin_appsecret,String url) throws Exception{
		WeiXinSignature signature = new WeiXinSignature();
		
		AccessToken token = getAccessToken(storeId, weixin_appid, weixin_appsecret);
		JsApiTicket jsapiticket = getJsApiTicket(storeId, token.getAccess_token(),weixin_appid,weixin_appsecret);
				
		signature.setAppId(weixin_appid);
		signature.setTimestamp(String.valueOf(System.currentTimeMillis()/1000));
		signature.setNonceStr(ECommon.nonceStr(32));
		if(url == null || url.equals("")){
			url = request.getRequestURL().toString();
			if(request.getQueryString() != null){
				url += "?" + request.getQueryString().toString();
			}
		}
		signature.setUrl(url);
		System.out.println("signature url:"+url);
		String sign_before = "jsapi_ticket="+jsapiticket.getTicket()+
				"&noncestr="+signature.getNonceStr()+
				"&timestamp="+signature.getTimestamp()+
				"&url="+signature.getUrl();
		System.out.println("signature sign_before:"+sign_before);
		String sign = EncryptUtils.sha1(sign_before);
		System.out.println("signature sign:"+sign);
		signature.setSignature(sign);
		
		return signature;
	}
	
	
	/**
	 * 创建菜单
	 * @param id
	 */
	public static String menu_create(int id,String token,String json) throws Exception{
		
		String requestUrl = WXConstants.menu_create_url
				.replace("ACCESS_TOKEN", token);
		String request = EHttpClientUtil.httpPostEntity(requestUrl, json);
		log.info("创建菜单返回信息："+request);
		return request;
	}
	
	
	/**生成获取用户openid的地址
	 * @param weixin_appid
	 * @param SCOPE : [snsapi_base , snsapi_userinfo]
	 * @param REDIRECT_URI 
	 * @return
	 */
	public static String authorize_snsapi(String weixin_appid,String SCOPE,String REDIRECT_URI){
		String url = WXConstants.authorize.replace("APPID", weixin_appid).replace("SCOPE", SCOPE).replace("REDIRECT_URI", REDIRECT_URI);
		return url ;
	}
	
//	public static String ehais_authorize_snsapi(String weixin_appid,String SCOPE,String REDIRECT_URI){
//		String url = WXConstants.ehais_authorize.replace("APPID", weixin_appid).replace("SCOPE", SCOPE).replace("REDIRECT_URI", REDIRECT_URI);
//		return url ;
//	}
	
	public static OpenidInfo getOpenid(String code,String weixin_appid,String weixin_appsecret) throws Exception {
		//code从这里来https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9439cbf94f9235f0&redirect_uri=http://www.gz96833.com/test.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect
		OpenidInfo info = null;
		if(StringUtil.NullOrEqual(weixin_appsecret) || StringUtil.NullOrEqual(weixin_appid) || StringUtil.NullOrEqual(code) ){
			return null;
		}
		String requestUrl = WXConstants.get_opendid_url + "&appid=" + weixin_appid + "&secret=" + weixin_appsecret + "&code=" +code;
		log.info("请求openid:"+requestUrl);
		String request = EHttpClientUtil.methodGet(requestUrl);
		log.info("获取openid"+request);
		JSONObject jsonObject = JSONObject.fromObject(request);
		if(null != jsonObject){
			try {
				info = new OpenidInfo();
				info.setOpenid(jsonObject.getString("openid"));
				info.setExpires_in(jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				// 获取token失败
				log.error("获取openid失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		
		return info;
	}
	
	public static OpenidInfo getJsCode2SessionOpenid(String code,String weixin_appid,String weixin_appsecret) throws Exception {
		//code从这里来https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9439cbf94f9235f0&redirect_uri=http://www.gz96833.com/test.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect
		OpenidInfo info = null;
		if(StringUtil.NullOrEqual(weixin_appsecret) || StringUtil.NullOrEqual(weixin_appid) || StringUtil.NullOrEqual(code) ){
			return null;
		}
		String requestUrl = WXConstants.jscode2session.replace("APPID", weixin_appid).replace("SECRET", weixin_appsecret).replace("JSCODE", code);
		log.info("请求jscode2session.openid:"+requestUrl);
		String request = EHttpClientUtil.methodGet(requestUrl);
		log.info("获取jscode2session.openid"+request);
		JSONObject jsonObject = JSONObject.fromObject(request);
		if(null != jsonObject){
			try {
				info = new OpenidInfo();
				info.setOpenid(jsonObject.getString("openid"));
				info.setSession_key(jsonObject.getString("session_key"));
				info.setUnionid(jsonObject.getString("unionid"));
			} catch (Exception e) {
				// 获取token失败
				log.error("获取openid失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		
		return info;
	}
	
	public static WeiXinUserInfo getUserInfo(String access_token,String openid) throws Exception {
		WeiXinUserInfo userinfo = null;//new WeiXinUserInfo();
		String requestUrl = WXConstants.get_user_info.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		String request = EHttpClientUtil.methodGet(requestUrl);
		log.info("微信获取用户信息接口返回："+request);
		System.out.println("微信获取用户信息接口返回："+request);
		JSONObject jsonObject = JSONObject.fromObject(request);
		if(jsonObject!=null && !jsonObject.has("errcode")){
			
			Gson gson = new Gson();
			userinfo = gson.fromJson(request, WeiXinUserInfo.class);
			if(userinfo!=null)userinfo.setNickname(EmojiFilterUtils.filterEmoji(userinfo.getNickname()));
			
//			if(jsonObject.has("subscribe"))userinfo.setSubscribe(jsonObject.getInt("subscribe"));
//			if(jsonObject.has("openid"))userinfo.setOpenid(jsonObject.getString("openid"));
			
//			if(jsonObject.has("sex"))userinfo.setSex(jsonObject.getInt("sex"));
//			if(jsonObject.has("city"))userinfo.setCity(jsonObject.getString("city"));
//			if(jsonObject.has("country"))userinfo.setCountry(jsonObject.getString("country"));
//			if(jsonObject.has("province"))userinfo.setProvince(jsonObject.getString("province"));
//			if(jsonObject.has("language"))userinfo.setLanguage(jsonObject.getString("language"));
//			if(jsonObject.has("headimgurl"))userinfo.setHeadimgurl(jsonObject.getString("headimgurl"));
//			if(jsonObject.has("subscribe_time"))userinfo.setSubscribe_time(jsonObject.getLong("subscribe_time"));
//			if(jsonObject.has("unionid"))userinfo.setUnionid(jsonObject.getString("unionid"));
//			if(jsonObject.has("remark"))userinfo.setRemark(jsonObject.getString("remark"));
//			if(jsonObject.has("groupid"))userinfo.setGroupid(jsonObject.getInt("groupid"));

		}
		
		return userinfo;
	}
	
	
	public static WeiXinUserInfoBatch batchUserInfo(String access_token,String content) throws Exception {
		WeiXinUserInfoBatch batch = null;
		String requestUrl = WXConstants.batchget.replace("ACCESS_TOKEN", access_token);
		String request = EHttpClientUtil.httpPostEntity(requestUrl, content);
		log.info("微信批量获取用户信息接口返回："+request);
		System.out.println(" 微信批量获取用户信息接口返回："+request);
		JSONObject jsonObject = JSONObject.fromObject(request);
		if(jsonObject!=null && !jsonObject.has("errcode")){
			Gson gson = new Gson();
//	        List<Person> persons = gson.fromJson(json, new TypeToken<List<Person>>() {}.getType());//对于不是类的情况，用这个参数给出
			batch = gson.fromJson(request, WeiXinUserInfoBatch.class);
		}
		
		return batch;
	}
	
	/**
	 * 合成统一下单xml
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public static String fromUnifiedOrderXml(WeiXinUnifiedOrder order) throws Exception{
		XStream xStream = new XStream();
		xStream.alias("xml",WeiXinUnifiedOrder.class);
		String content = xStream.toXML(order);
		content = content.replaceAll("__", "_");
		return content;
	}
	
	public static WeiXinUnifiedOrderResult toUnifiedOrderResultXml(String content) throws Exception{
		XStream xStream = new XStream();
		xStream.alias("xml",WeiXinUnifiedOrderResult.class);
		WeiXinUnifiedOrderResult model = (WeiXinUnifiedOrderResult) xStream.fromXML(content);
		return model;
	}
	
	/** 统一下单提交接口
	 * @param order
	 * @return
	 */
	public static WeiXinUnifiedOrderResult WeiXinUnifiedOrderApi(WeiXinUnifiedOrder order){
		

		try {
			
	        String postDataXML =fromUnifiedOrderXml(order);
	        System.out.println(postDataXML);
	        log.info("统一下单提交数据接口:"+postDataXML);
	        //统一下订单
	        String resultXml = EHttpClientUtil.httpPostEntity(WXConstants.unifiedorder, postDataXML);
	        System.out.println("sys统一下单返回数据接口："+resultXml);
	        log.info("log统一下单返回数据接口:"+resultXml);
	        WeiXinUnifiedOrderResult result = toUnifiedOrderResultXml(resultXml);
	        
	        return result;
	        
    		
    		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static String fromNotifyPayXml(WeiXinNotifyPay bean) throws Exception{
		XStream xStream = new XStream();
		xStream.alias("xml",WeiXinNotifyPay.class);
		String content = xStream.toXML(bean);
		content = content.replaceAll("__", "_");
		return content;
	}
	
	public static WeiXinNotifyPay toNotifyPayXml(String content) throws Exception{
		XStream xStream = new XStream();
		xStream.alias("xml",WeiXinNotifyPay.class);
		WeiXinNotifyPay notity = (WeiXinNotifyPay) xStream.fromXML(content);
		return notity;
	}
	

	/**
	 * 上传图文消息内的图片获取URL
	 * @param token
	 * @param filePath
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String uploadImg(String token,
			String filePath ) throws ClientProtocolException, IOException{
		String url = WXConstants.upload_img
				.replaceAll("ACCESS_TOKEN", token);
		Map<String,String> fileMap = new HashMap<String,String>();
		fileMap.put("media", filePath);
		String reqData = EHttpClientUtil.postHttpClientFile(url, null, fileMap, null);
		System.out.println("uploadImg:"+reqData);
		return reqData;
		
	}
	
	
	/**
	 * 上传媒体
	 * @param token
	 * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param paramsMap
	 * @param fileMap
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String uploadMedia(String token,String type,
			Map<String, String> paramsMap , 
			Map<String, String> fileMap ) throws ClientProtocolException, IOException{
		String url = WXConstants.upload_media
				.replaceAll("ACCESS_TOKEN", token)
				.replaceAll("TYPE", type);
		
		String reqData = EHttpClientUtil.postHttpClientFile(url, paramsMap, fileMap, null);
		System.out.println("uploadMedia:"+reqData);
		return reqData;
		
	}
	
	//下载媒体
	public static String downloadMedia(String token, String media_id, String filename) throws Exception
	{
		String url = WXConstants.download_media
				.replace("ACCESS_TOKEN", token)
				.replace("MEDIA_ID", media_id);
		
		System.out.println("weixin download media:" + url);
		
		String nu = EHttpClientUtil.downloadFile(url, filename);
		
		return nu;
	}
	
	//上传图文素材
	public static String uploadnews(String token,List<WeiXinMPNews> news) throws Exception{
		String url = WXConstants.upload_news
				.replace("ACCESS_TOKEN", token);
		Map<String, List<WeiXinMPNews>> map = new HashMap<String, List<WeiXinMPNews>>();
		map.put("articles", news);
		JSONObject json = JSONObject.fromObject(map);
		
		return EHttpClientUtil.httpPostEntity(url, json.toString());
		
	}
	
	//根据分组群发消息
	public static String mass_sendall(String token,
			boolean is_to_all,
			Integer group_id,
			String msgtype,
			String media_id,
			String content
			) throws Exception{
		String url = WXConstants.mass_sendall.replace("ACCESS_TOKEN", token);
		Map<String, Object> map = new HashMap<String, Object>();
		
		Map<String, Object> filterMap = new HashMap<String, Object>();
		filterMap.put("is_to_all", is_to_all);
		if(!is_to_all)filterMap.put("group_id", group_id);
		map.put("filter", filterMap);
		
		Map<String , Object > typeValueMap = new HashMap<String, Object>();
		
		if(msgtype.equals("text")){
			typeValueMap.put("content", content);
		}else if(msgtype.equals("wxcard")){
			typeValueMap.put("card_id", media_id);
		}else{
			typeValueMap.put("media_id", media_id);
		}		
		
		map.put(msgtype, typeValueMap);
		
		map.put("msgtype", msgtype);
		
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json.toString());
		return EHttpClientUtil.httpPostEntity(url, json.toString());
		
	}
	
	
	public static String QRcode(String token,
			String action_name,//QR_SCENE为临时,QR_LIMIT_SCENE为永久,QR_LIMIT_STR_SCENE为永久的字符串参数值
			Integer expire_seconds,//该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒
			Integer scene_id,
			String scene_str
			) throws Exception {
		String url = WXConstants.qrcode.replace("TOKENPOST", token);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("action_name", action_name);
		map.put("expire_seconds", expire_seconds);
		Map<String, Object> action_info = new HashMap<String, Object>();
		Map<String, Object> scene = new HashMap<String, Object>();
		scene.put("scene_id", scene_id);
		scene.put("scene_str", scene_str);
		action_info.put("secne", scene);
		map.put("action_info", action_info);
		
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json.toString());
		return EHttpClientUtil.httpPostEntity(url, json.toString());
	}
	
	
	public static String ShortUrl(String token,
			String action,
			String long_url
			) throws Exception {
		String url = WXConstants.shorturl.replace("ACCESS_TOKEN", token);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("action", action);
		map.put("long_url", long_url);

		
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json.toString());
		return EHttpClientUtil.httpPostEntity(url, json.toString());
	}
	
	
	public static String MerchantInfo(String token) throws Exception {
		String url = WXConstants.merchantinfo.replace("TOKEN", token);
		System.out.println(url);
		
		return EHttpClientUtil.methodGet(url);
	}
	
	
	public static String TemplateSend(String token,WeiXinTemplateMessage template) throws Exception {
		String url = WXConstants.template_send.replace("ACCESS_TOKEN", token);
		System.out.println(url);
		
		JSONObject json = JSONObject.fromObject(template);
		System.out.println(json.toString());
		return EHttpClientUtil.httpPostEntity(url, json.toString());
	}
	
	
	//微信下载对帐单
	public static String downloadbill(String appid,String mch_id,String bill_date,String secret) throws Exception{
		WeiXinDownloadBill downloadbill = new WeiXinDownloadBill();
		downloadbill.setAppid(appid);
		downloadbill.setMch_id(mch_id);
		downloadbill.setNonce_str(ECommon.nonceStr(32));
		downloadbill.setSign_type("MD5");
		downloadbill.setBill_date(bill_date);
		downloadbill.setBill_type("SUCCESS");
		String sign = SignUtil.getSign(downloadbill, secret);
		downloadbill.setSign(sign);
		
		String content = XStreamUtil.toXml(downloadbill);
		
		System.out.println(content);
		return EHttpClientUtil.httpPostEntity(WXConstants.downloadbill, content);
		
	}
	
	//微信环境验证
	public static String getSignKey(String mch_id,String secret) throws Exception {
		WeiXinGetSignKey gsk = new WeiXinGetSignKey();		
		gsk.setMch_id(mch_id);
		gsk.setNonce_str(ECommon.nonceStr(32));
		String sign = SignUtil.getSign(gsk, secret);
		gsk.setSign(sign);
		
		String content = XStreamUtil.toXml(gsk);
		
		System.out.println(content);
		return EHttpClientUtil.httpPostEntity(WXConstants.getsignkey, content);
		
	}
	
	//微信提现发送
	public static String transfers(String content,String weixin_p12_path, String weixin_mch_id) throws Exception {
		return EHttpClientUtil.ClientCustomSSL(WXConstants.transfers, content,weixin_p12_path,weixin_mch_id);
	}
	
	public static void main(String[] args) {
		try {
//			String mch_id = "1480510742";
//			String secret = "EhaisTylerEllen123456789LGJ628ok";
			String mch_id = "1340156101";
			String secret = "EhaisZkef1234567890oktylerokokok";
			
			String res = WeiXinUtil.getSignKey(mch_id, secret);
			System.out.println(res);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
