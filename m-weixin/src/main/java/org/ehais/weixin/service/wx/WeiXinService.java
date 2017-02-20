package org.ehais.weixin.service.wx;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WeiXinNotifyPay;
import org.ehais.weixin.model.WeiXinNotityXml;
import org.ehais.weixin.model.WpPublic;
import org.ehais.weixin.model.WpPublicWithBLOBs;

public interface WeiXinService extends CommonService {

	public ReturnObject<WeiXinNotifyPay> notifyPay(WeiXinNotifyPay notifyPay)throws Exception;
	
	public ReturnObject<Object> wxIndex(Integer id)throws Exception;
	
	/**根据微编号获取微商户号
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer wxStoreId(Integer wxid)throws Exception;
	
	public WpPublicWithBLOBs getWpPublic(Integer wxid)throws Exception;
	public void setWpPublic(Integer wxid,WpPublicWithBLOBs wpPublic) throws Exception;
	
	//接收微信事件的回复
	public String WeiXinNotityEvent(HttpServletRequest request, Integer wxid , WeiXinNotityXml notity)throws Exception;
	
}
