package org.ehais.shop.controller.admin;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.shop.service.HaiOrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@EPermissionModuleGroup(name="模组")

@EPermissionController(name="采购退货管理",value="haiOrderPurchaseBackController")
@Controller
@RequestMapping("/admin")
public class HaiOrderPurchaseBackController extends CommonController{
	private static Logger log = LoggerFactory.getLogger(HaiOrderPurchaseBackController.class);

	@Autowired
	private HaiOrderInfoService orderinfoService;
}
