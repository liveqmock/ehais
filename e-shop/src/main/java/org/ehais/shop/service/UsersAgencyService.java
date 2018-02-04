package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiUsers;
import org.ehais.tools.ReturnObject;

public interface UsersAgencyService {

	public ReturnObject<EHaiUsers> agencyLogin(HttpServletRequest request,String username,String password,String verificationcode) throws Exception;
	
}
