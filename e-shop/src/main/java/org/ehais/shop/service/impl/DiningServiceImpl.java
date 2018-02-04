package org.ehais.shop.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.tp.TpDiningMapper;
import org.ehais.shop.model.tp.TpDining;
import org.ehais.shop.model.tp.TpDiningExample;
import org.ehais.shop.model.tp.TpDiningWithBLOBs;
import org.ehais.shop.service.DiningService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("diningService")
public class DiningServiceImpl  extends CommonServiceImpl implements DiningService{
	
	@Autowired
	private TpDiningMapper tpDiningMapper;
	
	public ReturnObject<TpDining> dining_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<TpDining> rm = new ReturnObject<TpDining>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<TpDining> dining_list_json(HttpServletRequest request,EConditionObject condition) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDining> rm = new ReturnObject<TpDining>();
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		c.andSuppliersIdEqualTo(suppliers_id);
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		List<TpDining> list = tpDiningMapper.selectByExample(example);
		long total = tpDiningMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<TpDiningWithBLOBs> dining_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningWithBLOBs model = new TpDiningWithBLOBs();
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<TpDiningWithBLOBs> dining_insert_submit(HttpServletRequest request,TpDiningWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();

		if(model.getGoodsName() == null || model.getGoodsName().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


//		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
//		model.setStoreId(store_id);

		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		c.andGoodsNameEqualTo(model.getGoodsName());
		c.andSuppliersIdEqualTo(suppliers_id);
		long count = tpDiningMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = tpDiningMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<TpDiningWithBLOBs> dining_update(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		c.andGoodsIdEqualTo(goodsId);
		c.andSuppliersIdEqualTo(suppliers_id);
		List<TpDiningWithBLOBs> list = tpDiningMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningWithBLOBs model = list.get(0);
//		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<TpDiningWithBLOBs> dining_update_submit(HttpServletRequest request,TpDiningWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		
		c.andGoodsIdEqualTo(model.getGoodsId());
		c.andSuppliersIdEqualTo(suppliers_id);

		long count = tpDiningMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = tpDiningMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<TpDiningWithBLOBs> dining_info(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		c.andGoodsIdEqualTo(goodsId);
		c.andSuppliersIdEqualTo(suppliers_id);
		List<TpDiningWithBLOBs> list = tpDiningMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningWithBLOBs model = list.get(0);
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<TpDiningWithBLOBs> dining_find(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		TpDiningWithBLOBs model = tpDiningMapper.selectByPrimaryKey(goodsId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<TpDining> dining_delete(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDining> rm = new ReturnObject<TpDining>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGoodsIdEqualTo(goodsId);
		int code = tpDiningMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,TpDiningWithBLOBs model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "dining.xml",model,"tp_dining",optionMap);
		
		
		return bootStrapList;
	}
	
}











