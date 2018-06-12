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
import org.ehais.shop.mapper.HaiIncomeMapper;
import org.ehais.shop.model.HaiIncome;
import org.ehais.shop.model.HaiIncomeExample;
import org.ehais.shop.service.HaiIncomeService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "简码不能为空")//income_code
@NotBlank(message = "名称不能为空")//income_name

**/
/**

**/




@Service("haiIncomeService")
public class HaiIncomeServiceImpl  extends CommonServiceImpl implements HaiIncomeService{
	
	@Autowired
	private HaiIncomeMapper haiIncomeMapper;


	public ReturnObject<HaiIncome> income_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiIncome> rm = new ReturnObject<HaiIncome>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiIncome> income_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String incomeName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiIncome> rm = new ReturnObject<HaiIncome>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiIncomeExample example = new HaiIncomeExample();
		HaiIncomeExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(incomeName))c.andIncomeNameLike("%"+incomeName+"%");
		List<HaiIncome> list = haiIncomeMapper.selectByExample(example);
		long total = haiIncomeMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiIncome> income_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiIncome> rm = new ReturnObject<HaiIncome>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiIncome model = new HaiIncome();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiIncome> income_insert_submit(HttpServletRequest request,HaiIncome model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiIncome> rm = new ReturnObject<HaiIncome>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiIncomeExample example = new HaiIncomeExample();
		HaiIncomeExample.Criteria c = example.createCriteria();
		c.andIncomeNameEqualTo(model.getIncomeName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiIncomeMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiIncomeMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiIncome> income_update(HttpServletRequest request,Integer incomeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiIncome> rm = new ReturnObject<HaiIncome>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiIncomeExample example = new HaiIncomeExample();
		HaiIncomeExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andIncomeIdEqualTo(incomeId);
		long count = haiIncomeMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiIncome model = haiIncomeMapper.selectByPrimaryKey(incomeId);
**/
		HaiIncome model = haiIncomeMapper.get_hai_income_info(incomeId,store_id);
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
	
	public ReturnObject<HaiIncome> income_update_submit(HttpServletRequest request,HaiIncome model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiIncome> rm = new ReturnObject<HaiIncome>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiIncomeExample example = new HaiIncomeExample();
		HaiIncomeExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andIncomeIdEqualTo(model.getIncomeId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiIncomeMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiIncome bean = haiIncomeMapper.selectByPrimaryKey(model.getIncomeId());

bean.setIncomeCode(model.getIncomeCode());
bean.setIncomeName(model.getIncomeName());
bean.setRemark(model.getRemark());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiIncomeMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiIncome> income_info(HttpServletRequest request,Integer incomeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiIncome> rm = new ReturnObject<HaiIncome>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiIncomeExample example = new HaiIncomeExample();
		HaiIncomeExample.Criteria c = example.createCriteria();
		c.andIncomeIdEqualTo(incomeId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiIncome> list = haiIncomeMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiIncome model = list.get(0);
		**/

		HaiIncome model = haiIncomeMapper.get_hai_income_info(incomeId,store_id);
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


	public ReturnObject<HaiIncome> income_find(HttpServletRequest request,Integer incomeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiIncome> rm = new ReturnObject<HaiIncome>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiIncomeExample example = new HaiIncomeExample();
		HaiIncomeExample.Criteria c = example.createCriteria();
		c.andIncomeIdEqualTo(incomeId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiIncome> list = haiIncomeMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiIncome model = list.get(0);
		**/

		HaiIncome model = haiIncomeMapper.get_hai_income_info(incomeId,store_id);
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

	public ReturnObject<HaiIncome> income_delete(HttpServletRequest request,Integer incomeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiIncome> rm = new ReturnObject<HaiIncome>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiIncomeExample example = new HaiIncomeExample();
		HaiIncomeExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andIncomeIdEqualTo(incomeId);

		long count = haiIncomeMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiIncomeMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiIncome model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "income.xml",model,"hai_income",optionMap);
		
		
		return bootStrapList;
	}













	
}











