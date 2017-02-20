package org.ehais.shop.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.model.BootStrapModel;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.service.GoodsGalleryService;
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
public class GoodsGalleryController extends CommonController{
	
	private static Logger log = LoggerFactory.getLogger(GoodsGalleryController.class);

	@Autowired
	private GoodsGalleryService goodsGalleryService;
	
	@RequestMapping(value="/goods_gallery_add",method=RequestMethod.POST)
	public String goods_gallery_add(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@RequestParam(value = "fieldName", required = true) String fieldName,
			@RequestParam(value = "fieldLabel", required = true) String fieldLabel,
			@RequestParam(value = "tableName", required = true) String tableName,
			@RequestParam(value = "thumbUrl", required = false) String thumbUrl,
			@RequestParam(value = "imgUrl", required = false) String imgUrl,
			@RequestParam(value = "imgOriginal", required = true) String imgOriginal,
			@RequestParam(value = "gallery_index", required = true) Integer gallery_index) {	
		try{
			
			HaiGoodsGallery model = new HaiGoodsGallery();
			model.setGoodsId(goodsId);
			model.setTableName(tableName);
			model.setThumbUrl(thumbUrl);
			model.setImgUrl(imgUrl);
			model.setImgOriginal(imgOriginal);
			
//			ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
			
//			rm.setBootStrapList(bootStrapList);
			BootStrapModel bootstrap = new BootStrapModel();
			bootstrap.setField_name(fieldName);
			bootstrap.setField_label(fieldLabel);
			modelMap.addAttribute("bootstrap", bootstrap);
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("imgId", "0");
			map.put("goodsId", goodsId.toString());
			map.put("thumb", thumbUrl.indexOf("http://") >= 0 ? thumbUrl : "/"+thumbUrl);
			map.put("img", imgUrl.indexOf("http://") >= 0 ? imgUrl : "/"+imgUrl);
			map.put("original", imgOriginal.indexOf("http://") >= 0 ? imgOriginal : "/"+imgOriginal);
			map.put("tableName", tableName);
			
			modelMap.addAttribute("gallery", map);
			
			modelMap.addAttribute("gallery_index", gallery_index);
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("activity", e);
		}
		return "/temp/gallery_li_sub";
	}
	
	@RequestMapping(value="/goods_gallery_insert",method=RequestMethod.POST)
	public String goods_gallery_insert(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiGoodsGallery model) {
		try{
			ReturnObject<HaiGoodsGallery> rm = goodsGalleryService.goods_gallery_insert(request, model);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gallery", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/goods_gallery_edit",method=RequestMethod.POST)
	public String goods_gallery_edit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "imgId", required = true) Long imgId,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@RequestParam(value = "tableName", required = true) String tableName,
			@RequestParam(value = "thumbUrl", required = false) String thumbUrl,
			@RequestParam(value = "imgUrl", required = false) String imgUrl,
			@RequestParam(value = "imgOriginal", required = true) String imgOriginal) {	
		try{
			HaiGoodsGallery model = new HaiGoodsGallery();
			model.setImgId(imgId);
			model.setGoodsId(goodsId);
			model.setTableName(tableName);
			model.setThumbUrl(thumbUrl);
			model.setImgUrl(imgUrl);
			model.setImgOriginal(imgOriginal);
			ReturnObject<HaiGoodsGallery> rm = goodsGalleryService.goods_gallery_update(request, model);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gallery", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/goods_gallery_update",method=RequestMethod.POST)
	public String goods_gallery_update(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute HaiGoodsGallery model) {	
		try{			
			ReturnObject<HaiGoodsGallery> rm = goodsGalleryService.goods_gallery_update(request, model);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gallery", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/goods_gallery_delete")
	public String goods_gallery_delete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "img_id", required = false) Long img_id,
			@RequestParam(value = "goods_id", required = false) Long goods_id
			) {
		try{
			ReturnObject<HaiGoodsGallery> rm = goodsGalleryService.goods_gallery_delete(request, img_id, goods_id, null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("gallery", e);
		}
		return null;
	}
	
	
}
