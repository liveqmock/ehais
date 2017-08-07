package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.service.CommonService;
import org.ehais.shop.model.tp.TpAdmin;
import org.ehais.tools.ReturnObject;

public interface AdminUserService extends CommonService {

	public ReturnObject<EHaiAdminUser> admin_login(String username,String password) throws Exception;
	
	public ReturnObject<TpAdmin> login_submit(HttpServletRequest request,String username,String password,String verificationcode) throws Exception;
	
	
}
