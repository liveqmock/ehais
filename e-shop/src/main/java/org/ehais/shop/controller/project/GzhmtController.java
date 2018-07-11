package org.ehais.shop.controller.project;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.enums.EStoreStateEnum;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.ThinkRoleAdminMapper;
import org.ehais.epublic.mapper.ThinkRoleMapper;
import org.ehais.epublic.mapper.WpPublicMapper;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserExample;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.ThinkRoleAdminExample;
import org.ehais.epublic.model.ThinkRoleAdminKey;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class GzhmtController extends CommonController {

	@Autowired
	private EHaiAdminUserMapper haiAdminUserMapper;
	@Autowired
	private ThinkRoleMapper thinkRoleMapper;
	@Autowired
	private ThinkRoleAdminMapper thinkRoleAdminMapper;
	@Autowired
	private EHaiStoreMapper eHaiStoreMapper;
	@Autowired
	protected WpPublicMapper wpPublicMapper;
	
	
	
	@RequestMapping(value="/authentication",method=RequestMethod.GET)
	public String authentication(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username) {
		
		
		try {
			EHaiAdminUserExample auExp = new EHaiAdminUserExample();
			auExp.createCriteria().andUserNameEqualTo(username);
			
			Long c = haiAdminUserMapper.countByExample(auExp);
			if(c == 0) {
				EHaiAdminUserWithBLOBs model = new EHaiAdminUserWithBLOBs();
				//------------插入微信公众号配置public表
				WpPublicWithBLOBs wp = new WpPublicWithBLOBs();
				wp.setPublicName(username);
				wpPublicMapper.insertSelective(wp);
				
				Integer addTime = Long.valueOf(System.currentTimeMillis() / 1000).intValue();
				EHaiStore store = new EHaiStore();
				store.setStoreName(username);
				store.setTheme(EAdminClassifyEnum.company);
				store.setAddTime(addTime);
				store.setPublicId(wp.getId());
				store.setState(EStoreStateEnum.valid);
				eHaiStoreMapper.insert(store);
				
				model.setUserName(username);
				model.setPassword(EncryptUtils.md5("123456"));
				model.setEmail(username+"@qq.com");
				model.setStoreId(store.getStoreId());
				model.setProjectFolder("gzhmt");

				int code = haiAdminUserMapper.insertSelective(model);
				String roleId = "11";//学院管理员权限
//				this.saveRoleAdmin(request, model.getAdminId(), 11);
				
				ThinkRoleAdminExample rae = new ThinkRoleAdminExample();
				rae.createCriteria().andAdminIdEqualTo(model.getAdminId());
				thinkRoleAdminMapper.deleteByExample(rae);
				if(StringUtils.isNoneBlank(roleId)){
//					List<String> result = Arrays.asList(StringUtils.split(roleId,","));
					String[] roleids = StringUtils.split(roleId,",");
					for (String string : roleids) {
						ThinkRoleAdminKey ra = new ThinkRoleAdminKey();
						ra.setAdminId(model.getAdminId());
						ra.setRoleId(Integer.valueOf(string));
						thinkRoleAdminMapper.insert(ra);
					}
				}
				
			}
			
			
//			重新登录
			List<EHaiAdminUser> listAdmin = haiAdminUserMapper.selectByExample(auExp);
			
			
			EHaiAdminUser adminuser = listAdmin.get(0);
			HttpSession session = request.getSession(true);
			session.setAttribute(EConstants.SESSION_ADMIN_ID, adminuser.getAdminId());
			session.setAttribute(EConstants.SESSION_ADMIN_NAME, adminuser.getUserName());
			session.setAttribute(EConstants.SESSION_ADMIN_CLASSIFY, adminuser.getClassify());
			session.setAttribute(EConstants.SESSION_ADMIN_PROJECT_FOLDER, adminuser.getProjectFolder());
			
			ThinkRoleAdminExample exp = new ThinkRoleAdminExample();
			exp.createCriteria().andAdminIdEqualTo(adminuser.getAdminId());
			List<ThinkRoleAdminKey> role = thinkRoleAdminMapper.selectByExample(exp);
			
			List<Integer> roleids = new ArrayList<Integer>();
			for (ThinkRoleAdminKey thinkRoleAdmin : role) {
				roleids.add(thinkRoleAdmin.getRoleId());
			}
			
			String str = StringUtils.join(roleids.toArray(), ",");  
			request.getSession().setAttribute(EConstants.SESSION_ROLE_ID_ARRAY, str);
			
			
			EHaiStore store = eHaiStoreMapper.selectByPrimaryKey(adminuser.getStoreId());
			
			session.setAttribute(EConstants.SESSION_STORE_ID, store.getStoreId());
			session.setAttribute(EConstants.SESSION_STORE_NAME, store.getStoreName());
			session.setAttribute(EConstants.SESSION_STORE_THEME, store.getTheme());
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/project/gzhmt/authentication";
		
	}
	
}
