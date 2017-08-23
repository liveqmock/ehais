package org.ehais.shop.controller.wine;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryWithBLOBs;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.shop.service.CategoryService;
import org.ehais.shop.service.GoodsService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@EPermissionController(intro="红酒信息功能",value="wineGoodsController")
@Controller
@RequestMapping("/wine")
public class  GoodsAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(GoodsAdminController.class);

	@Autowired
	private GoodsService wineGoodsService;
	@Autowired
	private CategoryService wineCategoryService;
	
	
	@EPermissionMethod(intro="打开红酒信息页面",value="wineGoodsView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/wineGoodsView")
	public String wineGoodsView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiGoods> rm = wineGoodsService.goods_list(request);
			modelMap.addAttribute("rm", rm);
			return "/wine/goods/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回红酒信息数据",value="wineGoodsListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/wineGoodsListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineGoodsListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "goods_name", required = false) String goods_name) {
		try{
			ReturnObject<HaiGoods> rm = wineGoodsService.goods_list_json(request, condition ,cat_id , goods_name);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增红酒信息",value="wineGoodsAddDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/wineGoodsAddDetail",method=RequestMethod.GET)
	public String wineGoodsAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = wineGoodsService.goods_insert(request);
			modelMap.addAttribute("rm", rm);
			return "/wine/goods/detail";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交红酒信息",value="wineGoodsAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/wineGoodsAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineGoodsAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class}) @ModelAttribute("goods") HaiGoodsWithBLOBs goods,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiGoodsWithBLOBs> rm = wineGoodsService.wine_goods_insert_submit(request, goods);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	

	
	@EPermissionMethod(name="编辑",intro="编辑红酒信息",value="wineGoodsEditDetail",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/wineGoodsEditDetail",method=RequestMethod.GET)
	public String wineGoodsEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = wineGoodsService.goods_update(request,goodsId);
			modelMap.addAttribute("rm", rm);
			return "/wine/goods/detail";
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交红酒信息",value="wineGoodsEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/wineGoodsEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineGoodsEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("goods") HaiGoodsWithBLOBs goods,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(wineGoodsService.wine_goods_update_submit(request,goods));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除红酒信息",value="wineGoodsDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/wineGoodsDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineGoodsDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(wineGoodsService.goods_delete(request, goodsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	

	@ResponseBody
	@EPermissionMethod(intro="返回红酒信息数据",value="wineCategoryListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/wineCategoryListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineCategoryListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "other", required = false) String other) {
		try{
			ReturnObject<HaiCategory> rm = wineCategoryService.category_list_json(request, condition);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交红酒信息",value="wineCategoryAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/wineCategoryAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineCategoryAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Valid @ModelAttribute("category") HaiCategoryWithBLOBs category,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiCategoryWithBLOBs> rm = wineCategoryService.category_insert_submit(request, category);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交红酒信息",value="wineCategoryEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/manage/wineCategoryEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineCategoryEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@Valid @ModelAttribute("category") HaiCategoryWithBLOBs category,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(wineCategoryService.category_update_submit(request,category));
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	
	

	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除红酒信息",value="wineCategoryDelete",type=PermissionProtocol.BUTTON)
	@RequestMapping(value="/manage/wineCategoryDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String wineCategoryDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "catId", required = true) Integer catId,
			@RequestParam(value = "code", required = false) String code
			) {
		try{
			return this.writeJson(wineCategoryService.category_delete(request, catId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("category", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}


