package org.ehais.shop.processor;

import java.util.List;

import javax.servlet.ServletContext;

import org.ehais.cache.EStoreAppKeySecretCacheManager;
import org.ehais.epublic.mapper.EHaiStoreAppkeySecretMapper;
import org.ehais.epublic.model.EHaiStoreAppkeySecret;
import org.ehais.epublic.model.EHaiStoreAppkeySecretExample;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

public class InitDataListener implements InitializingBean, ServletContextAware{

	@Autowired
	private EHaiStoreAppkeySecretMapper eHaiStoreAppkeySecretMapper;
	
	
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		System.out.println("........InitDataListener........setServletContext");
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("........InitDataListener........afterPropertiesSet");
		
		EStoreAppKeySecretCacheManager cache = EStoreAppKeySecretCacheManager.getInstance();
		
		EHaiStoreAppkeySecretExample example =  new EHaiStoreAppkeySecretExample();
		
		List<EHaiStoreAppkeySecret>  list = eHaiStoreAppkeySecretMapper.selectByExample(example);
		for (EHaiStoreAppkeySecret eHaiStoreAppkeySecret : list) {
//			System.out.println(eHaiStoreAppkeySecret.getAppkey() +" == "+ eHaiStoreAppkeySecret.getSecret());
			cache.putStoreKey(eHaiStoreAppkeySecret.getAppkey(), eHaiStoreAppkeySecret.getSecret());
		}
		
		
	}

}
