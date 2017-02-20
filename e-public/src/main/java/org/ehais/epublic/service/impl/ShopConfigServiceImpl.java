package org.ehais.epublic.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.epublic.mapper.HaiShopConfigMapper;
import org.ehais.epublic.model.HaiShopConfig;
import org.ehais.epublic.model.HaiShopConfigExample;
import org.ehais.epublic.service.ShopConfigService;
import org.ehais.model.BootStrapModel;
import org.ehais.model.storeconfig.StoreConfigParent;
import org.ehais.model.storeconfig.StoreConfigSub;
import org.ehais.model.storeconfig.StoreConfigXml;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.StoreConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("shopconfigService")
public class ShopConfigServiceImpl  extends CommonServiceImpl implements ShopConfigService{
	
	@Autowired
	private HaiShopConfigMapper haiShopConfigMapper;
	
	
	public ReturnObject<HaiShopConfig> shopconfig_update_submit(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub  ,HaiShopConfig model
		ReturnObject<HaiShopConfig> rm = new ReturnObject<HaiShopConfig>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_ADMIN_ID);
		HaiShopConfigExample example = new HaiShopConfigExample();
		HaiShopConfigExample.Criteria c = null;
		int count = 0;
		
		haiShopConfigMapper.update_value_clear(store_id);//清空所有值
		
		for(Enumeration e = request.getParameterNames() ; e.hasMoreElements();){
			String thisName=e.nextElement().toString();
			String thisValue=request.getParameter(thisName);
			if(thisName.indexOf("[]") >= 0){
				String[] thisValues = request.getParameterValues(thisName);
//				System.out.println(thisName+"是数组 :"+thisValues.length);
				thisValue = "";
				for(int iv = 0 ; iv < thisValues.length ; iv++){
					thisValue += thisValues[iv] + ",";
				}
				if(thisValue.length() > 0)thisValue = thisValue.substring(0, thisValue.length()-1);
			}
			
//			System.out.println(thisName+"--------------"+thisValue);
			
			example.clear();
			c = example.createCriteria();
			c.andStoreIdEqualTo(store_id);
			c.andCodeEqualTo(thisName);
			count = haiShopConfigMapper.countByExample(example);
			HaiShopConfig model = new HaiShopConfig();
			model.setCode(thisName);
			model.setValue(thisValue);
			model.setStoreId(store_id);
			model.setIsvoid("1");
			if(count == 0){
				//插入操作
				haiShopConfigMapper.insertSelective(model);
			}else{
				//更新操作
				haiShopConfigMapper.updateByExampleSelective(model, example);
			}
		 
		}
		
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiShopConfig> shopconfig_find(HttpServletRequest request,String store_config_path)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiShopConfig> rm = new ReturnObject<HaiShopConfig>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_ADMIN_ID);
		
		HaiShopConfigExample example = new HaiShopConfigExample();
		HaiShopConfigExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andIsvoidEqualTo("1");
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiShopConfig> list = haiShopConfigMapper.selectByExampleWithBLOBs(example);
		List<StoreConfigParent> config = this.formatBootStrapList(request ,store_config_path , list);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("config", config);
		rm.setMap(map);
		rm.setCode(1);
		return rm;
	}

	
	private List<StoreConfigParent> formatBootStrapList(HttpServletRequest request,String store_config_path , List<HaiShopConfig> list) throws IOException{
		String role = (String)request.getSession().getAttribute(Constants.SESSION_ROLE_TYPE);
		
		Map<String, String> map = new HashMap<String, String>();
		for (HaiShopConfig haiShopConfig : list) {
			map.put(haiShopConfig.getCode().replace("[]", ""), haiShopConfig.getValue());
//			System.out.println("加载的..."+haiShopConfig.getCode().replace("[]", "") +" == "+ haiShopConfig.getValue());
		}

		StoreConfigXml xml = StoreConfigUtil.getStoreConfigXml(store_config_path);
		
		List<StoreConfigParent> config = xml.getConfig();
		
		for (StoreConfigParent parent : config) {
			if(role != null && parent.getRole() != null && !parent.getRole().equals("") && parent.getRole().indexOf(role) < 0)continue ;
			List<StoreConfigSub> item = parent.getItem();
			for (StoreConfigSub storeConfigSub : item) {
				if(role != null && storeConfigSub.getRole() != null && !storeConfigSub.getRole().equals("") && storeConfigSub.getRole().indexOf(role) < 0)continue ;
				storeConfigSub.setBootStrap(new BootStrapModel(storeConfigSub.getType(), storeConfigSub.getCode(), storeConfigSub.getText(), map.get(storeConfigSub.getCode()), "请输入"+storeConfigSub.getText(), "", "", storeConfigSub.getRangeMap(),0, Integer.valueOf(parent.getSort())));//最后一个，应该是用上级的序号
			}
		}
		
		return config;
	}
	
}











