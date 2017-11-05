package org.ehais.weixin.utils;

import java.util.HashMap;
import java.util.Map;

import org.ehais.weixin.model.WeiXinTemplateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeiXinTemplateMessageUtils {
	private static Logger log = LoggerFactory.getLogger(WeiXinTemplateMessageUtils.class);
	
	
	public static String sendTemplateMessage(
			Integer store_id,
			String appId,
			String secret,
			String templateId,
			String toUserOpenId,
			String url,			
			String firstMsg,
			Map<String,String> keyword,
			String remarkMsg
			) throws Exception{
		
		WeiXinTemplateMessage template = new WeiXinTemplateMessage();
		template.setTemplate_id(templateId);//订单支付成功通知
		template.setTouser(toUserOpenId);
		template.setUrl(url);
		template.setTopcolor("#FF0000");
		
		Map<String,Object> mapTemplateUser = new HashMap<String,Object>();
        
		Map<String,String> first = new HashMap<String,String>();
		first.put("value", firstMsg);
		first.put("color", "#173177");
		mapTemplateUser.put("first", first);
		
		for (Map.Entry<String,String> m : keyword.entrySet()) {
			
			Map<String,String> k = new HashMap<String,String>();
			k.put("value", m.getValue());
			k.put("color", "#173177");
			mapTemplateUser.put(m.getKey(), k);
			
		}
		
		Map<String,String> remark = new HashMap<String,String>();
		remark.put("value", remarkMsg);
		remark.put("color", "#173177");
		mapTemplateUser.put("remark", remark);
		
		template.setData(mapTemplateUser);
		
		return WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(store_id, appId, secret).getAccess_token(), template);
		
	}

}
