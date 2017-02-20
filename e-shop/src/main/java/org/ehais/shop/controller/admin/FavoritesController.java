package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiFavorites;
import org.ehais.shop.service.FavoritesService;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class  FavoritesController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(FavoritesController.class);

	@Autowired
	private FavoritesService favoritesService;
	
	
	@RequestMapping("/favorites_list")
	public String favorites_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "favorites_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("favorites", e);
		}
		return "/admin/favorites/list";
	}
	
	@ResponseBody
	@RequestMapping("/favorites_list_json")
	public String favorites_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<HaiFavorites> rm = favoritesService.favorites_list_json(request,null, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("favorites", e);
		}
		return null;
	}
	
	

	@RequestMapping("/favorites_insert")
	public String favorites_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiFavorites> rm = favoritesService.favorites_insert(request);
			rm.setAction("favorites_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("favorites", e);
		}
		return "/admin/favorites/info";
	}
	
	@RequestMapping(value="/favorites_insert_submit",method=RequestMethod.POST)
	public String favorites_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiFavorites favorites
			) {
		try{
			ReturnObject<HaiFavorites> rm = favoritesService.favorites_insert_submit(request,favorites);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "favorites_insert");
		}catch(Exception e){
			e.printStackTrace();
			log.error("favorites", e);
		}
		return "redirect:favorites_insert";
	}
	
	@RequestMapping("/favorites_update")
	public String favorites_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = true) Long id
			) {
		try{
			ReturnObject<HaiFavorites> rm = favoritesService.favorites_update(request, id);
			rm.setAction("favorites_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("favorites", e);
		}
		return "/admin/favorites/info";
	}
	
	@RequestMapping(value="/favorites_update_submit",method=RequestMethod.POST)
	public String favorites_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiFavorites favorites
			) {
		try{
			ReturnObject<HaiFavorites> rm = favoritesService.favorites_update_submit(request,favorites);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "favorites_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("favorites", e);
		}
		return "/admin/favorites/info";
	}
	
	
	@RequestMapping("/favorites_delete")
	public String favorites_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<HaiFavorites> rm = favoritesService.favorites_delete(request, id);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "favorites_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("favorites", e);
		}
		return null;
	}
	
	/**
	

	@ResponseBody
	@RequestMapping("/controller{store_id}")
	public String controller(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "store_id") Integer store_id,
			@RequestParam(value = "code", required = true) String code){
		
		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	22///////////////////////////////////////////////////	
	@ResponseBody
	@RequestMapping("/_list")
	public String _list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "cat_code", required = true) String cat_code,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		
		try {
			return this.writeJson(service._list(request,store_id, cat_code, page, len));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	**/
	
}


