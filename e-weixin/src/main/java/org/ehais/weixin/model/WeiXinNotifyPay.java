package org.ehais.weixin.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class WeiXinNotifyPay implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1692010165785385960L;
	private String return_code;
	private String return_msg;
	private String appid;
	private String mch_id;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String sign_type;
	private String result_code;
	private String err_code;
	private String err_code_des;
	private String openid;
	private String is_subscribe;
	private String trade_type;
	private String bank_type;
	private Integer total_fee;
	private Integer settlement_total_fee;
	private String fee_type;
	private Integer cash_fee;
	private String cash_fee_type;
	private Integer coupon_fee;
	private Integer coupon_count;
	private String coupon_id_0;
	private Integer coupon_fee_0;
	private String coupon_id_1;	
	private Integer coupon_fee_1;
	private String coupon_id_2;	
	private Integer coupon_fee_2;
	private String coupon_id_3;	
	private Integer coupon_fee_3;
	private String coupon_id_4;	
	private Integer coupon_fee_4;
	private String coupon_id_5;	
	private Integer coupon_fee_5;
	private String transaction_id;
	private String out_trade_no;
	private String attach;
	private Long time_end;
	
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public Integer getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getCash_fee_type() {
		return cash_fee_type;
	}
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}
	public Integer getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(Integer coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public Integer getCoupon_count() {
		return coupon_count;
	}
	public void setCoupon_count(Integer coupon_count) {
		this.coupon_count = coupon_count;
	}
	public String getCoupon_id_1() {
		return coupon_id_1;
	}
	public void setCoupon_id_1(String coupon_id_1) {
		this.coupon_id_1 = coupon_id_1;
	}
	public Integer getCoupon_fee_1() {
		return coupon_fee_1;
	}
	public void setCoupon_fee_1(Integer coupon_fee_1) {
		this.coupon_fee_1 = coupon_fee_1;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public Long getTime_end() {
		return time_end;
	}
	public void setTime_end(Long time_end) {
		this.time_end = time_end;
	}	
    public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public Integer getSettlement_total_fee() {
		return settlement_total_fee;
	}
	public void setSettlement_total_fee(Integer settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}
	public String getCoupon_id_0() {
		return coupon_id_0;
	}
	public void setCoupon_id_0(String coupon_id_0) {
		this.coupon_id_0 = coupon_id_0;
	}
	public Integer getCoupon_fee_0() {
		return coupon_fee_0;
	}
	public void setCoupon_fee_0(Integer coupon_fee_0) {
		this.coupon_fee_0 = coupon_fee_0;
	}
	public String getCoupon_id_2() {
		return coupon_id_2;
	}
	public void setCoupon_id_2(String coupon_id_2) {
		this.coupon_id_2 = coupon_id_2;
	}
	public Integer getCoupon_fee_2() {
		return coupon_fee_2;
	}
	public void setCoupon_fee_2(Integer coupon_fee_2) {
		this.coupon_fee_2 = coupon_fee_2;
	}
	public String getCoupon_id_3() {
		return coupon_id_3;
	}
	public void setCoupon_id_3(String coupon_id_3) {
		this.coupon_id_3 = coupon_id_3;
	}
	public Integer getCoupon_fee_3() {
		return coupon_fee_3;
	}
	public void setCoupon_fee_3(Integer coupon_fee_3) {
		this.coupon_fee_3 = coupon_fee_3;
	}
	public String getCoupon_id_4() {
		return coupon_id_4;
	}
	public void setCoupon_id_4(String coupon_id_4) {
		this.coupon_id_4 = coupon_id_4;
	}
	public Integer getCoupon_fee_4() {
		return coupon_fee_4;
	}
	public void setCoupon_fee_4(Integer coupon_fee_4) {
		this.coupon_fee_4 = coupon_fee_4;
	}
	public String getCoupon_id_5() {
		return coupon_id_5;
	}
	public void setCoupon_id_5(String coupon_id_5) {
		this.coupon_id_5 = coupon_id_5;
	}
	public Integer getCoupon_fee_5() {
		return coupon_fee_5;
	}
	public void setCoupon_fee_5(Integer coupon_fee_5) {
		this.coupon_fee_5 = coupon_fee_5;
	}
	public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
        	if(field.getName().equals("sign"))continue;
            
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
