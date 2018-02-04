package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.shop.model.vtu.VtuShare;
import org.ehais.tools.ReturnObject;

public interface VtuService {
	
	public ReturnObject<VtuShare> vtuMessage(HttpServletRequest request,Long vtuId)throws Exception;
	public String vtuMessage(String appdomain,String vtujson,String vtime)throws Exception;
	
}
