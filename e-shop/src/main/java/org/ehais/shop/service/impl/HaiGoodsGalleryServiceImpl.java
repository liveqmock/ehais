package org.ehais.shop.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ehais.service.impl.CommonServiceImpl;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**

**/
/**

**/




@Service("haiGoodsGalleryService")
public class HaiGoodsGalleryServiceImpl  extends CommonServiceImpl implements HaiGoodsGalleryService{
	
	@Autowired
	private HaiGoodsGalleryMapper haiGoodsGalleryMapper;


	public ReturnObject<HaiGoodsGallery> goodsgallery_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiGoodsGallery> goodsgallery_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String tableName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		HaiGoodsGalleryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		
		if(StringUtils.isNotEmpty(tableName))c.andTableNameLike("%"+tableName+"%");
		List<HaiGoodsGallery> list = haiGoodsGalleryMapper.selectByExample(example);
		long total = haiGoodsGalleryMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiGoodsGallery> goodsgallery_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsGallery model = new HaiGoodsGallery();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiGoodsGallery> goodsgallery_insert_submit(HttpServletRequest request,HaiGoodsGallery model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		HaiGoodsGalleryExample.Criteria c = example.createCriteria();
		c.andTableNameEqualTo(model.getTableName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiGoodsGalleryMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiGoodsGalleryMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiGoodsGallery> goodsgallery_update(HttpServletRequest request,Integer imgId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		HaiGoodsGalleryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andImgIdEqualTo(imgId);
		long count = haiGoodsGalleryMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGoodsGallery model = haiGoodsGalleryMapper.selectByPrimaryKey(imgId);
**/
		HaiGoodsGallery model = haiGoodsGalleryMapper.get_hai_goods_gallery_info(imgId,store_id);
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
	
	public ReturnObject<HaiGoodsGallery> goodsgallery_update_submit(HttpServletRequest request,HaiGoodsGallery model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		HaiGoodsGalleryExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andImgIdEqualTo(model.getImgId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiGoodsGalleryMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiGoodsGallery bean = haiGoodsGalleryMapper.selectByPrimaryKey(model.getImgId());

bean.setUserId(model.getUserId());
bean.setGoodsId(model.getGoodsId());
bean.setTableName(model.getTableName());
bean.setImgUrl(model.getImgUrl());
bean.setImgDesc(model.getImgDesc());
bean.setThumbUrl(model.getThumbUrl());
bean.setImgOriginal(model.getImgOriginal());
bean.setGalleryType(model.getGalleryType());
bean.setCatId(model.getCatId());
bean.setGalleryName(model.getGalleryName());
bean.setImgName(model.getImgName());
bean.setSortOrder(model.getSortOrder());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiGoodsGalleryMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiGoodsGallery> goodsgallery_info(HttpServletRequest request,Integer imgId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		HaiGoodsGalleryExample.Criteria c = example.createCriteria();
		c.andImgIdEqualTo(imgId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGoodsGallery> list = haiGoodsGalleryMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGoodsGallery model = list.get(0);
		**/

		HaiGoodsGallery model = haiGoodsGalleryMapper.get_hai_goods_gallery_info(imgId,store_id);
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


	public ReturnObject<HaiGoodsGallery> goodsgallery_find(HttpServletRequest request,Integer imgId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		HaiGoodsGalleryExample.Criteria c = example.createCriteria();
		c.andImgIdEqualTo(imgId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGoodsGallery> list = haiGoodsGalleryMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGoodsGallery model = list.get(0);
		**/

		HaiGoodsGallery model = haiGoodsGalleryMapper.get_hai_goods_gallery_info(imgId,store_id);
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

	public ReturnObject<HaiGoodsGallery> goodsgallery_delete(HttpServletRequest request,Integer imgId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		HaiGoodsGalleryExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andImgIdEqualTo(imgId);

		long count = haiGoodsGalleryMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiGoodsGalleryMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiGoodsGallery model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "goodsgallery.xml",model,"hai_goodsgallery",optionMap);
		
		
		return bootStrapList;
	}













	
}











