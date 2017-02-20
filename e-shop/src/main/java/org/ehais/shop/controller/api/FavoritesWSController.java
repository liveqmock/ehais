package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.shop.controller.api.include.FavoritesIController;
import org.ehais.shop.model.HaiFavorites;
import org.ehais.shop.model.HaiGoods;
import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ws")
public class FavoritesWSController extends FavoritesIController {

	@ResponseBody
	@RequestMapping("/favorites_add")
	public String favorites_add(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goods_id", required = true) Long goods_id) {
		try{
			ReturnObject<HaiFavorites> rm = favoritesService.favorites_add(request, goods_id , null,null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/favorites_list")
	public String favorites_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<HaiGoods> rm = favoritesService.goods_list_json(request , null , page,len,null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("cart", e);
		}
		return null;
	}
	
}
