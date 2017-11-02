package org.ehais.shop.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import org.ehais.util.ChineseCharToEn;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.WeiXinMenu;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

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
		mexp.createCriteria().andPidEqualTo(0).andStoreIdEqualTo(store_id);
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
			.andPidEqualTo(0).andStoreIdEqualTo(store_id);
			count = wpCustomMenuMapper.countByExample(example);
			if(count >= 3){
				rm.setMsg("一级菜单不能超过3个");
				return rm;
			}
		}else{
			example.clear();
			example.createCriteria()
			.andPublicIdEqualTo(wp.getId())
			.andPidEqualTo(model.getPid()).andStoreIdEqualTo(store_id);
			count = wpCustomMenuMapper.countByExample(example);
			if(count >= 5){
				rm.setMsg("二级菜单不能超过5个");
				return rm;
			}
		}

		
		if(StringUtils.isBlank(model.getKeyword())){
			model.setKeyword(ChineseCharToEn.getAllFirstLetter(model.getTitle()));
		}
		
		model.setStoreId(store_id);
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
		c.andStoreIdEqualTo(store_id);
		
		List<WpCustomMenu> list = wpCustomMenuMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		WpCustomMenu model = list.get(0);
		
		
		Map<String,Object> map = new HashMap<String,Object>();	
		
		WpCustomMenuExample mexp = new WpCustomMenuExample();
		mexp.createCriteria().andPidEqualTo(0).andStoreIdEqualTo(store_id);
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
		c.andStoreIdEqualTo(store_id);

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
			.andIdNotEqualTo(model.getId()).andStoreIdEqualTo(store_id)
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
			.andIdNotEqualTo(model.getId()).andStoreIdEqualTo(store_id)
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
		if(StringUtils.isBlank(model.getKeyword())){
			bean.setKeyword(ChineseCharToEn.getAllFirstLetter(model.getTitle()));
		}else{
			bean.setKeyword(model.getKeyword());
		}
		
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

	@Override
	public ReturnObject<WpCustomMenu> CreateMenu(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		try{
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(store_id);
			
			WpCustomMenuExample example = new WpCustomMenuExample();
			WpCustomMenuExample.Criteria criteria = example.createCriteria();
			criteria.andPublicIdEqualTo(wpPublic.getId());
			example.setOrderByClause("sort asc");
			List<WpCustomMenu> list = wpCustomMenuMapper.selectByExample(example);
			List<Object> listMenu = new ArrayList<Object>();
			for (WpCustomMenu wpCustomMenu : list) {
				if(wpCustomMenu.getPid().intValue() == 0){				
					if(wpCustomMenu.getType().equals("none")){

						Map<String, Object> subMenu = new HashMap<String, Object>();
						List<Object> subListMenu = new ArrayList<Object>();
						subMenu.put("name", wpCustomMenu.getTitle());
						for (WpCustomMenu wp : list) {
							if(wp.getPid().intValue() == wpCustomMenu.getId().intValue()){
								subListMenu.add(new WeiXinMenu(wp.getType(), wp.getTitle(), wp.getKeyword(), wp.getUrl(),null));
							}
						}
						subMenu.put("sub_button", subListMenu);					
						listMenu.add(subMenu);
					}else{
						listMenu.add(new WeiXinMenu(wpCustomMenu.getType(), wpCustomMenu.getTitle(), wpCustomMenu.getKeyword(), wpCustomMenu.getUrl(),null));
					}
				}
			}
			
			Map<String, Object> buttonMenu = new HashMap<String, Object>();
			buttonMenu.put("button", listMenu);
			JSONObject obj = JSONObject.fromObject(buttonMenu);
			System.out.println(obj.toString());
			
			AccessToken token = WeiXinUtil.getAccessToken(wpPublic.getId(),wpPublic.getAppid(),wpPublic.getSecret());
			
			String result = WeiXinUtil.menu_create(wpPublic.getId(), token.getAccess_token(), obj.toString());
			System.out.println(result);
			
			rm.setCode(1);
			rm.setMsg("同步成功");
		}catch(Exception e){
			e.printStackTrace();
		}
		return rm;
	}
	
	
	
	

	
	
	
	
}

