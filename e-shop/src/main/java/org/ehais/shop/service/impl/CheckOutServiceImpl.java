package org.ehais.shop.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.enums.OrderStatusEnum;
import org.ehais.enums.PayStatusEnum;
import org.ehais.enums.ShippingStatusEnum;
import org.ehais.epublic.mapper.EHaiRegionMapper;
import org.ehais.epublic.model.EHaiRegion;
import org.ehais.epublic.model.EHaiRegionExample;
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
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.shop.model.HaiPayment;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.model.HaiUserAddress;
import org.ehais.shop.model.HaiUserAddressExample;
import org.ehais.shop.service.CheckOutService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ECommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("checkOutService")
public class CheckOutServiceImpl extends CommonServiceImpl implements CheckOutService {

	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiCartMapper haiCartMapper;
	@Autowired
	private HaiUserAddressMapper haiUserAddressMapper;
	@Autowired
	private HaiPaymentMapper haiPaymentMapper;
	@Autowired
	private HaiShippingMapper haiShippingMapper;
	@Autowired
	private EHaiRegionMapper eHaiRegionMapper;
	
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;
	
	
	@Override
	public ReturnObject<Object> checkout(HttpServletRequest request,
			String recIds, Long user_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(0);
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
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
		addressExample.createCriteria().andUserIdEqualTo(user_id).andIsDefaultEqualTo("1");
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
			user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
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
		HaiOrderInfo order = new HaiOrderInfo();
		
		String year = String.valueOf(date.getYear()+1900);
		String month = String.format("%02d",date.getMonth()+1);
		String todate = String.format("%02d",date.getDate());
		String hours = String.format("%02d",date.getHours());
		String minutes = String.format("%02d",date.getMinutes());
		String second = String.format("%02d",date.getSeconds());
		
		order.setOrderSn(year + month + todate + hours + minutes + second + ECommon.nonceInt(6));
		order.setUserId(user_id);
		
		order.setOrderStatus(OrderStatusEnum.init);
		order.setShippingStatus(ShippingStatusEnum.init);
		order.setPayStatus(PayStatusEnum.init);
		
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
//		order.setTax(0);
//		order.setIsSeparate(Short.valueOf("0"));
//		order.setParentId(0L);
		order.setDiscount(0);
//		order.setStoreId(0);
		order.setRemark("");
		order.setIsVoid("1");
		
		
		order.setShippingId(ship_id);
		order.setShippingName(ship.getShippingName());
		
		order.setPayId(pay_id);
		order.setPayName(pay.getPayName());
		
		Integer total_amount = 0;
		for (HaiCart haiCart : cart_list) {
			total_amount += haiCart.getGoodsPrice().intValue() * haiCart.getGoodsNumber();
		}
		
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
			orderGoods.setGoodsNumber(Short.valueOf(haiCart.getGoodsNumber()+""));
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
			orderGoods.setSendNumber(Short.valueOf("0"));
			orderGoodsList.add(orderGoods);
		}
		
		int codeBatch = haiOrderGoodsMapper.insertBatch(orderGoodsList);
		
		//清空当前购物的清单
		haiCartMapper.deleteByExample(cartExample);
		
		map.put("cart", cart_list);
		rm.setCode(1);
		
		return rm;
	}
	
	
}
