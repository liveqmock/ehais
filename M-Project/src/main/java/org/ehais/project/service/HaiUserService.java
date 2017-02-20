package org.ehais.project.service;

import org.ehais.project.model.HaiUsers;
import org.ehais.tools.ReturnObject;

public interface HaiUserService {
	
	public HaiUsers getUserById(Integer userId)throws Exception;
	
	//登录
	public ReturnObject<HaiUsers> login(String username,String password) throws Exception;
	
	
	public ReturnObject<HaiUsers> register(String username,String password) throws Exception;
	

}
