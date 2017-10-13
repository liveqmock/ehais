package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.weixin.WpCustomMenuMapper;
import org.ehais.shop.model.weixin.WpCustomMenu;
import org.ehais.shop.model.weixin.WpCustomMenuExample;
import org.ehais.shop.service.WpCustomMenuService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("wpCustomMenuService")
public class WpCustomMenuServiceImpl  extends CommonServiceImpl implements WpCustomMenuService{
	
	@Autowired
	private WpCustomMenuMapper wpCustomMenuMapper;
	@Autowired
	private EStoreService eStoreService;
	@Autowired
	private EWPPublicService eWPPublicService;

	public ReturnObject<WpCustomMenu> custommenu_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<WpCustomMenu> custommenu_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String title) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(condition.getStore_id());
		
		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		c.andPublicIdEqualTo(wp.getId());
		example.setOrderByClause("sort asc");
		List<WpCustomMenu> list = wpCustomMenuMapper.selectByExample(example);
		long total = wpCustomMenuMapper.countByExample(example);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<WpCustomMenu> custommenu_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpCustomMenu model = new WpCustomMenu();

		Map<String,Object> map = new HashMap<String,Object>();	
		
		WpCustomMenuExample mexp = new WpCustomMenuExample();
		mexp.createCriteria().andPidEqualTo(0);
		mexp.setOrderByClause("sort asc");
		List<WpCustomMenu> list = wpCustomMenuMapper.selectByExample(mexp);
		map.put("plist", list);
		
		rm.setMap(map);
		
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<WpCustomMenu> custommenu_insert_submit(HttpServletRequest request,WpCustomMenu model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		
		model.setPublicId(wp.getId());
		
		Date date = new Date();

		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		c.andTitleEqualTo(model.getTitle());
		c.andPublicIdEqualTo(wp.getId());
		long count = wpCustomMenuMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}
		
		if(model.getPid() == null || model.getPid() == 0){
			//判断是否起过3个
			example.clear();
			example.createCriteria()
			.andPublicIdEqualTo(wp.getId())
			.andPidEqualTo(0);
			count = wpCustomMenuMapper.countByExample(example);
			if(count >= 3){
				rm.setMsg("一级菜单不能超过3个");
				return rm;
			}
		}else{
			example.clear();
			example.createCriteria()
			.andPublicIdEqualTo(wp.getId())
			.andPidEqualTo(model.getPid());
			count = wpCustomMenuMapper.countByExample(example);
			if(count >= 5){
				rm.setMsg("二级菜单不能超过5个");
				return rm;
			}
		}


		int code = wpCustomMenuMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<WpCustomMenu> custommenu_update(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		
		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
		c.andPublicIdEqualTo(wp.getId());
		List<WpCustomMenu> list = wpCustomMenuMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		WpCustomMenu model = list.get(0);
		
		
		Map<String,Object> map = new HashMap<String,Object>();	
		
		WpCustomMenuExample mexp = new WpCustomMenuExample();
		mexp.createCriteria().andPidEqualTo(0);
		mexp.setOrderByClause("sort asc");
		List<WpCustomMenu> plist = wpCustomMenuMapper.selectByExample(mexp);
		map.put("plist", plist);
		
		rm.setMap(map);
		
		

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<WpCustomMenu> custommenu_update_submit(HttpServletRequest request,WpCustomMenu model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		
		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		
		c.andIdEqualTo(model.getId());
		c.andPublicIdEqualTo(wp.getId());

		long count = wpCustomMenuMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		
		
		if(model.getPid() == null || model.getPid() == 0){
			//判断是否起过3个
			example.clear();
			example.createCriteria()
			.andPublicIdEqualTo(wp.getId())
			.andIdNotEqualTo(model.getId())
			.andPidEqualTo(0);
			count = wpCustomMenuMapper.countByExample(example);
			if(count >= 3){
				rm.setMsg("一级菜单不能超过3个");
				return rm;
			}
		}else{
			example.clear();
			example.createCriteria()
			.andPublicIdEqualTo(wp.getId())
			.andIdNotEqualTo(model.getId())
			.andPidEqualTo(model.getPid());
			count = wpCustomMenuMapper.countByExample(example);
			if(count >= 5){
				rm.setMsg("二级菜单不能超过5个");
				return rm;
			}
		}
		
		

		WpCustomMenu bean = wpCustomMenuMapper.selectByPrimaryKey(model.getId());

		bean.setPid(model.getPid());
		bean.setTitle(model.getTitle());
		bean.setType(model.getType());
		bean.setSort(model.getSort());
		bean.setRuleId(model.getRuleId());
		bean.setMaterial(model.getMaterial());
		bean.setUrl(model.getUrl());
		bean.setKeyword(model.getKeyword());
		bean.setAppid(model.getAppid());
		bean.setPagepath(model.getPagepath());
		bean.setAppurl(model.getAppurl());


		wpCustomMenuMapper.updateByPrimaryKeySelective(bean);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<WpCustomMenu> custommenu_info(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
		c.andPublicIdEqualTo(wp.getId());
		List<WpCustomMenu> list = wpCustomMenuMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		WpCustomMenu model = list.get(0);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<WpCustomMenu> custommenu_find(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
		c.andPublicIdEqualTo(wp.getId());
		List<WpCustomMenu> list = wpCustomMenuMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		WpCustomMenu model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<WpCustomMenu> custommenu_delete(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		c.andPublicIdEqualTo(wp.getId());
		c.andIdEqualTo(id);

		long count = wpCustomMenuMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = wpCustomMenuMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	
}

