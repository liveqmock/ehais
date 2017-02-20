package org.ehais.shop.controller.api.include;

import org.ehais.controller.CommonController;
import org.ehais.epublic.service.EUsersService;
import org.ehais.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberIController extends CommonController{

	@Autowired
	protected EUsersService usersService;
	
	@Autowired
	protected MemberService memberService;
	
}
