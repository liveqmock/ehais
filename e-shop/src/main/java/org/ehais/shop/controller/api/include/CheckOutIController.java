package org.ehais.shop.controller.api.include;

import org.ehais.controller.CommonController;
import org.ehais.shop.service.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckOutIController extends CommonController{

	@Autowired
	protected CheckOutService checkOutService;
	
	
}
