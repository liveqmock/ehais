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
import org.ehais.shop.mapper.HaiAdMapper;
import org.ehais.shop.mapper.HaiAdPositionMapper;
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdExample;
import org.ehais.shop.model.HaiAdPosition;
import org.ehais.shop.model.HaiAdPositionExample;
import org.ehais.shop.service.HaiAdService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("haiAdService")
public class HaiAdServiceImpl  extends CommonServiceImpl implements HaiAdService{
	
	@Autowired
	private HaiAdMapper haiAdMapper;

	@Autowired
	private HaiAdPositionMapper haiAdPositionMapper;
	
	public ReturnObject<HaiAd> ad_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiAd> ad_list_json(HttpServletRequest request,EConditionObject condition,Integer positionId,String adName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();

		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("ad_id desc");
		if(positionId > 0 ) c.andPositionIdEqualTo(positionId);
		if(StringUtils.isNotEmpty(adName))c.andAdNameLike("%"+adName+"%");
		List<HaiAd> list = haiAdMapper.selectByExample(example);
		long total = haiAdMapper.countByExample(example);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adpositionList",this.adpositionList(request));
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiAd> ad_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAd model = new HaiAd();


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adpositionList",this.adpositionList(request));
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiAd> ad_insert_submit(HttpServletRequest request,HaiAd model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);

		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		c.andAdNameEqualTo(model.getAdName());
		
		if(store_id != null && store_id > 0){
			model.setStoreId(store_id);
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			model.setPartnerId(partner_id);
			c.andPartnerIdEqualTo(partner_id);
		}
		
		long count = haiAdMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiAdMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiAd> ad_update(HttpServletRequest request,Integer adId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		c.andAdIdEqualTo(adId);
		
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		List<HaiAd> list = haiAdMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAd model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adpositionList",this.adpositionList(request));
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiAd> ad_update_submit(HttpServletRequest request,HaiAd model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		
		c.andAdIdEqualTo(model.getAdId());
		
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}

		long count = haiAdMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiAd bean = haiAdMapper.selectByPrimaryKey(model.getAdId());

bean.setPositionId(model.getPositionId());
bean.setMediaType(model.getMediaType());
bean.setAdName(model.getAdName());
bean.setAdLink(model.getAdLink());
bean.setAdPic(model.getAdPic());
bean.setAdCode(model.getAdCode());
bean.setLinkMan(model.getLinkMan());
bean.setLinkEmail(model.getLinkEmail());
bean.setLinkPhone(model.getLinkPhone());
bean.setClickCount(model.getClickCount());
bean.setEnabled(model.getEnabled());
bean.setStoreId(model.getStoreId());
bean.setStartTime(model.getStartTime());
bean.setEndTime(model.getEndTime());
bean.setNavId(model.getNavId());
bean.setSort(model.getSort());
bean.setIsMobile(model.getIsMobile());
bean.setAgencyId(model.getAgencyId());
bean.setPartnerId(model.getPartnerId());
bean.setIsVoid(model.getIsVoid());


		int code = haiAdMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiAd> ad_info(HttpServletRequest request,Integer adId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		c.andAdIdEqualTo(adId);

		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		List<HaiAd> list = haiAdMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAd model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adpositionList",this.adpositionList(request));
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiAd> ad_find(HttpServletRequest request,Integer adId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		c.andAdIdEqualTo(adId);

		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		List<HaiAd> list = haiAdMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAd model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adpositionList",this.adpositionList(request));
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiAd> ad_delete(HttpServletRequest request,Integer adId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		c.andAdIdEqualTo(adId);

		long count = haiAdMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiAdMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	

/////////////////////////////////////////////////////////////////////////////////////

	public List<HaiAdPosition> adpositionList(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		
		HaiAdPositionExample example = new HaiAdPositionExample();
		HaiAdPositionExample.Criteria c = example.createCriteria();
		
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		List<HaiAdPosition> list = haiAdPositionMapper.selectByExample(example);
		
		return list;
	}


	public ReturnObject<HaiAdPosition> adposition_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiAdPosition> rm = new ReturnObject<HaiAdPosition>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiAdPosition> adposition_list_json(HttpServletRequest request,EConditionObject condition,String positionName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdPosition> rm = new ReturnObject<HaiAdPosition>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		
		
		HaiAdPositionExample example = new HaiAdPositionExample();
		HaiAdPositionExample.Criteria c = example.createCriteria();
		
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		if(StringUtils.isNotEmpty(positionName))c.andPositionNameLike("%"+positionName+"%");
		List<HaiAdPosition> list = haiAdPositionMapper.selectByExample(example);
		long total = haiAdPositionMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiAdPosition> adposition_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdPosition> rm = new ReturnObject<HaiAdPosition>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdPosition model = new HaiAdPosition();

		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiAdPosition> adposition_insert_submit(HttpServletRequest request,HaiAdPosition model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdPosition> rm = new ReturnObject<HaiAdPosition>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		

		HaiAdPositionExample example = new HaiAdPositionExample();
		HaiAdPositionExample.Criteria c = example.createCriteria();
		c.andPositionNameEqualTo(model.getPositionName());
		
		if(store_id != null && store_id > 0){
			model.setStoreId(store_id);
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
			model.setPartnerId(partner_id);
		}
		
		long count = haiAdPositionMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiAdPositionMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiAdPosition> adposition_update(HttpServletRequest request,Integer positionId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdPosition> rm = new ReturnObject<HaiAdPosition>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdPositionExample example = new HaiAdPositionExample();
		HaiAdPositionExample.Criteria c = example.createCriteria();
		c.andPositionIdEqualTo(positionId);
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		List<HaiAdPosition> list = haiAdPositionMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAdPosition model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiAdPosition> adposition_update_submit(HttpServletRequest request,HaiAdPosition model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdPosition> rm = new ReturnObject<HaiAdPosition>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdPositionExample example = new HaiAdPositionExample();
		HaiAdPositionExample.Criteria c = example.createCriteria();
		
		c.andPositionIdEqualTo(model.getPositionId());
		
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}

		long count = haiAdPositionMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiAdPosition bean = haiAdPositionMapper.selectByPrimaryKey(model.getPositionId());

bean.setPositionCode(model.getPositionCode());
bean.setPositionName(model.getPositionName());
bean.setAdWidth(model.getAdWidth());
bean.setAdHeight(model.getAdHeight());
bean.setPositionDesc(model.getPositionDesc());
bean.setPositionStyle(model.getPositionStyle());
bean.setStoreId(model.getStoreId());
bean.setAgencyId(model.getAgencyId());
bean.setPartnerId(model.getPartnerId());
bean.setIsVoid(model.getIsVoid());


		int code = haiAdPositionMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiAdPosition> adposition_info(HttpServletRequest request,Integer positionId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdPosition> rm = new ReturnObject<HaiAdPosition>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdPositionExample example = new HaiAdPositionExample();
		HaiAdPositionExample.Criteria c = example.createCriteria();
		c.andPositionIdEqualTo(positionId);
		
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		List<HaiAdPosition> list = haiAdPositionMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAdPosition model = list.get(0);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiAdPosition> adposition_find(HttpServletRequest request,Integer positionId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdPosition> rm = new ReturnObject<HaiAdPosition>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdPositionExample example = new HaiAdPositionExample();
		HaiAdPositionExample.Criteria c = example.createCriteria();
		c.andPositionIdEqualTo(positionId);
		
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		List<HaiAdPosition> list = haiAdPositionMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiAdPosition model = list.get(0);
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiAdPosition> adposition_delete(HttpServletRequest request,Integer positionId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAdPosition> rm = new ReturnObject<HaiAdPosition>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
		HaiAdPositionExample example = new HaiAdPositionExample();
		HaiAdPositionExample.Criteria c = example.createCriteria();
		
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		c.andPositionIdEqualTo(positionId);

		long count = haiAdPositionMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		HaiAdExample exampleCheck = new HaiAdExample();
		HaiAdExample.Criteria cCheck = exampleCheck.createCriteria();
		cCheck.andPositionIdEqualTo(positionId);
		
		if(store_id != null && store_id > 0){
			c.andStoreIdEqualTo(store_id);
		}else if(partner_id != null && partner_id > 0){
			c.andPartnerIdEqualTo(partner_id);
		}
		
		long countCheck = haiAdMapper.countByExample(exampleCheck);
		if(countCheck > 0){
			rm.setMsg("存在关联记录，请先移除关联记录后再删除此分类");
			return rm;
		}

		int code = haiAdPositionMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}



	
}











