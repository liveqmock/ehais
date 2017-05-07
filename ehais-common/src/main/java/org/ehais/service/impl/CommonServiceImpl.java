package org.ehais.service.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.enums.ControlEnum;
import org.ehais.model.BootStrapCheckBoxModel;
import org.ehais.model.BootStrapModel;
import org.ehais.model.TreeModel;
import org.ehais.model.ExtendsField.ExtendsFieldsGroup;
import org.ehais.model.ExtendsField.ExtendsFieldsTabs;
import org.ehais.model.ExtendsField.ExtendsFieldsXml;
import org.ehais.service.CommonService;
import org.ehais.tools.CriteriaObject;
import org.ehais.util.FSO;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.thoughtworks.xstream.XStream;

public class CommonServiceImpl implements CommonService {

	
	public void storeIdMap(HttpServletRequest request,Map<String, Object> map) {
		String role_type = (String)request.getSession().getAttribute(EConstants.SESSION_ROLE_TYPE);
		if(role_type.equals("student") || role_type.equals("teacher")){
			map.put("schoolId", (Integer)request.getSession().getAttribute(EConstants.SESSION_SCHOOL_ID));
		}else if(role_type.equals("school")){
			map.put("schoolId", (Integer)request.getSession().getAttribute(EConstants.SESSION_USER_ID));
		}
	}
	
