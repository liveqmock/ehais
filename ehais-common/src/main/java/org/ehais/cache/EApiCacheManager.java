package org.ehais.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.cache.BaseOSCache;
import org.ehais.controller.CommonController;
import org.ehais.model.eApi.ApiParameter;
import org.ehais.model.eApi.eParameter;
import org.ehais.model.eMenu.eMenuXml;
import org.ehais.model.eMenu.eParent;
import org.ehais.util.FSO;

import com.thoughtworks.xstream.XStream;

public class EApiCacheManager extends CommonController{

	private BaseOSCache bOSCache;
	private static EApiCacheManager instance;
	private static Map<String,String[]> eParameter;
	
	public EApiCacheManager (){
		bOSCache = new BaseOSCache("api_parameter", 1880000);
	}
	
	public static EApiCacheManager getInstance(HttpServletRequest request){
		if( instance == null ){
			instance = new EApiCacheManager();
			CommonController c = new CommonController();
			String eApiParameterContent = c.config_file(request,"ApiParameter.xml");
			try {
				String menu_content = FSO.BufferedReader(eApiParameterContent);
				XStream xStream = new XStream();
				xStream.autodetectAnnotations(true); 
				xStream.alias("xml",eParameter.class);
				xStream.alias("api",ApiParameter.class);
				eParameter p = (eParameter)xStream.fromXML(menu_content);
				List<ApiParameter> apiP = p.getApiParameter();
				eParameter = new HashMap<String, String[]>();
				for (ApiParameter apiParameter : apiP) {
					System.out.println(apiParameter.getName() + " "+apiParameter.getMustParameter().length);
					eParameter.put(apiParameter.getName(), apiParameter.getMustParameter());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return instance;
	}
	
	public static String[] getApiParamterMap(String name){
		if(eParameter != null){
			if(eParameter.get(name) != null){
				return eParameter.get(name);			
			}
		}
		return null;			
	}
	
}
