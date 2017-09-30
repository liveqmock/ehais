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
import org.ehais.shop.mapper.HaiShippingMapper;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.model.HaiShippingExample;
import org.ehais.shop.model.HaiShippingWithBLOBs;
import org.ehais.shop.service.HaiShippingService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ChineseCharToEn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("haiShippingService")
public class HaiShippingServiceImpl  extends CommonServiceImpl implements HaiShippingService{
	
	@Autowired
	private HaiShippingMapper haiShippingMapper;

	public ReturnObject<HaiShipping> shipping_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiShipping> rm = new ReturnObject<HaiShipping>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiShipping> shipping_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String shippingName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShipping> rm = new ReturnObject<HaiShipping>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("shipping_order asc");
		if(StringUtils.isNotEmpty(shippingName))c.andShippingNameLike("%"+shippingName+"%");
		List<HaiShipping> list = haiShippingMapper.selectByExample(example);
		long total = haiShippingMapper.countByExample(example);



		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiShippingWithBLOBs> shipping_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiShippingWithBLOBs model = new HaiShippingWithBLOBs();


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiShippingWithBLOBs> shipping_insert_submit(HttpServletRequest request,HaiShippingWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		

		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
		c.andShippingNameEqualTo(model.getShippingName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiShippingMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}

		model.setShippingCode(ChineseCharToEn.getAllFirstLetter(model.getShippingName()));

		int code = haiShippingMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiShippingWithBLOBs> shipping_update(HttpServletRequest request,Integer shippingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
		c.andShippingIdEqualTo(shippingId);
		c.andStoreIdEqualTo(store_id);
		List<HaiShippingWithBLOBs> list = haiShippingMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiShippingWithBLOBs model = list.get(0);


		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiShippingWithBLOBs> shipping_update_submit(HttpServletRequest request,HaiShippingWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andShippingIdEqualTo(model.getShippingId());
		c.andStoreIdEqualTo(store_id);

		long count = haiShippingMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiShippingWithBLOBs bean = haiShippingMapper.selectByPrimaryKey(model.getShippingId());
		bean.setShippingCode(ChineseCharToEn.getAllFirstLetter(model.getShippingName()));
bean.setShippingName(model.getShippingName());
bean.setShippingDesc(model.getShippingDesc());
bean.setInsure(model.getInsure());
bean.setSupportCod(model.getSupportCod());
bean.setEnabled(model.getEnabled());
bean.setIsDefault(model.getIsDefault());
bean.setShippingPrint(model.getShippingPrint());
bean.setPrintBg(model.getPrintBg());
bean.setConfigLable(model.getConfigLable());
bean.setPrintModel(model.getPrintModel());
bean.setShippingOrder(model.getShippingOrder());
bean.setStoreId(model.getStoreId());
bean.setClassname(model.getClassname());


		int code = haiShippingMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiShippingWithBLOBs> shipping_info(HttpServletRequest request,Integer shippingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
		c.andShippingIdEqualTo(shippingId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiShippingWithBLOBs> list = haiShippingMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiShippingWithBLOBs model = list.get(0);


		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiShippingWithBLOBs> shipping_find(HttpServletRequest request,Integer shippingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShippingWithBLOBs> rm = new ReturnObject<HaiShippingWithBLOBs>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
		c.andShippingIdEqualTo(shippingId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiShippingWithBLOBs> list = haiShippingMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiShippingWithBLOBs model = list.get(0);
		

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiShipping> shipping_delete(HttpServletRequest request,Integer shippingId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShipping> rm = new ReturnObject<HaiShipping>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiShippingExample example = new HaiShippingExample();
		HaiShippingExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andShippingIdEqualTo(shippingId);

		long count = haiShippingMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiShippingMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiShippingWithBLOBs model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "shipping.xml",model,"hai_shipping",optionMap);
		
		
		return bootStrapList;
	}









	
}











