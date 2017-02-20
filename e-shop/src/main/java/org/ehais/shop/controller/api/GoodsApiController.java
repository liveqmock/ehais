package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.shop.controller.api.include.GoodsIController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api")
public class GoodsApiController extends GoodsIController{

	
	@ResponseBody
	@RequestMapping("/app_goods_info")
	public String app_goods_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "s_encode", required = true) String s_encode,
			@RequestParam(value = "goodsId", required = true) Long goodsId){
		
		try {
			return this.writeJson(goodsService.app_goods_info(request,store_id ,user_id , s_encode, goodsId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
}
