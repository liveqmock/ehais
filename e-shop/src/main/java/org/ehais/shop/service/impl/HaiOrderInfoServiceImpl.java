package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.model.HaiOrderInfo;
import org.ehais.epublic.model.HaiOrderInfoExample;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiOrderGoodsMapper;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.model.HaiOrderGoodsExample;
import org.ehais.shop.service.HaiOrderInfoService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "order_sn不能为空")//order_sn
@NotBlank(message = "user_id不能为空")//user_id
@NotBlank(message = "order_status不能为空")//order_status
@NotBlank(message = "shipping_status不能为空")//shipping_status
@NotBlank(message = "pay_status不能为空")//pay_status
@NotBlank(message = "consignee不能为空")//consignee
@NotBlank(message = "country不能为空")//country
@NotBlank(message = "province不能为空")//province
@NotBlank(message = "city不能为空")//city
@NotBlank(message = "county不能为空")//county
@NotBlank(message = "address不能为空")//address
@NotBlank(message = "zipcode不能为空")//zipcode
@NotBlank(message = "tel不能为空")//tel
@NotBlank(message = "mobile不能为空")//mobile
@NotBlank(message = "email不能为空")//email
@NotBlank(message = "best_time不能为空")//best_time
@NotBlank(message = "sign_building不能为空")//sign_building
@NotBlank(message = "postscript不能为空")//postscript
@NotBlank(message = "shipping_id不能为空")//shipping_id
@NotBlank(message = "shipping_name不能为空")//shipping_name
@NotBlank(message = "pay_id不能为空")//pay_id
@NotBlank(message = "pay_name不能为空")//pay_name
@NotBlank(message = "how_oos不能为空")//how_oos
@NotBlank(message = "how_surplus不能为空")//how_surplus
@NotBlank(message = "pack_name不能为空")//pack_name
@NotBlank(message = "card_name不能为空")//card_name
@NotBlank(message = "card_message不能为空")//card_message
@NotBlank(message = "inv_payee不能为空")//inv_payee
@NotBlank(message = "inv_content不能为空")//inv_content
@NotBlank(message = "goods_amount不能为空")//goods_amount
@NotBlank(message = "shipping_fee不能为空")//shipping_fee
@NotBlank(message = "insure_fee不能为空")//insure_fee
@NotBlank(message = "pay_fee不能为空")//pay_fee
@NotBlank(message = "pack_fee不能为空")//pack_fee
@NotBlank(message = "card_fee不能为空")//card_fee
@NotBlank(message = "money_paid不能为空")//money_paid
@NotBlank(message = "surplus不能为空")//surplus
@NotBlank(message = "integral不能为空")//integral
@NotBlank(message = "integral_money不能为空")//integral_money
@NotBlank(message = "bonus不能为空")//bonus
@NotBlank(message = "order_amount不能为空")//order_amount
@NotBlank(message = "from_ad不能为空")//from_ad
@NotBlank(message = "referer不能为空")//referer
@NotBlank(message = "confirm_time不能为空")//confirm_time
@NotBlank(message = "pay_time不能为空")//pay_time
@NotBlank(message = "shipping_time不能为空")//shipping_time
@NotBlank(message = "pack_id不能为空")//pack_id
@NotBlank(message = "card_id不能为空")//card_id
@NotBlank(message = "bonus_id不能为空")//bonus_id
@NotBlank(message = "invoice_no不能为空")//invoice_no
@NotBlank(message = "extension_code不能为空")//extension_code
@NotBlank(message = "extension_id不能为空")//extension_id
@NotBlank(message = "to_buyer不能为空")//to_buyer
@NotBlank(message = "pay_note不能为空")//pay_note
@NotBlank(message = "agency_id不能为空")//agency_id
@NotBlank(message = "inv_type不能为空")//inv_type
@NotBlank(message = "tax不能为空")//tax
@NotBlank(message = "is_separate不能为空")//is_separate
@NotBlank(message = "parent_id不能为空")//parent_id
@NotBlank(message = "discount不能为空")//discount

