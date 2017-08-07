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
import org.ehais.shop.model.tp.TpDining;
import org.ehais.shop.model.tp.TpDiningCategory;
import org.ehais.shop.model.tp.TpDiningCategoryExample;
import org.ehais.shop.model.tp.TpDiningExample;
import org.ehais.shop.model.tp.TpDiningWithBLOBs;
import org.ehais.shop.service.TpDiningService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("tpDiningService")
public class TpDiningServiceImpl  extends CommonServiceImpl implements TpDiningService{
	
	@Autowired
	private TpDiningMapper tpDiningMapper;
	@Autowired
	private TpDiningCategoryMapper tpDiningCategoryMapper;
	
	public ReturnObject<TpDining> dining_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<TpDining> rm = new ReturnObject<TpDining>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<TpDining> dining_list_json(HttpServletRequest request,
			EConditionObject condition,Integer cat_id, String keyword) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDining> rm = new ReturnObject<TpDining>();
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		if(cat_id != null && cat_id > 0)c.andCatIdEqualTo(cat_id);
		if(keyword != null && !keyword.equals(""))c.andGoodsNameLike("%"+keyword+"%");
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		List<TpDining> list = tpDiningMapper.selectByExample(example);
		long total = tpDiningMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}
	
	public ReturnObject<TpDiningCategory> dining_category_list_json(HttpServletRequest request) throws Exception{
		ReturnObject<TpDiningCategory> rm = new ReturnObject<TpDiningCategory>();
		rm.setCode(0);
		TpDiningCategoryExample dcExample = new TpDiningCategoryExample();
		TpDiningCategoryExample.Criteria c = dcExample.createCriteria();
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		List<TpDiningCategory> dcList = tpDiningCategoryMapper.selectByExample(dcExample);
		rm.setRows(dcList);
		rm.setCode(1);
		return rm;		
	}

	public ReturnObject<TpDiningWithBLOBs> dining_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();	
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		TpDiningWithBLOBs model = new TpDiningWithBLOBs();
		Map<String, Object> map = new HashMap<String, Object>();
		TpDiningCategoryExample dcExample = new TpDiningCategoryExample();
		TpDiningCategoryExample.Criteria c = dcExample.createCriteria();
		c.andSuppliersIdEqualTo(suppliers_id);
		List<TpDiningCategory> dcList = tpDiningCategoryMapper.selectByExample(dcExample);
		map.put("dcList", dcList);
		
		rm.setModel(model);
		rm.setCode(1);
		rm.setMap(map);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<TpDiningWithBLOBs> dining_insert_submit(HttpServletRequest request,TpDiningWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();

		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		c.andGoodsNameEqualTo(model.getGoodsName());
		c.andSuppliersIdEqualTo(suppliers_id);
		long count = tpDiningMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}
		
		model.setIsOnSale(model.getIsOnSale() == null ? false : true);
		model.setIsHot(model.getIsHot() == null ? false : true);
		model.setSuppliersId(suppliers_id);
		model.setClassify("dining");
		model.setLastUpdate(Long.valueOf(System.currentTimeMillis() / 1000).intValue());
		int code = tpDiningMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<TpDiningWithBLOBs> dining_update(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();
		rm.setCode(0);
//		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		c.andGoodsIdEqualTo(goodsId);
		c.andSuppliersIdEqualTo(suppliers_id);
		List<TpDiningWithBLOBs> list = tpDiningMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningWithBLOBs model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		TpDiningCategoryExample dcExample = new TpDiningCategoryExample();
		TpDiningCategoryExample.Criteria dc = dcExample.createCriteria();
		dc.andSuppliersIdEqualTo(suppliers_id);
		List<TpDiningCategory> dcList = tpDiningCategoryMapper.selectByExample(dcExample);
		map.put("dcList", dcList);
		rm.setMap(map);
		
		rm.setCode(1);
		rm.setModel(model);
		rm.setAction("edit");
		return rm;
	}
	
	public ReturnObject<TpDiningWithBLOBs> dining_update_submit(HttpServletRequest request,TpDiningWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGoodsIdEqualTo(model.getGoodsId());
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		
		long count = tpDiningMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		
		TpDiningWithBLOBs bean = tpDiningMapper.selectByPrimaryKey(model.getGoodsId());
		
		bean.setCatId(model.getCatId());
		bean.setShopPrice(model.getShopPrice());
		bean.setCostPrice(model.getCostPrice());
		bean.setOriginalImg(model.getOriginalImg());
		bean.setIsOnSale(model.getIsOnSale() == null ? false : true);
		bean.setSort(model.getSort());
		bean.setIsHot(model.getIsHot() == null ? false : true);
		bean.setLastUpdate(Long.valueOf(System.currentTimeMillis() / 1000).intValue());
		
		int code = tpDiningMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<TpDiningWithBLOBs> dining_info(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		c.andGoodsIdEqualTo(goodsId);
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		List<TpDiningWithBLOBs> list = tpDiningMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		TpDiningWithBLOBs model = list.get(0);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<TpDiningWithBLOBs> dining_find(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDiningWithBLOBs> rm = new ReturnObject<TpDiningWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		TpDiningWithBLOBs model = tpDiningMapper.selectByPrimaryKey(goodsId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<TpDining> dining_delete(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TpDining> rm = new ReturnObject<TpDining>();
		TpDiningExample example = new TpDiningExample();
		TpDiningExample.Criteria c = example.createCriteria();
		c.andGoodsIdEqualTo(goodsId);
		Long suppliers_id = (Long)request.getSession().getAttribute(EConstants.SESSION_SUPPLIERS_ID);
		c.andSuppliersIdEqualTo(suppliers_id);
		int code = tpDiningMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,TpDiningWithBLOBs model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "dining.xml",model,"tp_dining",optionMap);
		
		
		return bootStrapList;
	}
	
}











