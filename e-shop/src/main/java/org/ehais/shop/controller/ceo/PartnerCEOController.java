package org.ehais.shop.controller.ceo;

import org.ehais.annotation.EPermissionController;
import org.ehais.controller.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@EPermissionController(intro="基础数据",value="PartnerCEOController")
@Controller
@RequestMapping("/ceo")
public class PartnerCEOController extends CommonController{

}
