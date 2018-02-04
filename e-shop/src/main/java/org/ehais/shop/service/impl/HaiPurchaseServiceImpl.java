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
import org.ehais.shop.mapper.HaiPurchaseMapper;
import org.ehais.shop.mapper.HaiWarehouseMapper;
import org.ehais.shop.model.HaiPurchase;
import org.ehais.shop.model.HaiPurchaseExample;
import org.ehais.shop.model.HaiWarehouse;
import org.ehais.shop.model.HaiWarehouseExample;
import org.ehais.shop.service.HaiPurchaseService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "进货单不能为空")//purchase_no

**/
/**
@NotBlank(message = "仓库名称不能为空")//warehouse_name
@NotBlank(message = "排序不能为空")//sort

**/




@Service("haiPurchaseService")
public class HaiPurchaseServiceImpl  extends CommonServiceImpl implements HaiPurchaseService{
	
	@Autowired
	private HaiPurchaseMapper haiPurchaseMapper;

	@Autowired
	private HaiWarehouseMapper haiWarehouseMapper;
	
	public ReturnObject<HaiPurchase> purchase_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiPurchase> rm = new ReturnObject<HaiPurchase>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiPurchase> purchase_list_json(HttpServletRequest request,EConditionObject condition,Integer warehouseId,String purchaseNo) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPurchase> rm = new ReturnObject<HaiPurchase>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiPurchaseExample example = new HaiPurchaseExample();
		HaiPurchaseExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("update_date desc");
		if(warehouseId > 0 ) c.andWarehouseIdEqualTo(warehouseId);
		if(StringUtils.isNotEmpty(purchaseNo))c.andPurchaseNoLike("%"+purchaseNo+"%");
		List<HaiPurchase> list = haiPurchaseMapper.selectByExample(example);
		long total = haiPurchaseMapper.countByExample(example);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warehouseList",this.warehouseList(request));
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiPurchase> purchase_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPurchase> rm = new ReturnObject<HaiPurchase>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPurchase model = new HaiPurchase();


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warehouseList",this.warehouseList(request));
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiPurchase> purchase_insert_submit(HttpServletRequest request,HaiPurchase model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPurchase> rm = new ReturnObject<HaiPurchase>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setUpdateDate(date);

		HaiPurchaseExample example = new HaiPurchaseExample();
		HaiPurchaseExample.Criteria c = example.createCriteria();
		c.andPurchaseNoEqualTo(model.getPurchaseNo());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiPurchaseMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiPurchaseMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiPurchase> purchase_update(HttpServletRequest request,Integer purchaseId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPurchase> rm = new ReturnObject<HaiPurchase>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPurchaseExample example = new HaiPurchaseExample();
		HaiPurchaseExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPurchaseIdEqualTo(purchaseId);
		List<HaiPurchase> list = haiPurchaseMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiPurchase model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warehouseList",this.warehouseList(request));
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiPurchase> purchase_update_submit(HttpServletRequest request,HaiPurchase model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPurchase> rm = new ReturnObject<HaiPurchase>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPurchaseExample example = new HaiPurchaseExample();
		HaiPurchaseExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPurchaseIdEqualTo(model.getPurchaseId());
		c.andStoreIdEqualTo(store_id);

		long count = haiPurchaseMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiPurchase bean = haiPurchaseMapper.selectByPrimaryKey(model.getPurchaseId());

		bean.setPurchaseNo(model.getPurchaseNo());
bean.setOrderId(model.getOrderId());
bean.setOrderSnRmk(model.getOrderSnRmk());
bean.setGoodsId(model.getGoodsId());
bean.setGoodsNameRmk(model.getGoodsNameRmk());
bean.setPurchaseTime(model.getPurchaseTime());
bean.setQuantity(model.getQuantity());
bean.setWarehouseId(model.getWarehouseId());
bean.setRemark(model.getRemark());
bean.setStoreId(model.getStoreId());
bean.setCreateDate(model.getCreateDate());
bean.setUpdateDate(model.getUpdateDate());


		Date date = new Date();
		model.setUpdateDate(date);

		int code = haiPurchaseMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiPurchase> purchase_info(HttpServletRequest request,Integer purchaseId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPurchase> rm = new ReturnObject<HaiPurchase>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPurchaseExample example = new HaiPurchaseExample();
		HaiPurchaseExample.Criteria c = example.createCriteria();
		c.andPurchaseIdEqualTo(purchaseId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiPurchase> list = haiPurchaseMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiPurchase model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warehouseList",this.warehouseList(request));
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiPurchase> purchase_find(HttpServletRequest request,Integer purchaseId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPurchase> rm = new ReturnObject<HaiPurchase>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPurchaseExample example = new HaiPurchaseExample();
		HaiPurchaseExample.Criteria c = example.createCriteria();
		c.andPurchaseIdEqualTo(purchaseId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiPurchase> list = haiPurchaseMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiPurchase model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warehouseList",this.warehouseList(request));
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiPurchase> purchase_delete(HttpServletRequest request,Integer purchaseId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPurchase> rm = new ReturnObject<HaiPurchase>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPurchaseExample example = new HaiPurchaseExample();
		HaiPurchaseExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPurchaseIdEqualTo(purchaseId);

		long count = haiPurchaseMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiPurchaseMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiPurchase model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "purchase.xml",model,"hai_purchase",optionMap);
		
		
		return bootStrapList;
	}



/////////////////////////////////////////////////////////////////////////////////////

	public List<HaiWarehouse> warehouseList(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		
		HaiWarehouseExample example = new HaiWarehouseExample();
		HaiWarehouseExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiWarehouse> list = haiWarehouseMapper.selectByExample(example);
		
		return list;
	}


	public ReturnObject<HaiWarehouse> warehouse_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiWarehouse> warehouse_list_json(HttpServletRequest request,EConditionObject condition,String warehouseName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiWarehouse> rm = new ReturnObject<HaiWarehouse>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiWarehouseExample example = new HaiWarehouseExample();
		HaiWarehouseExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		if(StringUtils.isNotEmpty(warehouseName))c.andWarehouseNameLike("%"+warehouseName+"%");
		List<HaiWarehouse> list = haiWarehouseMapper.selectByExample(example);
		long total = haiWarehouseMapper.countByExample(example);
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
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));

		long count = haiWarehouseMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiWarehouse bean = haiWarehouseMapper.selectByPrimaryKey(model.getWarehouseId());

		bean.setWarehouseName(model.getWarehouseName());
bean.setSort(model.getSort());
bean.setStoreId(model.getStoreId());
bean.setCreateDate(model.getCreateDate());
bean.setUpdateDate(model.getUpdateDate());


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


		HaiPurchaseExample exampleCheck = new HaiPurchaseExample();
		HaiPurchaseExample.Criteria cCheck = exampleCheck.createCriteria();
		cCheck.andWarehouseIdEqualTo(warehouseId);
		exampleCheck.CriteriaStoreId(cCheck, this.storeIdCriteriaObject(request));
		long countCheck = haiPurchaseMapper.countByExample(exampleCheck);
		if(countCheck > 0){
			rm.setMsg("存在关联记录，请先移除关联记录后再删除此分类");
			return rm;
		}

		int code = haiWarehouseMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}












	
}











