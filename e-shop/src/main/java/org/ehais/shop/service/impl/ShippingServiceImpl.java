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
import org.ehais.shop.service.ShippingService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("shippingService")
public class ShippingServiceImpl  extends CommonServiceImpl implements ShippingService{
	
	@Autowired
	private HaiShippingMapper haiShippingMapper;
	
	public ReturnObject<HaiShipping> shipping_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiShipping> rm = new ReturnObject<HaiShipping>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiShipping> shipping_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShipping> rm = new ReturnObject<HaiShipping>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(start);
		example.setLimitEnd(len);
		List<HaiShipping> list = haiShippingMapper.hai_shipping_list_by_example(example);
		Integer total = haiShippingMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiShippingWithBLOBs> shipping_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();	
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		HaiShippingWithBLOBs model = new HaiShippingWithBLOBs();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiShippingWithBLOBs> shipping_insert_submit(HttpServletRequest request,HaiShippingWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();

		if(model.getShippingName() == null || model.getShippingName().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
		c.andShippingNameEqualTo(model.getShippingName());
		c.andStoreIdEqualTo(store_id);
		int count = haiShippingMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiShippingMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiShippingWithBLOBs> shipping_update(HttpServletRequest request,Integer shippingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		HaiShippingWithBLOBs model = haiShippingMapper.selectByPrimaryKey(shippingId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiShippingWithBLOBs> shipping_update_submit(HttpServletRequest request,HaiShippingWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
		
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andShippingIdEqualTo(model.getShippingId());
		c.andStoreIdEqualTo(store_id);

		int count = haiShippingMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = haiShippingMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiShippingWithBLOBs> shipping_find(HttpServletRequest request,Integer shippingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		HaiShippingWithBLOBs model = haiShippingMapper.selectByPrimaryKey(shippingId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiShipping> shipping_delete(HttpServletRequest request,Integer shippingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShipping> rm = new ReturnObject<HaiShipping>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andShippingIdEqualTo(shippingId);
		int code = haiShippingMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiShippingWithBLOBs model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}
	
}











