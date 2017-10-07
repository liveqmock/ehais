package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.epublic.cache.EAdminTokenCacheManager;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.service.EStoreService;
import org.ehais.shop.controller.api.include.AdminUserIController;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ECommon;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class AdminUserApiContorller extends AdminUserIController{

	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	@Autowired
	protected EStoreService eStoreService;
	
	@ResponseBody
	@RequestMapping("/dining_manage_login")
	public String dining_manage_login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password){
		ReturnObject<EHaiStore> rm = new ReturnObject<EHaiStore>();
		rm.setCode(0);
		try{
			password = EncryptUtils.md5(password);
			EHaiAdminUser adminuser = eHaiAdminUserMapper.login_admin(username, password);
			if(adminuser == null){
				rm.setMsg("用户名或密码不正确");
				return this.writeJson(rm);
			}
			if(adminuser.getClassify() == null || !adminuser.getClassify().equals(EAdminClassifyEnum.dining)){
				rm.setMsg("商家不正确");
				return this.writeJson(rm);
			}
			String token = ECommon.nonceStr(16);
			EAdminTokenCacheManager.getInstance().putAdminToken(adminuser.getStoreId(), token);
			EHaiStore store = eStoreService.getEStore(adminuser.getStoreId());
			rm.setModel(store);
			rm.setCode(1);
			rm.setToken(token);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("admin_login api", e);
		}
		return this.writeJson(rm);
	}
	
	
}
