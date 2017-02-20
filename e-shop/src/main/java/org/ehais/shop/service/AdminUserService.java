package org.ehais.shop.service;

import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface AdminUserService extends CommonService {

	public ReturnObject<EHaiAdminUser> admin_login(String username,String password) throws Exception;
	
}