	public CriteriaObject storeIdCriteriaObject(HttpServletRequest request){
		CriteriaObject co = new CriteriaObject();
		String role_type = (String)request.getSession().getAttribute(EConstants.SESSION_ROLE_TYPE);
		co.setRoleType(role_type);
		if(role_type == null) {
			co.setOperator("");
		}else if(role_type.equals("admin")){
			co.setOperator("");
		}else if(role_type.equals("store")){
			co.setOperator("eq");
			co.setStoreId((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		}else if(role_type.equals("user")){
			co.setOperator("user_eq");
			co.setUserId((Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID));
		}else{
			co.setOperator("eq");
			co.setStoreId((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		}
		
		return co;
	}
	
	public CriteriaObject storeIdCriteriaObject(HttpServletRequest request , Integer store_id){
		CriteriaObject co = new CriteriaObject();
		String role_type = (String)request.getSession().getAttribute(EConstants.SESSION_ROLE_TYPE);
		co.setRoleType(role_type);
		if(role_type == null) {
			co.setOperator("");
		}else if(role_type.equals("admin")){
			co.setOperator("");
		}else if(role_type.equals("store")){
			co.setOperator("eq");
			co.setStoreId((store_id == null) ? (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID) : store_id);
		}else if(role_type.equals("user")){
			co.setOperator("user_eq");
			co.setUserId((Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID));
		}else{
			co.setOperator("eq");
			co.setStoreId((store_id == null)?(Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID):store_id);
		}
		
		return co;
	}

	public <T> Map<String,Object> getClassValue(T t) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		if(t == null)return map;
		PropertyDescriptor[] props = Introspector.getBeanInfo(t.getClass(), Object.class).getPropertyDescriptors();
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				String key = props[i].getName();// 获取bean中的属性
				Method method = props[i].getReadMethod();				
				Object value = method.invoke(t, new Object[] {});
				map.put(key, value);
			}
		}
		return map;
	}

	
	
	public <T> List<BootStrapModel> BootStrapXml(HttpServletRequest request , String bootStrapXml,T t,String tablename,Map<String,Object> optionMap) throws Exception{
		return this.BootStrapXml(request, bootStrapXml, t, tablename, optionMap,null);
	}
	
	

	public <T> List<BootStrapModel> BootStrapXml(HttpServletRequest request , String bootStrapXml,T t,String tablename,Map<String,Object> optionMap,Map<String,Object> galleryMap) throws Exception{
		String role = (String) request.getSession().getAttribute(EConstants.SESSION_ROLE_TYPE);
		if (role == null) role = "user";
		String manager = role;
		if(role.equals("admin")){
			manager = (String) request.getSession().getAttribute(EConstants.SESSION_ADMIN_NAME);
		}else if(role.equals("user")){
			manager = (String) request.getSession().getAttribute(EConstants.SESSION_USER_NAME);
		}
		
		if(manager == null)manager = "";
		
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		Map<String, Object> goodsMap = this.getClassValue(t);
		
		CommonController c = new CommonController();
		String general = c.config_file(request, bootStrapXml);
		String general_content = FSO.BufferedReader(general);
		XStream xStream = new XStream();
		xStream.processAnnotations(ExtendsFieldsXml.class);
		xStream.processAnnotations(ExtendsFieldsGroup.class);
		xStream.processAnnotations(BootStrapModel.class);
		xStream.processAnnotations(BootStrapCheckBoxModel.class);
		
		ExtendsFieldsXml atxml = (ExtendsFieldsXml) xStream.fromXML(general_content);
		List<ExtendsFieldsGroup> efg = atxml.getGroup();
		for (ExtendsFieldsGroup extendsFieldsGroup : efg) {
			if(((!role.equals("") && !extendsFieldsGroup.getRole().equals(role)) && (!manager.equals("") && !extendsFieldsGroup.getManager().equals(manager))) || !extendsFieldsGroup.getTable().equals(tablename)) continue;
			List<BootStrapModel> bsml = extendsFieldsGroup.getField();
			if(bsml == null)continue;
			for (BootStrapModel bootStrapModel : bsml) {
				bootStrapModel.setValue(goodsMap.get(bootStrapModel.getField_name()));
				//设置下拉select
				if(bootStrapModel.getOptionKey() != null && !bootStrapModel.getOptionKey().equals("")){
					if(optionMap!=null && optionMap.size() > 0)bootStrapModel.setOption((Map<String,String>)optionMap.get(bootStrapModel.getOptionKey()));
				}
				//设置树状的select
				if(bootStrapModel.getTreeKey() != null && !bootStrapModel.getTreeKey().equals("")){
					if(optionMap!=null && optionMap.size() > 0)bootStrapModel.setTreeList((List<TreeModel>)optionMap.get(bootStrapModel.getTreeKey()));
				}
				//设置相册
				if(bootStrapModel.getGallery_format() != null && !bootStrapModel.getGallery_format().equals("")){
					if(galleryMap!=null && galleryMap.size() > 0)bootStrapModel.setGallery_list((List<Map<String, String>>)galleryMap.get(bootStrapModel.getGallery_format()));
				}
				//checkbox界面
				if(bootStrapModel.getCheckboxs() != null && bootStrapModel.getCheckboxs().size()>0){
					List<BootStrapCheckBoxModel> checkboxList = bootStrapModel.getCheckboxs();
					Map<String, String> option = new HashMap<String, String>();
					for (BootStrapCheckBoxModel bootStrapCheckBoxModel : checkboxList) {
						option.put(bootStrapCheckBoxModel.getKey(), bootStrapCheckBoxModel.getValue());
					}						
					bootStrapModel.setOption(option);
				}
				if(bootStrapModel.getSort()!=null && bootStrapModel.getSort()>0){
					bootStrapList.set(bootStrapModel.getSort(), bootStrapModel);
				}else{
					bootStrapList.add(bootStrapModel);
				}
				
				//添加。。。。类
			}
			
		}
		return bootStrapList;
		
	}
	
	
	
	public <T> List<ExtendsFieldsTabs> BootStrapTabXml(HttpServletRequest request , String bootStrapXml,T t,String tablename,Map<String,Object> optionMap,Map<String,Object> galleryMap) throws Exception{
		String role = (String) request.getSession().getAttribute(EConstants.SESSION_ROLE_TYPE);
		String manager = role;
		if(role.equals("admin")){
			manager = (String) request.getSession().getAttribute(EConstants.SESSION_ADMIN_NAME);
		}else if(role.equals("user")){
			manager = (String) request.getSession().getAttribute(EConstants.SESSION_USER_NAME);
		}
		
		Map<String, Object> goodsMap = this.getClassValue(t);
		
		CommonController c = new CommonController();
		String general = c.config_file(request, bootStrapXml);
		String general_content = FSO.BufferedReader(general);
		XStream xStream = new XStream();
		xStream.processAnnotations(ExtendsFieldsXml.class);
		xStream.processAnnotations(ExtendsFieldsGroup.class);
		xStream.processAnnotations(BootStrapModel.class);
		
		List<ExtendsFieldsTabs> extendsFieldsTabsList = new ArrayList<ExtendsFieldsTabs>();
		ExtendsFieldsXml atxml = (ExtendsFieldsXml) xStream.fromXML(general_content);
		List<ExtendsFieldsGroup> efg = atxml.getGroup();
		for (ExtendsFieldsGroup extendsFieldsGroup : efg) {
			if(((!role.equals("") && !extendsFieldsGroup.getRole().equals(role)) && (!manager.equals("") && !extendsFieldsGroup.getManager().equals(manager))) || !extendsFieldsGroup.getTable().equals(tablename)) continue;
			
			List<ExtendsFieldsTabs> eftl = extendsFieldsGroup.getTab();
			if(eftl == null)continue;			
			for (ExtendsFieldsTabs extendsFieldsTabs : eftl) {
				List<BootStrapModel> bsml = extendsFieldsTabs.getField();
				if(bsml == null)continue;
				for (BootStrapModel bootStrapModel : bsml) {
					bootStrapModel.setValue(goodsMap.get(bootStrapModel.getField_name()));
					//设置下拉select
					if(bootStrapModel.getOptionKey() != null && !bootStrapModel.getOptionKey().equals("")){
						if(optionMap!=null && optionMap.size() > 0)bootStrapModel.setOption((Map<String,String>)optionMap.get(bootStrapModel.getOptionKey()));
					}
					//设置树状的select
					if(bootStrapModel.getTreeKey() != null && !bootStrapModel.getTreeKey().equals("")){
						if(optionMap!=null && optionMap.size() > 0)bootStrapModel.setTreeList((List<TreeModel>)optionMap.get(bootStrapModel.getTreeKey()));
					}
					//设置相册
					if(bootStrapModel.getGallery_format() != null && !bootStrapModel.getGallery_format().equals("")){
						if(galleryMap!=null && galleryMap.size() > 0)bootStrapModel.setGallery_list((List<Map<String, String>>)galleryMap.get(bootStrapModel.getGallery_format()));
					}
					//checkbox界面
					if(bootStrapModel.getCheckboxs() != null && bootStrapModel.getCheckboxs().size()>0){
						List<BootStrapCheckBoxModel> checkboxList = bootStrapModel.getCheckboxs();
						Map<String, String> option = new HashMap<String, String>();
						for (BootStrapCheckBoxModel bootStrapCheckBoxModel : checkboxList) {
							option.put(bootStrapCheckBoxModel.getKey(), bootStrapCheckBoxModel.getValue());
						}						
						bootStrapModel.setOption(option);
					}
					
					
					//针对select,selectTree,checkbox,radio等控件绑定数据源
					if(bootStrapModel.getDataSource() != null && !bootStrapModel.getDataSource().trim().equals("")){
						String className = bootStrapModel.getDataSource().substring(0, bootStrapModel.getDataSource().lastIndexOf("."));
						String funcName = bootStrapModel.getDataSource().substring(bootStrapModel.getDataSource().lastIndexOf(".") + 1 , bootStrapModel.getDataSource().length());

						WebApplicationContext wac = RequestContextUtils.getWebApplicationContext(request);
						Object o = wac.getBean(className);
						Class<?> clz = Class.forName(o.getClass().getName());
						Method m = clz.getMethod(funcName, HttpServletRequest.class);
						if(bootStrapModel.getControl().equals(ControlEnum.select_tree)){
							List<TreeModel> treeList = (List<TreeModel>) m.invoke(o, request);
							bootStrapModel.setTreeList(treeList);
						}else{
							Map<String, String> option = (Map<String, String>) m.invoke(o, request);
							bootStrapModel.setOption(option);
						}
						
						
						
					}
					
					
				}
				//添加返回
				extendsFieldsTabsList.add(extendsFieldsTabs);
			}
			
			
		}
		return extendsFieldsTabsList;
		
	}
	
	
	
	public <T> List<BootStrapModel> easyuiListXml(HttpServletRequest request , String bootStrapXml , String tablename) throws Exception{
		List<BootStrapModel> list = new ArrayList<BootStrapModel>();
		
		String role = (String) request.getSession().getAttribute(EConstants.SESSION_ROLE_TYPE);
		String manager = role;
		if(role.equals("admin")){
			manager = (String) request.getSession().getAttribute(EConstants.SESSION_ADMIN_NAME);
		}else if(role.equals("user")){
			manager = (String) request.getSession().getAttribute(EConstants.SESSION_USER_NAME);
		}
		
		CommonController c = new CommonController();
		String general = c.config_file(request, bootStrapXml);
		String general_content = FSO.BufferedReader(general);
		XStream xStream = new XStream();
		xStream.processAnnotations(ExtendsFieldsXml.class);
		xStream.processAnnotations(ExtendsFieldsGroup.class);
		xStream.processAnnotations(BootStrapModel.class);
		
		ExtendsFieldsXml atxml = (ExtendsFieldsXml) xStream.fromXML(general_content);
		List<ExtendsFieldsGroup> efg = atxml.getGroup();
		
		for (ExtendsFieldsGroup extendsFieldsGroup : efg) {
			if(((!role.equals("") && !extendsFieldsGroup.getRole().equals(role)) && (!manager.equals("") && !extendsFieldsGroup.getManager().equals(manager))) || !extendsFieldsGroup.getTable().equals(tablename)) continue;
			List<BootStrapModel> bsml = extendsFieldsGroup.getField();
			if(bsml == null)continue;
			for (BootStrapModel bootStrapModel : bsml) {
				list.add(bootStrapModel);
			}			
		}
		
		
		return list;
	}
	
	
	
}
