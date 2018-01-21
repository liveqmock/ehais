package org.ehais.controller.epublic;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ehais.util.SCaptchaUtils;
import org.ehais.util.VerifyCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
	/**
	 * 验证码
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/AuthImage")
	public void AuthImage(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws IOException {	
		
		response.setHeader("Pragma", "No-cache"); 
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg"); 
           
        //生成随机字串 
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4); 
        System.out.println("verifyCode:"+verifyCode);
        //存入会话session 
        HttpSession session = request.getSession(true); 
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toUpperCase()); 
        //生成图片 
        int w = 100, h = 30; 
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode); 
        
	}
	
	@RequestMapping("/AuthImage2")
	public void AuthImage2(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws IOException {	
		
        
     // 设置响应的类型格式为图片格式  
        response.setContentType("image/jpeg");  
        // 禁止图像缓存。  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        
        
        SCaptchaUtils instance = new SCaptchaUtils();  
        
      //生成随机字串 
        String verifyCode = instance.getCode(); 
        System.out.println("verifyCode:"+verifyCode);
        
        HttpSession session = request.getSession(true); 
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toUpperCase());
         
        instance.write(response.getOutputStream());  
        
        
	}
	
	
}
