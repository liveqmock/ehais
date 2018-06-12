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
import org.ehais.shop.mapper.HaiWarehouseMapper;
import org.ehais.shop.model.HaiWarehouse;
import org.ehais.shop.model.HaiWarehouseExample;
import org.ehais.shop.service.HaiWarehouseService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "简码不能为空")//warehouse_code
@NotBlank(message = "名称不能为空")//warehouse_name

**/
/**

**/




@Service("haiWarehouseService")
public class HaiWarehouseServiceImpl  extends CommonServiceImpl implements HaiWarehouseService{
	
	@Autowired
	private HaiWarehouseMapper haiWarehouseMapper;


	public ReturnObject<HaiWarehouse> warehouse_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiWarehouse> warehouse_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String warehouseName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiWarehouseExample example = new HaiWarehouseExample();
		HaiWarehouseExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(warehouseName))c.andWarehouseNameLike("%"+warehouseName+"%");
		List<HaiWarehouse> list = haiWarehouseMapper.selectByExample(example);
		long total = haiWarehouseMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiWarehouse> warehouse_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiWarehouse model = new HaiWarehouse();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiWarehouse> warehouse_insert_submit(HttpServletRequest request,HaiWarehouse model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiWarehouseExample example = new HaiWarehouseExample();
		HaiWarehouseExample.Criteria c = example.createCriteria();
		c.andWarehouseNameEqualTo(model.getWarehouseName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiWarehouseMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiWarehouseMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiWarehouse> warehouse_update(HttpServletRequest request,Integer warehouseId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiWarehouseExample example = new HaiWarehouseExample();
		HaiWarehouseExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andWarehouseIdEqualTo(warehouseId);
		long count = haiWarehouseMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiWarehouse model = haiWarehouseMapper.selectByPrimaryKey(warehouseId);
**/
		HaiWarehouse model = haiWarehouseMapper.get_hai_warehouse_info(warehouseId,store_id);
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
	
	public ReturnObject<HaiWarehouse> warehouse_update_submit(HttpServletRequest request,HaiWarehouse model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiWarehouseExample example = new HaiWarehouseExample();
		HaiWarehouseExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andWarehouseIdEqualTo(model.getWarehouseId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiWarehouseMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiWarehouse bean = haiWarehouseMapper.selectByPrimaryKey(model.getWarehouseId());

bean.setWarehouseCode(model.getWarehouseCode());
bean.setWarehouseName(model.getWarehouseName());
bean.setRemark(model.getRemark());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiWarehouseMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiWarehouse> warehouse_info(HttpServletRequest request,Integer warehouseId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiWarehouseExample example = new HaiWarehouseExample();
		HaiWarehouseExample.Criteria c = example.createCriteria();
		c.andWarehouseIdEqualTo(warehouseId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiWarehouse> list = haiWarehouseMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiWarehouse model = list.get(0);
		**/

		HaiWarehouse model = haiWarehouseMapper.get_hai_warehouse_info(warehouseId,store_id);
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


	public ReturnObject<HaiWarehouse> warehouse_find(HttpServletRequest request,Integer warehouseId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiWarehouseExample example = new HaiWarehouseExample();
		HaiWarehouseExample.Criteria c = example.createCriteria();
		c.andWarehouseIdEqualTo(warehouseId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiWarehouse> list = haiWarehouseMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiWarehouse model = list.get(0);
		**/

		HaiWarehouse model = haiWarehouseMapper.get_hai_warehouse_info(warehouseId,store_id);
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

	public ReturnObject<HaiWarehouse> warehouse_delete(HttpServletRequest request,Integer warehouseId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiWarehouseExample example = new HaiWarehouseExample();
		HaiWarehouseExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andWarehouseIdEqualTo(warehouseId);

		long count = haiWarehouseMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiWarehouseMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiWarehouse model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "warehouse.xml",model,"hai_warehouse",optionMap);
		
		
		return bootStrapList;
	}













	
}











