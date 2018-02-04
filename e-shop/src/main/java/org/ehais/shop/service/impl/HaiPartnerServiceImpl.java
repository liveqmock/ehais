package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.HaiPartnerMapper;
import org.ehais.epublic.model.HaiPartner;
import org.ehais.epublic.model.HaiPartnerExample;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.HaiPartnerService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )




@Service("haiPartnerService")
public class HaiPartnerServiceImpl  extends CommonServiceImpl implements HaiPartnerService{
	
	@Autowired
	private HaiPartnerMapper haiPartnerMapper;

	public ReturnObject<HaiPartner> partner_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiPartner> rm = new ReturnObject<HaiPartner>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiPartner> partner_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String partnerName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPartner> rm = new ReturnObject<HaiPartner>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiPartnerExample example = new HaiPartnerExample();
		HaiPartnerExample.Criteria c = example.createCriteria();
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("partner_id desc");
		
		if(StringUtils.isNotEmpty(partnerName))c.andPartnerNameLike("%"+partnerName+"%");
		List<HaiPartner> list = haiPartnerMapper.selectByExample(example);
		long total = haiPartnerMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiPartner> partner_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPartner> rm = new ReturnObject<HaiPartner>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPartner model = new HaiPartner();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiPartner> partner_insert_submit(HttpServletRequest request,HaiPartner model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPartner> rm = new ReturnObject<HaiPartner>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		model.setStoreId(store_id);
//		
//		Date date = new Date();
//		model.setCreateDate(date);
//		model.setUpdateDate(date);

		HaiPartnerExample example = new HaiPartnerExample();
		HaiPartnerExample.Criteria c = example.createCriteria();
		c.andPartnerNameEqualTo(model.getPartnerName());
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiPartnerMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiPartnerMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiPartner> partner_update(HttpServletRequest request,Integer partnerId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPartner> rm = new ReturnObject<HaiPartner>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPartnerExample example = new HaiPartnerExample();
		HaiPartnerExample.Criteria c = example.createCriteria();
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPartnerIdEqualTo(partnerId);
		List<HaiPartner> list = haiPartnerMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiPartner model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiPartner> partner_update_submit(HttpServletRequest request,HaiPartner model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPartner> rm = new ReturnObject<HaiPartner>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPartnerExample example = new HaiPartnerExample();
		HaiPartnerExample.Criteria c = example.createCriteria();
		
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPartnerIdEqualTo(model.getPartnerId());
//		c.andStoreIdEqualTo(store_id);

		long count = haiPartnerMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiPartner bean = haiPartnerMapper.selectByPrimaryKey(model.getPartnerId());

		bean.setPartnerName(model.getPartnerName());
		bean.setArea(model.getArea());
		bean.setLinkman(model.getLinkman());
		bean.setMobile(model.getMobile());
		bean.setTheme(model.getTheme());
		bean.setPayModule(model.getPayModule());


		Date date = new Date();
//		model.setUpdateDate(date);

		int code = haiPartnerMapper.updateByExampleSelective(bean, example);
		
		
		
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiPartner> partner_info(HttpServletRequest request,Integer partnerId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPartner> rm = new ReturnObject<HaiPartner>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPartnerExample example = new HaiPartnerExample();
		HaiPartnerExample.Criteria c = example.createCriteria();
		c.andPartnerIdEqualTo(partnerId);
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiPartner> list = haiPartnerMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiPartner model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiPartner> partner_find(HttpServletRequest request,Integer partnerId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPartner> rm = new ReturnObject<HaiPartner>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPartnerExample example = new HaiPartnerExample();
		HaiPartnerExample.Criteria c = example.createCriteria();
		c.andPartnerIdEqualTo(partnerId);
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiPartner> list = haiPartnerMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiPartner model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiPartner> partner_delete(HttpServletRequest request,Integer partnerId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiPartner> rm = new ReturnObject<HaiPartner>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiPartnerExample example = new HaiPartnerExample();
		HaiPartnerExample.Criteria c = example.createCriteria();
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPartnerIdEqualTo(partnerId);

		long count = haiPartnerMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiPartnerMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiPartner model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "partner.xml",model,"hai_partner",optionMap);
		
		
		return bootStrapList;
	}













	
}











