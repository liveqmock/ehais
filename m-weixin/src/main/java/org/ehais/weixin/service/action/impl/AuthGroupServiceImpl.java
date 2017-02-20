package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.model.BootStrapModel;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EHttpClientUtil;
import org.ehais.weixin.EConstants;
import org.ehais.weixin.mapper.WpAuthGroupMapper;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.WpAuthGroup;
import org.ehais.weixin.model.WpAuthGroupExample;
import org.ehais.weixin.model.WpAuthGroupWithBLOBs;
import org.ehais.weixin.model.WpPublicWithBLOBs;
import org.ehais.weixin.service.action.AuthGroupService;
import org.ehais.weixin.service.wx.impl.WeiXinCommonServiceImpl;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("authgroupService")
public class AuthGroupServiceImpl  extends WeiXinCommonServiceImpl implements AuthGroupService{
	
	@Autowired
	private WpAuthGroupMapper wpAuthGroupMapper;
	
	public ReturnObject<WpAuthGroup> authgroup_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<WpAuthGroup> rm = new ReturnObject<WpAuthGroup>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<WpAuthGroup> authgroup_list_json(HttpServletRequest request,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpAuthGroup> rm = new ReturnObject<WpAuthGroup>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		Integer start = (page - 1 ) * len;
		
		WpAuthGroupExample example = new WpAuthGroupExample();
		WpAuthGroupExample.Criteria c = example.createCriteria();
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andTokenEqualTo(this.getWpPublic(wxid).getToken());
		example.setStart(start);
		example.setLen(len);
		List<WpAuthGroup> list = wpAuthGroupMapper.wp_auth_group_list_by_example(example);
		Integer total = wpAuthGroupMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpAuthGroupWithBLOBs> rm = new ReturnObject<WpAuthGroupWithBLOBs>();	
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		WpAuthGroupWithBLOBs model = new WpAuthGroupWithBLOBs();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_insert_submit(HttpServletRequest request,WpAuthGroupWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpAuthGroupWithBLOBs> rm = new ReturnObject<WpAuthGroupWithBLOBs>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		model.setToken(this.getWpPublic(wxid).getToken());
		model.setStatus(Integer.valueOf(1).byteValue());
		model.setType(Integer.valueOf(1).byteValue());
		model.setIsDefault(false);
		int code = wpAuthGroupMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_update(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpAuthGroupWithBLOBs> rm = new ReturnObject<WpAuthGroupWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		WpAuthGroupWithBLOBs model = wpAuthGroupMapper.selectByPrimaryKey(id);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_update_submit(HttpServletRequest request,WpAuthGroupWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpAuthGroupWithBLOBs> rm = new ReturnObject<WpAuthGroupWithBLOBs>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		WpAuthGroupExample example = new WpAuthGroupExample();
		WpAuthGroupExample.Criteria c = example.createCriteria();
		c.andTokenEqualTo(this.getWpPublic(wxid).getToken());
		c.andIdEqualTo(model.getId());
		int code = wpAuthGroupMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_find(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpAuthGroupWithBLOBs> rm = new ReturnObject<WpAuthGroupWithBLOBs>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		
		WpAuthGroupWithBLOBs model = wpAuthGroupMapper.selectByPrimaryKey(id);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<WpAuthGroup> authgroup_delete(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpAuthGroup> rm = new ReturnObject<WpAuthGroup>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		WpAuthGroupExample example = new WpAuthGroupExample();
		WpAuthGroupExample.Criteria c = example.createCriteria();
		c.andTokenEqualTo(this.getWpPublic(wxid).getToken());
		c.andIdEqualTo(id);
		int code = wpAuthGroupMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(WpAuthGroupWithBLOBs model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		

		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "title", "分组标题", model.getTitle(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "icon", "", model.getIcon(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("textareaV1", "description", "分组描述", model.getDescription(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "status", "", model.getStatus(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "type", "", model.getType(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("textarea", "rules", "", model.getRules(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "managerId", "", model.getManagerId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "token", "", model.getToken(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isDefault", "", model.getIsDefault(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "qrCode", "", model.getQrCode(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "wechatGroup_id", "", model.getWechatGroup_id(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "wechatGroup_name", "", model.getWechatGroup_name(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "wechatGroup_count", "", model.getWechatGroup_count(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isDel", "", model.getIsDel(), "请输入", "", "", null, 0));

		return bootStrapList;
	}

	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_wx_create(
			HttpServletRequest request, Integer id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpAuthGroupWithBLOBs> rm = new ReturnObject<WpAuthGroupWithBLOBs>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		
		WpAuthGroupWithBLOBs model = wpAuthGroupMapper.selectByPrimaryKey(id);
		
//		{"group":{"name":"test"}}
		Map<String, Object> mapName = new HashMap<String, Object>();
		Map<String, Map<String, Object>> mapGroup = new HashMap<String, Map<String, Object>>();
		mapName.put("name", model.getTitle());
		mapGroup.put("group", mapName);
		JSONObject json = JSONObject.fromObject(mapGroup);

		WpPublicWithBLOBs wp = this.getWpPublic(wxid);
		AccessToken token = WeiXinUtil.getAccessToken(wxid, wp.getAppid(), wp.getSecret());
		
		if(model.getWechatGroupId() == null || model.getWechatGroupId().intValue() == 0 || model.getWechatGroupId().intValue() == -1){
			//创建微信分组
			String groups_create = EConstants.groups_create.replace("ACCESS_TOKEN", token.getToken());
			String resData = EHttpClientUtil.httpPostEntity(groups_create, json.toString());
			System.out.println("请求返回："+resData);
			json = JSONObject.fromObject(resData);
			int wechatGroupId = json.getJSONObject("group").getInt("id");
			model.setWechatGroupId(wechatGroupId);
			model.setWechatGroupName(model.getTitle());
			wpAuthGroupMapper.updateByPrimaryKeyWithBLOBs(model);
		}else{
			//修改微信分组
			mapName.put("id", model.getWechatGroupId());
			mapGroup.put("group", mapName);
			json = JSONObject.fromObject(mapGroup);
			System.out.println(json.toString());
			String groups_update = EConstants.groups_update.replace("ACCESS_TOKEN", token.getToken());
			String resData = EHttpClientUtil.httpPostEntity(groups_update, json.toString());
			System.out.println("请求返回："+resData);
			json = JSONObject.fromObject(resData);
		}
//		oAZoNxCHm5acG7Vb0Vz6SWXFbt0k
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
}











