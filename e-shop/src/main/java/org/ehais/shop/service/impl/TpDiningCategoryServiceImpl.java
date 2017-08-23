package org.ehais.shop.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.tp.TpDiningCategoryMapper;
import org.ehais.shop.mapper.tp.TpDiningMapper;
import org.ehais.shop.model.tp.TpDiningCategory;
import org.ehais.shop.model.tp.TpDiningCategoryExample;
import org.ehais.shop.model.tp.TpDiningExample;
import org.ehais.shop.service.TpDiningCategoryService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("tpDiningCategoryService")
public class TpDiningCategoryServiceImpl  extends CommonServiceImpl implements TpDiningCategoryService{
	
	@Autowired
	private TpDiningCategoryMapper tpDiningCategoryMapper;
	@Autowired
	private TpDiningMapper tpDiningMapper;
	
	public ReturnObject<TpDiningCategory> diningcategory_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<TpDiningCategory> rm = new ReturnObject<TpDiningCategory>();
		rm.setCode(0);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<TpDiningCategory> diningcategory_list_json(HttpServletRequest request,EConditionObject condition) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningCategory> rm = new ReturnObject<TpDiningCategory>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		TpDiningCategoryExample example = new TpDiningCategoryExample();
		TpDiningCategoryExample.Criteria c = example.createCriteria();
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		List<TpDiningCategory> list = tpDiningCategoryMapper.selectByExample(example);
		long total = tpDiningCategoryMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<TpDiningCategory> diningcategory_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningCategory> rm = new ReturnObject<TpDiningCategory>();
		rm.setCode(0);
		TpDiningCategory model = new TpDiningCategory();

		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<TpDiningCategory> diningcategory_insert_submit(HttpServletRequest request,TpDiningCategory model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningCategory> rm = new ReturnObject<TpDiningCategory>();
		rm.setCode(0);

		

		TpDiningCategoryExample example = new TpDiningCategoryExample();
		TpDiningCategoryExample.Criteria c = example.createCriteria();
		c.andNameEqualTo(model.getName());
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		long count = tpDiningCategoryMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}
		
		model.setSuppliersId(suppliers_id);
		model.setClassify("dining");

		int code = tpDiningCategoryMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<TpDiningCategory> diningcategory_update(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningCategory> rm = new ReturnObject<TpDiningCategory>();
		rm.setCode(0);
		TpDiningCategoryExample example = new TpDiningCategoryExample();
		TpDiningCategoryExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
		c.andClassifyEqualTo("dining");
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		List<TpDiningCategory> list = tpDiningCategoryMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningCategory model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<TpDiningCategory> diningcategory_update_submit(HttpServletRequest request,TpDiningCategory model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningCategory> rm = new ReturnObject<TpDiningCategory>();
		rm.setCode(0);
		TpDiningCategoryExample example = new TpDiningCategoryExample();
		TpDiningCategoryExample.Criteria c = example.createCriteria();
		
		c.andIdEqualTo(model.getId());
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		c.andClassifyEqualTo("dining");
		long count = tpDiningCategoryMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		TpDiningCategory bean = tpDiningCategoryMapper.selectByPrimaryKey(model.getId());

		bean.setName(model.getName());


		int code = tpDiningCategoryMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<TpDiningCategory> diningcategory_info(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningCategory> rm = new ReturnObject<TpDiningCategory>();
		rm.setCode(0);
		TpDiningCategoryExample example = new TpDiningCategoryExample();
		TpDiningCategoryExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		c.andClassifyEqualTo("dining");
		List<TpDiningCategory> list = tpDiningCategoryMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningCategory model = list.get(0);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<TpDiningCategory> diningcategory_find(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningCategory> rm = new ReturnObject<TpDiningCategory>();
		rm.setCode(0);
		
		TpDiningCategory model = tpDiningCategoryMapper.selectByPrimaryKey(id);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<TpDiningCategory> diningcategory_delete(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningCategory> rm = new ReturnObject<TpDiningCategory>();
		rm.setCode(0);
		TpDiningCategoryExample example = new TpDiningCategoryExample();
		TpDiningCategoryExample.Criteria c = example.createCriteria();
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		c.andIdEqualTo(id);
		c.andClassifyEqualTo("dining");
		long count = tpDiningCategoryMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		
		//检查是否有商品关联
		TpDiningExample dExample = new TpDiningExample();
		dExample.createCriteria().andCatIdEqualTo(id).andSuppliersIdEqualTo(suppliers_id);
		count = tpDiningMapper.countByExample(dExample);
		if(count > 0){
			rm.setMsg("存在关联商品，请先删除关联商品");
			return rm;
		}
		
		int code = tpDiningCategoryMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,TpDiningCategory model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "diningcategory.xml",model,"tp_diningcategory",optionMap);
		
		
		return bootStrapList;
	}
	
}











