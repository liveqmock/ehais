package org.ehais.shop.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.tp.TpDiningOrderMapper;
import org.ehais.shop.model.tp.TpDiningOrder;
import org.ehais.shop.model.tp.TpDiningOrderExample;
import org.ehais.shop.service.TpDiningOrderService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("tpDiningOrderService")
public class TpDiningOrderServiceImpl  extends CommonServiceImpl implements TpDiningOrderService{
	
	@Autowired
	private TpDiningOrderMapper tpDiningOrderMapper;
	
	public ReturnObject<TpDiningOrder> diningorder_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<TpDiningOrder> rm = new ReturnObject<TpDiningOrder>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<TpDiningOrder> diningorder_list_json(HttpServletRequest request,EConditionObject condition,String orderSn,String goodsDesc,String consignee,String mobile,String address,Integer orderStatus) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningOrder> rm = new ReturnObject<TpDiningOrder>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		TpDiningOrderExample example = new TpDiningOrderExample();
		TpDiningOrderExample.Criteria c = example.createCriteria();
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		
		
		if(StringUtils.isNotEmpty(orderSn)) c.andOrderSnLike("%"+orderSn+"%");
		if(StringUtils.isNotEmpty(goodsDesc)) c.andGoodsDescLike("%"+goodsDesc+"%");
		if(StringUtils.isNotEmpty(consignee)) c.andOrderSnLike("%"+consignee+"%");
		if(StringUtils.isNotEmpty(mobile)) c.andMobileLike("%"+mobile+"%");
		if(StringUtils.isNotEmpty(address)) c.andAddressLike("%"+address+"%");
		if(orderStatus != null && orderStatus > 0) c.andOrderStatusEqualTo(orderStatus == 1 ? true : false);
		
		
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		List<TpDiningOrder> list = tpDiningOrderMapper.selectByExample(example);
		long total = tpDiningOrderMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<TpDiningOrder> diningorder_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningOrder> rm = new ReturnObject<TpDiningOrder>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningOrder model = new TpDiningOrder();

		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<TpDiningOrder> diningorder_insert_submit(HttpServletRequest request,TpDiningOrder model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningOrder> rm = new ReturnObject<TpDiningOrder>();
		rm.setCode(0);

		

		TpDiningOrderExample example = new TpDiningOrderExample();
		TpDiningOrderExample.Criteria c = example.createCriteria();
		c.andOrderSnEqualTo(model.getOrderSn());
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		long count = tpDiningOrderMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = tpDiningOrderMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<TpDiningOrder> diningorder_update(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningOrder> rm = new ReturnObject<TpDiningOrder>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningOrderExample example = new TpDiningOrderExample();
		TpDiningOrderExample.Criteria c = example.createCriteria();
		c.andOrderIdEqualTo(orderId);
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		List<TpDiningOrder> list = tpDiningOrderMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningOrder model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<TpDiningOrder> diningorder_update_submit(HttpServletRequest request,TpDiningOrder model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningOrder> rm = new ReturnObject<TpDiningOrder>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningOrderExample example = new TpDiningOrderExample();
		TpDiningOrderExample.Criteria c = example.createCriteria();
		
		c.andOrderIdEqualTo(model.getOrderId());
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);

		long count = tpDiningOrderMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		TpDiningOrder bean = tpDiningOrderMapper.selectByPrimaryKey(model.getOrderId());

		bean.setOrderId(model.getOrderId());
bean.setOrderSn(model.getOrderSn());
bean.setUserId(model.getUserId());
bean.setOpenid(model.getOpenid());
bean.setOrderStatus(model.getOrderStatus());
bean.setShippingStatus(model.getShippingStatus());
bean.setPayStatus(model.getPayStatus());
bean.setConsignee(model.getConsignee());
bean.setCountry(model.getCountry());
bean.setProvince(model.getProvince());
bean.setCity(model.getCity());
bean.setDistrict(model.getDistrict());
bean.setTwon(model.getTwon());
bean.setAddress(model.getAddress());
bean.setZipcode(model.getZipcode());
bean.setMobile(model.getMobile());
bean.setEmail(model.getEmail());
bean.setShippingCode(model.getShippingCode());
bean.setShippingName(model.getShippingName());
bean.setPayCode(model.getPayCode());
bean.setPayName(model.getPayName());
bean.setInvoiceTitle(model.getInvoiceTitle());
bean.setGoodsPrice(model.getGoodsPrice());
bean.setShippingPrice(model.getShippingPrice());
bean.setUserMoney(model.getUserMoney());
bean.setCouponPrice(model.getCouponPrice());
bean.setIntegral(model.getIntegral());
bean.setIntegralMoney(model.getIntegralMoney());
bean.setOrderAmount(model.getOrderAmount());
bean.setTotalAmount(model.getTotalAmount());
bean.setAddTime(model.getAddTime());
bean.setShippingTime(model.getShippingTime());
bean.setConfirmTime(model.getConfirmTime());
bean.setPayTime(model.getPayTime());
bean.setOrderPromType(model.getOrderPromType());
bean.setOrderPromId(model.getOrderPromId());
bean.setOrderPromAmount(model.getOrderPromAmount());
bean.setDiscount(model.getDiscount());
bean.setUserNote(model.getUserNote());
bean.setAdminNote(model.getAdminNote());
bean.setParentSn(model.getParentSn());
bean.setIsDistribut(model.getIsDistribut());
bean.setPaidMoney(model.getPaidMoney());
bean.setClassify(model.getClassify());
bean.setSuppliersId(model.getSuppliersId());
bean.setTableno(model.getTableno());
bean.setDiningType(model.getDiningType());


		int code = tpDiningOrderMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<TpDiningOrder> diningorder_info(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningOrder> rm = new ReturnObject<TpDiningOrder>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningOrderExample example = new TpDiningOrderExample();
		TpDiningOrderExample.Criteria c = example.createCriteria();
		c.andOrderIdEqualTo(orderId);
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		List<TpDiningOrder> list = tpDiningOrderMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningOrder model = list.get(0);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<TpDiningOrder> diningorder_find(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningOrder> rm = new ReturnObject<TpDiningOrder>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		TpDiningOrder model = tpDiningOrderMapper.selectByPrimaryKey(orderId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<TpDiningOrder> diningorder_delete(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningOrder> rm = new ReturnObject<TpDiningOrder>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningOrderExample example = new TpDiningOrderExample();
		TpDiningOrderExample.Criteria c = example.createCriteria();
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		c.andOrderIdEqualTo(orderId);

		long count = tpDiningOrderMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = tpDiningOrderMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,TpDiningOrder model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "diningorder.xml",model,"tp_diningorder",optionMap);
		
		
		return bootStrapList;
	}
	
}











