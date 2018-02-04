package org.ehais.shop.controller.api;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.tools.ReturnObject;
import org.ehais.util.FSO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class ArApiController extends CommonController{

	
	@ResponseBody
	@RequestMapping("/write_code")
	public String write_code(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = true) String code){
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(1);
		try{
			String path = request.getRealPath("/arcode.txt");
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			FSO.WriteTextFile2(path, code);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		rm.setMsg("success");
		return this.writeJson(rm);
	}
	

	@ResponseBody
	@RequestMapping("/read_code")
	public String read_code(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response){
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(1);
		try{
			String path = request.getRealPath("/arcode.txt");
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			String code = FSO.ReadFileName(path);
			rm.setMsg(code);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		rm.setMsg("v001");
		return this.writeJson(rm);
	}


	///////////////////////////////////////////////////////////////

	@ResponseBody
	@RequestMapping("/write_dining")
	public String write_dining(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = true) String code){
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(1);
		try{
			String path = request.getRealPath("/ardining.txt");
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			FSO.WriteTextFile2(path, code);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		rm.setMsg("success");
		return this.writeJson(rm);
	}
	

	@ResponseBody
	@RequestMapping("/read_dining")
	public String read_dining(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response){
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(1);
		try{
			String path = request.getRealPath("/ardining.txt");
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			String code = FSO.ReadFileName(path);
			rm.setMsg(code);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		rm.setMsg("v001");
		return this.writeJson(rm);
	}
	
}
