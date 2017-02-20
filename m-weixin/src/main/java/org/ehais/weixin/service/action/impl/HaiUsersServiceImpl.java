package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.ehais.weixin.service.action.HaiUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("HaiUsersService")
public class HaiUsersServiceImpl  extends CommonServiceImpl implements HaiUsersService{
	
	@Autowired
	private EHaiUsersMapper haiUsersMapper;
	
	public ReturnObject<EHaiUsers> HaiUsers_list(HttpServletRequest request,Integer store_id) throws Exception{
		
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiUsers> HaiUsers_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();

		
		
		return rm;
	}

	public ReturnObject<EHaiUsers> HaiUsers_insert(HttpServletRequest request,Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();	
		EHaiUsers model = new EHaiUsers();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<EHaiUsers> HaiUsers_insert_submit(HttpServletRequest request,EHaiUsers model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		int code = haiUsersMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiUsers> HaiUsers_update(HttpServletRequest request,Integer store_id,Long key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		
		EHaiUsers model = haiUsersMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiUsers> HaiUsers_update_submit(HttpServletRequest request,EHaiUsers model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		EHaiUsersExample example = new EHaiUsersExample();
		EHaiUsersExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(model.getStoreId());
		c.andUserIdEqualTo(model.getUserId());
		int code = haiUsersMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiUsers> HaiUsers_find(HttpServletRequest request,Integer store_id,Long key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		
		
		EHaiUsers model = haiUsersMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiUsers> HaiUsers_delete(HttpServletRequest request,Integer store_id,Long userId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		EHaiUsersExample example = new EHaiUsersExample();
		EHaiUsersExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andUserIdEqualTo(userId);
		int code = haiUsersMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(EHaiUsers model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "userId", "", model.getUserId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "userName", "", model.getUserName(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "password", "", model.getPassword(), "请输入", "", "", null, 0));

		return bootStrapList;
	}
	
	
	private List<BootStrapModel> formatModify(){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("password", "old_password", "旧密码", "" , "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("password", "new_password", "新密码", "" , "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("password", "confirm_password", "确认新密码", "" , "请输入", "", "", null, 0));

		return bootStrapList;
	}


	public ReturnObject<EHaiUsers> modifyPassword(HttpServletRequest request,
			Integer store_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();	
		rm.setBootStrapList(this.formatModify());
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiUsers> modifyPasswordSubmit(
			HttpServletRequest request, String old_password,String new_password,String confirm_password) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		
		if(old_password.trim().length() == 0){
			rm.setMsg("请输入旧密码");
			return rm;
		}
		if(new_password.trim().length() == 0){
			rm.setMsg("请输入旧密码");
			return rm;
		}
		if(confirm_password.trim().length() == 0){
			rm.setMsg("请输入旧密码");
			return rm;
		}
		if(!new_password.trim().equals(confirm_password.trim())){
			rm.setMsg("确认密码不正确");
			return rm;
		}
		
		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		EHaiUsers u = haiUsersMapper.userInfo(user_id);
		if(u == null){
			rm.setMsg("登录异常，请退出重新登录");
			return rm;
		}
		
		old_password = EncryptUtils.md5(old_password.trim());
		if(!u.getPassword().equals(old_password)){
			rm.setMsg("原密码不正确");
			return rm;
		}
		
		haiUsersMapper.modifyPassword(user_id, EncryptUtils.md5(new_password.trim()));
		rm.setCode(1);
		rm.setMsg("密码更改成功");
		return rm;
	}
	
}











