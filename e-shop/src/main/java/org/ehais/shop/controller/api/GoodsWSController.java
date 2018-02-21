package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.ECommonMapper;
import org.ehais.shop.controller.api.include.GoodsIController;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ws")
public class GoodsWSController extends GoodsIController{

	@Autowired
	private ECommonMapper eCommonMapper;
	
	@ResponseBody
	@RequestMapping("/app_goods_info")
	public String app_goods_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "goodsId", required = true) Long goodsId){
		
		try {
			return this.writeJson(goodsService.app_goods_info(request,store_id ,null , null, goodsId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/set_hot_goods",method=RequestMethod.POST)
	public String set_hot_goods(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@RequestParam(value = "hot", required = true) Integer hot ){
		
		ReturnObject<Integer> rm = new ReturnObject<Integer>();
		rm.setCode(0);
		try {
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			int c = eCommonMapper.commonUpdateBooleanValue("hai_goods", "is_hot", hot.toString(), "goods_id", goodsId.toString(), store_id);
			
			rm.setCode(1);
			rm.setMsg("success");
			rm.setModel(c);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return this.writeJson(rm);
	}
	
	
	@ResponseBody
	@RequestMapping("/goods_list_aid!{cid}")
	public String goods_list_aid(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") String cid,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "len", required = true) Integer len){
		
		try {
			return this.writeJson(goodsService.goods_list(request,cid,store_id, catId, page, len));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	
}
