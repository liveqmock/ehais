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
import org.ehais.shop.mapper.HaiAccountMapper;
import org.ehais.shop.model.HaiAccount;
import org.ehais.shop.model.HaiAccountExample;
import org.ehais.shop.service.HaiAccountService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "简码不能为空")//account_code
@NotBlank(message = "名称不能为空")//account_name

**/
/**

**/




@Service("haiAccountService")
public class HaiAccountServiceImpl  extends CommonServiceImpl implements HaiAccountService{
	
	@Autowired
	private HaiAccountMapper haiAccountMapper;


	public ReturnObject<HaiAccount> account_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiAccount> rm = new ReturnObject<HaiAccount>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiAccount> account_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String accountName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccount> rm = new ReturnObject<HaiAccount>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiAccountExample example = new HaiAccountExample();
		HaiAccountExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(accountName))c.andAccountNameLike("%"+accountName+"%");
		List<HaiAccount> list = haiAccountMapper.selectByExample(example);
		long total = haiAccountMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiAccount> account_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccount> rm = new ReturnObject<HaiAccount>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAccount model = new HaiAccount();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiAccount> account_insert_submit(HttpServletRequest request,HaiAccount model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccount> rm = new ReturnObject<HaiAccount>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiAccountExample example = new HaiAccountExample();
		HaiAccountExample.Criteria c = example.createCriteria();
		c.andAccountNameEqualTo(model.getAccountName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiAccountMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiAccountMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiAccount> account_update(HttpServletRequest request,Integer accountId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccount> rm = new ReturnObject<HaiAccount>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiAccountExample example = new HaiAccountExample();
		HaiAccountExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAccountIdEqualTo(accountId);
		long count = haiAccountMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAccount model = haiAccountMapper.selectByPrimaryKey(accountId);
**/
		HaiAccount model = haiAccountMapper.get_hai_account_info(accountId,store_id);
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
	
	public ReturnObject<HaiAccount> account_update_submit(HttpServletRequest request,HaiAccount model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccount> rm = new ReturnObject<HaiAccount>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAccountExample example = new HaiAccountExample();
		HaiAccountExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAccountIdEqualTo(model.getAccountId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiAccountMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiAccount bean = haiAccountMapper.selectByPrimaryKey(model.getAccountId());

bean.setAccountCode(model.getAccountCode());
bean.setAccountName(model.getAccountName());
bean.setRemark(model.getRemark());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiAccountMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiAccount> account_info(HttpServletRequest request,Integer accountId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccount> rm = new ReturnObject<HaiAccount>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiAccountExample example = new HaiAccountExample();
		HaiAccountExample.Criteria c = example.createCriteria();
		c.andAccountIdEqualTo(accountId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiAccount> list = haiAccountMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAccount model = list.get(0);
		**/

		HaiAccount model = haiAccountMapper.get_hai_account_info(accountId,store_id);
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


	public ReturnObject<HaiAccount> account_find(HttpServletRequest request,Integer accountId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccount> rm = new ReturnObject<HaiAccount>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiAccountExample example = new HaiAccountExample();
		HaiAccountExample.Criteria c = example.createCriteria();
		c.andAccountIdEqualTo(accountId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiAccount> list = haiAccountMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAccount model = list.get(0);
		**/

		HaiAccount model = haiAccountMapper.get_hai_account_info(accountId,store_id);
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

	public ReturnObject<HaiAccount> account_delete(HttpServletRequest request,Integer accountId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAccount> rm = new ReturnObject<HaiAccount>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAccountExample example = new HaiAccountExample();
		HaiAccountExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAccountIdEqualTo(accountId);

		long count = haiAccountMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiAccountMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiAccount model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "account.xml",model,"hai_account",optionMap);
		
		
		return bootStrapList;
	}













	
}











