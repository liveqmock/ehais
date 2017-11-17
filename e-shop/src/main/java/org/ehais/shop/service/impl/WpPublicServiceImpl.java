package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.cache.WXPublicCacheManager;
import org.ehais.epublic.mapper.WpPublicMapper;
import org.ehais.epublic.model.WpPublic;
import org.ehais.epublic.model.WpPublicExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.WpPublicService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("wpPublicService")
public class WpPublicServiceImpl  extends CommonServiceImpl implements WpPublicService{
	
	@Autowired
	private WpPublicMapper wpPublicMapper;
	@Autowired
	protected EWPPublicService eWPPublicService;
	
	
	public ReturnObject<WpPublic> public_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<WpPublic> rm = new ReturnObject<WpPublic>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<WpPublic> public_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String publicName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublic> rm = new ReturnObject<WpPublic>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		WpPublicExample example = new WpPublicExample();
		WpPublicExample.Criteria c = example.createCriteria();
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("id desc");
//		if(keySubId > 0 ) c.andKeySubIdEqualTo(keySubId);
		if(StringUtils.isNotEmpty(publicName))c.andPublicNameLike("%"+publicName+"%");
		List<WpPublic> list = wpPublicMapper.selectByExample(example);
		long total = wpPublicMapper.countByExample(example);


//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("tempSublateList",this.tempSublateList(request));
//		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<WpPublicWithBLOBs> public_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicWithBLOBs model = new WpPublicWithBLOBs();


//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("tempSublateList",this.tempSublateList(request));
//		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<WpPublicWithBLOBs> public_insert_submit(HttpServletRequest request,WpPublicWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		model.setStoreId(store_id);
		
		Date date = new Date();
//		model.setCreateDate(date);
//		model.setUpdateDate(date);

		WpPublicExample example = new WpPublicExample();
		WpPublicExample.Criteria c = example.createCriteria();
		c.andPublicNameEqualTo(model.getPublicName());
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = wpPublicMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = wpPublicMapper.insertSelective(model);
		
