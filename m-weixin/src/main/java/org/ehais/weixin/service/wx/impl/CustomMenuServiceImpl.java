package org.ehais.weixin.service.wx.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.ehais.model.BootStrapModel;
import org.ehais.model.TreeModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.TreeUtil;
import org.ehais.weixin.mapper.WpCustomMenuMapper;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.WeiXinMenu;
import org.ehais.weixin.model.WpCustomMenu;
import org.ehais.weixin.model.WpCustomMenuExample;
import org.ehais.weixin.model.WpCustomMenuExample.Criteria;
import org.ehais.weixin.model.WpPublic;
import org.ehais.weixin.service.wx.CustomMenuService;
import org.ehais.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service("customMenuService")
public class CustomMenuServiceImpl extends CommonServiceImpl implements CustomMenuService {

	private static Logger log = LoggerFactory.getLogger(CustomMenuServiceImpl.class);

	
	@Autowired
	private WpCustomMenuMapper wpCustomMenuMapper ;
	public void CreateMenu(WpPublic wpPublic) {
		// TODO Auto-generated method stub
		try{

			WpCustomMenuExample example = new WpCustomMenuExample();
			Criteria criteria = example.createCriteria();
			criteria.andTokenEqualTo(wpPublic.getToken());
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
			log.info(obj.toString());
			
			AccessToken token = WeiXinUtil.getAccessToken(wpPublic.getId(),wpPublic.getAppid(),wpPublic.getSecret());
			
			String request = WeiXinUtil.menu_create(wpPublic.getId(), token.getToken(), obj.toString());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	public ReturnObject<WpCustomMenu> custom_menu_list(String token) throws Exception{
		
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<TreeModel> custom_menu_list_json(String token,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<TreeModel> rm = new ReturnObject<TreeModel>();
		Integer start = (page - 1 ) * len;
		
		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		c.andTokenEqualTo(token);
		example.setStart(start);
		example.setLen(len);
		example.setOrderByClause("sort asc");
		List<WpCustomMenu> list = wpCustomMenuMapper.wp_custom_menu_list_by_example(example);
		Integer total = wpCustomMenuMapper.countByExample(example);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custom_menu_list", list);
		rm.setMap(map);
		
		
		List<TreeModel> treeList = new ArrayList<TreeModel>();
		for (WpCustomMenu wpCustomMenu : list) {
			treeList.add(new TreeModel(wpCustomMenu.getId(), wpCustomMenu.getTitle(), wpCustomMenu.getPid(), 0,null, null));
		}
		
		//进行树状数据整理
		TreeUtil treeUtil = new TreeUtil();
		treeUtil.setTreeList(treeList);
		treeUtil.getTree(0);
		treeList = treeUtil.getTreeNewList();
		
//		for (TreeModel treeModel : treeList) {
//			System.out.println("xid:"+treeModel.getId() + "  title:" + treeModel.getTitle() + "  pid:" + treeModel.getParent_id() + "  level:" + treeModel.getLevel());
//		}

		rm.setCode(1);
		rm.setRows(treeList);
		rm.setTotal(total);

		return rm;
	}

	public ReturnObject<WpCustomMenu> custom_menu_insert(String token)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();	
		WpCustomMenu model = new WpCustomMenu();
		model.setToken(token);
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<WpCustomMenu> custom_menu_insert_submit(WpCustomMenu model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		int code = wpCustomMenuMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<WpCustomMenu> custom_menu_update(String token,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		
		WpCustomMenu model = wpCustomMenuMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<WpCustomMenu> custom_menu_update_submit(WpCustomMenu model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		c.andTokenEqualTo(model.getToken());
		c.andIdEqualTo(model.getId());
		int code = wpCustomMenuMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<WpCustomMenu> custom_menu_find(String token,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		
		
		WpCustomMenu model = wpCustomMenuMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<WpCustomMenu> custom_menu_delete(String token,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpCustomMenu> rm = new ReturnObject<WpCustomMenu>();
		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		c.andTokenEqualTo(token);
		c.andIdEqualTo(key);
		int code = wpCustomMenuMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<WpCustomMenu> customMenuParent(String token){
		WpCustomMenuExample example = new WpCustomMenuExample();
		WpCustomMenuExample.Criteria c = example.createCriteria();
		c.andTokenEqualTo(token);
		c.andPidEqualTo(0);
		return wpCustomMenuMapper.selectByExample(example);
	}
	
	private List<BootStrapModel> formatBootStrapList(WpCustomMenu model){
		
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		Map<String,String> cMenuMap = new TreeMap<String,String>();
		List<WpCustomMenu> cmenuList = this.customMenuParent(model.getToken());
		
		for (WpCustomMenu wpCustomMenu : cmenuList) {
			cMenuMap.put(String.valueOf(wpCustomMenu.getId()), wpCustomMenu.getTitle());
		}
		
		
		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "title", "标题", model.getTitle(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select", "pid", "所属上层", model.getPid(), "请输入", "", "", cMenuMap, 0));
		
		Map<String,String> mapType = new TreeMap<String,String>();
		mapType.put("click", "点击推事件");
		mapType.put("view", "跳转URL");
		mapType.put("scancode_push", "扫码推事件");
		mapType.put("scancode_waitmsg", "扫码带提示");
		mapType.put("pic_sysphoto", "弹出系统拍照发图");
		mapType.put("pic_photo_or_album", "弹出拍照或者相册发图");
		mapType.put("pic_weixin", "弹出微信相册发图器");
		mapType.put("location_select", "弹出地理位置选择器");
		mapType.put("none", " 无事件的一级菜单");
		bootStrapList.add(new BootStrapModel("select", "type", "类型", model.getType(), "请输入", "", "", mapType, 0));

		bootStrapList.add(new BootStrapModel("text", "keyword", "关键字", model.getKeyword(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "url", "链接", model.getUrl(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "sort", "排序", model.getSort(), "请输入", "", "", null, 0));

		return bootStrapList;
	}
	
	
}
