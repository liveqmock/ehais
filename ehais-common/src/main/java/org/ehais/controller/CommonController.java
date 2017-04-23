package org.ehais.controller;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.tools.ReturnObject;
import org.ehais.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


public class CommonController {
	
	protected static Logger log = LoggerFactory.getLogger(CommonController.class); 

	/**
	 * 统一获取config里面的文件
	 * @param request
	 * @param file_name
	 * @return
	 */
	public String config_file(HttpServletRequest request,String file_name){
		String path = "";		
		if(request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1") || IpUtil.getIpAddr(request).equals("127.0.0.1")){
			path = request.getRealPath("").replace("webapp", "");
			path += "/resources/config/"+file_name;
		}else{
			path = request.getRealPath("/WEB-INF/classes/config/"+file_name);
		}		
		return path;
	}
	
/**
	protected String menu_path(HttpServletRequest request){		
		String menu_path = "";
		Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		if(request.getServerName().equals("localhost")){
			menu_path = request.getRealPath("").replace("webapp", "");
			menu_path += "/resources/config/member_menu_"+userId+".json";
		}else{
			menu_path = request.getRealPath("/WEB-INF/classes/config/member_menu_"+userId+".json");
		}		
		return menu_path;
	}

	protected String menu_path(HttpServletRequest request,String menu_json){
		
		String menu_path = "";
		if(request.getServerName().equals("localhost")){
			menu_path = request.getRealPath("").replace("webapp", "");
			menu_path += "/resources/config/"+menu_json;
		}else{
			menu_path = request.getRealPath("/WEB-INF/classes/config/"+menu_json);
		}		
		return menu_path;
	}
	
	protected String menu_xml_path(HttpServletRequest request,Integer store_id){		
		String menu_path = "";
		Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		if(request.getServerName().equals("localhost")){
			menu_path = request.getRealPath("").replace("webapp", "");
			menu_path += "/resources/config/menu_"+store_id+".xml";
		}else{
			menu_path = request.getRealPath("/WEB-INF/classes/config/menu_"+store_id+".xml");
		}		
		return menu_path;
	}

	
	protected String menu_xml_path(HttpServletRequest request){		
		String menu_path = "";
		String ROLE = (String) request.getSession().getAttribute(Constants.SESSION_ROLE_TYPE);
		
		if(ROLE!=null && !ROLE.equals("")) {
			ROLE="_"+ROLE;
		}else{
			ROLE = "";
		}
		
		if(request.getServerName().equals("localhost")){
			menu_path = request.getRealPath("").replace("webapp", "");
			menu_path += "/resources/config/menu"+ROLE+".xml";
		}else{
			menu_path = request.getRealPath("/WEB-INF/classes/config/menu"+ROLE+".xml");
		}
		
		return menu_path;
	}
	
	
	
	public String eapi_parameter_path(HttpServletRequest request){		
		String menu_path = "";
		if(request.getServerName().equals("localhost")){
			menu_path = request.getRealPath("").replace("webapp", "");
			menu_path += "/resources/config/ApiParameter.xml";
		}else{
			menu_path = request.getRealPath("/WEB-INF/classes/config/ApiParameter.xml");
		}		
		return menu_path;
	}
	
	
	public String store_config_path(HttpServletRequest request){		
		String menu_path = "";
		if(request.getServerName().equals("localhost")){
			menu_path = request.getRealPath("").replace("webapp", "");
			menu_path += "/resources/config/StoreConfig.xml";
		}else{
			menu_path = request.getRealPath("/WEB-INF/classes/config/StoreConfig.xml");
		}		
		return menu_path;
	}
	
	**/
	
	//成功后统一返回的信息提示页
	public String successJump(ModelMap modelMap,String successMsg,String jumpUrl){
//		modelMap.addAttribute("status","ok");
		modelMap.addAttribute("successMsg",successMsg);
		modelMap.addAttribute("jumpUrl",jumpUrl);
		modelMap.addAttribute("waitSecond",3);
		
		return "/system/dispatch_jump";
	}
	
	//失败后统一返回的信息提示页
	public String errorJump(ModelMap modelMap,String errorMsg){
//		modelMap.addAttribute("status","fail");
		modelMap.addAttribute("errorMsg",errorMsg);
		modelMap.addAttribute("jumpUrl","javascript:history.back(-1);");
		modelMap.addAttribute("waitSecond",2);		
		
		return "/system/dispatch_jump";
	}
	
	public String errorJump(ModelMap modelMap){
		modelMap.addAttribute("errorMsg","系统错误");
		modelMap.addAttribute("jumpUrl","javascript:history.back(-1);");
		modelMap.addAttribute("waitSecond",2);		
		
		return "/system/dispatch_jump";
	}
	
