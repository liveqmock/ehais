package org.ehais.shop.controller.api.include;

import org.ehais.controller.CommonController;
import org.ehais.epublic.service.ERegionService;
import org.ehais.epublic.service.EUsersService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserIController extends CommonController{

	@Autowired
	protected EUsersService eUsersService;
	@Autowired
	protected ERegionService eRegionService;
	
	
}
