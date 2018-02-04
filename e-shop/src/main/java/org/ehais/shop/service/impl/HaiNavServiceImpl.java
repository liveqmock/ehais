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
import org.ehais.shop.mapper.HaiNavMapper;
import org.ehais.shop.model.HaiNav;
import org.ehais.shop.model.HaiNavExample;
import org.ehais.shop.service.HaiNavService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "名称不能为空")//name
@NotBlank(message = "生效不能为空")//is_valid
@NotBlank(message = "排序不能为空")//sort
@NotBlank(message = "新窗口不能为空")//opennew
@NotBlank(message = "类型不能为空")//classify

**/
/**

**/




@Service("haiNavService")
public class HaiNavServiceImpl  extends CommonServiceImpl implements HaiNavService{
	
	@Autowired
	private HaiNavMapper haiNavMapper;


	public ReturnObject<HaiNav> nav_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiNav> nav_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String name) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("sort asc");
		
		if(StringUtils.isNotEmpty(name))c.andNameLike("%"+name+"%");
		List<HaiNav> list = haiNavMapper.selectByExample(example);
		long total = haiNavMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiNav> nav_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiNav model = new HaiNav();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiNav> nav_insert_submit(HttpServletRequest request,HaiNav model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
//		model.setCreateDate(date);
//		model.setUpdateDate(date);

		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		c.andNameEqualTo(model.getName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiNavMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiNavMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiNav> nav_update(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andIdEqualTo(id);
		List<HaiNav> list = haiNavMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiNav model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiNav> nav_update_submit(HttpServletRequest request,HaiNav model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andIdEqualTo(model.getId());
		c.andStoreIdEqualTo(store_id);

		long count = haiNavMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiNav bean = haiNavMapper.selectByPrimaryKey(model.getId());

bean.setName(model.getName());
bean.setIntro(model.getIntro());
bean.setIsValid(model.getIsValid());
bean.setSort(model.getSort());
bean.setOpennew(model.getOpennew());
bean.setUrl(model.getUrl());
bean.setClassify(model.getClassify());
bean.setStoreId(model.getStoreId());
bean.setIsMobile(model.getIsMobile());
bean.setClassname(model.getClassname());
bean.setParentId(model.getParentId());
bean.setThumb(model.getThumb());
bean.setImage(model.getImage());


		Date date = new Date();
//		model.setUpdateDate(date);

		int code = haiNavMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiNav> nav_info(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiNav> list = haiNavMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiNav model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiNav> nav_find(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiNav> list = haiNavMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiNav model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiNav> nav_delete(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andIdEqualTo(id);

		long count = haiNavMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiNavMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiNav model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "nav.xml",model,"hai_nav",optionMap);
		
		
		return bootStrapList;
	}













	
}











