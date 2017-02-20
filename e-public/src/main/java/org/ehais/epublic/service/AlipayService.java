package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.alipay.AlipayNotifyModel;
import org.ehais.epublic.model.alipay.AlipayReturnModel;
import org.ehais.tools.ReturnObject;

public interface AlipayService {
	
	public ReturnObject<AlipayReturnModel> ReturnUrl(HttpServletRequest request,AlipayReturnModel returnModel)throws Exception;
	
	public ReturnObject<AlipayNotifyModel> NotifyUrl(HttpServletRequest request,AlipayNotifyModel returnModel)throws Exception;
	
}