		eWPPublicService.setWpPublic(model.getId(), model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<WpPublicWithBLOBs> public_update(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicExample example = new WpPublicExample();
		WpPublicExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
//		c.andStoreIdEqualTo(store_id);
		List<WpPublicWithBLOBs> list = wpPublicMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		WpPublicWithBLOBs model = list.get(0);


//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("tempSublateList",this.tempSublateList(request));
//		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<WpPublicWithBLOBs> public_update_submit(HttpServletRequest request,WpPublicWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicExample example = new WpPublicExample();
		WpPublicExample.Criteria c = example.createCriteria();
		
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andIdEqualTo(model.getId());
//		c.andStoreIdEqualTo(store_id);

		long count = wpPublicMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		WpPublicWithBLOBs bean = wpPublicMapper.selectByPrimaryKey(model.getId());

//		bean.setId(model.getId());
bean.setUid(model.getUid());
bean.setPublicName(model.getPublicName());
bean.setPublicNo(model.getPublicNo());
//bean.setPublicId(model.getPublicId());
bean.setWechat(model.getWechat());
bean.setInterfaceUrl(model.getInterfaceUrl());
bean.setHeadfaceUrl(model.getHeadfaceUrl());
bean.setArea(model.getArea());
bean.setAddonConfig(model.getAddonConfig());
bean.setAddonStatus(model.getAddonStatus());
bean.setToken(model.getToken());
bean.setIsUse(model.getIsUse());
bean.setType(model.getType());
bean.setAppid(model.getAppid());
bean.setSecret(model.getSecret());
bean.setGroupId(model.getGroupId());
bean.setEncodingaeskey(model.getEncodingaeskey());
bean.setTipsUrl(model.getTipsUrl());
bean.setDomain(model.getDomain());
bean.setIsBind(model.getIsBind());
bean.setPayAppid(model.getPayAppid());
bean.setPaySecret(model.getPaySecret());
bean.setMchId(model.getMchId());
bean.setSubMchId(model.getSubMchId());
bean.setMchSecret(model.getMchSecret());
//bean.setStoreId(model.getStoreId());


		Date date = new Date();
//		model.setUpdateDate(date);

		int code = wpPublicMapper.updateByPrimaryKey(bean);
		
		eWPPublicService.setWpPublic(bean.getId(), bean);
		
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<WpPublicWithBLOBs> public_info(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicExample example = new WpPublicExample();
		WpPublicExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<WpPublicWithBLOBs> list = wpPublicMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		WpPublicWithBLOBs model = list.get(0);

//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("tempSublateList",this.tempSublateList(request));
//		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<WpPublicWithBLOBs> public_find(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicExample example = new WpPublicExample();
		WpPublicExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<WpPublicWithBLOBs> list = wpPublicMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		WpPublicWithBLOBs model = list.get(0);
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("tempSublateList",this.tempSublateList(request));
//		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<WpPublic> public_delete(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublic> rm = new ReturnObject<WpPublic>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicExample example = new WpPublicExample();
		WpPublicExample.Criteria c = example.createCriteria();
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andIdEqualTo(id);

		long count = wpPublicMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = wpPublicMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}

	@Override
	public ReturnObject<WpPublicWithBLOBs> public_detail(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();
		try {
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			WpPublicWithBLOBs model = eWPPublicService.getWpPublic(store_id);
			if(model == null)model = new WpPublicWithBLOBs();
			rm.setModel(model);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return rm;
	}

	@Override
	public ReturnObject<WpPublicWithBLOBs> public_detail_submit(HttpServletRequest request, WpPublicWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		WpPublicWithBLOBs bean = eWPPublicService.getWpPublic(store_id);
		if(bean == null) {
			bean = new WpPublicWithBLOBs();
			BeanByModel(bean,model);
			wpPublicMapper.insertSelective(bean);
		}else {
			BeanByModel(bean,model);
			bean.setId(model.getId());
			wpPublicMapper.updateByPrimaryKeySelective(bean);
		}
		
		eWPPublicService.setWpPublic(bean.getId(), bean);

		rm.setCode(1);
		rm.setMsg("保存成功");
		return rm;
	}
	



	private void BeanByModel(WpPublicWithBLOBs bean,WpPublicWithBLOBs model) {
		bean.setUid(model.getUid());
		bean.setPublicName(model.getPublicName());
		bean.setPublicNo(model.getPublicNo());
		//bean.setPublicId(model.getPublicId());
		bean.setWechat(model.getWechat());
		bean.setInterfaceUrl(model.getInterfaceUrl());
		bean.setHeadfaceUrl(model.getHeadfaceUrl());
		bean.setArea(model.getArea());
		bean.setAddonConfig(model.getAddonConfig());
		bean.setAddonStatus(model.getAddonStatus());
		bean.setToken(model.getToken());
		bean.setIsUse(model.getIsUse());
		bean.setType(model.getType());
		bean.setAppid(model.getAppid());
		bean.setSecret(model.getSecret());
		bean.setGroupId(model.getGroupId());
		bean.setEncodingaeskey(model.getEncodingaeskey());
		bean.setTipsUrl(model.getTipsUrl());
		bean.setDomain(model.getDomain());
		bean.setIsBind(model.getIsBind());
		bean.setPayAppid(model.getPayAppid());
		bean.setPaySecret(model.getPaySecret());
		bean.setMchId(model.getMchId());
		bean.setSubMchId(model.getSubMchId());
		bean.setMchSecret(model.getMchSecret());
	}






	public ReturnObject<WpPublicWithBLOBs> public_cache(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();
		rm.setCode(0);
		
		WpPublicWithBLOBs model = wpPublicMapper.selectByPrimaryKey(id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		WXPublicCacheManager.getInstance().putWXPublic(id, model);
		
		rm.setCode(1);
		rm.setMsg("更新缓存成功");
		return rm;
	}






	
}











