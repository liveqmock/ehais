package com.ehais.tracking.service.web;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface IndexService extends CommonService{

	public ReturnObject<Object> index(HttpServletRequest request) throws Exception ;
	
}
