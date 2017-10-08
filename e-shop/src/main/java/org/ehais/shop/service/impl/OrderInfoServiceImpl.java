package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EIsVoidEnum;
import org.ehais.enums.EOrderClassifyEnum;
import org.ehais.enums.EPayStatusEnum;
import org.ehais.enums.EShippingStatusEnum;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiOrderGoodsMapper;
import org.ehais.shop.mapper.HaiOrderInfoMapper;
import org.ehais.shop.mapper.HaiShippingMapper;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.model.HaiOrderGoodsExample;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.shop.model.HaiOrderInfoExample;
import org.ehais.shop.model.HaiOrderInfoWithBLOBs;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.model.HaiShippingExample;
import org.ehais.shop.service.OrderInfoService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("orderinfoService")
public class OrderInfoServiceImpl  extends CommonServiceImpl implements OrderInfoService{
	
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;
	@Autowired
	private HaiShippingMapper haiShippingMapper;
	@Autowired
	protected EWPPublicService eWPPublicService;
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	
	public ReturnObject<HaiOrderInfo> orderinfo_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		HaiShippingExample shippingExample = new HaiShippingExample();
		shippingExample.createCriteria().andStoreIdEqualTo(store_id);
		shippingExample.setOrderByClause("shipping_order asc");
		List<HaiShipping> listShipping = haiShippingMapper.selectByExample(shippingExample);
		map.put("listShipping", listShipping);
		
