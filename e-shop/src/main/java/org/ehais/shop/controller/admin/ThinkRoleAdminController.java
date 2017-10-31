package org.ehais.shop.controller.admin;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.cache.ERolePermissionCacheManager;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.ThinkRoleMapper;
import org.ehais.epublic.model.ThinkRole;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.model.EPermissionControllerModel;
import org.ehais.model.EPermissionMethodModel;
import org.ehais.model.EPermissionModuleGroupModel;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.service.ThinkRoleService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;  

@EPermissionModuleGroup(name="系统模组")

@EPermissionController(name="角色功能",value="thinkRoleController")
@Controller
@RequestMapping("/admin")
public class  ThinkRoleAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(ThinkRoleAdminController.class);

	@Autowired
	private ThinkRoleService thinkRoleService;
	
	@Autowired
    private RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	private ThinkRoleMapper thinkRoleMapper;
	
	
	//获取所有类文件
	private Set<String> controllerClassNameList(RequestMappingHandlerMapping handlerMapping){
		Set<String> set = new HashSet<String>();
		Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {  
      	  
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
          
            HandlerMethod handlerMethod = entry.getValue(); 
            String controllerName = handlerMethod.getBeanType().getName();
            set.add(controllerName);
        }
		return set;
	}
	
	//获取模组与controller
	private Map<String,Set<String>> moduleList(Set<String> set) throws ClassNotFoundException{
		Map<String,Set<String>> map = new HashMap<String,Set<String>>();
		for (String string : set) {
			
			Class clazz = Class.forName(string);
			if(clazz.isAnnotationPresent(EPermissionModuleGroup.class)){
				
				EPermissionModuleGroup ann = (EPermissionModuleGroup) clazz.getAnnotation(EPermissionModuleGroup.class);
//				mapModule.put(string, ann.name());
				
//				Set<String> s = map.get(ann.name());
//				if(s == null){
//					map
//				}
			}
			/*
			Annotation[] anto = clazz.getAnnotations();
			for (Annotation annotation : anto) {
				System.out.println("annotation.toString():"+annotation.toString());
			}
			
			Method[] methods = clazz.getMethods();
			for(Method method : methods) {
				System.out.println("method.getName():"+method.getName());
				if(method.isAnnotationPresent(EPermissionModuleGroup.class)) {
					
				}
			}
			*/
			
		}
		
		
		
		
		
		return map;
	}
	
	//获取注解模组名称
	private String permissionModuleGroupName(String className) throws ClassNotFoundException{
		Class clazz = Class.forName(className);
		if(clazz.isAnnotationPresent(EPermissionModuleGroup.class)){
			EPermissionModuleGroup ann = (EPermissionModuleGroup) clazz.getAnnotation(EPermissionModuleGroup.class);
			return ann.name();
		}
		return null;
	}
	
	//获取注解控制controller名称
	private EPermissionControllerModel permissionControllerName(String className) throws ClassNotFoundException{
		Class clazz = Class.forName(className);
		if(clazz.isAnnotationPresent(EPermissionController.class)){
			EPermissionController ann = (EPermissionController) clazz.getAnnotation(EPermissionController.class);
			EPermissionControllerModel cm = new EPermissionControllerModel();
			cm.setClassName(className);
			cm.setName(ann.name());
			cm.setValue(ann.value());
			return cm;
		}
		return null;
	}
	//获取注解Method名称
	private List<EPermissionMethodModel> permissionMethodName(String className) throws ClassNotFoundException{
		List<EPermissionMethodModel> list = new ArrayList<EPermissionMethodModel>();
		Class clazz = Class.forName(className);
		Method[] methods = clazz.getDeclaredMethods(); 
		for(Method method : methods) {
			if(method.isAnnotationPresent(EPermissionMethod.class)) {
//				System.out.println("获取方法的名称。。。------------EPermissionMethod----------------"+method.getName());
				EPermissionMethod pm = method.getAnnotation(EPermissionMethod.class);
				EPermissionMethodModel model = new EPermissionMethodModel();
				model.setName(pm.name());
				model.setValue(pm.value());
				model.setRelation(pm.relation());
				if(StringUtils.isNotBlank(pm.name()))list.add(model);
			}
		}
		
		return list;
	}
	//从模组列表匹配返回模组对象
	private EPermissionModuleGroupModel getPermissionModuleGroup(List<EPermissionModuleGroupModel> listModule,String moduleName){
		for (EPermissionModuleGroupModel ePermissionModuleGroupModel : listModule) {
			if(ePermissionModuleGroupModel.getName().equals(moduleName))return ePermissionModuleGroupModel;
		}
		return null;
	}
	
	//从controller列表匹配返回controller对象
	private EPermissionControllerModel getPermissionController(List<EPermissionControllerModel> listMethod,String className){
		for (EPermissionControllerModel ePermissionControllerModel : listMethod) {
			if(ePermissionControllerModel.getClassName().equals(className))return ePermissionControllerModel;
		}
		return null;
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交角色",value="thinkRoleSettingSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/thinkRoleSettingSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String thinkRoleSettingSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "roleId", required = true) Integer roleId,
			@RequestParam(value = "permission", required = true) String permission
			) {
		try{
			
			ThinkRole trole = thinkRoleMapper.selectByPrimaryKey(roleId);
			trole.setPermission(permission);
			trole.setUpdateTime(new Date());
			thinkRoleMapper.updateByPrimaryKeyWithBLOBs(trole);
			
//			更新缓存的权限
			List<String> listPermission = null;
			if(StringUtils.isNoneBlank(permission)){
				listPermission= Arrays.asList(StringUtils.split(permission,","));
			}
			ERolePermissionCacheManager.putRolePermission(roleId, listPermission);
			
			
			return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 1);this.put("msg", "保存成功");}});
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJSON(e);
		}
	}
	
	
	@EPermissionMethod(name="权限管理",intro="权限管理",value="thinkRoleSetting",relation="thinkRoleSettingSubmit",type=PermissionProtocol.URL)
	@RequestMapping("/thinkRoleSetting")
	public String thinkRoleSetting(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "roleId", required = true) Integer roleId ) {	
		try{
			ReturnObject<ThinkRole> rm = thinkRoleService.role_info(request,roleId);
			modelMap.addAttribute("rm", rm);
			modelMap.addAttribute("roleId", roleId);
			
			List<EPermissionModuleGroupModel> listPermission = new ArrayList<EPermissionModuleGroupModel>();
	        
	        
			Set<String> controllerList = this.controllerClassNameList(handlerMapping);
			for (String controllerName : controllerList) {    
				String moduleGroupName = this.permissionModuleGroupName(controllerName);//读取当前controller的模组注解名称
				if(StringUtils.isBlank(moduleGroupName))continue;//没有注解模组，就算了
				EPermissionModuleGroupModel pmgm = this.getPermissionModuleGroup(listPermission,moduleGroupName);//根据模组名称，获取名称与所有的controller列表
				List<EPermissionControllerModel> listController = null;
				if(pmgm == null) {//第一次进入，先初始化列表包名称
					pmgm = new EPermissionModuleGroupModel();
					listController = new ArrayList<EPermissionControllerModel>();
					pmgm.setName(moduleGroupName);
					pmgm.setListController(listController);
					listPermission.add(pmgm);
				}else{
					listController = pmgm.getListController();//当前模组名称下面，所有的controller
				}
				
				//读取controller的注解
				EPermissionControllerModel pcm = this.permissionControllerName(controllerName);//
				if(pcm == null)continue;//如果当前controller没有注解，也不进行下去
				
//				EPermissionControllerModel pcm2 = this.getPermissionController(pmgm.getListController(), controllerName);
//				if(pcm2 == null){//如果列表还未有controller，就先加进来
//					listController.add(pcm);	            	
//				}
				
//				System.out.println("========调用方法的注解.....");
				List<EPermissionMethodModel> listMethod = this.permissionMethodName(controllerName);
				pcm.setListMethod(listMethod);
		        
				listController.add(pcm);
		            
			} 
//			this.moduleList(controllerList);
//			Map<String, Object> mapController = controllerClassNameHandlerMapping.getHandlerMap();
//			for (Map.Entry<String, Object> entry : mapController.entrySet()) {  
//				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
//			}
//			WebApplicationContext wc = getWebApplicationContext(request.getSession().getServletContext());  
//	        RequestMappingHandlerMapping rmhp = wc.getBean(RequestMappingHandlerMapping.class);  
//	        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
	        
	        /*
	        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {  
	        	  
//	            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
	          
	            HandlerMethod handlerMethod = entry.getValue(); 
	            String controllerName = handlerMethod.getBeanType().getName();
	            System.out.println(controllerName);
	        }  
	        */
	        
	        
	        
//	        for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator.hasNext();) {    
//	            RequestMappingInfo info = iterator.next(); //key 
//	            
//	            HandlerMethod handlerMethod = map.get(info); //value
//	            String controllerName = handlerMethod.getBeanType().getName();//读取当前是哪个controller
//	            
//	            
	            
	            
	            
	            
	            /*
	            System.out.println("控制层："+controllerName);
	            Map<String,Map<String,Map<String,String>>> mController = mapController.get(controllerName);
	            if(mController == null){
	            	System.out.println("创建控制场");
	            	mController = new TreeMap<String,Map<String,String>>();
	            	mController.put("name", this.permissionControllerName(controllerName));
	            	mController.put("method", new TreeMap<String,Map<String,Map<String,String>>>());	            	
	            	mapController.put(controllerName, mController);
	            }
	            System.out.println(mapController.toString());
	            Map<String,Map<String,String>> mMethod = (Map<String,Map<String,String>>)mController.get("method");
	            */
//	            System.out.print("1:"+info.getConsumesCondition());  
//	            System.out.print("2:"+info.getCustomCondition());  
//	            System.out.print("3:"+info.getHeadersCondition());  
//	            System.out.print("4:"+info.getMethodsCondition());  
//	            System.out.print("5:"+info.getParamsCondition());  
//	            System.out.print("6:"+info.getPatternsCondition());  
//	            System.out.print("7:"+info.getProducesCondition());  
//	            System.out.print("8:"+info.getClass().getName());  
	            
	            
	            
//	            System.out.print("==================");  
	              
	           
	            
//	            System.out.print("8:"+controllerName+"/////");  
//	            System.out.print(handlerMethod.getMethod().getName() + "--");  
//	            System.out.print(handlerMethod.getMethodAnnotation(RequestMapping.class).name() + "--");  
//	            System.out.print(method.getMethodAnnotation(RequestMapping.class).params()[0]);  
	            
	            /*
	            Method[] methods = info.getClass().getMethods();
	            for(Method method : methods) {
	            	if(method.isAnnotationPresent(RequestMapping.class)) {
	            		RequestMapping mapping = method.getAnnotation(RequestMapping.class);
	            		 String[] urls = mapping.value();
	                     String name = null;
	                     String value = null;
	                     String relation = null;
	                     if(method.isAnnotationPresent(EPermissionMethod.class)) {
	                    	 EPermissionMethod pm = method.getAnnotation(EPermissionMethod.class);
	                    	 value = pm.value();
	                    	 name = pm.name();
	                    	 relation = pm.relation();
	                    	 if(StringUtils.isNotBlank(name)){
	                    		 Map<String,String> mapMethod = mMethod.get(value);
	                    		 if(mapMethod == null){
	                    			 mapMethod = new HashMap<String,String>();
	                    			 mapMethod.put("name", name);
	                    			 mapMethod.put("relation", relation);
	                    			 mMethod.put(value, mapMethod);
	                    		 }
	                    	 }
	                    	 
	                         System.out.print("::::::"+name+"::"+value);
	                     }
	            	}
	            }
	            */
//	        } 
	        
//	        System.out.println(JSONArray.fromObject(listPermission).toString());
	        modelMap.addAttribute("listPermission", listPermission);
	        
			return "/admin/role/permission";
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	
	@EPermissionMethod(intro="打开角色页面",value="thinkRoleView",relation="thinkRoleListJson" ,type=PermissionProtocol.URL)
	@RequestMapping("/thinkRoleView")
	public String thinkRoleView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<ThinkRole> rm = thinkRoleService.role_list(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/role/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回角色数据",value="thinkRoleListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/thinkRoleListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String thinkRoleListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keySubId", required = false) Integer keySubId,
			@RequestParam(value = "name", required = false) String name) {
		try{
			ReturnObject<ThinkRole> rm = thinkRoleService.role_list_json(request, condition,keySubId,name);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增角色",value="thinkRoleAddDetail",relation="thinkRoleAddSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/thinkRoleAddDetail",method=RequestMethod.GET)
	public String thinkRoleAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<ThinkRole> rm = thinkRoleService.role_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/role/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交角色",value="thinkRoleAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/thinkRoleAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String thinkRoleAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("role") ThinkRole role,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<ThinkRole> rm = thinkRoleService.role_insert_submit(request, role);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑角色",value="thinkRoleEditDetail",relation="thinkRoleEditSubmit",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/thinkRoleEditDetail",method=RequestMethod.GET)
	public String thinkRoleEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "roleId", required = true) Integer roleId
			) {
		try{
			ReturnObject<ThinkRole> rm = thinkRoleService.role_update(request,roleId);
			modelMap.addAttribute("rm", rm);
			return "/"+this.getAdminProjectFolder(request)+"/role/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交角色",value="thinkRoleEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/thinkRoleEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String thinkRoleEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("role") ThinkRole role,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(thinkRoleService.role_update_submit(request,role));
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除角色",value="thinkRoleDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/thinkRoleDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String thinkRoleDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "roleId", required = true) Integer roleId
			) {
		try{
			return this.writeJson(thinkRoleService.role_delete(request, roleId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("role", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	
	
	
	public WebApplicationContext getWebApplicationContext(ServletContext sc) {  
        return WebApplicationContextUtils.getRequiredWebApplicationContext(sc);  
    } 
	

}


