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
import org.ehais.shop.mapper.HaiExpensesMapper;
import org.ehais.shop.model.HaiExpenses;
import org.ehais.shop.model.HaiExpensesExample;
import org.ehais.shop.service.HaiExpensesService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "简码不能为空")//expenses_code
@NotBlank(message = "名称不能为空")//expenses_name

**/
/**

**/




@Service("haiExpensesService")
public class HaiExpensesServiceImpl  extends CommonServiceImpl implements HaiExpensesService{
	
	@Autowired
	private HaiExpensesMapper haiExpensesMapper;


	public ReturnObject<HaiExpenses> expenses_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiExpenses> rm = new ReturnObject<HaiExpenses>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiExpenses> expenses_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String expensesName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiExpenses> rm = new ReturnObject<HaiExpenses>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiExpensesExample example = new HaiExpensesExample();
		HaiExpensesExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(expensesName))c.andExpensesNameLike("%"+expensesName+"%");
		List<HaiExpenses> list = haiExpensesMapper.selectByExample(example);
		long total = haiExpensesMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiExpenses> expenses_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiExpenses> rm = new ReturnObject<HaiExpenses>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiExpenses model = new HaiExpenses();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiExpenses> expenses_insert_submit(HttpServletRequest request,HaiExpenses model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiExpenses> rm = new ReturnObject<HaiExpenses>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiExpensesExample example = new HaiExpensesExample();
		HaiExpensesExample.Criteria c = example.createCriteria();
		c.andExpensesNameEqualTo(model.getExpensesName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiExpensesMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiExpensesMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiExpenses> expenses_update(HttpServletRequest request,Integer expensesId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiExpenses> rm = new ReturnObject<HaiExpenses>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiExpensesExample example = new HaiExpensesExample();
		HaiExpensesExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andExpensesIdEqualTo(expensesId);
		long count = haiExpensesMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiExpenses model = haiExpensesMapper.selectByPrimaryKey(expensesId);
**/
		HaiExpenses model = haiExpensesMapper.get_hai_expenses_info(expensesId,store_id);
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
	
	public ReturnObject<HaiExpenses> expenses_update_submit(HttpServletRequest request,HaiExpenses model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiExpenses> rm = new ReturnObject<HaiExpenses>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiExpensesExample example = new HaiExpensesExample();
		HaiExpensesExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andExpensesIdEqualTo(model.getExpensesId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiExpensesMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiExpenses bean = haiExpensesMapper.selectByPrimaryKey(model.getExpensesId());

bean.setExpensesCode(model.getExpensesCode());
bean.setExpensesName(model.getExpensesName());
bean.setRemark(model.getRemark());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiExpensesMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiExpenses> expenses_info(HttpServletRequest request,Integer expensesId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiExpenses> rm = new ReturnObject<HaiExpenses>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiExpensesExample example = new HaiExpensesExample();
		HaiExpensesExample.Criteria c = example.createCriteria();
		c.andExpensesIdEqualTo(expensesId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiExpenses> list = haiExpensesMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiExpenses model = list.get(0);
		**/

		HaiExpenses model = haiExpensesMapper.get_hai_expenses_info(expensesId,store_id);
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


	public ReturnObject<HaiExpenses> expenses_find(HttpServletRequest request,Integer expensesId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiExpenses> rm = new ReturnObject<HaiExpenses>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiExpensesExample example = new HaiExpensesExample();
		HaiExpensesExample.Criteria c = example.createCriteria();
		c.andExpensesIdEqualTo(expensesId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiExpenses> list = haiExpensesMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiExpenses model = list.get(0);
		**/

		HaiExpenses model = haiExpensesMapper.get_hai_expenses_info(expensesId,store_id);
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

	public ReturnObject<HaiExpenses> expenses_delete(HttpServletRequest request,Integer expensesId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiExpenses> rm = new ReturnObject<HaiExpenses>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiExpensesExample example = new HaiExpensesExample();
		HaiExpensesExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andExpensesIdEqualTo(expensesId);

		long count = haiExpensesMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiExpensesMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiExpenses model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "expenses.xml",model,"hai_expenses",optionMap);
		
		
		return bootStrapList;
	}













	
}











