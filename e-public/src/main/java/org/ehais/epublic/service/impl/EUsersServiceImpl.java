package org.ehais.epublic.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.service.EUsersService;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("eUsersService")
public class EUsersServiceImpl  extends CommonServiceImpl implements EUsersService{
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	
	public ReturnObject<EHaiUsers> users_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiUsers> users_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		EHaiUsersExample example = new EHaiUsersExample();
		EHaiUsersExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setStart(start);
		example.setLen(len);
		List<EHaiUsers> list = eHaiUsersMapper.hai_users_list_by_example(example);
		Integer total = eHaiUsersMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EHaiUsers> users_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiUsers model = new EHaiUsers();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<EHaiUsers> users_insert_submit(HttpServletRequest request,EHaiUsers model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();

		if(model.getUserName() == null || model.getUserName().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		EHaiUsersExample example = new EHaiUsersExample();
		EHaiUsersExample.Criteria c = example.createCriteria();
		c.andUserNameEqualTo(model.getUserName());
		c.andStoreIdEqualTo(store_id);
		int count = eHaiUsersMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = eHaiUsersMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiUsers> users_update(HttpServletRequest request,Long userId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiUsers model = eHaiUsersMapper.selectByPrimaryKey(userId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiUsers> users_update_submit(HttpServletRequest request,EHaiUsers model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiUsersExample example = new EHaiUsersExample();
		EHaiUsersExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andUserIdEqualTo(model.getUserId());
		c.andStoreIdEqualTo(store_id);

		int count = eHaiUsersMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = eHaiUsersMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiUsers> users_find(HttpServletRequest request,Long userId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
//		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		EHaiUsers model = eHaiUsersMapper.selectByPrimaryKey(userId);
		
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiUsers> users_delete(HttpServletRequest request,Long userId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiUsersExample example = new EHaiUsersExample();
		EHaiUsersExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andUserIdEqualTo(userId);
		int code = eHaiUsersMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(EHaiUsers model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}

	@Override
	public ReturnObject<EHaiUsers> login(HttpServletRequest request,
			String username, String password,String source) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		if(username == null || username.trim().length() == 0){
			rm.setMsg("用户名不能为空");
			return rm;
		}
		if(password == null || password.trim().length() == 0){
			rm.setMsg("密码不能为空");
			return rm;
		}
		
		password = EncryptUtils.md5(password);
		
		EHaiUsers users = eHaiUsersMapper.login(username, password);
		if(users == null){
			rm.setMsg("用户不存在或密码错误");
			return rm;
		}
		
		rm.setCode(1);
		rm.setModel(users);
		
		if(source.equals("ws") || source.equals("web")){
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, users.getUserId());
			request.getSession().setAttribute(EConstants.SESSION_USER_NAME, users.getUserName());
			
			String back_shop_url = (String) request.getSession().getAttribute("BACK-SHOP-URL");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("back_shop_url", back_shop_url);
			rm.setMap(map);
		}
		
		return rm;
	}

	@Override
	public ReturnObject<EHaiUsers> register(HttpServletRequest request,
			String username, String password, String comfirmPassword,String source)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		if(username == null || username.trim().length() == 0){
			rm.setMsg("用户名不能为空");
			return rm;
		}
		if(password == null || password.trim().length() == 0){
			rm.setMsg("密码不能为空");
			return rm;
		}
		if(comfirmPassword == null || comfirmPassword.trim().length() == 0){
			rm.setMsg("确认密码不能为空");
			return rm;
		}
		
		if(!password.equals(comfirmPassword)){
			rm.setMsg("密码不一致");
			return rm;
		}
		
		EHaiUsersExample example = new EHaiUsersExample();
		EHaiUsersExample.Criteria c = example.createCriteria();
		c.andUserNameEqualTo(username);
		int count = eHaiUsersMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("用户名已存在");
			return rm;
		}
		
		password = EncryptUtils.md5(password);
		EHaiUsers users = new EHaiUsers();
		users.setUserName(username);
		users.setPassword(password);
		int code = eHaiUsersMapper.insertSelective(users);
		
		
		rm.setCode(code);
		rm.setMsg(code == 1 ? "注册成功":"注册失败");
		rm.setModel(users);
		
		if(source.equals("ws") || source.equals("web")){
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, users.getUserId());
			request.getSession().setAttribute(EConstants.SESSION_USER_NAME, users.getUserName());
		}
		
		return rm;
	}

	@Override
	public ReturnObject<EHaiUsers> users_info_edit(HttpServletRequest request, EHaiUsers model, Long user_id,
			String token) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		if(user_id == null || user_id == 0){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(user_id == null || user_id == 0){
				rm.setMsg("参数不正确0020");
				return rm;
			}
		}
		
		EHaiUsersExample example = new EHaiUsersExample();
		EHaiUsersExample.Criteria c = example.createCriteria();
		
		c.andUserIdEqualTo(model.getUserId());
		
		int count = eHaiUsersMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("用户不存在");
			return rm;
		}

		int code = eHaiUsersMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	@Override
	public ReturnObject<EHaiUsers> logout(HttpServletRequest request,  String source)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(1);
		request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
		request.getSession().removeAttribute(EConstants.SESSION_USER_NAME);
		
		
		return rm;
	}

	@Override
	public ReturnObject<EHaiUsers> wx_user_save(HttpServletRequest request, 
			Integer wxid,//store_id
			String email,
			String userName, 
			String password,
			String nickname,
			String realname,
			Integer sex,
			Integer subscribe,
			String openid,
			String city,
			String country,
			String province,
			String language,
			String headimgurl,
			Long subscribe_time,
			String unionid,
			String remark,
			Integer groupid) throws Exception {
		// TODO Auto-generated method stub
		//根据openid判断用户是否存在
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		EHaiUsersExample example = new EHaiUsersExample();
		example.createCriteria().andOpenidEqualTo(openid).andStoreIdEqualTo(wxid);
		List<EHaiUsers> list = eHaiUsersMapper.selectByExample(example);
		EHaiUsers users = null;
		if(list != null && list.size()>0){
			users = list.get(0);
		}else{
			users = new EHaiUsers();
		}
		
		users.setStoreId(wxid);
//		users.setEmail(email);
//		users.setUserName(userName);
//		users.setPassword(password);
		users.setNickname(nickname);
		users.setRealname(realname);
		if(sex != null && !sex.equals(""))users.setSex(Short.valueOf(sex.toString()));
		users.setSubscribe(subscribe);
		users.setOpenid(openid);
		users.setFaceImage(headimgurl);
		
		if(list != null && list.size()>0){
			eHaiUsersMapper.updateByExampleSelective(users, example);
		}else{
			eHaiUsersMapper.insertSelective(users);
		}
		rm.setModel(users);
		rm.setCode(1);
		return rm;
	}
	
}











