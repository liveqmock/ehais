package org.ehais.shop.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiBrand;
import org.ehais.shop.service.BrandService;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class  BrandController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(BrandController.class);

	@Autowired
	private BrandService brandService;
	
	
	@RequestMapping("/brand_list")
	public String brand_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "brand_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
		}
		return "/admin/brand/list";
	}
	
	@ResponseBody
	@RequestMapping("/brand_list_json")
	public String brand_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<HaiBrand> rm = brandService.brand_list_json(request, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
		}
		return null;
	}
	
	

	@RequestMapping("/brand_insert")
	public String brand_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiBrand> rm = brandService.brand_insert(request);
			rm.setAction("brand_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
		}
		return "/admin/brand/info";
	}
	
	@RequestMapping(value="/brand_insert_submit",method=RequestMethod.POST)
	public String brand_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("brand") HaiBrand brand,
			BindingResult result
			) {
		try{
			if(result.hasErrors()){
				List<ObjectError>  list = result.getAllErrors();
				ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
				rm.setCode(0);
				String[] msgs = new String[list.size()];
				int i = 0;
				for(ObjectError error: list){
					System.out.println(error.getDefaultMessage());
					msgs[i] = error.getDefaultMessage();
					i++;
				}
				rm.setMsgs(msgs);
				return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "brand_insert");
			}
			ReturnObject<HaiBrand> rm = brandService.brand_insert_submit(request,brand);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "brand_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
		}
		return "redirect:brand_insert";
	}
	
	@RequestMapping("/brand_update")
	public String brand_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "brandId", required = true) Integer brandId
			) {
		try{
			ReturnObject<HaiBrand> rm = brandService.brand_update(request, brandId);
			rm.setAction("brand_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
		}
		return "/admin/brand/info";
	}
	
	@RequestMapping(value="/brand_update_submit",method=RequestMethod.POST)
	public String brand_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiBrand brand
			) {
		try{
			ReturnObject<HaiBrand> rm = brandService.brand_update_submit(request,brand);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "brand_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
		}
		return "/admin/brand/info";
	}
	
	
	@RequestMapping("/brand_delete")
	public String brand_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "brandId", required = false) Integer brandId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<HaiBrand> rm = brandService.brand_delete(request, brandId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "brand_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("brand", e);
		}
		return null;
	}
	
	
}


