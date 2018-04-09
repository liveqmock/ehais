package org.ehais.shop.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.ECommonMapper;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.controller.api.include.GoodsIController;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.QiniuUtil;
import org.ehais.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ebms")
public class GoodsEBMSController extends GoodsIController{

	
	
}
