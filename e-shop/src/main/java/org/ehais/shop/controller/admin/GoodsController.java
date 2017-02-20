package org.ehais.shop.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.shop.service.GoodsService;
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
public class  GoodsController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(GoodsController.class);

	@Autowired
	private GoodsService goodsService;
	
	
	@RequestMapping("/goods_list")
	public String goods_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		try{
			modelMap.addAttribute("wxid", user_id);
			modelMap.addAttribute("action", "goods_list_json");
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
		}
		return "/admin/goods/list";
	}
	
	@ResponseBody
	@RequestMapping("/goods_list_json")
	public String goods_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len) {
		try{
			ReturnObject<HaiGoods> rm = goodsService.goods_list_json(request, page, len);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
		}
		return null;
	}
	
	

	@RequestMapping("/goods_insert")
	public String goods_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = goodsService.goods_insert(request);
			rm.setAction("goods_insert_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
		}
		return "/admin/goods/info";
	}
	
	@RequestMapping(value="/goods_insert_submit",method=RequestMethod.POST)
	public String goods_insert_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiGoodsWithBLOBs goods
			) throws Exception {
//		try{
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error("goods", e);
//		}
//		return "redirect:goods_insert";
		
		
		ReturnObject<HaiGoodsWithBLOBs> rm = goodsService.goods_insert_submit(request,goods);
		return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "goods_insert");
		
	}
	
	@RequestMapping("/goods_update")
	public String goods_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = goodsService.goods_update(request, goodsId);
			rm.setAction("goods_update_submit");
			modelMap.addAttribute("rm", rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
		}
		return "/admin/goods/info";
	}
	
	@RequestMapping(value="/goods_update_submit",method=RequestMethod.POST)
	public String goods_update_submit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiGoodsWithBLOBs goods
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = goodsService.goods_update_submit(request,goods);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "goods_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
		}
		return "/admin/goods/info";
	}
	
	
	@RequestMapping("/goods_delete")
	public String goods_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = false) Long goodsId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			ReturnObject<HaiGoods> rm = goodsService.goods_delete(request, goodsId);
			return this.ReturnJump(modelMap, rm.getCode(), rm.getMsg(), "goods_list");
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
		}
		return null;
	}
	
	
}


