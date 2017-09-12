package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiStoreExample;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.HaiStoreService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("haiStoreService")
public class HaiStoreServiceImpl  extends CommonServiceImpl implements HaiStoreService{
	
	@Autowired
	private EHaiStoreMapper haiStoreMapper;

	
	public ReturnObject<EHaiStore> store_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiStore> store_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String storeName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		
		EHaiStoreExample example = new EHaiStoreExample();
		EHaiStoreExample.Criteria c = example.createCriteria();
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		c.andPartnerIdEqualTo(partner_id);
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("store_id desc");
		if(StringUtils.isNotEmpty(storeName))c.andStoreNameLike("%"+storeName+"%");
		List<EHaiStore> list = haiStoreMapper.selectByExample(example);
		long total = haiStoreMapper.countByExample(example);



		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EHaiStore> store_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		EHaiStore model = new EHaiStore();

		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<EHaiStore> store_insert_submit(HttpServletRequest request,EHaiStore model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);

		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		model.setPartnerId(partner_id);
		

		EHaiStoreExample example = new EHaiStoreExample();
		EHaiStoreExample.Criteria c = example.createCriteria();
		c.andStoreNameEqualTo(model.getStoreName());
		c.andPartnerIdEqualTo(partner_id);
		long count = haiStoreMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiStoreMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiStore> store_update(HttpServletRequest request,Integer storeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		EHaiStoreExample example = new EHaiStoreExample();
		EHaiStoreExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(storeId);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		c.andPartnerIdEqualTo(partner_id);
		List<EHaiStore> list = haiStoreMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiStore model = list.get(0);


		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiStore> store_update_submit(HttpServletRequest request,EHaiStore model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		EHaiStoreExample example = new EHaiStoreExample();
		EHaiStoreExample.Criteria c = example.createCriteria();
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		c.andPartnerIdEqualTo(partner_id);
		c.andStoreIdEqualTo(model.getStoreId());

		long count = haiStoreMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		EHaiStore bean = haiStoreMapper.selectByPrimaryKey(model.getStoreId());

bean.setStoreName(model.getStoreName());
bean.setOwnerName(model.getOwnerName());
bean.setOwnerCard(model.getOwnerCard());
bean.setRegionId(model.getRegionId());
bean.setRegionName(model.getRegionName());
bean.setAddress(model.getAddress());
bean.setZipcode(model.getZipcode());
bean.setTel(model.getTel());
bean.setSgrade(model.getSgrade());
bean.setApplyRemark(model.getApplyRemark());
bean.setCreditValue(model.getCreditValue());
bean.setPraiseRate(model.getPraiseRate());
bean.setDomain(model.getDomain());
bean.setState(model.getState());
bean.setCloseReason(model.getCloseReason());
bean.setAddTime(model.getAddTime());
bean.setEndTime(model.getEndTime());
bean.setCertification(model.getCertification());
bean.setSortOrder(model.getSortOrder());
bean.setRecommended(model.getRecommended());
bean.setTheme(model.getTheme());
bean.setStoreBanner(model.getStoreBanner());
bean.setStoreLogo(model.getStoreLogo());
bean.setDescription(model.getDescription());
bean.setImage1(model.getImage1());
bean.setImage2(model.getImage2());
bean.setImage3(model.getImage3());
bean.setImQq(model.getImQq());
bean.setImWw(model.getImWw());
bean.setImMsn(model.getImMsn());
bean.setEnableGroupbuy(model.getEnableGroupbuy());
bean.setEnableRadar(model.getEnableRadar());
bean.setPartnerId(model.getPartnerId());
bean.setContacts(model.getContacts());
bean.setMobile(model.getMobile());



		int code = haiStoreMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiStore> store_info(HttpServletRequest request,Integer storeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		EHaiStoreExample example = new EHaiStoreExample();
		EHaiStoreExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(storeId);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		c.andPartnerIdEqualTo(partner_id);
		List<EHaiStore> list = haiStoreMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiStore model = list.get(0);


		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<EHaiStore> store_find(HttpServletRequest request,Integer storeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		
		EHaiStoreExample example = new EHaiStoreExample();
		EHaiStoreExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(storeId);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		c.andPartnerIdEqualTo(partner_id);
		List<EHaiStore> list = haiStoreMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiStore model = list.get(0);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiStore> store_delete(HttpServletRequest request,Integer storeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		EHaiStoreExample example = new EHaiStoreExample();
		EHaiStoreExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(storeId);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		c.andPartnerIdEqualTo(partner_id);
		long count = haiStoreMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiStoreMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
}

