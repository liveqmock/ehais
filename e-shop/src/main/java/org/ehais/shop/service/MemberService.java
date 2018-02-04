package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiUsers;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface MemberService extends CommonService{
	//会员信息
	public ReturnObject<EHaiUsers> member(HttpServletRequest request,Long user_id,String token);
	//会员签到
	public ReturnObject<EHaiUsers> sign(HttpServletRequest request,Long user_id,String token);
	
}
