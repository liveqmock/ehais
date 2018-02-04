package org.ehais.shop.controller.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.maven.project.builder.ProjectUri.Modules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
@RequestMapping("/reflect")
public class ReflectController {

    @Autowired  
    private RequestMappingHandlerMapping requestMappingHandlerMapping;  
  
    
	@ResponseBody  
    @RequestMapping("getUrlMapping")  
    public Object getUrlMapping(HttpServletRequest request) {  


		Map<String, Object> mapc = requestMappingHandlerMapping.getApplicationContext().getBeansWithAnnotation(Controller.class);
		for(String beanName : mapc.keySet()) {
            Object bean = mapc.get(beanName);
            System.out.println(beanName);
        }
//		
//		List<HashMap<String, String>> urlList = new ArrayList<HashMap<String, String>>();  
//		  
//        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();  
//        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {  
//            HashMap<String, String> hashMap = new HashMap<String, String>();  
//            RequestMappingInfo info = m.getKey();  
//            HandlerMethod method = m.getValue();  
//            
//            System.out.println(info.getPatternsCondition().toString()  
//                    + ","  
//                    +map.get(info).getBean().toString()); 
//            
//            PatternsRequestCondition p = info.getPatternsCondition();  
//            for (String url : p.getPatterns()) {  
//                hashMap.put("url", url);  
//            }  
//            hashMap.put("className", method.getMethod().getDeclaringClass().getName()); // 类名  
//            hashMap.put("method", method.getMethod().getName()); // 方法名  
//            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();  
//            String type = methodsCondition.toString();  
//            if (type != null && type.startsWith("[") && type.endsWith("]")) {  
//                type = type.substring(1, type.length() - 1);  
//                hashMap.put("type", type); // 方法名  
//            }  
//            urlList.add(hashMap);  
//        }  
        
        return null;  
    }  
	
}
