package org.ehais.controller;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ECommon;
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
		if(this.isLocalHost(request)){
			path = request.getRealPath("").replace("webapp", "");
			path += "/resources/config/"+file_name;
		}else{
			path = request.getRealPath("/WEB-INF/classes/config/"+file_name);
		}		
		return path;
	}
	
	/**
	 * 判断否本地请求，微信方面的在本地请求可以不通过微信外网请求
	 * @param request
	 * @return
	 */
	protected boolean isLocalHost(HttpServletRequest request){
		if(request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1")){
			log.info("服务器存在本地请求。。。#### server has local request");
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取商家ID
	 * @param request
	 * @return
	 */
	protected Integer getStoreId(HttpServletRequest request){
		return (Integer) request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
	}
	
	/**
	 * 获取商家的主题模板
	 * @param request
	 * @return
	 */
	protected String getStoreTheme(HttpServletRequest request){
		String theme = (String) request.getSession().getAttribute(EConstants.SESSION_STORE_THEME);
		if(StringUtils.isEmpty(theme))theme = "ehais";
		return theme;
	}
	
	
	//成功后统一返回的信息提示页
	protected String successJump(ModelMap modelMap,String successMsg,String jumpUrl){
//		modelMap.addAttribute("status","ok");
		modelMap.addAttribute("successMsg",successMsg);
		modelMap.addAttribute("jumpUrl",jumpUrl);
		modelMap.addAttribute("waitSecond",3);
		
		return "/system/dispatch_jump";
	}
	
	//失败后统一返回的信息提示页
	protected String errorJump(ModelMap modelMap,String errorMsg){
//		modelMap.addAttribute("status","fail");
		modelMap.addAttribute("errorMsg",errorMsg);
		modelMap.addAttribute("jumpUrl","javascript:history.back(-1);");
		modelMap.addAttribute("waitSecond",2);		
		
		return "/system/dispatch_jump";
	}
	
	protected String errorJump(ModelMap modelMap){
		modelMap.addAttribute("errorMsg","系统错误");
		modelMap.addAttribute("jumpUrl","javascript:history.back(-1);");
		modelMap.addAttribute("waitSecond",2);		
		
		return "/system/dispatch_jump";
	}
	
	protected String errorJSON(Exception e){
		ReturnObject<Object> rm = new ReturnObject<Object>();		
		rm.setCode(0);
		rm.setMsg(e.getMessage());
		JSONObject json = JSONObject.fromObject(rm);
		return json.toString();
	}
	
	
	protected String ReturnJump(ModelMap modelMap,Integer code , String msg,String jumpUrl){

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
	
	protected String ReturnJump(ModelMap modelMap,Integer code , String[] msgs,String jumpUrl){

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
	
	protected <T> String ReturnJump(ModelMap modelMap,ReturnObject<T> rm,String jumpUrl){

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
	
	protected <T> String ReturnJumpWrong(ModelMap modelMap,BindingResult result){
		List<ObjectError> errorList = result.getAllErrors();
//        for(ObjectError error : errorList){
//            System.out.println(error.getDefaultMessage());
//        }
		modelMap.addAttribute("errorMsgs",errorList);
		modelMap.addAttribute("jumpUrl","javascript:history.back(-1);");
		modelMap.addAttribute("waitSecond",3);
		
		return "/system/dispatch_jump";
	}
	
	
	protected <T> String writeBindingResult(ModelMap modelMap,BindingResult result){
		List<ObjectError> errorList = result.getAllErrors();
//        for(ObjectError error : errorList){
//            System.out.println(error.getDefaultMessage());
//        }
		modelMap.addAttribute("errorMsgs",errorList);
		modelMap.addAttribute("jumpUrl","javascript:history.back(-1);");
		modelMap.addAttribute("waitSecond",3);
		
		return "/system/dispatch_jump";
	}
	
	protected <T> String writeBindingResult(BindingResult result){
		ReturnObject<ObjectError> rm = new ReturnObject<ObjectError>();
		rm.setCode(0);
		List<ObjectError> errorList = result.getAllErrors();
		String msg = "";
		rm.setRows(errorList);
        for(ObjectError error : errorList){
        	msg += error.getDefaultMessage()+"<br>";
        }
		rm.setMsg(msg);
		return this.writeJson(rm);
	}
	
	
	
	protected <T> String ReturnWriteWrong(ModelMap modelMap,BindingResult result){
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(-2);
		rm.setErrorList(result.getAllErrors());
		JSONObject json = JSONObject.fromObject(rm,this.getDefaultJsonConfig());
		return json.toString();
	}
	
	protected String wrongJump(ModelMap modelMap,String msg){
		modelMap.addAttribute("errorMsg",msg);
		return "/system/wrong";
	}
	
	
	/**返回json
	 * @param ro
	 * @return
	 */
	protected <T> String writeJson(ReturnObject<T> rm){
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
	protected <T> String writeJsonObject(T t){
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
	protected String myExceptionHandler(HttpServletRequest request, Exception ex) { 
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
	
	
	protected boolean isWeiXin(HttpServletRequest request){
		String ua = ((HttpServletRequest) request).getHeader("user-agent") .toLowerCase();  
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器  
		    return  true;  
		}else{
			return false;
		}
	}
	
	protected String shop_encode(HttpServletRequest request){
		String s_encode = (String) request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		if(s_encode == null || s_encode.equals("")){
			s_encode = ECommon.nonceStrUpper(32);
			System.out.println("商码："+s_encode);
			request.getSession().setAttribute(EConstants.SESSION_SHOP_ENCODE,s_encode);
		}
		return s_encode;
	}
}

