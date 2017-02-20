package org.ehais.epublic.model.paybill;

import java.io.UnsupportedEncodingException;

import org.ehais.util.EncryptUtils;

/** 
 * 快钱接口RESPONSE 
 */  
public class PayBillResponse {  
    private String merchant_key = "99billKeyForTest";  
    private String merchant_id = "";// 获取商户编号  
    private String orderid = "";// 获取订单编号  
    private String amount = "";// 获取订单金额  
    private String dealdate = "";// 获取交易日期  
    private String succeed = "";// 获取交易结果,Y成功,N失败  
    private String mac = "";// 获取安全加密串  
    private String merchant_param = "";// 获取商户私有参数  
    private String couponid = "";// 获取优惠券编码  
    private String couponvalue = "";// 获取优惠券面额  
    public String getMerchant_key() {  
        return merchant_key;  
    }  
    public void setMerchant_key(String merchant_key) {  
        this.merchant_key = merchant_key;  
    }  
    public String getMerchant_id() {  
        return merchant_id;  
    }  
    public void setMerchant_id(String merchant_id) {  
        this.merchant_id = merchant_id;  
    }  
    public String getOrderid() {  
        return orderid;  
    }  
    public void setOrderid(String orderid) {  
        this.orderid = orderid;  
    }  
    public String getAmount() {  
        return amount;  
    }  
    public void setAmount(String amount) {  
        this.amount = amount;  
    }  
    public String getDealdate() {  
        return dealdate;  
    }  
    public void setDealdate(String dealdate) {  
        this.dealdate = dealdate;  
    }  
    public String getSucceed() {  
        return succeed;  
    }  
    public void setSucceed(String succeed) {  
        this.succeed = succeed;  
    }  
    public String getRemoteMac() {  
        return mac;  
    }  
    public void setMac(String mac) {  
        this.mac = mac;  
    }  
    public String getMerchant_param() {  
        return merchant_param;  
    }  
    public void setMerchant_param(String merchant_param) {  
        this.merchant_param = merchant_param;  
    }  
    public String getCouponid() {  
        return couponid;  
    }  
    public void setCouponid(String couponid) {  
        this.couponid = couponid;  
    }  
    public String getCouponvalue() {  
        return couponvalue;  
    }  
    public void setCouponvalue(String couponvalue) {  
        this.couponvalue = couponvalue;  
    }  
    public String getLocalMac() throws UnsupportedEncodingException{  
        String ScrtStr = "merchant_id="+this.merchant_id+"&orderid="+this.orderid+"&amount="+this.amount+"&date="+this.dealdate+"&succeed="+this.succeed+"&merchant_key="+this.merchant_key;  
        String rest = EncryptUtils.md5(ScrtStr);
        return rest;  
    }  
} 