**/
/**
@NotBlank(message = "order_id不能为空")//order_id
@NotBlank(message = "goods_id不能为空")//goods_id
@NotBlank(message = "goods_name不能为空")//goods_name
@NotBlank(message = "goods_sn不能为空")//goods_sn
@NotBlank(message = "product_id不能为空")//product_id
@NotBlank(message = "goods_number不能为空")//goods_number
@NotBlank(message = "market_price不能为空")//market_price
@NotBlank(message = "goods_price不能为空")//goods_price
@NotBlank(message = "goods_attr不能为空")//goods_attr
@NotBlank(message = "send_number不能为空")//send_number
@NotBlank(message = "is_real不能为空")//is_real
@NotBlank(message = "extension_code不能为空")//extension_code
@NotBlank(message = "parent_id不能为空")//parent_id
@NotBlank(message = "is_gift不能为空")//is_gift
@NotBlank(message = "goods_attr_id不能为空")//goods_attr_id

**/




@Service("haiOrderInfoService")
public class HaiOrderInfoServiceImpl  extends CommonServiceImpl implements HaiOrderInfoService{
	
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;

	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;

	public ReturnObject<HaiOrderInfo> orderinfo_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_list_json(HttpServletRequest request,EConditionObject condition,Long recId,String orderName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
//		if(recId > 0 ) c.andRecIdEqualTo(recId);
//		if(StringUtils.isNotEmpty(orderName))c.andOrderNameLike("%"+orderName+"%");
		List<HaiOrderInfo> list = haiOrderInfoMapper.selectByExample(example);
		long total = haiOrderInfoMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ordergoodsList",this.ordergoodsList(request));
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderInfoWithBLOBs model = new HaiOrderInfoWithBLOBs();


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ordergoodsList",this.ordergoodsList(request));
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_insert_submit(HttpServletRequest request,HaiOrderInfoWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
//		c.andOrderNameEqualTo(model.getOrderName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiOrderInfoMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiOrderInfoMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_update(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andOrderIdEqualTo(orderId);
		long count = haiOrderInfoMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiOrderInfoWithBLOBs model = haiOrderInfoMapper.selectByPrimaryKey(orderId);
**/
		HaiOrderInfoWithBLOBs model = haiOrderInfoMapper.get_hai_order_info_info_v2(orderId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ordergoodsList",this.ordergoodsList(request));
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_update_submit(HttpServletRequest request,HaiOrderInfoWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andOrderIdEqualTo(model.getOrderId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiOrderInfoMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiOrderInfoWithBLOBs bean = haiOrderInfoMapper.selectByPrimaryKey(model.getOrderId());

bean.setOrderSn(model.getOrderSn());
bean.setUserId(model.getUserId());
bean.setOrderStatus(model.getOrderStatus());
bean.setShippingStatus(model.getShippingStatus());
bean.setPayStatus(model.getPayStatus());
bean.setConsignee(model.getConsignee());
bean.setCountry(model.getCountry());
bean.setProvince(model.getProvince());
bean.setCity(model.getCity());
bean.setCounty(model.getCounty());
bean.setDistrict(model.getDistrict());
bean.setStreet(model.getStreet());
bean.setAddress(model.getAddress());
bean.setZipcode(model.getZipcode());
bean.setTel(model.getTel());
bean.setMobile(model.getMobile());
bean.setEmail(model.getEmail());
bean.setBestTime(model.getBestTime());
bean.setSignBuilding(model.getSignBuilding());
bean.setPostscript(model.getPostscript());
bean.setShippingId(model.getShippingId());
bean.setShippingName(model.getShippingName());
bean.setShippingNumber(model.getShippingNumber());
bean.setPayId(model.getPayId());
bean.setPayName(model.getPayName());
bean.setHowOos(model.getHowOos());
bean.setHowSurplus(model.getHowSurplus());
bean.setPackName(model.getPackName());
bean.setCardName(model.getCardName());
bean.setCardMessage(model.getCardMessage());
bean.setInvPayee(model.getInvPayee());
bean.setInvContent(model.getInvContent());
bean.setGoodsAmount(model.getGoodsAmount());
bean.setShippingFee(model.getShippingFee());
bean.setInsureFee(model.getInsureFee());
bean.setPayFee(model.getPayFee());
bean.setPackFee(model.getPackFee());
bean.setCardFee(model.getCardFee());
bean.setMoneyPaid(model.getMoneyPaid());
bean.setSurplus(model.getSurplus());
bean.setIntegral(model.getIntegral());
bean.setIntegralMoney(model.getIntegralMoney());
bean.setBonus(model.getBonus());
bean.setOrderAmount(model.getOrderAmount());
bean.setFromAd(model.getFromAd());
bean.setReferer(model.getReferer());
bean.setAddTime(model.getAddTime());
bean.setConfirmTime(model.getConfirmTime());
bean.setPayTime(model.getPayTime());
bean.setShippingTime(model.getShippingTime());
bean.setPackId(model.getPackId());
bean.setCardId(model.getCardId());
bean.setBonusId(model.getBonusId());
bean.setInvoiceNo(model.getInvoiceNo());
bean.setExtensionCode(model.getExtensionCode());
bean.setExtensionId(model.getExtensionId());
bean.setToBuyer(model.getToBuyer());
bean.setPayNote(model.getPayNote());
bean.setAgencyId(model.getAgencyId());
bean.setInvType(model.getInvType());
bean.setTax(model.getTax());
bean.setIsSeparate(model.getIsSeparate());
bean.setParentId(model.getParentId());
bean.setDiscount(model.getDiscount());
bean.setRemark(model.getRemark());
bean.setValid(true);
bean.setOrderSource(model.getOrderSource());
bean.setGoodsDesc(model.getGoodsDesc());
bean.setClassify(model.getClassify());
bean.setSid(model.getSid());
bean.setHasForum(model.getHasForum());
bean.setDaySerialNumber(model.getDaySerialNumber());
bean.setCouponsUserId(model.getCouponsUserId());
bean.setCouponsId(model.getCouponsId());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiOrderInfoMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_info(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		c.andOrderIdEqualTo(orderId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiOrderInfoWithBLOBs> list = haiOrderInfoMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiOrderInfoWithBLOBs model = list.get(0);
		**/

		HaiOrderInfoWithBLOBs model = haiOrderInfoMapper.get_hai_order_info_info_v2(orderId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ordergoodsList",this.ordergoodsList(request));
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_find(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		c.andOrderIdEqualTo(orderId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiOrderInfoWithBLOBs> list = haiOrderInfoMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiOrderInfoWithBLOBs model = list.get(0);
		**/

		HaiOrderInfoWithBLOBs model = haiOrderInfoMapper.get_hai_order_info_info_v2(orderId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ordergoodsList",this.ordergoodsList(request));
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_delete(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andOrderIdEqualTo(orderId);

		long count = haiOrderInfoMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiOrderInfoMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiOrderInfoWithBLOBs model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "orderinfo.xml",model,"hai_orderinfo",optionMap);
		
		
		return bootStrapList;
	}


/////////////////////////////////////////////////////////////////////////////////////

	public List<HaiOrderGoods> ordergoodsList(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		
		HaiOrderGoodsExample example = new HaiOrderGoodsExample();
		HaiOrderGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiOrderGoods> list = haiOrderGoodsMapper.selectByExample(example);
		
		return list;
	}


	public ReturnObject<HaiOrderGoods> ordergoods_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiOrderGoods> rm = new ReturnObject<HaiOrderGoods>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiOrderGoods> ordergoods_list_json(HttpServletRequest request,EConditionObject condition,String goodsName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderGoods> rm = new ReturnObject<HaiOrderGoods>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiOrderGoodsExample example = new HaiOrderGoodsExample();
		HaiOrderGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		if(StringUtils.isNotEmpty(goodsName))c.andGoodsNameLike("%"+goodsName+"%");
		List<HaiOrderGoods> list = haiOrderGoodsMapper.selectByExample(example);
		long total = haiOrderGoodsMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiOrderGoods> ordergoods_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderGoods> rm = new ReturnObject<HaiOrderGoods>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderGoods model = new HaiOrderGoods();

		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiOrderGoods> ordergoods_insert_submit(HttpServletRequest request,HaiOrderGoods model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderGoods> rm = new ReturnObject<HaiOrderGoods>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiOrderGoodsExample example = new HaiOrderGoodsExample();
		HaiOrderGoodsExample.Criteria c = example.createCriteria();
		c.andGoodsNameEqualTo(model.getGoodsName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiOrderGoodsMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiOrderGoodsMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiOrderGoods> ordergoods_update(HttpServletRequest request,Long recId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderGoods> rm = new ReturnObject<HaiOrderGoods>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderGoodsExample example = new HaiOrderGoodsExample();
		HaiOrderGoodsExample.Criteria c = example.createCriteria();
		c.andRecIdEqualTo(recId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiOrderGoods> list = haiOrderGoodsMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiOrderGoods model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiOrderGoods> ordergoods_update_submit(HttpServletRequest request,HaiOrderGoods model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderGoods> rm = new ReturnObject<HaiOrderGoods>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderGoodsExample example = new HaiOrderGoodsExample();
		HaiOrderGoodsExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andRecIdEqualTo(model.getRecId());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));

		long count = haiOrderGoodsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiOrderGoods bean = haiOrderGoodsMapper.selectByPrimaryKey(model.getRecId());

		bean.setOrderId(model.getOrderId());
bean.setSubOrderSn(model.getSubOrderSn());
bean.setGoodsId(model.getGoodsId());
bean.setGoodsName(model.getGoodsName());
bean.setGoodsSn(model.getGoodsSn());
bean.setProductId(model.getProductId());
bean.setGoodsNumber(model.getGoodsNumber());
bean.setMarketPrice(model.getMarketPrice());
bean.setGoodsPrice(model.getGoodsPrice());
bean.setGoodsAttr(model.getGoodsAttr());
bean.setGoodsThumb(model.getGoodsThumb());
bean.setSendNumber(model.getSendNumber());
bean.setIsReal(model.getIsReal());
bean.setExtensionCode(model.getExtensionCode());
bean.setParentId(model.getParentId());
bean.setIsGift(model.getIsGift());
bean.setGoodsAttrId(model.getGoodsAttrId());
bean.setAgencyId(model.getAgencyId());
bean.setArticleId(model.getArticleId());
bean.setUserId(model.getUserId());
bean.setSellerUserId(model.getSellerUserId());


		int code = haiOrderGoodsMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiOrderGoods> ordergoods_info(HttpServletRequest request,Long recId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderGoods> rm = new ReturnObject<HaiOrderGoods>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderGoodsExample example = new HaiOrderGoodsExample();
		HaiOrderGoodsExample.Criteria c = example.createCriteria();
		c.andRecIdEqualTo(recId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiOrderGoods> list = haiOrderGoodsMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiOrderGoods model = list.get(0);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiOrderGoods> ordergoods_find(HttpServletRequest request,Long recId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderGoods> rm = new ReturnObject<HaiOrderGoods>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderGoodsExample example = new HaiOrderGoodsExample();
		HaiOrderGoodsExample.Criteria c = example.createCriteria();
		c.andRecIdEqualTo(recId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiOrderGoods> list = haiOrderGoodsMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiOrderGoods model = list.get(0);
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiOrderGoods> ordergoods_delete(HttpServletRequest request,Long recId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderGoods> rm = new ReturnObject<HaiOrderGoods>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderGoodsExample example = new HaiOrderGoodsExample();
		HaiOrderGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andRecIdEqualTo(recId);

		long count = haiOrderGoodsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


//		HaiOrderInfoExample exampleCheck = new HaiOrderInfoExample();
//		HaiOrderInfoExample.Criteria cCheck = exampleCheck.createCriteria();
//		cCheck.andRecIdEqualTo(recId);
//		exampleCheck.CriteriaStoreId(cCheck, this.storeIdCriteriaObject(request));
//		long countCheck = haiOrderInfoMapper.countByExample(exampleCheck);
//		if(countCheck > 0){
//			rm.setMsg("存在关联记录，请先移除关联记录后再删除此分类");
//			return rm;
//		}

		int code = haiOrderGoodsMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}











	
}











