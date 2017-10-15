package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiActivityMapper;
import org.ehais.shop.model.HaiActivity;
import org.ehais.shop.model.HaiActivityExample;
import org.ehais.shop.service.HaiActivityService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("haiActivityService")
public class HaiActivityServiceImpl  extends CommonServiceImpl implements HaiActivityService{
	
	@Autowired
	private HaiActivityMapper haiActivityMapper;

	@Autowired
	private EHaiArticleCatMapper haiArticleCatMapper;
	
	public ReturnObject<HaiActivity> activity_list(HttpServletRequest request,String module) throws Exception{
		
		ReturnObject<HaiActivity> rm = new ReturnObject<HaiActivity>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiActivity> activity_list_json(HttpServletRequest request,String module,EConditionObject condition,Integer catId,String activityName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivity> rm = new ReturnObject<HaiActivity>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andModuleEqualTo(module);
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("update_time desc");
		if(catId > 0 ) c.andCatIdEqualTo(catId);
		if(StringUtils.isNotEmpty(activityName))c.andActivityNameLike("%"+activityName+"%");
		List<HaiActivity> list = haiActivityMapper.selectByExample(example);
		long total = haiActivityMapper.countByExample(example);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articlecatList",this.articlecatList(request,module));
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiActivity> activity_insert(HttpServletRequest request,String module)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivity> rm = new ReturnObject<HaiActivity>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiActivity model = new HaiActivity();


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articlecatList",this.articlecatList(request,module));
		map.put("parentActivityList",this.parentActivityList(request,module,null));
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiActivity> activity_insert_submit(HttpServletRequest request,String module,HaiActivity model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivity> rm = new ReturnObject<HaiActivity>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateTime(date);
		model.setUpdateTime(date);

		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
		c.andActivityNameEqualTo(model.getActivityName());
		c.andModuleEqualTo(module);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiActivityMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiActivityMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiActivity> activity_update(HttpServletRequest request,String module,Integer activityId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivity> rm = new ReturnObject<HaiActivity>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andActivityIdEqualTo(activityId);
		c.andModuleEqualTo(module);
		List<HaiActivity> list = haiActivityMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiActivity model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articlecatList",this.articlecatList(request,module));
		map.put("parentActivityList",this.parentActivityList(request,module,model.getActivityId()));
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiActivity> activity_update_submit(HttpServletRequest request,String module,HaiActivity model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivity> rm = new ReturnObject<HaiActivity>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andActivityIdEqualTo(model.getActivityId());
		c.andModuleEqualTo(module);
		
		long count = haiActivityMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiActivity bean = haiActivityMapper.selectByPrimaryKey(model.getActivityId());

bean.setActivityName(model.getActivityName());
bean.setAdminId(model.getAdminId());
bean.setUpdator(model.getUpdator());
bean.setCatId(model.getCatId());
bean.setParentId(model.getParentId());
bean.setStatus(model.getStatus());
bean.setStartTime(model.getStartTime());
bean.setEndTime(model.getEndTime());
bean.setReleaseTime(model.getReleaseTime());
bean.setCutOffTime(model.getCutOffTime());
bean.setRemark(model.getRemark());
bean.setSummary(model.getSummary());
bean.setContent(model.getContent());
bean.setActivityThumb(model.getActivityThumb());
bean.setActivityImages(model.getActivityImages());
bean.setApplyCount(model.getApplyCount());
bean.setPlayCount(model.getPlayCount());
bean.setMoney(model.getMoney());
bean.setLimitCount(model.getLimitCount());
bean.setReadCount(model.getReadCount());
bean.setCollectCount(model.getCollectCount());
bean.setCommentCount(model.getCommentCount());
bean.setAttentionCount(model.getAttentionCount());
bean.setLinkMan(model.getLinkMan());
bean.setMobile(model.getMobile());
bean.setKeywords(model.getKeywords());
bean.setAddress(model.getAddress());
bean.setOrganisers(model.getOrganisers());
bean.setAccessory(model.getAccessory());
bean.setIsValid(model.getIsValid());
bean.setCity(model.getCity());
bean.setCounty(model.getCounty());
bean.setModule(model.getModule());
bean.setStoreId(model.getStoreId());
bean.setUserId(model.getUserId());


		Date date = new Date();
		model.setUpdateTime(date);

		int code = haiActivityMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiActivity> activity_info(HttpServletRequest request,String module,Integer activityId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivity> rm = new ReturnObject<HaiActivity>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
		c.andActivityIdEqualTo(activityId);
		c.andModuleEqualTo(module);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiActivity> list = haiActivityMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiActivity model = list.get(0);

//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("articlecatList",this.articlecatList(request,module));
//		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiActivity> activity_find(HttpServletRequest request,String module,Integer activityId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivity> rm = new ReturnObject<HaiActivity>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
		c.andActivityIdEqualTo(activityId);
		c.andModuleEqualTo(module);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiActivity> list = haiActivityMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiActivity model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articlecatList",this.articlecatList(request,module));
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiActivity> activity_delete(HttpServletRequest request,String module,Integer activityId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivity> rm = new ReturnObject<HaiActivity>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andActivityIdEqualTo(activityId);
		c.andModuleEqualTo(module);
		
		long count = haiActivityMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiActivityMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	

	//获取父类活动
	private List<HaiActivity> parentActivityList(HttpServletRequest request,String module,Integer activityId) throws Exception{
		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andModuleEqualTo(module);
		if(activityId!=null && activityId > 0)c.andActivityIdNotEqualTo(activityId);
		List<HaiActivity> list = haiActivityMapper.selectByExample(example);
		
		return list;
		
	}


/////////////////////////////////////////////////////////////////////////////////////

	public List<EHaiArticleCat> articlecatList(HttpServletRequest request,String module) throws Exception {
		// TODO Auto-generated method stub
		
		
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andModuleEqualTo(module);
		List<EHaiArticleCat> list = haiArticleCatMapper.selectByExample(example);
		
		return list;
	}


	public ReturnObject<EHaiArticleCat> articlecat_list(HttpServletRequest request,String module) throws Exception{
		
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_list_json(HttpServletRequest request,String module,EConditionObject condition,String catName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andModuleEqualTo(module);
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		if(StringUtils.isNotEmpty(catName))c.andCatNameLike("%"+catName+"%");
		List<EHaiArticleCat> list = haiArticleCatMapper.selectByExample(example);
		long total = haiArticleCatMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_insert(HttpServletRequest request,String module)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCat model = new EHaiArticleCat();

		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<EHaiArticleCat> articlecat_insert_submit(HttpServletRequest request,String module,EHaiArticleCat model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andCatNameEqualTo(model.getCatName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andModuleEqualTo(module);
		long count = haiArticleCatMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiArticleCatMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_update(HttpServletRequest request,String module,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andCatIdEqualTo(catId);
		c.andModuleEqualTo(module);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andModuleEqualTo(module);
		List<EHaiArticleCat> list = haiArticleCatMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticleCat model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiArticleCat> articlecat_update_submit(HttpServletRequest request,String module,EHaiArticleCat model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(model.getCatId());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andModuleEqualTo(module);

		long count = haiArticleCatMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		EHaiArticleCat bean = haiArticleCatMapper.selectByPrimaryKey(model.getCatId());

bean.setCatName(model.getCatName());
bean.setCatType(model.getCatType());
bean.setModule(model.getModule());
bean.setKeywords(model.getKeywords());
bean.setCatDesc(model.getCatDesc());
bean.setSortOrder(model.getSortOrder());
bean.setShowInNav(model.getShowInNav());
bean.setParentId(model.getParentId());
bean.setStoreId(model.getStoreId());
bean.setCode(model.getCode());
bean.setUserId(model.getUserId());
bean.setImages(model.getImages());
bean.setIsValid(model.getIsValid());


		int code = haiArticleCatMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_info(HttpServletRequest request,String module,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andCatIdEqualTo(catId);
		c.andModuleEqualTo(module);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<EHaiArticleCat> list = haiArticleCatMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticleCat model = list.get(0);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<EHaiArticleCat> articlecat_find(HttpServletRequest request,String module,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andCatIdEqualTo(catId);
		c.andModuleEqualTo(module);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<EHaiArticleCat> list = haiArticleCatMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticleCat model = list.get(0);
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiArticleCat> articlecat_delete(HttpServletRequest request,String module,Integer catId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticleCat> rm = new ReturnObject<EHaiArticleCat>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andCatIdEqualTo(catId);
		c.andModuleEqualTo(module);
		
		long count = haiArticleCatMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		HaiActivityExample exampleCheck = new HaiActivityExample();
		HaiActivityExample.Criteria cCheck = exampleCheck.createCriteria();
		cCheck.andCatIdEqualTo(catId);
		exampleCheck.CriteriaStoreId(cCheck, this.storeIdCriteriaObject(request));
		long countCheck = haiActivityMapper.countByExample(exampleCheck);
		if(countCheck > 0){
			rm.setMsg("存在关联记录，请先移除关联记录后再删除此分类");
			return rm;
		}

		int code = haiArticleCatMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}








	
}











