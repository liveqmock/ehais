package org.ehais.shop.controller.dining;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EUserTypeEnum;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserExample;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiStoreExample;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class DiningUnionController extends EhaisCommonController{
	private static Logger log = LoggerFactory.getLogger(DiningUnionController.class);
	public static String website = ResourceUtil.getProValue("website");
	public static String defaultimg = ResourceUtil.getProValue("defaultimg");
	public static String weixin_appid = ResourceUtil.getProValue("weixin_appid");
	public static String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
	public static String weixin_token = ResourceUtil.getProValue("weixin_token");
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	@Autowired
	private EHaiStoreMapper eHaiStoreMapper;
	
	
	
	
	//http://127.0.0.1/diningUnion!5674d100-033b4b301-1299581252-2e64baa931f09d6c22
	@RequestMapping("/diningUnion!{pid}")
	public String diningUnion(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "pid") String pid	,
			@RequestParam(value = "code", required = false) String code ) {	
		
		try{
			
			Map<String,Object> map = SignUtil.getPartnerId(pid,weixin_token);
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			modelMap.addAttribute("pid", pid);
			
			if(this.isWeiXin(request) && false){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/diningUnion!"+pid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					String newPid = SignUtil.setPartnerId(Integer.valueOf(map.get("partnerId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), weixin_token);
					String link = request.getScheme() + "://" + request.getServerName() + "/diningUnion!"+newPid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					return "/dining/diningUnion";
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				    return this.redirect_wx_authorize(request,weixin_appid, "/diningUnion!"+pid);
				}else{
					System.out.println(pid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/dining/diningUnion";
		
	}
	
	
	
	@RequestMapping("/diningRegiterUnion!{pid}")
	public String diningRegiterUnion(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "pid") String pid	,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "confirmPassword", required = false) String confirmPassword,
			@RequestParam(value = "store_name", required = false) String store_name,
			@RequestParam(value = "contacts", required = false) String contacts,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "address", required = false) String address
			
			) {	
		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
		rm.setCode(0);
		try{

			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(user_id == null || user_id == 0){rm.setMsg("user sess empty");return this.writeJson(rm);}
			
			EHaiUsers user = eHaiUsersMapper.selectByPrimaryKey(user_id);
			if(user == null){rm.setMsg("user obj empty");return this.writeJson(rm);}
			//效验用户名
			EHaiUsersExample userExp = new EHaiUsersExample();
			userExp.createCriteria().andUserNameEqualTo(username).andUserIdNotEqualTo(user_id);
			long cUser = eHaiUsersMapper.countByExample(userExp);
			if(cUser > 0){rm.setMsg("此用户名已存在");return this.writeJson(rm);}

			//效验用户名
			EHaiAdminUserExample adminExp = new EHaiAdminUserExample();
			adminExp.createCriteria().andUserNameEqualTo(username);
			long aUser = eHaiAdminUserMapper.countByExample(adminExp);
			if(aUser > 0){rm.setMsg("此用户名已存在");return this.writeJson(rm);}
			
			//效验商家名称
			EHaiStoreExample storeExp = new EHaiStoreExample();
			storeExp.createCriteria().andStoreNameEqualTo(store_name);
			long sUser = eHaiStoreMapper.countByExample(storeExp);
			if(sUser > 0){rm.setMsg("此商户名称已存在，如同名请联系管理员微信:haisoftware");return this.writeJson(rm);}
			
			EHaiStore store = new EHaiStore();
			store.setStoreName(store_name);
			store.setContacts(contacts);
			store.setMobile(mobile);
			store.setAddress(address);
			store.setTheme("dining");
			eHaiStoreMapper.insert(store);
			
			user.setStoreId(store.getStoreId());
			user.setUserType(EUserTypeEnum.dining);
			eHaiUsersMapper.updateByPrimaryKey(user);
			
			EHaiAdminUserWithBLOBs admin = new EHaiAdminUserWithBLOBs();
			admin.setUserName(username);
			admin.setPassword(EncryptUtils.md5(password));
			admin.setStoreId(store.getStoreId());
			admin.setEmail("");
			eHaiAdminUserMapper.insert(admin);
			
			rm.setModel(admin);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		rm.setCode(1);
		return this.writeJson(rm);
	}
	
	
}
