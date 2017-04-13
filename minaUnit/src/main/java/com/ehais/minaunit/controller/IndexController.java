package com.ehais.minaunit.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehais.minaunit.handler.MinaSessionMap;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/")
public class IndexController {

	
	@RequestMapping("index")
	public String index(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response){
		
		
		return "index";
	}
	
	
	@ResponseBody
	@RequestMapping("/wx{wxid}.do")
	public String wx(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable("wxid") Integer wxid,
			@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "timestamp", required = false) String timestamp,
			@RequestParam(value = "nonce", required = false) String nonce,
			@RequestParam(value = "echostr", required = false) String echostr) {
		
		
		try {
			String inputLine = null;
			String notityXml = "";
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			JSONObject json = new JSONObject();
			json.put("wxid", wxid.toString());
			json.put("signature", signature);
			json.put("timestamp", timestamp);
			json.put("nonce", nonce);
			json.put("echostr", echostr);
			json.put("notityXml", notityXml);
			
			
			MinaSessionMap sessionMap = MinaSessionMap.newInstance();
			IoSession session = sessionMap.getSession(json.getString("wxid"));
			session.getConfig().setUseReadOperation(true);
			WriteFuture future = session.write(json.toString());//发送消息
			future.awaitUninterruptibly();
			
			ReadFuture readFuture = session.read();
			readFuture.awaitUninterruptibly();  
			
			String result = (String) readFuture.getMessage();
			
			return result;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		

		
		
		return "success";
	}
	
}
