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
import org.ehais.shop.mapper.HaiLabourMapper;
import org.ehais.shop.model.HaiLabour;
import org.ehais.shop.model.HaiLabourExample;
import org.ehais.shop.service.HaiLabourService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "简码不能为空")//labour_code
@NotBlank(message = "名称不能为空")//labour_name

**/
/**

**/




@Service("haiLabourService")
public class HaiLabourServiceImpl  extends CommonServiceImpl implements HaiLabourService{
	
	@Autowired
	private HaiLabourMapper haiLabourMapper;


	public ReturnObject<HaiLabour> labour_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiLabour> rm = new ReturnObject<HaiLabour>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiLabour> labour_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String labourName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiLabour> rm = new ReturnObject<HaiLabour>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiLabourExample example = new HaiLabourExample();
		HaiLabourExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(labourName))c.andLabourNameLike("%"+labourName+"%");
		List<HaiLabour> list = haiLabourMapper.selectByExample(example);
		long total = haiLabourMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiLabour> labour_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiLabour> rm = new ReturnObject<HaiLabour>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiLabour model = new HaiLabour();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiLabour> labour_insert_submit(HttpServletRequest request,HaiLabour model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiLabour> rm = new ReturnObject<HaiLabour>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiLabourExample example = new HaiLabourExample();
		HaiLabourExample.Criteria c = example.createCriteria();
		c.andLabourNameEqualTo(model.getLabourName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiLabourMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiLabourMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiLabour> labour_update(HttpServletRequest request,Integer labourId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiLabour> rm = new ReturnObject<HaiLabour>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiLabourExample example = new HaiLabourExample();
		HaiLabourExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andLabourIdEqualTo(labourId);
		long count = haiLabourMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiLabour model = haiLabourMapper.selectByPrimaryKey(labourId);
**/
		HaiLabour model = haiLabourMapper.get_hai_labour_info(labourId,store_id);
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
	
	public ReturnObject<HaiLabour> labour_update_submit(HttpServletRequest request,HaiLabour model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiLabour> rm = new ReturnObject<HaiLabour>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiLabourExample example = new HaiLabourExample();
		HaiLabourExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andLabourIdEqualTo(model.getLabourId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiLabourMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiLabour bean = haiLabourMapper.selectByPrimaryKey(model.getLabourId());

bean.setLabourCode(model.getLabourCode());
bean.setLabourName(model.getLabourName());
bean.setRemark(model.getRemark());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiLabourMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiLabour> labour_info(HttpServletRequest request,Integer labourId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiLabour> rm = new ReturnObject<HaiLabour>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiLabourExample example = new HaiLabourExample();
		HaiLabourExample.Criteria c = example.createCriteria();
		c.andLabourIdEqualTo(labourId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiLabour> list = haiLabourMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiLabour model = list.get(0);
		**/

		HaiLabour model = haiLabourMapper.get_hai_labour_info(labourId,store_id);
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


	public ReturnObject<HaiLabour> labour_find(HttpServletRequest request,Integer labourId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiLabour> rm = new ReturnObject<HaiLabour>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiLabourExample example = new HaiLabourExample();
		HaiLabourExample.Criteria c = example.createCriteria();
		c.andLabourIdEqualTo(labourId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiLabour> list = haiLabourMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiLabour model = list.get(0);
		**/

		HaiLabour model = haiLabourMapper.get_hai_labour_info(labourId,store_id);
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

	public ReturnObject<HaiLabour> labour_delete(HttpServletRequest request,Integer labourId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiLabour> rm = new ReturnObject<HaiLabour>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiLabourExample example = new HaiLabourExample();
		HaiLabourExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andLabourIdEqualTo(labourId);

		long count = haiLabourMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiLabourMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiLabour model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "labour.xml",model,"hai_labour",optionMap);
		
		
		return bootStrapList;
	}













	
}











