package org.ehais.epublic.model.paybill;

import java.io.UnsupportedEncodingException;

import org.ehais.util.EncryptUtils;

/** 
 * 快钱接口REQUEST 
 */ 
public class PayBillRequest {

	/** 
     * 商户编号 
     */  
    private String merchant_id = "";  
    /** 
     * 商户密钥 
     */  
    private String merchant_key = "";  
    /** 
     * 订单编号 
     */  
    private String orderid = "";  
    /** 
     * 订单金额 
     */  
    private Double amount = 0d;  
    /** 
     * 货币类型,1为人民币 
     */  
    private String curr = "1";  
    /** 
     * 是否安全校验,2为必校验,推荐 
     */  
    private String isSupportDES = "2";  
    /** 
     * 支付结果返回地址 
     */  
    private String merchant_url = "";  
    /** 
     * 支付人真实姓名 
     */  
    private String pname = "";  
    /** 
     * 商品信息 
     */  
    private String commodity_info = "";  
    /** 
     * 商户私有参数 
     */  
    private String merchant_param = "";  
    /** 
     * 传递email到快钱网关页面 
     */  
    private String pemail = "";  
    /** 
     * 代理/合作伙伴商户编号 
     */  
    private String pid = "";  
    /** 
     * 加密串 
     */  
    private String mac = "";  
    public String getMerchant_id() {  
        return merchant_id;  
    }  
    public void setMerchant_id(String merchant_id) {  
        this.merchant_id = merchant_id;  
    }  
    public String getMerchant_key() {  
        return merchant_key;  
    }  
    public void setMerchant_key(String merchant_key) {  
        this.merchant_key = merchant_key;  
    }  
    public String getOrderid() {  
        return orderid;  
    }  
    public void setOrderid(String orderid) {  
        this.orderid = orderid;  
    }  
    public Double getAmount() {  
        return amount;  
    }  
    public void setAmount(Double amount) {  
        this.amount = amount;  
    }  
    public String getCurr() {  
        return curr;  
    }  
    public void setCurr(String curr) {  
        this.curr = curr;  
    }  
    public String getIsSupportDES() {  
        return isSupportDES;  
    }  
    public void setIsSupportDES(String isSupportDES) {  
        this.isSupportDES = isSupportDES;  
    }  
    public String getMerchant_url() {  
        return merchant_url;  
    }  
    public void setMerchant_url(String merchant_url) {  
        this.merchant_url = merchant_url;  
    }  
    public String getPname() {  
        return pname;  
    }  
    public void setPname(String pname) {  
        this.pname = pname;  
    }  
    public String getCommodity_info() {  
        return commodity_info;  
    }  
    public void setCommodity_info(String commodity_info) {  
        this.commodity_info = commodity_info;  
    }  
    public String getMerchant_param() {  
        return merchant_param;  
    }  
    public void setMerchant_param(String merchant_param) {  
        this.merchant_param = merchant_param;  
    }  
    public String getPemail() {  
        return pemail;  
    }  
    public void setPemail(String pemail) {  
        this.pemail = pemail;  
    }  
    public String getPid() {  
        return pid;  
    }  
    public void setPid(String pid) {  
        this.pid = pid;  
    }  
    /** 
     * 这个方法一定要最后调用 
     * 生成加密串,注意顺序 
     * @throws UnsupportedEncodingException 
     */  
    public String getMac() throws UnsupportedEncodingException {  
        String ScrtStr = "merchant_id=" + this.merchant_id + "&orderid=" + this.orderid  
        + "&amount=" + this.amount + "&merchant_url=" + this.merchant_url  
        + "&merchant_key=" + this.merchant_key;  
        mac = EncryptUtils.md5(ScrtStr);
        
        return mac;  
    }  
} 
