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
import org.ehais.shop.mapper.HaiAccountingMapper;
import org.ehais.shop.model.HaiAccounting;
import org.ehais.shop.model.HaiAccountingExample;
import org.ehais.shop.service.HaiAccountingService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "简码不能为空")//accounting_code
@NotBlank(message = "名称不能为空")//accounting_name

**/
/**

**/




@Service("haiAccountingService")
public class HaiAccountingServiceImpl  extends CommonServiceImpl implements HaiAccountingService{
	
	@Autowired
	private HaiAccountingMapper haiAccountingMapper;


	public ReturnObject<HaiAccounting> accounting_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiAccounting> rm = new ReturnObject<HaiAccounting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiAccounting> accounting_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String accountingName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccounting> rm = new ReturnObject<HaiAccounting>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiAccountingExample example = new HaiAccountingExample();
		HaiAccountingExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(accountingName))c.andAccountingNameLike("%"+accountingName+"%");
		List<HaiAccounting> list = haiAccountingMapper.selectByExample(example);
		long total = haiAccountingMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiAccounting> accounting_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccounting> rm = new ReturnObject<HaiAccounting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAccounting model = new HaiAccounting();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiAccounting> accounting_insert_submit(HttpServletRequest request,HaiAccounting model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccounting> rm = new ReturnObject<HaiAccounting>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiAccountingExample example = new HaiAccountingExample();
		HaiAccountingExample.Criteria c = example.createCriteria();
		c.andAccountingNameEqualTo(model.getAccountingName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiAccountingMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiAccountingMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiAccounting> accounting_update(HttpServletRequest request,Integer accountingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccounting> rm = new ReturnObject<HaiAccounting>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiAccountingExample example = new HaiAccountingExample();
		HaiAccountingExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAccountingIdEqualTo(accountingId);
		long count = haiAccountingMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAccounting model = haiAccountingMapper.selectByPrimaryKey(accountingId);
**/
		HaiAccounting model = haiAccountingMapper.get_hai_accounting_info(accountingId,store_id);
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
	
	public ReturnObject<HaiAccounting> accounting_update_submit(HttpServletRequest request,HaiAccounting model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccounting> rm = new ReturnObject<HaiAccounting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAccountingExample example = new HaiAccountingExample();
		HaiAccountingExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAccountingIdEqualTo(model.getAccountingId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiAccountingMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiAccounting bean = haiAccountingMapper.selectByPrimaryKey(model.getAccountingId());

bean.setAccountingCode(model.getAccountingCode());
bean.setAccountingName(model.getAccountingName());
bean.setRemark(model.getRemark());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiAccountingMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiAccounting> accounting_info(HttpServletRequest request,Integer accountingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccounting> rm = new ReturnObject<HaiAccounting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiAccountingExample example = new HaiAccountingExample();
		HaiAccountingExample.Criteria c = example.createCriteria();
		c.andAccountingIdEqualTo(accountingId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiAccounting> list = haiAccountingMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAccounting model = list.get(0);
		**/

		HaiAccounting model = haiAccountingMapper.get_hai_accounting_info(accountingId,store_id);
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


	public ReturnObject<HaiAccounting> accounting_find(HttpServletRequest request,Integer accountingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccounting> rm = new ReturnObject<HaiAccounting>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiAccountingExample example = new HaiAccountingExample();
		HaiAccountingExample.Criteria c = example.createCriteria();
		c.andAccountingIdEqualTo(accountingId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiAccounting> list = haiAccountingMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAccounting model = list.get(0);
		**/

		HaiAccounting model = haiAccountingMapper.get_hai_accounting_info(accountingId,store_id);
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

	public ReturnObject<HaiAccounting> accounting_delete(HttpServletRequest request,Integer accountingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccounting> rm = new ReturnObject<HaiAccounting>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAccountingExample example = new HaiAccountingExample();
		HaiAccountingExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAccountingIdEqualTo(accountingId);

		long count = haiAccountingMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiAccountingMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiAccounting model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "accounting.xml",model,"hai_accounting",optionMap);
		
		
		return bootStrapList;
	}













	
}











