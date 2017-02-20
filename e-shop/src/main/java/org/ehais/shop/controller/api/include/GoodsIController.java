package org.ehais.shop.controller.api.include;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class GoodsIController extends CommonController{

	@Autowired
	protected GoodsService goodsService;
	
	@ResponseBody
	@RequestMapping("/goods_list")
	public String goods_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		
		try {
			return this.writeJson(goodsService.goods_list(request,store_id, catId, page, len));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/goods_info")
	public String goods_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "goodsId", required = true) Long goodsId){
		
		try {
			return this.writeJson(goodsService.goods_info(request,store_id, goodsId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	

	
}