	public String errorJSON(Exception e){
		ReturnObject<Object> rm = new ReturnObject<Object>();		
		rm.setCode(0);
		rm.setMsg(e.getMessage());
		JSONObject json = JSONObject.fromObject(rm);
		return json.toString();
	}
	
	
	public String ReturnJump(ModelMap modelMap,Integer code , String msg,String jumpUrl){

		if(code == 1){
			modelMap.addAttribute("successMsg",msg);
			modelMap.addAttribute("jumpUrl",jumpUrl);
			modelMap.addAttribute("waitSecond",3);
		}else{
			modelMap.addAttribute("errorMsg",msg);
			modelMap.addAttribute("jumpUrl","javascript:history.back(-1);");
			modelMap.addAttribute("waitSecond",2);
		}
		
		return "/system/dispatch_jump";
	}
	
	public String ReturnJump(ModelMap modelMap,Integer code , String[] msgs,String jumpUrl){

		if(code == 1){
			modelMap.addAttribute("successMsgs",msgs);
			modelMap.addAttribute("jumpUrl",jumpUrl);
			modelMap.addAttribute("waitSecond",3);
		}else{
			modelMap.addAttribute("errorMsgs",msgs);
			modelMap.addAttribute("jumpUrl","javascript:history.back(-1);");
			modelMap.addAttribute("waitSecond",3);
		}
		
		return "/system/dispatch_jump";
	}
	
	public <T> String ReturnJump(ModelMap modelMap,ReturnObject<T> rm,String jumpUrl){

		if(rm.getCode() == 1){
			modelMap.addAttribute("successMsg",rm.getMsg());
			modelMap.addAttribute("successMsgs",rm.getMsgs());
			modelMap.addAttribute("jumpUrl",jumpUrl);
			modelMap.addAttribute("waitSecond",3);
		}else{
			modelMap.addAttribute("errorMsg",rm.getMsg());
			modelMap.addAttribute("errorMsgs",rm.getMsgs());
			modelMap.addAttribute("jumpUrl","javascript:history.back(-1);");
			modelMap.addAttribute("waitSecond",2);
		}
		
		return "/system/dispatch_jump";
	}
	
	public <T> String ReturnJumpWrong(ModelMap modelMap,BindingResult result){
		List<ObjectError> errorList = result.getAllErrors();
//        for(ObjectError error : errorList){
//            System.out.println(error.getDefaultMessage());
//        }
		modelMap.addAttribute("errorMsgs",errorList);
		modelMap.addAttribute("jumpUrl","javascript:history.back(-1);");
		modelMap.addAttribute("waitSecond",3);
		
		return "/system/dispatch_jump";
	}
	
	public <T> String ReturnWriteWrong(ModelMap modelMap,BindingResult result){
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(-2);
		rm.setErrorList(result.getAllErrors());
		JSONObject json = JSONObject.fromObject(rm,this.getDefaultJsonConfig());
		return json.toString();
	}
	
	public String wrongJump(ModelMap modelMap,String msg){
		modelMap.addAttribute("errorMsg",msg);
		return "/system/wrong";
	}
	
	
	/**返回json
	 * @param ro
	 * @return
	 */
	public <T> String writeJson(ReturnObject<T> rm){
		JSONObject json = JSONObject.fromObject(rm,this.getDefaultJsonConfig());
		return json.toString();
	}
	
	
	/**
	 * @描述 返回对象的json
	 * @param t
	 * @return
	 * @作者 lgj628
	 * @日期 2016年10月29日
	 * @返回 String
	 */
	public <T> String writeJsonObject(T t){
		JSONObject json = JSONObject.fromObject(t,this.getDefaultJsonConfig());
		return json.toString();
	}
	
	/**
	 * json转换config
	 * 
	 * @return
	 */
	protected JsonConfig getDefaultJsonConfig() {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public Object processObjectValue(String key, Object value, JsonConfig cfg) {
				if (value != null && value instanceof Date) {
					return sdf.format(value);
				}
				return null;
				/*if (value != null && value instanceof Date) {
					return ((Date)value).getTime();
				}
				return 0L;*/
			}

			@Override
			public Object processArrayValue(Object value, JsonConfig cfg) {
				if (value != null && value instanceof Date) {
					return sdf.format(value);
				}
				return null;
				/*if (value != null && value instanceof Date) {
					return ((Date)value).getTime();
				}
				return 0L;*/
			}
		});
		return config;
	}
	
	
	@ExceptionHandler  
    public String myExceptionHandler(HttpServletRequest request, Exception ex) { 
		ex.printStackTrace();
        request.setAttribute("ex", ex);  
        // 根据不同错误转向不同页面  
        if(ex instanceof SQLException) {  
            return "error";  
        }else if(ex instanceof RuntimeException) {  
            return "error";  
        } else {  
            return "error";  
        }  
    }
	
	
}

