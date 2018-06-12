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
import org.ehais.shop.mapper.HaiSectorsMapper;
import org.ehais.shop.model.HaiSectors;
import org.ehais.shop.model.HaiSectorsExample;
import org.ehais.shop.service.HaiSectorsService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**

**/
/**

**/




@Service("haiSectorsService")
public class HaiSectorsServiceImpl  extends CommonServiceImpl implements HaiSectorsService{
	
	@Autowired
	private HaiSectorsMapper haiSectorsMapper;


	public ReturnObject<HaiSectors> sectors_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiSectors> rm = new ReturnObject<HaiSectors>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiSectors> sectors_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String sectorsName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSectors> rm = new ReturnObject<HaiSectors>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiSectorsExample example = new HaiSectorsExample();
		HaiSectorsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(sectorsName))c.andSectorsNameLike("%"+sectorsName+"%");
		List<HaiSectors> list = haiSectorsMapper.selectByExample(example);
		long total = haiSectorsMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiSectors> sectors_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSectors> rm = new ReturnObject<HaiSectors>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiSectors model = new HaiSectors();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiSectors> sectors_insert_submit(HttpServletRequest request,HaiSectors model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSectors> rm = new ReturnObject<HaiSectors>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiSectorsExample example = new HaiSectorsExample();
		HaiSectorsExample.Criteria c = example.createCriteria();
		c.andSectorsNameEqualTo(model.getSectorsName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiSectorsMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiSectorsMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiSectors> sectors_update(HttpServletRequest request,Integer sectorsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSectors> rm = new ReturnObject<HaiSectors>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiSectorsExample example = new HaiSectorsExample();
		HaiSectorsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andSectorsIdEqualTo(sectorsId);
		long count = haiSectorsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiSectors model = haiSectorsMapper.selectByPrimaryKey(sectorsId);
**/
		HaiSectors model = haiSectorsMapper.get_hai_sectors_info(sectorsId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiSectors> sectors_update_submit(HttpServletRequest request,HaiSectors model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSectors> rm = new ReturnObject<HaiSectors>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiSectorsExample example = new HaiSectorsExample();
		HaiSectorsExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andSectorsIdEqualTo(model.getSectorsId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiSectorsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiSectors bean = haiSectorsMapper.selectByPrimaryKey(model.getSectorsId());

bean.setSectorsCode(model.getSectorsCode());
bean.setSectorsName(model.getSectorsName());
bean.setParentId(model.getParentId());
bean.setIsTop(model.getIsTop());
bean.setOrder(model.getOrder());
bean.setIsHeader(model.getIsHeader());
bean.setRemark(model.getRemark());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiSectorsMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiSectors> sectors_info(HttpServletRequest request,Integer sectorsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSectors> rm = new ReturnObject<HaiSectors>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiSectorsExample example = new HaiSectorsExample();
		HaiSectorsExample.Criteria c = example.createCriteria();
		c.andSectorsIdEqualTo(sectorsId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiSectors> list = haiSectorsMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiSectors model = list.get(0);
		**/

		HaiSectors model = haiSectorsMapper.get_hai_sectors_info(sectorsId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiSectors> sectors_find(HttpServletRequest request,Integer sectorsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSectors> rm = new ReturnObject<HaiSectors>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiSectorsExample example = new HaiSectorsExample();
		HaiSectorsExample.Criteria c = example.createCriteria();
		c.andSectorsIdEqualTo(sectorsId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiSectors> list = haiSectorsMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiSectors model = list.get(0);
		**/

		HaiSectors model = haiSectorsMapper.get_hai_sectors_info(sectorsId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiSectors> sectors_delete(HttpServletRequest request,Integer sectorsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSectors> rm = new ReturnObject<HaiSectors>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiSectorsExample example = new HaiSectorsExample();
		HaiSectorsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andSectorsIdEqualTo(sectorsId);

		long count = haiSectorsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiSectorsMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiSectors model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "sectors.xml",model,"hai_sectors",optionMap);
		
		
		return bootStrapList;
	}













	
}











