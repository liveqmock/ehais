package org.ehais.weixin.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class WeiXinWCPay {

	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String pack_age;
	private String signType;
	private String paySign;
	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getPack_age() {
		return pack_age;
	}
	public void setPack_age(String pack_age) {
		this.pack_age = pack_age;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	
	

    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                	if(field.getName().equals("pack_age")){
                		map.put("package", obj);
                	}else{
                		map.put(field.getName(), obj);
                	}
                    
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
