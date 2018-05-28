package org.ehais.weixin.model;

public class WeiXinTransfers {
	
	private String mch_appid;//申请商户号的appid或商户号绑定的appid
	private String mchid;//微信支付分配的商户号
	private String device_info;//微信支付分配的终端设备号
	private String nonce_str;//随机字符串
	private String sign;//签名
	private String partner_trade_no;//商户订单号，需保持唯一性
	private String openid;//商户appid下
	private String check_name;//NO_CHECK：不校验真实姓名 ;FORCE_CHECK：强校验真实姓名
	private String re_user_name;//收款用户真实姓名
	private Integer amount;//企业付款金额，单位为分
	private String desc;//企业付款操作说明信息
	private String spbill_create_ip;//用户端或者服务端的IP
	

}
