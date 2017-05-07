package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiPaymentMapper;
import org.ehais.shop.model.HaiPayment;
import org.ehais.shop.model.HaiPaymentExample;
import org.ehais.shop.model.HaiPaymentWithBLOBs;
import org.ehais.shop.service.PaymentService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("paymentService")
public class PaymentServiceImpl  extends CommonServiceImpl implements PaymentService{
	
	@Autowired
	private HaiPaymentMapper haiPaymentMapper;
	
	public ReturnObject<HaiPayment> payment_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiPayment> payment_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
//		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setStart(start);
		example.setLen(len);
		List<HaiPayment> list = haiPaymentMapper.hai_payment_list_by_example(example);
		Integer total = haiPaymentMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiPaymentWithBLOBs> payment_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPaymentWithBLOBs> rm = new ReturnObject<HaiPaymentWithBLOBs>();	
//		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		HaiPaymentWithBLOBs model = new HaiPaymentWithBLOBs();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiPaymentWithBLOBs> payment_insert_submit(HttpServletRequest request,HaiPaymentWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPaymentWithBLOBs> rm = new ReturnObject<HaiPaymentWithBLOBs>();

		if(model.getPayName() == null || model.getPayName().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
		c.andPayNameEqualTo(model.getPayName());
		c.andStoreIdEqualTo(store_id);
		int count = haiPaymentMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiPaymentMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiPaymentWithBLOBs> payment_update(HttpServletRequest request,Integer payId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPaymentWithBLOBs> rm = new ReturnObject<HaiPaymentWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPaymentWithBLOBs model = haiPaymentMapper.selectByPrimaryKey(payId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiPaymentWithBLOBs> payment_update_submit(HttpServletRequest request,HaiPaymentWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPaymentWithBLOBs> rm = new ReturnObject<HaiPaymentWithBLOBs>();
//		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
		
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPayIdEqualTo(model.getPayId());
//		c.andStoreIdEqualTo(store_id);

		int count = haiPaymentMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = haiPaymentMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiPaymentWithBLOBs> payment_find(HttpServletRequest request,Integer payId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPaymentWithBLOBs> rm = new ReturnObject<HaiPaymentWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiPaymentWithBLOBs model = haiPaymentMapper.selectByPrimaryKey(payId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiPayment> payment_delete(HttpServletRequest request,Integer payId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPayment> rm = new ReturnObject<HaiPayment>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPaymentExample example = new HaiPaymentExample();
		HaiPaymentExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPayIdEqualTo(payId);
		int code = haiPaymentMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiPaymentWithBLOBs model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}
	
}











