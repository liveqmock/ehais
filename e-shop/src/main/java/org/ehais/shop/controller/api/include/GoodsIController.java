package org.ehais.shop.controller.api.include;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.shop.service.GoodsService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.QiniuUtil;
import org.ehais.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class GoodsIController extends CommonController{

	@Autowired
	protected GoodsService goodsService;
	
	private static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	private static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	private static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	private static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	
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
	
	
	
	

	/**
	 * 商品列表返回数据
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param condition
	 * @param cat_id
	 * @param goods_name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/goodsListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisGoodsListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "goods_name", required = false) String goods_name) {
		try{
			ReturnObject<HaiGoods> rm = goodsService.goods_list_json(request, condition ,cat_id , goods_name,true);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/goodsDetail",method=RequestMethod.POST)
	public String ehaisGoodsDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = goodsService.goods_update(request,goodsId);
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("uptoken", QiniuUtil.getUpToken(accessKey,secretKey,bucket));
			map.put("domain", domain);
			
			rm.setMap(map);
			
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
		
	}
	
	
	/**
	 * 新增提交商品信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param goodsDistribution
	 * @param goods
	 * @param result
	 * @param imgOriginal
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/goodsAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisGoodsAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("goods") HaiGoodsWithBLOBs goods
			) {
		try{
			
			return this.writeJson(goodsService.goods_insert_submit(request, goods));
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/goodsEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisGoodsEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("goods") HaiGoodsWithBLOBs goods
			) {
		try{
			return this.writeJson(goodsService.goods_update_submit(request,goods));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/goodsDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String ehaisGoodsDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId
			) {
		try{
			return this.writeJson(goodsService.goods_delete(request, goodsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	

	
}
