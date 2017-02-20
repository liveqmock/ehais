package org.ehais.weixin.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiUsers;
import org.ehais.tools.ReturnObject;

public interface LoginService {

	public ReturnObject<EHaiUsers> login(HttpServletRequest request ,String userName,String password)throws Exception;
	
	public ReturnObject<EHaiUsers> register(String userName,
			String email,
			String password,
			String confirmPassword,
			String verificationCode,
			String sessionCode)throws Exception;
	
	
}
