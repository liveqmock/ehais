package org.ehais.shop.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ehais.service.impl.CommonServiceImpl;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**

**/
/**

**/




@Service("haiOrderFinanceService")
public class HaiOrderFinanceServiceImpl  extends CommonServiceImpl implements HaiOrderFinanceService{
	
	@Autowired
	private HaiOrderFinanceMapper haiOrderFinanceMapper;


	public ReturnObject<HaiOrderFinance> orderfinance_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiOrderFinance> rm = new ReturnObject<HaiOrderFinance>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiOrderFinance> orderfinance_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String financeName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderFinance> rm = new ReturnObject<HaiOrderFinance>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiOrderFinanceExample example = new HaiOrderFinanceExample();
		HaiOrderFinanceExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(financeName))c.andFinanceNameLike("%"+financeName+"%");
		List<HaiOrderFinance> list = haiOrderFinanceMapper.selectByExample(example);
		long total = haiOrderFinanceMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiOrderFinance> orderfinance_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderFinance> rm = new ReturnObject<HaiOrderFinance>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderFinance model = new HaiOrderFinance();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiOrderFinance> orderfinance_insert_submit(HttpServletRequest request,HaiOrderFinance model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderFinance> rm = new ReturnObject<HaiOrderFinance>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiOrderFinanceExample example = new HaiOrderFinanceExample();
		HaiOrderFinanceExample.Criteria c = example.createCriteria();
		c.andFinanceNameEqualTo(model.getFinanceName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiOrderFinanceMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiOrderFinanceMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiOrderFinance> orderfinance_update(HttpServletRequest request,Integer financeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderFinance> rm = new ReturnObject<HaiOrderFinance>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiOrderFinanceExample example = new HaiOrderFinanceExample();
		HaiOrderFinanceExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andFinanceIdEqualTo(financeId);
		long count = haiOrderFinanceMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiOrderFinance model = haiOrderFinanceMapper.selectByPrimaryKey(financeId);
**/
		HaiOrderFinance model = haiOrderFinanceMapper.get_hai_order_finance_info(financeId,store_id);
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
	
	public ReturnObject<HaiOrderFinance> orderfinance_update_submit(HttpServletRequest request,HaiOrderFinance model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderFinance> rm = new ReturnObject<HaiOrderFinance>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderFinanceExample example = new HaiOrderFinanceExample();
		HaiOrderFinanceExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andFinanceIdEqualTo(model.getFinanceId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiOrderFinanceMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiOrderFinance bean = haiOrderFinanceMapper.selectByPrimaryKey(model.getFinanceId());

bean.setOrderId(model.getOrderId());
bean.setOrderDate(model.getOrderDate());
bean.setUserType(model.getUserType());
bean.setHabit(model.getHabit());
bean.setDocType(model.getDocType());
bean.setGoodsId(model.getGoodsId());
bean.setGoodsName(model.getGoodsName());
bean.setQuantity(model.getQuantity());
bean.setSellPrice(model.getSellPrice());
bean.setCostPrice(model.getCostPrice());
bean.setAveragePrice(model.getAveragePrice());
bean.setTotalAmount(model.getTotalAmount());
bean.setAccountId(model.getAccountId());
bean.setAccountName(model.getAccountName());
bean.setMoney(model.getMoney());
bean.setBusinessId(model.getBusinessId());
bean.setClientName(model.getClientName());
bean.setRemark(model.getRemark());
bean.setVideoPath(model.getVideoPath());
bean.setVideoToText(model.getVideoToText());
bean.setValid(model.getValid());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiOrderFinanceMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiOrderFinance> orderfinance_info(HttpServletRequest request,Integer financeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderFinance> rm = new ReturnObject<HaiOrderFinance>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiOrderFinanceExample example = new HaiOrderFinanceExample();
		HaiOrderFinanceExample.Criteria c = example.createCriteria();
		c.andFinanceIdEqualTo(financeId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiOrderFinance> list = haiOrderFinanceMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiOrderFinance model = list.get(0);
		**/

		HaiOrderFinance model = haiOrderFinanceMapper.get_hai_order_finance_info(financeId,store_id);
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


	public ReturnObject<HaiOrderFinance> orderfinance_find(HttpServletRequest request,Integer financeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderFinance> rm = new ReturnObject<HaiOrderFinance>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiOrderFinanceExample example = new HaiOrderFinanceExample();
		HaiOrderFinanceExample.Criteria c = example.createCriteria();
		c.andFinanceIdEqualTo(financeId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiOrderFinance> list = haiOrderFinanceMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiOrderFinance model = list.get(0);
		**/

		HaiOrderFinance model = haiOrderFinanceMapper.get_hai_order_finance_info(financeId,store_id);
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

	public ReturnObject<HaiOrderFinance> orderfinance_delete(HttpServletRequest request,Integer financeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderFinance> rm = new ReturnObject<HaiOrderFinance>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiOrderFinanceExample example = new HaiOrderFinanceExample();
		HaiOrderFinanceExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andFinanceIdEqualTo(financeId);

		long count = haiOrderFinanceMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiOrderFinanceMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiOrderFinance model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "orderfinance.xml",model,"hai_orderfinance",optionMap);
		
		
		return bootStrapList;
	}













	
}











