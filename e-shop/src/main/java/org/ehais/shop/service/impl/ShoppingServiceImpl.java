package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.enums.OrderStatusEnum;
import org.ehais.enums.PayStatusEnum;
import org.ehais.enums.ShippingStatusEnum;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiCartMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiPaymentMapper;
import org.ehais.shop.mapper.HaiShippingMapper;
import org.ehais.shop.mapper.HaiUserAddressMapper;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.shop.model.HaiPaymentWithBLOBs;
import org.ehais.shop.model.HaiShippingWithBLOBs;
import org.ehais.shop.model.HaiUserAddress;
import org.ehais.shop.model.OrderDoneParam;
import org.ehais.shop.service.ShoppingService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

@Service("shoppingService")
public class ShoppingServiceImpl extends CommonServiceImpl implements ShoppingService {

	@Autowired
	private HaiShippingMapper haiShippingMapper;
	@Autowired
	private HaiPaymentMapper haiPaymentMapper;
	@Autowired
	private HaiUserAddressMapper haiUserAddressMapper;
	@Autowired
	private HaiCartMapper haiCartMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	
	
	
	
	@Override
	public ReturnObject<OrderDoneParam> OrderDone(HttpServletRequest request,
			OrderDoneParam order_done) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<OrderDoneParam> rm = new ReturnObject<OrderDoneParam>();
		rm.setCode(0);
		
		HaiShippingWithBLOBs ship = null;
		//运输方式不为空，检验运输方式是否正确
		if(order_done.getShipping_id()!=null && order_done.getShipping_id()!=0){
			ship = haiShippingMapper.web_shipping_info(order_done.getStore_id(), order_done.getShipping_id());
//			if(ship != null){
//				rm.setMsg("运输方式错误");return rm;
//			}
		}
				
		HaiPaymentWithBLOBs payment = null;
		//支付方式不为空的情况，检验支付方式是否正确
		if(order_done.getPay_id()!=null && order_done.getPay_id()!=0){
			payment = haiPaymentMapper.payment_info(order_done.getStore_id(), order_done.getPay_id());
//			if(payment != null){
//				rm.setMsg("支付方式错误");return rm;
//			}
		}
		
		HaiUserAddress userAddress = null;
		//配送地址不为空的情况，检验配送地址是否此商家
		if(order_done.getAddress_id()!=null && order_done.getAddress_id()!=0){
			userAddress = haiUserAddressMapper.get_hai_user_address_info(order_done.getUser_id(), order_done.getAddress_id());
//			if(userAddress != null){
//				rm.setMsg("支付方式错误");return rm;
//			}
		}
		
		
		//读取购物车的信息是否正确
		if(!RequestUtils.sqlValidate(order_done.getRec_id_data())){
			rm.setMsg("参数不安全");return rm;
		}
		List<HaiCart> cart_list = haiCartMapper.cart_list(order_done.getStore_id(), order_done.getUser_id(), order_done.getRec_id_data());
		if(cart_list == null || cart_list.size() != order_done.getRec_id_data().split(",").length){
			rm.setMsg("购物车记录不正确");return rm;
		}
		
		//兼判断商品编号
		List<Long> goodsIds = new ArrayList<Long>();
		for (HaiCart haiCart : cart_list) {
			goodsIds.add(haiCart.getGoodsId());
		}
		if(goodsIds.size() == 0){
			rm.setMsg("无购物商品");return rm;
		}
		//检验商品信息是否正确
		HaiGoodsExample goodsExample = new HaiGoodsExample();
		goodsExample.createCriteria()
		.andStoreIdEqualTo(order_done.getStore_id())
		.andGoodsIdIn(goodsIds)
		.andIsDeleteEqualTo(false)
		.andIsOnSaleEqualTo(true);
		List<HaiGoods> goodsList = haiGoodsMapper.selectByExample(goodsExample);
		if(goodsList.size() != goodsIds.size()){
			rm.setMsg("商品数量不正确");return rm;
		}
		
		Integer totalPrice = 0 ;//以分为单位
		//根据商品取价
		for (HaiCart cart : cart_list) {
			for (HaiGoods goods : goodsList){
				if(cart.getGoodsId() == goods.getGoodsId()){
					if(goods.getGoodsNumber() == 0 || goods.getGoodsNumber() < cart.getGoodsNumber()){
						rm.setMsg("商品数量不足");return rm;
					}
					totalPrice += cart.getGoodsNumber() * goods.getShopPrice();//以分为单位
					continue;
				}
			}
		}
		
		Date date = new Date();
		//插入订单主表
		HaiOrderInfo orderInfo = new HaiOrderInfo();
		String orderSn = DateUtil.formatDate(date, DateUtil.FORMATSTR_4) + ECommon.nonceInt(4) + order_done.getUser_id().toString();
		orderInfo.setOrderSn(orderSn);
		orderInfo.setUserId(order_done.getUser_id());
		orderInfo.setOrderStatus(OrderStatusEnum.init);
		orderInfo.setShippingStatus(ShippingStatusEnum.init);
		orderInfo.setPayStatus(PayStatusEnum.init);
		if(userAddress!=null){
			orderInfo.setConsignee(userAddress.getConsignee());
			orderInfo.setCountry(userAddress.getCountry());
			orderInfo.setProvince(userAddress.getProvince());
			orderInfo.setCity(userAddress.getCity());
			orderInfo.setDistrict(userAddress.getDistrict());
			orderInfo.setAddress(userAddress.getAddress());
			orderInfo.setZipcode(userAddress.getZipcode());
			orderInfo.setTel(userAddress.getTel());
			orderInfo.setMobile(userAddress.getMobile());
			orderInfo.setEmail(userAddress.getEmail());
			orderInfo.setBestTime(userAddress.getBestTime());
			orderInfo.setSignBuilding(userAddress.getSignBuilding());
		}
		orderInfo.setPostscript(order_done.getPostscript());//订单附言
		if(ship!=null){
			orderInfo.setShippingId(ship.getShippingId());
			orderInfo.setShippingName(ship.getShippingName());
		}
		if(payment != null){
			orderInfo.setPayId(payment.getPayId());
			orderInfo.setPayName(payment.getPayName());
		}
		orderInfo.setInvPayee(order_done.getInvPayee());
		orderInfo.setGoodsAmount(totalPrice);//总价钱
		orderInfo.setAddTime(date);
		
		//插入订单商品表
		
		
		//清空购物车
		
		
		//判断支付方式，整理支付的运算逻辑
		
		
		//跳转支付信息返回
		
		
		
		return null;
	}


}