		rm.setMap(map);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
//		example.setStart(start);
//		example.setLen(len);
		List<HaiOrderInfo> list = haiOrderInfoMapper.hai_order_info_list_by_example(example);
		Long total = haiOrderInfoMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderInfo model = new HaiOrderInfo();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiOrderInfo> orderinfo_insert_submit(HttpServletRequest request,HaiOrderInfoWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();

		if(model.getOrderSn() == null || model.getOrderSn().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		c.andOrderSnEqualTo(model.getOrderSn());
		c.andStoreIdEqualTo(store_id);
		Long count = haiOrderInfoMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiOrderInfoMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_update(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderInfo model = haiOrderInfoMapper.selectByPrimaryKey(orderId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiOrderInfo> orderinfo_update_submit(HttpServletRequest request,HaiOrderInfoWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andOrderIdEqualTo(model.getOrderId());
		c.andStoreIdEqualTo(store_id);

		Long count = haiOrderInfoMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = haiOrderInfoMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_find(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiOrderInfoExample oexa = new HaiOrderInfoExample();
		oexa.createCriteria().andStoreIdEqualTo(store_id).andOrderIdEqualTo(orderId);
		List<HaiOrderInfoWithBLOBs> list = haiOrderInfoMapper.selectByExampleWithBLOBs(oexa);
		if(list==null || list.size()==0){
			rm.setMsg("无此订单");
			return rm;
		}
		HaiOrderInfoWithBLOBs model = list.get(0);
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_delete(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andOrderIdEqualTo(orderId);
		int code = haiOrderInfoMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	
	public ReturnObject<HaiOrderInfo> orderinfo_disvalid(HttpServletRequest request,Long user_id,Long orderId) throws Exception{
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		rm.setCode(0);
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		c.andUserIdEqualTo(user_id);
		c.andOrderIdEqualTo(orderId);
		
		List<HaiOrderInfo> list = haiOrderInfoMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("订单不存在");
			return rm;
		}
		HaiOrderInfo order = list.get(0);
		order.setIsVoid("0");
		haiOrderInfoMapper.updateByPrimaryKey(order);
		
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
		
	}
	
	
	private List<BootStrapModel> formatBootStrapList(HaiOrderInfo model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}

	@Override
	public ReturnObject<HaiOrderInfo> orderinfo_list(HttpServletRequest request, Long user_id,Integer order_status,Integer pay_status,Integer shipping_status,EConditionObject condition,String order_sn) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		c.andUserIdEqualTo(user_id);
		if(order_status != null && order_status > 0)c.andOrderStatusEqualTo(order_status);
		if(pay_status != null && pay_status > 0)c.andPayStatusEqualTo(pay_status);
		if(shipping_status != null && shipping_status > 0)c.andShippingStatusEqualTo(shipping_status);
		if(StringUtils.isNotBlank(order_sn))c.andOrderSnLike("%"+order_sn+"%");
		c.andIsVoidEqualTo("1");
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("add_time desc");
		List<HaiOrderInfo> list = haiOrderInfoMapper.selectByExample(example);
		Long total = haiOrderInfoMapper.countByExample(example);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Long> orderIds = new ArrayList<Long>();
		for (HaiOrderInfo haiOrderInfo : list) {
			orderIds.add(haiOrderInfo.getOrderId());
		}
		
		if(orderIds.size() > 0){
			HaiOrderGoodsExample exampleGoods = new HaiOrderGoodsExample();
			HaiOrderGoodsExample.Criteria cg = exampleGoods.createCriteria();
			cg.andOrderIdIn(orderIds);
			List<HaiOrderGoods> orderGoodsList = haiOrderGoodsMapper.selectByExample(exampleGoods);
			map.put("order_goods_list", orderGoodsList);
		}
		
		rm.setMap(map);
		
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	@Override
	public ReturnObject<HaiOrderInfo> orderinfo_info(HttpServletRequest request, Long user_id, Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		
		HaiOrderInfo model = haiOrderInfoMapper.get_hai_order_info_info(user_id, orderId);
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	@Override
	public void setDefaultOrder(HaiOrderInfoWithBLOBs orderInfo,Date date,Integer store_id) {
		// TODO Auto-generated method stub
		//暂时未确认运输方式，这里是空的先
		orderInfo.setShippingId(0);
		orderInfo.setShippingName("");
		
		orderInfo.setPayId(0);
		orderInfo.setPayName("");
		orderInfo.setPayStatus(EPayStatusEnum.init);
		
		orderInfo.setShippingId(0);
		orderInfo.setShippingName("");
		orderInfo.setShippingStatus(EShippingStatusEnum.init);
		
		orderInfo.setConsignee("");
		orderInfo.setCountry(0);
		orderInfo.setProvince(0);
		orderInfo.setCity(0);
		orderInfo.setCounty(0);
		orderInfo.setDistrict(0);
		orderInfo.setAddress("");
		orderInfo.setZipcode("");
		orderInfo.setTel("");
		orderInfo.setMobile("");
		orderInfo.setEmail("");
		orderInfo.setBestTime("");
		orderInfo.setSignBuilding("");
		
		orderInfo.setPostscript("");//订单附言
		
		
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
		orderInfo.setRemark("");
	}
	
	
	@Override
	public ReturnObject<HaiOrderInfoWithBLOBs> order_list_json(HttpServletRequest request,EConditionObject condition,Integer orderStatus,String orderSn,String classify,String startDate,String endDate) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andOrderStatusEqualTo(orderStatus);
		c.andClassifyEqualTo(classify);
		if(StringUtils.isNotBlank(startDate)){
			c.andPayTimeGreaterThanOrEqualTo(Long.valueOf(DateUtil.formatDate(startDate, DateUtil.FORMATSTR_3).getTime() / 1000).intValue());
			System.out.println(Long.valueOf(DateUtil.formatDate(startDate, DateUtil.FORMATSTR_3).getTime() / 1000).intValue());
		}
		if(StringUtils.isNotBlank(endDate)){
			c.andPayTimeLessThanOrEqualTo(Long.valueOf(DateUtil.addDate(DateUtil.formatDate(endDate, DateUtil.FORMATSTR_3), 1).getTime() / 1000).intValue());
			System.out.println(Long.valueOf(DateUtil.addDate(DateUtil.formatDate(endDate, DateUtil.FORMATSTR_3), 1).getTime() / 1000).intValue());
		}
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("pay_time desc");
		if(StringUtils.isNotEmpty(orderSn))c.andOrderSnLike("%"+orderSn+"%");
		List<HaiOrderInfoWithBLOBs> list = haiOrderInfoMapper.selectByExampleWithBLOBs(example);
		long total = haiOrderInfoMapper.countByExample(example);
		
		try{
			if(classify.equals(EOrderClassifyEnum.dining)){//餐饮模式.........
				WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(condition.getStore_id());
				String sid = null;
				List<Long> userIdList = new ArrayList<Long>();
				for (HaiOrderInfoWithBLOBs order : list) {
					userIdList.add(order.getUserId());
					sid = order.getSid();
					if(StringUtils.isNotEmpty(sid)){
						Map<String, Object> m = SignUtil.getDiningId(sid, wp.getToken());
						order.setZipcode(m.get("tableNo").toString());
					}
				}
				
				
				if(userIdList.size() > 0){
					EHaiUsersExample userExample = new EHaiUsersExample();
					userExample.createCriteria().andUserIdIn(userIdList);
					List<EHaiUsers> usersList = eHaiUsersMapper.selectByExample(userExample);
					for (HaiOrderInfoWithBLOBs order : list) {
						for (EHaiUsers eHaiUsers : usersList) {
							if(order.getUserId().longValue() == eHaiUsers.getUserId().longValue()){
								order.setCardName(eHaiUsers.getFaceImage());
								break;
							}
						}
					}
				}
			//餐饮模式=================	
			}else if(classify.equals(EOrderClassifyEnum.shop)){
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		

		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}
	
}











