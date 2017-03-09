package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiShippingMapper;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.model.HaiShippingExample;
import org.ehais.shop.model.HaiShippingWithBLOBs;
import org.ehais.shop.model.OrderDoneParam;
import org.ehais.shop.service.ShoppingService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

@Service("shippingService")
public class ShoppingServiceImpl extends CommonServiceImpl implements ShoppingService {

	@Override
	public ReturnObject<OrderDoneParam> OrderDone(HttpServletRequest request,
			OrderDoneParam order_done) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
