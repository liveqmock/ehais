package org.ehais.project.service;

import org.ehais.project.model.HaiAdminUser;
import org.ehais.tools.ReturnObject;

public interface HaiAdminUserService {

	public ReturnObject<HaiAdminUser> login(String userName,String password)throws Exception;
	
}
