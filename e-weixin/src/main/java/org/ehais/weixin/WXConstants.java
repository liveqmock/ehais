package org.ehais.weixin;

import java.io.InputStream;
import java.util.Properties;

public class WXConstants {
	Properties prop = new Properties();
	InputStream ins = getClass().getResourceAsStream("/config/config.properties"); 
//	prop.load(ins);
	
//	public static String weixin_appid = ResourceUtil.getProValue("weixin_appid");
//	public static String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
//	public static String weixin_mch_id = ResourceUtil.getProValue("weixin_mch_id");
//	public static String weixin_mch_secret = ResourceUtil.getProValue("weixin_mch_secret");
	public static String wxdev_token = "ehais_wxdev";//ResourceUtil.getProValue("wxdev_token");

	
	//*******************************************
	//*******************************************
	//*******************************************
//	组成可以获取授权的链接
//	public static String authorize = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	public static String authorize = "http://mg.ehais.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
//  获取access_token的接口地址（GET） 限200（次/天）
//	public static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static String access_token_url = "http://mg.ehais.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET&store_id=STORE_ID";
	// 获取getJsApiTicket
//	public static String get_jsapi_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=ACCESS_TOKEN&type=jsapi";
	public static String get_jsapi_url = "http://mg.ehais.com/cgi-bin/ticket/getticket?type=jsapi&access_token=ACCESS_TOKEN&type=jsapi&store_id=STORE_ID";
		
	
	
	
	
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	
	// 获取opendid的接口地址
	public static String get_opendid_url = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
	// 获取用户信息，并判断是否关注此微信号
	public static String get_user_info = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	//批量获取用户信息
	public static String batchget = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
	// 获取ip
	public static String get_call_back_ip = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
	// 统一下单
	public static String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 退款申请
	public static String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	// 退款查询
	public static String refund_query_url = "https://api.mch.weixin.qq.com/pay/refundquery";

	// 发送红包
	public static String send_red_pack = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";

	// 上传媒体
	public static String upload_media = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	// 下载媒体
	public static String download_media = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	//上传图片
	public static String upload_img = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	
	//上传图文消息
	public static String upload_news = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	
	//
	
	//创建微信分组
	public static String groups_create = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	//修改微信分组
	public static String groups_update = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	//删除微信分组
	public static String groups_delete = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";
	
	//根据分组进行群发消息
	public static String mass_sendall = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	
	//创建二维码
	public static String qrcode = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
	
	//创建短码
	public static String shorturl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
	
	//使用该接口，商户可获取账号下的类目与号段等信息
	public static String merchantinfo = "https://api.weixin.qq.com/scan/merchantinfo/get?access_token=TOKEN";
	
	//消息模板发送
	public static String template_send = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	//新增临时素材
	public static String temp_media_upload = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	
	//获取临时素材
	public static String get_temp_media = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	
	//下载对帐单
	public static String downloadbill = "https://api.mch.weixin.qq.com/pay/downloadbill";
	
//	调用微信支付沙箱环境的API接口验证 
	public static String getsignkey = "https://apitest.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
	
	/**
	 * 商户银行类接口
	 * ****************************************************************
	 * ****************************************************************
	 * ****************************************************************
	 */
	
	//用于企业向微信用户个人付款
	public static String transfers = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	
	
	//小程序登录接口
	public static String jscode2session = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
	
	
	
}








