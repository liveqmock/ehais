package org.ehais.epublic.processor;


import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.ehais.cache.ERolePermissionCacheManager;
import org.ehais.cache.EStoreAppKeySecretCacheManager;
import org.ehais.epublic.mapper.EHaiStoreAppkeySecretMapper;
import org.ehais.epublic.mapper.ThinkRoleMapper;
import org.ehais.epublic.model.EHaiStoreAppkeySecret;
import org.ehais.epublic.model.EHaiStoreAppkeySecretExample;
import org.ehais.epublic.model.ThinkRole;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;


@Transactional(value = "qualifier", rollbackFor = Exception.class)  
public class InitDataListener implements InitializingBean, ServletContextAware{

	@Autowired
	private EHaiStoreAppkeySecretMapper eHaiStoreAppkeySecretMapper;
	@Autowired
	private ThinkRoleMapper thinkRoleMapper;
	
	
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		System.out.println("........InitDataListener........setServletContext");
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("........InitDataListener........afterPropertiesSet");
		
		EStoreAppKeySecretCacheManager cache = EStoreAppKeySecretCacheManager.getInstance();
		
		EHaiStoreAppkeySecretExample example =  new EHaiStoreAppkeySecretExample();
		example.createCriteria().andIsvalidEqualTo("1");
		List<EHaiStoreAppkeySecret>  list = eHaiStoreAppkeySecretMapper.selectByExample(example);
		for (EHaiStoreAppkeySecret eHaiStoreAppkeySecret : list) {
//			System.out.println(eHaiStoreAppkeySecret.getAppkey() +" == "+ eHaiStoreAppkeySecret.getSecret());
			cache.putStoreKey(eHaiStoreAppkeySecret.getAppkey(), eHaiStoreAppkeySecret.getSecret());
		}
		
		//初始化系统权限
		ERolePermissionCacheManager.getInstance();
		
		List<ThinkRole> roleList = thinkRoleMapper.selectByExampleWithBLOBs(null);
		for (ThinkRole thinkRole : roleList) {
			List<String> permission = null;
			if(StringUtils.isNoneBlank(thinkRole.getPermission())){
				permission= Arrays.asList(StringUtils.split(thinkRole.getPermission(),","));
			}
			ERolePermissionCacheManager.putRolePermission(thinkRole.getRoleId(), permission);
		}
		
	}

}

