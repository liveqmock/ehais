package org.ehais.epublic.model.alipay;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AlipayRequest {

	private String out_trade_no;//商户订单号，商户网站订单系统中唯一订单号，必填
	private String subject;//订单名称，必填
	private Double total_fee;//付款金额，必填	
	private String body;//商品描述，可空
	private String seller_email;//卖家
	
	private String key;
	private String sign_type;
	private String log_path;
	private String service;
	private String partner;
	private String seller_id;
	private String _input_charset;
	private String payment_type;
	private String notify_url;
	private String return_url;
	private String anti_phishing_key;
	private String exter_invoke_ip;
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getAnti_phishing_key() {
		return anti_phishing_key;
	}
	public void setAnti_phishing_key(String anti_phishing_key) {
		this.anti_phishing_key = anti_phishing_key;
	}
	public String getExter_invoke_ip() {
		return exter_invoke_ip;
	}
	public void setExter_invoke_ip(String exter_invoke_ip) {
		this.exter_invoke_ip = exter_invoke_ip;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getLog_path() {
		return log_path;
	}
	public void setLog_path(String log_path) {
		this.log_path = log_path;
	}	
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
//        	if(field.getName().equals("sign"))continue;
            
            try {
            	Object obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj.toString());
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	
}
