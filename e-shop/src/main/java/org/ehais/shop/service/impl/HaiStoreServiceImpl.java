package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.model.EHaiAdminUserExample;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiStoreExample;
import org.ehais.epublic.service.EStoreService;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.HaiStoreService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.ehais.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("haiStoreService")
public class HaiStoreServiceImpl  extends CommonServiceImpl implements HaiStoreService{
	
	@Autowired
	protected EStoreService eStoreService;
	@Autowired
	private EHaiStoreMapper haiStoreMapper;
	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	protected Integer default_public_id = Integer.valueOf(ResourceUtil.getProValue("default_public_id"));
	protected String default_admin_password = ResourceUtil.getProValue("default_admin_password");
	
	public ReturnObject<EHaiStore> store_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiStore> store_list_json(HttpServletRequest request,EConditionObject condition,Integer partnerId,String storeName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		
		EHaiStoreExample example = new EHaiStoreExample();
		EHaiStoreExample.Criteria c = example.createCriteria();
		if(partnerId == null)
		partnerId = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		c.andPartnerIdEqualTo(partnerId);
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
		
		
		//效验用户名
		EHaiAdminUserExample adminExp = new EHaiAdminUserExample();
		adminExp.createCriteria().andUserNameEqualTo(model.getStoreName());
		long aUser = eHaiAdminUserMapper.countByExample(adminExp);
		if(aUser > 0){rm.setMsg("此用户名已存在");return rm;}
		
		//效验商家名称
		EHaiStoreExample example = new EHaiStoreExample();
		EHaiStoreExample.Criteria c = example.createCriteria();
		c.andStoreNameEqualTo(model.getStoreName());
//		c.andPartnerIdEqualTo(partner_id);
		long count = haiStoreMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}
		Integer addTime = Long.valueOf(System.currentTimeMillis() / 1000).intValue();
		model.setTheme(EAdminClassifyEnum.dining);
		model.setPartnerId(partner_id);
		model.setPublicId(default_public_id);
		model.setState(true);
		
		int code = haiStoreMapper.insertSelective(model);
		
		EHaiAdminUserWithBLOBs admin = new EHaiAdminUserWithBLOBs();
		admin.setUserName(model.getStoreName());
		admin.setPassword(EncryptUtils.md5(default_admin_password));
		admin.setStoreId(model.getStoreId());
		admin.setEmail("");
		admin.setClassify(EAdminClassifyEnum.dining);
		admin.setAddTime(addTime);
		admin.setLastLogin(addTime);
		admin.setPartnerId(partner_id);
		eHaiAdminUserMapper.insert(admin);
		
		
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiStore> store_partner_update(HttpServletRequest request,Integer storeId)
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
	
	public ReturnObject<EHaiStore> store_partner_update_submit(HttpServletRequest request,EHaiStore model)
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

		this.StoreModelToBean(bean, model);

		int code = haiStoreMapper.updateByExampleSelective(bean, example);
		
		eStoreService.setEStore(bean.getStoreId(),bean);
		
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}
	
	
	public ReturnObject<EHaiStore> store_update(HttpServletRequest request,Integer storeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		
		EHaiStore model = haiStoreMapper.selectByPrimaryKey(storeId);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

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

		EHaiStore bean = haiStoreMapper.selectByPrimaryKey(model.getStoreId());
		if(bean == null){
			rm.setMsg("记录不存在");
			return rm;
		}
		
		this.StoreModelToBean(bean, model);



		int code = haiStoreMapper.updateByPrimaryKeyWithBLOBs(bean);
		
		
		eStoreService.setEStore(bean.getStoreId(),bean);
		
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}
	
	//model to bean
	private void StoreModelToBean(EHaiStore bean,EHaiStore model){
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
	
	
	public ReturnObject<EHaiStore> store_cache(HttpServletRequest request,Integer storeId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		
		EHaiStore store = haiStoreMapper.selectByPrimaryKey(storeId);
		if(store == null){
			rm.setMsg("记录不存在");
			return rm;
		}
		
		eStoreService.setEStore(storeId, store);
		
		rm.setCode(1);
		rm.setMsg("更新成功");
		return rm;
	}
	
	
}

