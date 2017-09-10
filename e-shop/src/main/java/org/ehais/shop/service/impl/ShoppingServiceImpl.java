package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.enums.EIsVoidEnum;
import org.ehais.enums.EOrderClassifyEnum;
import org.ehais.enums.EOrderSourceEnum;
import org.ehais.enums.EOrderStatusEnum;
import org.ehais.enums.EPayStatusEnum;
import org.ehais.enums.EShippingStatusEnum;
import org.ehais.epublic.mapper.EHaiRegionMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiRegion;
import org.ehais.epublic.model.EHaiRegionExample;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.service.WeiXinPayService;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiCartMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiOrderGoodsMapper;
import org.ehais.shop.mapper.HaiOrderInfoMapper;
import org.ehais.shop.mapper.HaiPaymentMapper;
import org.ehais.shop.mapper.HaiShippingMapper;
import org.ehais.shop.mapper.HaiUserAddressMapper;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCartExample;
import org.ehais.shop.model.HaiCartWithBLOBs;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.shop.model.HaiOrderInfoWithBLOBs;
import org.ehais.shop.model.HaiPayment;
import org.ehais.shop.model.HaiPaymentWithBLOBs;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.model.HaiShippingWithBLOBs;
import org.ehais.shop.model.HaiUserAddress;
import org.ehais.shop.model.HaiUserAddressExample;
import org.ehais.shop.model.OrderDoneParam;
import org.ehais.shop.service.ShoppingService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.RequestUtils;
import org.ehais.weixin.model.WeiXinWCPay;
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
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;
	@Autowired
	private EHaiRegionMapper eHaiRegionMapper;
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private WeiXinPayService weiXinPayService;
	

	@Override
	public ReturnObject<Object> checkout(HttpServletRequest request,
			String recIds, Long user_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(0);
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		
		
		if(user_id == null || user_id == 0){
			rm.setMsg("非客户端使用");
			return rm;
		}
		
		if(recIds == null || recIds.equals("")){
			rm.setMsg("必填项不能为空1003");
			return rm;
		}
		
		
		List<Long> rec_list = new ArrayList<Long>();
		String[] rec_id = recIds.split(",");
		if(rec_id.length == 0){
			rm.setMsg("存在非法请求1005");
			return rm;
		}
		for (String string : rec_id) {
			if(!ECommon.isNumeric(string)){
				rm.setMsg("存在非法请求1004");
				return rm;
			}
			rec_list.add(Long.valueOf(string));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//获取购物车
		HaiCartExample cartExample = new HaiCartExample();
		cartExample.createCriteria().andRecIdIn(rec_list).andUserIdEqualTo(user_id);
		List<HaiCart> cart_list = haiCartMapper.selectByExample(cartExample);
		
		if(cart_list == null || rec_id.length != cart_list.size()){
			rm.setMsg("购物车清单不正确");
			return rm;
		}
		
		map.put("cart", cart_list);
		
		HaiUserAddressExample addressExample = new HaiUserAddressExample();
		addressExample.createCriteria().andUserIdEqualTo(user_id);
		addressExample.setOrderByClause("is_default desc");
		List<HaiUserAddress> address_list = haiUserAddressMapper.selectByExample(addressExample);
		if(address_list != null && address_list.size() > 0){
			HaiUserAddress address = address_list.get(0);
			map.put("address", address);
			List<Integer> regionIds = new ArrayList<Integer>();
			if(address.getCountry()!=null && address.getCountry()!=0)regionIds.add(address.getCountry());
			if(address.getCity()!=null && address.getCity()!=0)regionIds.add(address.getCity());
			if(address.getCounty()!=null && address.getCounty()!=0)regionIds.add(address.getCounty());
			if(address.getDistrict()!=null && address.getDistrict()!=0)regionIds.add(address.getDistrict());
			if(address.getProvince()!=null && address.getProvince()!=0)regionIds.add(address.getProvince());
			if(address.getStreet()!=null && address.getStreet()!=0)regionIds.add(address.getStreet());
			
			if(regionIds.size() > 0){
				EHaiRegionExample regionExample = new EHaiRegionExample();
				regionExample.createCriteria().andRegionIdIn(regionIds);
				List<EHaiRegion> region_list = eHaiRegionMapper.selectByExample(regionExample);
				map.put("region", region_list);				
			}
			
			
		}
		
		
		List<HaiPayment> pay_list = haiPaymentMapper.selectByExample(null);
		map.put("pay", pay_list);
		
		List<HaiShipping> ship_list = haiShippingMapper.selectByExample(null);
		map.put("ship", ship_list);
		
		
		rm.setMap(map);
		
		rm.setCode(1);
		return rm;
	}

	@Override
	public ReturnObject<HaiOrderInfo> done(HttpServletRequest request, String recIds,
			Integer pay_id, Integer ship_id, Long address_id,
			Long user_id, String message) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		rm.setCode(0);
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		
		
		if(user_id == null || user_id == 0){
			rm.setMsg("非客户端使用");
			return rm;
		}
		
		if(pay_id == null || pay_id == 0){
			rm.setMsg("必填项为空1101");
			return rm;
		}
		
		if(ship_id == null || ship_id == 0){
			rm.setMsg("必填项为空1102");
			return rm;
		}
		
		if(address_id == null || address_id == 0){
			rm.setMsg("必填项为空1103");
			return rm;
		}
		
		if(recIds == null || recIds.equals("")){
			rm.setMsg("必填项不能为空1003");
			return rm;
		}
		
		
		List<Long> rec_list = new ArrayList<Long>();
		String[] rec_id = recIds.split(",");
		if(rec_id.length == 0){
			rm.setMsg("存在非法请求1005");
			return rm;
		}
		for (String string : rec_id) {
			if(!ECommon.isNumeric(string)){
				rm.setMsg("存在非法请求1004");
				return rm;
			}
			rec_list.add(Long.valueOf(string));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//获取购物车
		HaiCartExample cartExample = new HaiCartExample();
		cartExample.createCriteria().andRecIdIn(rec_list).andUserIdEqualTo(user_id);
		List<HaiCart> cart_list = haiCartMapper.selectByExample(cartExample);
		
		
		
		if(cart_list == null || rec_id.length != cart_list.size()){
			rm.setMsg("购物车清单不正确");
			return rm;
		}
		
		
		HaiUserAddress address = haiUserAddressMapper.get_hai_user_address_info(user_id, address_id);
		if(address == null){
			rm.setMsg("送货地址有误");
			return rm;
		}
		
		HaiPayment pay = haiPaymentMapper.selectByPrimaryKey(pay_id);
		if(pay == null){
			rm.setMsg("支付信息有误");
			return rm;
		}
		
		HaiShipping ship = haiShippingMapper.selectByPrimaryKey(ship_id);
		if(ship == null){
			rm.setMsg("货运信息有误");
			return rm;
		}
		Date date = new Date();
		HaiOrderInfoWithBLOBs order = new HaiOrderInfoWithBLOBs();
		
		String year = String.valueOf(date.getYear()+1900);
		String month = String.format("%02d",date.getMonth()+1);
		String todate = String.format("%02d",date.getDate());
		String hours = String.format("%02d",date.getHours());
		String minutes = String.format("%02d",date.getMinutes());
		String second = String.format("%02d",date.getSeconds());
		
		order.setOrderSn("800"+year + month + todate + hours + minutes + second + ECommon.nonceInt(6));
		order.setUserId(user_id);
		
		order.setOrderStatus(EOrderStatusEnum.init);
		order.setShippingStatus(EShippingStatusEnum.init);
		order.setPayStatus(EPayStatusEnum.init);
		
		order.setConsignee(address.getConsignee());
		order.setCountry(address.getCountry());
		order.setProvince(address.getProvince());
		order.setCity(address.getCity());
		order.setCounty(address.getCounty());
		order.setStreet(address.getStreet());
		order.setAddress(address.getAddress());
		order.setZipcode(address.getZipcode());
		order.setTel(address.getTel());
		order.setMobile(address.getMobile());
		order.setEmail(address.getEmail());
		order.setBestTime("");//最佳送货时间，待定
		order.setPostscript(message);//附言
		order.setSignBuilding("");
		order.setHowOos("");
		order.setHowSurplus("");
		order.setPackName("");
		order.setCardName("");
		order.setCardMessage("");
		order.setInvPayee("");
		order.setInvContent("");
		order.setShippingFee(0);
		order.setInsureFee(0);
		order.setPayFee(0);
		order.setPackFee(0);
		order.setCardFee(0);
		order.setMoneyPaid(0);
		order.setSurplus(0);
		order.setIntegral(0);
		order.setIntegralMoney(0);
		order.setBonus(0);
		order.setOrderAmount(0);//订单金额，需要修入一下
		order.setFromAd(0);
		order.setReferer("");
		order.setAddTime(date);
		order.setConfirmTime(null);
		order.setPayTime(null);
		order.setShippingTime(null);
		order.setPackId(0);
		order.setCardId(0);
		order.setBonusId(0);
		order.setInvoiceNo("");
		order.setExtensionCode("");
		order.setExtensionId(0);
		order.setToBuyer("");
		order.setPayNote("");
		order.setAgencyId(0);
		order.setInvType("");
		order.setParentId(0L);
		order.setDiscount(0F);
//		order.setStoreId(0);
		order.setRemark("");
		order.setIsVoid("1");
		
		order.setConfirmTime(0);
		order.setPayTime(0);
		order.setShippingTime(0);
		order.setTax(0F);
		order.setIsSeparate(0);
		
		order.setShippingId(ship_id);
		order.setShippingName(ship.getShippingName());
		
		order.setPayId(pay_id);
		order.setPayName(pay.getPayName());
		
		Integer total_amount = 0;
		StringBuffer sb = new StringBuffer();
		for (HaiCart haiCart : cart_list) {
			total_amount += haiCart.getGoodsPrice().intValue() * haiCart.getGoodsNumber();
			sb.append(haiCart.getGoodsName()+"【"+haiCart.getGoodsNumber()+"】  ");
		}
		order.setGoodsDesc(sb.toString());
		order.setGoodsAmount(total_amount);
		
		int code = haiOrderInfoMapper.insert(order);
		Long order_id = order.getOrderId();
		
		rm.setModel(order);
		
		List<HaiOrderGoods> orderGoodsList = new ArrayList<HaiOrderGoods>();
		for (HaiCart haiCart : cart_list) {
			HaiOrderGoods orderGoods = new HaiOrderGoods();
			
			orderGoods.setOrderId(order_id);
			orderGoods.setGoodsId(haiCart.getGoodsId());
			orderGoods.setGoodsName(haiCart.getGoodsName());
			orderGoods.setGoodsSn(haiCart.getGoodsSn());
			orderGoods.setGoodsNumber(haiCart.getGoodsNumber());
			orderGoods.setMarketPrice(haiCart.getMarketPrice());
			orderGoods.setGoodsPrice(haiCart.getGoodsPrice());
			orderGoods.setGoodsThumb(haiCart.getGoodsThumb());
			orderGoods.setProductId(0L);
			orderGoods.setParentId(0L);
			orderGoods.setIsGift(Short.valueOf("0"));
			orderGoods.setGoodsAttrId("");
			orderGoods.setGoodsAttr("");
			orderGoods.setExtensionCode("");
			orderGoods.setIsReal(true);
			orderGoods.setSendNumber(0);
			orderGoodsList.add(orderGoods);
		}
		
		int codeBatch = haiOrderGoodsMapper.insertBatch(orderGoodsList);
		
		//清空当前购物的清单
		haiCartMapper.deleteByExample(cartExample);
		
		map.put("cart", cart_list);
		rm.setCode(1);
		
		return rm;
	}
	
	
	
	
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
		orderInfo.setOrderStatus(EOrderStatusEnum.init);
		orderInfo.setShippingStatus(EShippingStatusEnum.init);
		orderInfo.setPayStatus(EPayStatusEnum.init);
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
		
		int code = haiOrderInfoMapper.insert(orderInfo);
		Long order_id = orderInfo.getOrderId();
		
		//插入订单商品表
		

		List<HaiOrderGoods> orderGoodsList = new ArrayList<HaiOrderGoods>();
		for (HaiCart haiCart : cart_list) {
			HaiOrderGoods orderGoods = new HaiOrderGoods();
			
			orderGoods.setOrderId(order_id);
			orderGoods.setGoodsId(haiCart.getGoodsId());
			orderGoods.setGoodsName(haiCart.getGoodsName());
			orderGoods.setGoodsSn(haiCart.getGoodsSn());
			orderGoods.setGoodsNumber(haiCart.getGoodsNumber());
			orderGoods.setMarketPrice(haiCart.getMarketPrice());
			orderGoods.setGoodsPrice(haiCart.getGoodsPrice());
			orderGoods.setGoodsThumb(haiCart.getGoodsThumb());
			orderGoods.setProductId(0L);
			orderGoods.setParentId(0L);
			orderGoods.setIsGift(Short.valueOf("0"));
			orderGoods.setGoodsAttrId("");
			orderGoods.setGoodsAttr("");
			orderGoods.setExtensionCode("");
			orderGoods.setIsReal(true);
			orderGoods.setSendNumber(0);
			orderGoodsList.add(orderGoods);
		}
		
		int codeBatch = haiOrderGoodsMapper.insertBatch(orderGoodsList);
		
		
		
		
		//清空购物车
		
		
		//判断支付方式，整理支付的运算逻辑
		
		
		//跳转支付信息返回
		
		
		
		return null;
	}
	
	
	@Override
	public ReturnObject<OrderDoneParam> WeixinOrderDone(HttpServletRequest request,
			OrderDoneParam order_done) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<OrderDoneParam> rm = new ReturnObject<OrderDoneParam>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		order_done.setStore_id(store_id);
		order_done.setUser_id(user_id);
		
		EHaiUsersExample userExample = new EHaiUsersExample();
		userExample.createCriteria().andUserIdEqualTo(user_id);
		List<EHaiUsers> userList = eHaiUsersMapper.selectByExample(userExample);
		if(userList == null || userList.size() == 0){
			rm.setMsg("用户信息不存在");return rm;
		}
		EHaiUsers users = userList.get(0);
		
		HaiShippingWithBLOBs ship = null;
		//运输方式不为空，检验运输方式是否正确
		if(order_done.getShipping_id()!=null && order_done.getShipping_id()!=0){
			ship = haiShippingMapper.web_shipping_info(order_done.getStore_id(), order_done.getShipping_id());
//			if(ship == null){
//				rm.setMsg("运输方式错误");return rm;
//			}
		}
				
		HaiPaymentWithBLOBs payment = null;
		//支付方式不为空的情况，检验支付方式是否正确
		if(order_done.getPay_id()!=null && order_done.getPay_id()!=0){
			payment = haiPaymentMapper.payment_info(order_done.getStore_id(), order_done.getPay_id());
//			if(payment == null){
//				rm.setMsg("支付方式错误");return rm;
//			}
		}
		
		HaiUserAddress userAddress = null;
		//配送地址不为空的情况，检验配送地址是否此商家
		if(order_done.getAddress_id()!=null && order_done.getAddress_id()!=0){
			userAddress = haiUserAddressMapper.get_hai_user_address_info(order_done.getUser_id(), order_done.getAddress_id());
			if(userAddress == null){
				rm.setMsg("收货人错误");return rm;
			}
		}
		
		
		//读取购物车的信息是否正确
		if(!RequestUtils.sqlValidate(order_done.getRec_id_data())){
			rm.setMsg("参数不安全");return rm;
		}
		String rec_cart = order_done.getRec_id_data();
		String[] recCart = rec_cart.split(",");
		List<Long> recCartList = new ArrayList<Long>();
		for (String string : recCart) {
			recCartList.add(Long.valueOf(string));
		}
		
		
		HaiCartExample cartExample = new HaiCartExample();
		cartExample.createCriteria()
		.andStoreIdEqualTo(order_done.getStore_id())
		.andUserIdEqualTo(order_done.getUser_id())
		.andRecIdIn(recCartList);
		
		List<HaiCartWithBLOBs> cart_list = haiCartMapper.selectByExampleWithBLOBs(cartExample);
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
		StringBuffer sb = new StringBuffer();
		//根据商品取价
		for (HaiCart cart : cart_list) {
			for (HaiGoods goods : goodsList){
				if(cart.getGoodsId().longValue() == goods.getGoodsId().longValue()){
					if(goods.getGoodsNumber() == 0 || goods.getGoodsNumber() < cart.getGoodsNumber()){
						rm.setMsg("商品数量不足");return rm;
					}
					totalPrice += cart.getGoodsNumber() * goods.getShopPrice();//以分为单位
					sb.append(cart.getGoodsName()+"【"+cart.getGoodsNumber()+"】  ");
					continue;
				}
			}
		}
		
		
		Date date = new Date();
		//插入订单主表
		HaiOrderInfoWithBLOBs orderInfo = new HaiOrderInfoWithBLOBs();
		String orderSn = "100"+DateUtil.formatDate(date, DateUtil.FORMATSTR_4) + ECommon.nonceInt(4) + order_done.getUser_id().toString();
		orderInfo.setOrderSn(orderSn);
		orderInfo.setUserId(order_done.getUser_id());
		orderInfo.setOrderStatus(EOrderStatusEnum.init);
		orderInfo.setShippingStatus(EShippingStatusEnum.init);
		orderInfo.setPayStatus(EPayStatusEnum.init);
		//暂时未确认运输方式，这里是空的先
		orderInfo.setShippingId(0);
		orderInfo.setShippingName("商家待定");
		
		orderInfo.setPayId(0);
		orderInfo.setPayName("微信支付");
		
		if(userAddress!=null){
			orderInfo.setConsignee(userAddress.getConsignee());
			orderInfo.setCountry(userAddress.getCountry());
			orderInfo.setProvince(userAddress.getProvince());
			orderInfo.setCity(userAddress.getCity());
			orderInfo.setCounty(userAddress.getCounty());
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
		orderInfo.setOrderSource(EOrderSourceEnum.weixin);
		orderInfo.setConfirmTime(0);
		orderInfo.setPayTime(0);
		orderInfo.setShippingTime(0);
		orderInfo.setTax(0F);
		orderInfo.setIsSeparate(0);
		orderInfo.setStoreId(store_id);
		orderInfo.setParentId(0L);
		orderInfo.setHowOos("");//缺货处理方式，等待所有商品备齐后再发； 取消订单；与店主协商
		orderInfo.setHowSurplus("");//根据字段猜测应该是余额处理方式，程序未作这部分实现
		orderInfo.setPackName("");//包装名称，取值表ecs_pack
		orderInfo.setCardName("");//贺卡的名称，取值ecs_card
		orderInfo.setCardMessage("");//'贺卡内容，由用户提交',
		orderInfo.setInvPayee("");//'发票抬头，用户页面填写',
		orderInfo.setInvContent("");//'发票内容，用户页面选择，取值ecs_shop_config的code字段的值为invoice_content的value',
		orderInfo.setShippingFee(0);//'配送费用',
		orderInfo.setInsureFee(0);//'保价费用',
		orderInfo.setPayFee(0);//'支付费用,跟支付方式的配置相关，取值表ecs_payment',
		orderInfo.setPackFee(0);//'包装费用，取值表取值表ecs_pack',
		orderInfo.setCardFee(0);//'贺卡费用，取值ecs_card ',
		orderInfo.setMoneyPaid(0);//'已付款金额',
		orderInfo.setSurplus(0);//'该订单使用余额的数量，取用户设定余额，用户可用余额，订单金额中最小者',
		orderInfo.setIntegral(0);//'使用的积分的数量，取用户使用积分，商品可用积分，用户拥有积分中最小者',
		orderInfo.setIntegralMoney(0);//'使用积分金额',
		orderInfo.setBonus(0);//'使用红包金额',
		orderInfo.setOrderAmount(0);//'应付款金额',
		orderInfo.setFromAd(0);//'订单由某广告带来的广告id，应该取值于ecs_ad',
		orderInfo.setReferer("");//'订单的来源页面',
		orderInfo.setAddTime(date);//'订单生成时间',
		orderInfo.setConfirmTime(0);//'订单确认时间',Long.valueOf(System.currentTimeMillis() / 1000).intValue()
		orderInfo.setPayTime(0);//'订单支付时间',
		orderInfo.setShippingTime(0);//'订单配送时间',
		orderInfo.setPackId(0);//'包装id，取值取值表ecs_pack',
		orderInfo.setCardId(0);//'贺卡id，用户在页面选择，取值取值ecs_card ',
		orderInfo.setBonusId(0);//'红包的id，ecs_user_bonus的bonus_id',
		orderInfo.setInvoiceNo("");//'发货单号，发货时填写，可在订单查询查看',
		orderInfo.setExtensionCode("");//'通过活动购买的商品的代号；GROUP_BUY是团购；AUCTION，是拍卖；SNATCH，夺宝奇兵；正常普通产品该处为空',
		orderInfo.setExtensionId(0);//'通过活动购买的物品的id，取值ecs_goods_activity；如果是正常普通商品，该处为0',
		orderInfo.setToBuyer("");//'商家给客户的留言,当该字段有值时可以在订单查询看到',
		orderInfo.setPayNote("");//'付款备注，在订单管理里编辑修改',
		orderInfo.setAgencyId(0);//'该笔订单被指派给的办事处的id，根据订单内容和办事处负责范围自动决定，也可以有管理员修改，取值于表ecs_agency',
		orderInfo.setInvType("");//'发票类型，用户页面选择，取值ecs_shop_config的code字段的值为invoice_type的value',
		orderInfo.setTax(0F);//'发票税额',
		orderInfo.setIsSeparate(0);//'0，未分成或等待分成；1，已分成；2，取消分成；',
		orderInfo.setParentId(0L);// '能获得推荐分成的用户id，id取值于表ecs_users',
		orderInfo.setDiscount(0F);//'折扣金额',
		orderInfo.setIsVoid(EIsVoidEnum.valid);
		orderInfo.setGoodsDesc(sb.toString());
		orderInfo.setClassify(EOrderClassifyEnum.shop);
		orderInfo.setRemark("");
		
		int code = haiOrderInfoMapper.insert(orderInfo);
		Long order_id = orderInfo.getOrderId();
		
		//插入订单商品表
		

		List<HaiOrderGoods> orderGoodsList = new ArrayList<HaiOrderGoods>();
		for (HaiCart haiCart : cart_list) {
			HaiOrderGoods orderGoods = new HaiOrderGoods();
			
			orderGoods.setOrderId(order_id);
			orderGoods.setGoodsId(haiCart.getGoodsId());
			orderGoods.setGoodsName(haiCart.getGoodsName());
			orderGoods.setGoodsSn(haiCart.getGoodsSn());
			orderGoods.setGoodsNumber(haiCart.getGoodsNumber());
			orderGoods.setMarketPrice(haiCart.getMarketPrice());
			orderGoods.setGoodsPrice(haiCart.getGoodsPrice());
			orderGoods.setGoodsThumb(haiCart.getGoodsThumb());
			orderGoods.setProductId(0L);
			orderGoods.setParentId(0L);
			orderGoods.setIsGift(Short.valueOf("0"));
			orderGoods.setGoodsAttrId("");
			orderGoods.setGoodsAttr("");
			orderGoods.setExtensionCode("");
			orderGoods.setIsReal(true);
			orderGoods.setSendNumber(0);
			orderGoods.setAgencyId(haiCart.getAgencyId());
			orderGoods.setArticleId(haiCart.getArticleId());
			orderGoods.setSellerUserId(haiCart.getSellerUserId());
			orderGoodsList.add(orderGoods);
		}
		
		int codeBatch = haiOrderGoodsMapper.insertBatch(orderGoodsList);
		
		//清空购物车
		haiCartMapper.deleteByExample(cartExample);
		
		//跳转支付信息返回
		WeiXinWCPay cpay = weiXinPayService.WeiXinPayApi(request, store_id, user_id, users.getOpenid(), orderSn, totalPrice, "易海司微信支付订单", "hai_order", order_id);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("WeiXinWCPay", cpay);
		rm.setMap(map);
		rm.setCode(1);
		rm.setModel(order_done);
		
		return rm;
	}


}
