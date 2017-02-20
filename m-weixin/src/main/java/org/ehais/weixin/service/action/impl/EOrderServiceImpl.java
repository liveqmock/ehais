package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.List;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.mapper.EOrderMapper;
import org.ehais.weixin.model.EOrder;
import org.ehais.weixin.model.EOrderExample;
import org.ehais.weixin.service.action.EOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("eOrderService")
public class EOrderServiceImpl  extends CommonServiceImpl implements EOrderService{
	
	@Autowired
	private EOrderMapper eOrderMapper;
	
	public ReturnObject<EOrder> e_order_list(Integer wxid) throws Exception{
		
		ReturnObject<EOrder> rm = new ReturnObject<EOrder>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EOrder> e_order_list_json(Integer wxid,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EOrder> rm = new ReturnObject<EOrder>();
		Integer start = (page - 1 ) * len;
		
		EOrderExample example = new EOrderExample();
		EOrderExample.Criteria c = example.createCriteria();
		c.andWxidEqualTo(wxid);
		c.andEPayStatusEqualTo(Short.valueOf("1"));//支付成功的
		example.setStart(start);
		example.setLen(len);
		List<EOrder> list = eOrderMapper.e_order_list_by_example(example);
		Integer total = eOrderMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EOrder> e_order_insert(Integer wxid)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EOrder> rm = new ReturnObject<EOrder>();	
		EOrder model = new EOrder();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<EOrder> e_order_insert_submit(EOrder model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EOrder> rm = new ReturnObject<EOrder>();
		int code = eOrderMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EOrder> e_order_update(Integer wxid,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EOrder> rm = new ReturnObject<EOrder>();
		
		EOrder model = eOrderMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EOrder> e_order_update_submit(EOrder model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EOrder> rm = new ReturnObject<EOrder>();
		EOrderExample example = new EOrderExample();
		EOrderExample.Criteria c = example.createCriteria();
		c.andWxidEqualTo(model.getWxid());
		c.andOrderIdEqualTo(model.getOrderId());
		int code = eOrderMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EOrder> e_order_find(Integer wxid,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EOrder> rm = new ReturnObject<EOrder>();
		
		
		EOrder model = eOrderMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EOrder> e_order_delete(Integer wxid,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EOrder> rm = new ReturnObject<EOrder>();
		EOrderExample example = new EOrderExample();
		EOrderExample.Criteria c = example.createCriteria();
		c.andWxidEqualTo(wxid);
		c.andOrderIdEqualTo(key);
		int code = eOrderMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(EOrder model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}
	
}

