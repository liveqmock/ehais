package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsAttr;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.shop.service.HaiGoodsService;
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

@EPermissionModuleGroup(name="模组")

@EPermissionController(name="商品管理管理",value="haiGoodsController")
@Controller
@RequestMapping("/admin")
public class  HaiGoodsAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiGoodsAdminController.class);

	@Autowired
	private HaiGoodsService haiGoodsService;
	
	
	@EPermissionMethod(name="查询",intro="打开商品管理页面",value="haiGoodsView",relation="haiGoodsListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiGoodsView")
	public String haiGoodsView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiGoods> rm = haiGoodsService.goods_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/goods/view";
			//return this.view(request, "/goods/view");
			return this.view(request, "/goods/goodsView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回商品管理数据",value="haiGoodsListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiGoodsListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "goodsAttrId", required = false) Integer goodsAttrId,
			@RequestParam(value = "goodsName", required = false) String goodsName) {
		try{
			ReturnObject<HaiGoods> rm = haiGoodsService.goods_list_json(request, condition,goodsAttrId,goodsName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增商品管理",value="haiGoodsAddDetail",relation="haiGoodsAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiGoodsAddDetail",method=RequestMethod.GET)
	public String haiGoodsAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = haiGoodsService.goods_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/goods/detail";
			return this.view(request, "/goods/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交商品管理",value="haiGoodsAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiGoodsAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("goods") HaiGoodsWithBLOBs goods,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiGoodsWithBLOBs> rm = haiGoodsService.goods_insert_submit(request, goods);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑商品管理",value="haiGoodsEditDetail",relation="haiGoodsEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiGoodsEditDetail",method=RequestMethod.POST)
	public String haiGoodsEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = haiGoodsService.goods_update(request,goodsId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑商品管理",value="haiGoodsEditDetail",relation="haiGoodsEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiGoodsEditDetail",method=RequestMethod.GET)
	public String haiGoodsEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId
			) {
		try{
			ReturnObject<HaiGoodsWithBLOBs> rm = haiGoodsService.goods_update(request,goodsId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/goods/detail";
			return this.view(request, "/goods/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交商品管理",value="haiGoodsEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiGoodsEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("goods") HaiGoodsWithBLOBs goods,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiGoodsService.goods_update_submit(request,goods));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除商品管理",value="haiGoodsDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiGoodsDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Long goodsId
			) {
		try{
			return this.writeJson(haiGoodsService.goods_delete(request, goodsId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goods", e);
			return this.errorJSON(e);
		}
	}

	

	/////////////////////////////////////////////////////////////////////////////



	@EPermissionMethod(name="查询",intro="打开商品管理页面",value="haiGoodsAttrView",relation="haiGoodsAttrListJson",type=PermissionProtocol.URL,sort="5")
	@RequestMapping("/haiGoodsAttrView")
	public String haiGoodsAttrView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiGoodsAttr> rm = haiGoodsService.goodsattr_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/goods/goodsattr_view";
			return this.view(request, "/goods/goodsattr_view");
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsattr", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回商品管理数据",value="haiGoodsAttrListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiGoodsAttrListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsAttrListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "attrValue ", required = false) String attrValue ) {
		try{
			ReturnObject<HaiGoodsAttr> rm = haiGoodsService.goodsattr_list_json(request, condition,attrValue );
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsattr", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增商品管理",value="haiGoodsAttrAddDetail",relation="haiGoodsAttrAddSubmit",type=PermissionProtocol.BUTTON,sort="6")
	@RequestMapping(value="/haiGoodsAttrAddDetail",method=RequestMethod.GET)
	public String haiGoodsAttrAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiGoodsAttr> rm = haiGoodsService.goodsattr_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/goods/goodsattr_detail";
			return this.view(request, "/goods/goodsattr_detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsattr", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交商品管理",value="haiGoodsAttrAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiGoodsAttrAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsAttrAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("goodsattr") HaiGoodsAttr goodsattr,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiGoodsAttr> rm = haiGoodsService.goodsattr_insert_submit(request, goodsattr);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsattr", e);
			return this.errorJSON(e);
		}
	}
	

	/**
	@EPermissionMethod(name="编辑",intro="编辑商品管理",value="haiGoodsAttrEditDetail",relation="haiGoodsAttrEditSubmit",type=PermissionProtocol.BUTTON,sort="7")
	@RequestMapping(value="/haiGoodsAttrEditDetail",method=RequestMethod.GET)
	public String haiGoodsAttrEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsAttrId", required = true) Integer goodsAttrId
			) {
		try{
			ReturnObject<HaiGoodsAttr> rm = haiGoodsService.goodsattr_update(request,goodsAttrId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/goods/goodsattr_detail";
			return this.view(request, "/goods/goodsattr_detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsattr", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
**/


	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑商品管理",value="haiGoodsAttrEditData",relation="haiGoodsAttrEditSubmit",type=PermissionProtocol.BUTTON,sort="7")
	@RequestMapping(value="/haiGoodsAttrEditData",method=RequestMethod.POST)
	public String haiGoodsAttrEditData(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsAttrId", required = true) Integer goodsAttrId
			) {
		try{
			ReturnObject<HaiGoodsAttr> rm = haiGoodsService.goodsattr_update(request,goodsAttrId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsattr", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}


	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交商品管理",value="haiGoodsAttrEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiGoodsAttrEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsAttrEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("goodsattr") HaiGoodsAttr goodsattr,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiGoodsService.goodsattr_update_submit(request,goodsattr));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsattr", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除商品管理",value="haiGoodsAttrDelete",type=PermissionProtocol.BUTTON,sort="8")
	@RequestMapping(value="/haiGoodsAttrDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiGoodsAttrDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "goodsAttrId", required = true) Integer goodsAttrId
			) {
		try{
			return this.writeJson(haiGoodsService.goodsattr_delete(request, goodsAttrId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("goodsattr", e);
			return this.errorJSON(e);
		}
	}

}


