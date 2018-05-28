package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.HaiWithdrawDepositMapper;
import org.ehais.epublic.model.HaiWithdrawDeposit;
import org.ehais.epublic.model.HaiWithdrawDepositExample;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.HaiWithdrawDepositService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**

**/
/**

**/




@Service("haiWithdrawDepositService")
public class HaiWithdrawDepositServiceImpl  extends CommonServiceImpl implements HaiWithdrawDepositService{
	
	@Autowired
	private HaiWithdrawDepositMapper haiWithdrawDepositMapper;


	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiWithdrawDeposit> rm = new ReturnObject<HaiWithdrawDeposit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String title) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWithdrawDeposit> rm = new ReturnObject<HaiWithdrawDeposit>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiWithdrawDepositExample example = new HaiWithdrawDepositExample();
		HaiWithdrawDepositExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("update_date desc");
		
//		if(StringUtils.isNotEmpty(title))c.andTitleLike("%"+title+"%");
		List<HaiWithdrawDeposit> list = haiWithdrawDepositMapper.selectByExample(example);
		long total = haiWithdrawDepositMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWithdrawDeposit> rm = new ReturnObject<HaiWithdrawDeposit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiWithdrawDeposit model = new HaiWithdrawDeposit();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_insert_submit(HttpServletRequest request,HaiWithdrawDeposit model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWithdrawDeposit> rm = new ReturnObject<HaiWithdrawDeposit>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
//		model.setUpdateDate(date);

		HaiWithdrawDepositExample example = new HaiWithdrawDepositExample();
		HaiWithdrawDepositExample.Criteria c = example.createCriteria();
//		c.andTitleEqualTo(model.getTitle());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiWithdrawDepositMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiWithdrawDepositMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_update(HttpServletRequest request,Integer wdId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWithdrawDeposit> rm = new ReturnObject<HaiWithdrawDeposit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiWithdrawDepositExample example = new HaiWithdrawDepositExample();
		HaiWithdrawDepositExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andWdIdEqualTo(wdId);
		List<HaiWithdrawDeposit> list = haiWithdrawDepositMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiWithdrawDeposit model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_update_submit(HttpServletRequest request,HaiWithdrawDeposit model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWithdrawDeposit> rm = new ReturnObject<HaiWithdrawDeposit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiWithdrawDepositExample example = new HaiWithdrawDepositExample();
		HaiWithdrawDepositExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andWdIdEqualTo(model.getWdId());
		c.andStoreIdEqualTo(store_id);

		long count = haiWithdrawDepositMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiWithdrawDeposit bean = haiWithdrawDepositMapper.selectByPrimaryKey(model.getWdId());

bean.setUserId(model.getUserId());
bean.setCreateDate(model.getCreateDate());
//bean.setWithdrawDeposit(model.getWithdrawDeposit());
bean.setIsSuccess(model.getIsSuccess());
bean.setSuccessDate(model.getSuccessDate());
bean.setReturnMessage(model.getReturnMessage());


		Date date = new Date();
//		model.setUpdateDate(date);

		int code = haiWithdrawDepositMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_info(HttpServletRequest request,Integer wdId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWithdrawDeposit> rm = new ReturnObject<HaiWithdrawDeposit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiWithdrawDepositExample example = new HaiWithdrawDepositExample();
		HaiWithdrawDepositExample.Criteria c = example.createCriteria();
		c.andWdIdEqualTo(wdId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiWithdrawDeposit> list = haiWithdrawDepositMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiWithdrawDeposit model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_find(HttpServletRequest request,Integer wdId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWithdrawDeposit> rm = new ReturnObject<HaiWithdrawDeposit>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiWithdrawDepositExample example = new HaiWithdrawDepositExample();
		HaiWithdrawDepositExample.Criteria c = example.createCriteria();
		c.andWdIdEqualTo(wdId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiWithdrawDeposit> list = haiWithdrawDepositMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiWithdrawDeposit model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiWithdrawDeposit> withdrawdeposit_delete(HttpServletRequest request,Integer wdId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWithdrawDeposit> rm = new ReturnObject<HaiWithdrawDeposit>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiWithdrawDepositExample example = new HaiWithdrawDepositExample();
		HaiWithdrawDepositExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andWdIdEqualTo(wdId);

		long count = haiWithdrawDepositMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiWithdrawDepositMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiWithdrawDeposit model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "withdrawdeposit.xml",model,"hai_withdrawdeposit",optionMap);
		
		
		return bootStrapList;
	}













	
}











