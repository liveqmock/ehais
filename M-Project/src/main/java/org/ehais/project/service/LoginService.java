package org.ehais.project.service;

import org.ehais.project.model.HaiUsers;
import org.ehais.tools.ReturnObject;

public interface LoginService {

	public ReturnObject<HaiUsers> login(String userName,String password)throws Exception;
	
	public ReturnObject<HaiUsers> register(String userName,
			String email,
			String password,
			String confirmPassword,
			String verificationCode,
			String sessionCode)throws Exception;
	
	
}
