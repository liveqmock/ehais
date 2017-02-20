package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.unionpay.UnionpayRcvResponse;
import org.ehais.tools.ReturnObject;

public interface UnionPayService {
	
	public ReturnObject<Object> BackRcvResponse(HttpServletRequest request,UnionpayRcvResponse unionpayRcvResponse) throws Exception;
	
	public ReturnObject<Object> FrontRcvResponse(HttpServletRequest request,UnionpayRcvResponse unionpayRcvResponse) throws Exception;
	
}
