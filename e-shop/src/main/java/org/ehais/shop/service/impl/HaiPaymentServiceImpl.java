package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiPaymentMapper;
import org.ehais.shop.model.HaiPayment;
import org.ehais.shop.model.HaiPaymentExample;
import org.ehais.shop.service.HaiPaymentService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "pay_code不能为空")//pay_code
@NotBlank(message = "pay_name不能为空")//pay_name

**/
/**

**/




@Service("haiPaymentService")
public class HaiPaymentServiceImpl  extends CommonServiceImpl implements HaiPaymentService{
	
	@Autowired
	private HaiPaymentMapper haiPaymentMapper;


	public ReturnObject<HaiPayment> payment_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiPayment> payment_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String payName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(payName))c.andPayNameLike("%"+payName+"%");
		List<HaiPayment> list = haiPaymentMapper.selectByExample(example);
		long total = haiPaymentMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiPayment> payment_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPayment model = new HaiPayment();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiPayment> payment_insert_submit(HttpServletRequest request,HaiPayment model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
		c.andPayNameEqualTo(model.getPayName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiPaymentMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiPaymentMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiPayment> payment_update(HttpServletRequest request,Integer payId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPayIdEqualTo(payId);
		long count = haiPaymentMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiPayment model = haiPaymentMapper.selectByPrimaryKey(payId);
**/
		HaiPayment model = haiPaymentMapper.get_hai_payment_info(payId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiPayment> payment_update_submit(HttpServletRequest request,HaiPayment model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPayIdEqualTo(model.getPayId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiPaymentMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiPayment bean = haiPaymentMapper.selectByPrimaryKey(model.getPayId());

bean.setPayCode(model.getPayCode());
bean.setPayName(model.getPayName());
bean.setPayFee(model.getPayFee());
bean.setPayDesc(model.getPayDesc());
bean.setPayOrder(model.getPayOrder());
bean.setPayConfig(model.getPayConfig());
bean.setEnabled(model.getEnabled());
bean.setIsDefault(model.getIsDefault());
bean.setIsCod(model.getIsCod());
bean.setIsOnline(model.getIsOnline());
bean.setClassname(model.getClassname());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiPaymentMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiPayment> payment_info(HttpServletRequest request,Integer payId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
		c.andPayIdEqualTo(payId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiPayment> list = haiPaymentMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiPayment model = list.get(0);
		**/

		HaiPayment model = haiPaymentMapper.get_hai_payment_info(payId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiPayment> payment_find(HttpServletRequest request,Integer payId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
		c.andPayIdEqualTo(payId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiPayment> list = haiPaymentMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiPayment model = list.get(0);
		**/

		HaiPayment model = haiPaymentMapper.get_hai_payment_info(payId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiPayment> payment_delete(HttpServletRequest request,Integer payId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPayIdEqualTo(payId);

		long count = haiPaymentMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiPaymentMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiPayment model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "payment.xml",model,"hai_payment",optionMap);
		
		
		return bootStrapList;
	}













	
}











