package org.ehais.shop.controller.api.include;

import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.shop.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderInfoIController extends CommonController{

	@Autowired
	protected OrderInfoService orderinfoService;
	@Autowired
	protected HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	protected EStoreService eStoreService;
	@Autowired
	protected EWPPublicService eWPPublicService;
	
}
