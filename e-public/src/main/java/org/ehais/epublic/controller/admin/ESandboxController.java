package org.ehais.epublic.controller.admin;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 扫描所有注解的方法
 * @author lgj628
 *
 */
@Controller
@RequestMapping("/sandbox")
public class ESandboxController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ESandboxController.class);
	
    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
 
    @RequestMapping("/index")
    public void index(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {
 
        Map map =  this.handlerMapping.getHandlerMethods();
        Iterator<?> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println(entry.getKey() +"\n" + entry.getValue());
        }
    }
    
}
