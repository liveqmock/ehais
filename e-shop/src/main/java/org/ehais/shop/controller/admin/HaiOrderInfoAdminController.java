package org.ehais.shop.controller.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.HaiOrderInfo;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.epublic.validator.EInsertValidator;
import org.ehais.epublic.validator.EUniqueValidator;
import org.ehais.epublic.validator.EUpdateValidator;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.service.HaiOrderInfoService;
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

@EPermissionController(name="订单信息管理",value="haiOrderInfoController")
@Controller
@RequestMapping("/admin")
public class  HaiOrderInfoAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiOrderInfoAdminController.class);

	@Autowired
	private HaiOrderInfoService haiOrderInfoService;
	
	
	@EPermissionMethod(name="查询",intro="打开订单信息页面",value="haiOrderInfoView",relation="haiOrderInfoListJson",type=PermissionProtocol.URL,sort="1")
	@RequestMapping("/haiOrderInfoView")
	public String haiOrderInfoView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiOrderInfo> rm = haiOrderInfoService.orderinfo_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/orderinfo/view";
			//return this.view(request, "/orderinfo/view");
			return this.view(request, "/orderinfo/orderinfoView");
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}



	

	@ResponseBody
	@EPermissionMethod(intro="返回订单信息数据",value="haiOrderInfoListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiOrderInfoListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderInfoListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "recId", required = false) Long recId,
			@RequestParam(value = "orderName", required = false) String orderName) {
		try{
			ReturnObject<HaiOrderInfo> rm = haiOrderInfoService.orderinfo_list_json(request, condition,recId,orderName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增订单信息",value="haiOrderInfoAddDetail",relation="haiOrderInfoAddSubmit",type=PermissionProtocol.BUTTON,sort="2")
	@RequestMapping(value="/haiOrderInfoAddDetail",method=RequestMethod.GET)
	public String haiOrderInfoAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiOrderInfoWithBLOBs> rm = haiOrderInfoService.orderinfo_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/orderinfo/detail";
			return this.view(request, "/orderinfo/detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交订单信息",value="haiOrderInfoAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiOrderInfoAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderInfoAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("orderinfo") HaiOrderInfoWithBLOBs orderinfo,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiOrderInfoWithBLOBs> rm = haiOrderInfoService.orderinfo_insert_submit(request, orderinfo);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
			return this.errorJSON(e);
		}
	}
	

	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑订单信息",value="haiOrderInfoEditDetail",relation="haiOrderInfoEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiOrderInfoEditDetail",method=RequestMethod.POST)
	public String haiOrderInfoEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId
			) {
		try{
			ReturnObject<HaiOrderInfoWithBLOBs> rm = haiOrderInfoService.orderinfo_update(request,orderId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}

	/**
	@EPermissionMethod(name="编辑",intro="编辑订单信息",value="haiOrderInfoEditDetail",relation="haiOrderInfoEditSubmit",type=PermissionProtocol.BUTTON,sort="3")
	@RequestMapping(value="/haiOrderInfoEditDetail",method=RequestMethod.GET)
	public String haiOrderInfoEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Integer orderId
			) {
		try{
			ReturnObject<HaiOrderInfoWithBLOBs> rm = haiOrderInfoService.orderinfo_update(request,orderId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/orderinfo/detail";
			return this.view(request, "/orderinfo/detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	**/
	
	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交订单信息",value="haiOrderInfoEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiOrderInfoEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderInfoEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("orderinfo") HaiOrderInfoWithBLOBs orderinfo,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiOrderInfoService.orderinfo_update_submit(request,orderinfo));
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除订单信息",value="haiOrderInfoDelete",type=PermissionProtocol.BUTTON,sort="4")
	@RequestMapping(value="/haiOrderInfoDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderInfoDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId
			) {
		try{
			return this.writeJson(haiOrderInfoService.orderinfo_delete(request, orderId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("orderinfo", e);
			return this.errorJSON(e);
		}
	}

	

	/////////////////////////////////////////////////////////////////////////////



	@EPermissionMethod(name="查询",intro="打开订单信息页面",value="haiOrderGoodsView",relation="haiOrderGoodsListJson",type=PermissionProtocol.URL,sort="5")
	@RequestMapping("/haiOrderGoodsView")
	public String haiOrderGoodsView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			ReturnObject<HaiOrderGoods> rm = haiOrderInfoService.ordergoods_list(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/orderinfo/ordergoods_view";
			return this.view(request, "/orderinfo/ordergoods_view");
		}catch(Exception e){
			e.printStackTrace();
			log.error("ordergoods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回订单信息数据",value="haiOrderGoodsListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiOrderGoodsListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderGoodsListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "goodsName", required = false) String goodsName) {
		try{
			ReturnObject<HaiOrderGoods> rm = haiOrderInfoService.ordergoods_list_json(request, condition,goodsName);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ordergoods", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@EPermissionMethod(name="新增",intro="新增订单信息",value="haiOrderGoodsAddDetail",relation="haiOrderGoodsAddSubmit",type=PermissionProtocol.BUTTON,sort="6")
	@RequestMapping(value="/haiOrderGoodsAddDetail",method=RequestMethod.GET)
	public String haiOrderGoodsAddDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) {
		try{
			ReturnObject<HaiOrderGoods> rm = haiOrderInfoService.ordergoods_insert(request);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/orderinfo/ordergoods_detail";
			return this.view(request, "/orderinfo/ordergoods_detail");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("ordergoods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	@ResponseBody
	@EPermissionMethod(intro="新增提交订单信息",value="haiOrderGoodsAddSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiOrderGoodsAddSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderGoodsAddSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EInsertValidator.class,EUniqueValidator.class})  @ModelAttribute("ordergoods") HaiOrderGoods ordergoods,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			
			ReturnObject<HaiOrderGoods> rm = haiOrderInfoService.ordergoods_insert_submit(request, ordergoods);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("ordergoods", e);
			return this.errorJSON(e);
		}
	}
	

	/**
	@EPermissionMethod(name="编辑",intro="编辑订单信息",value="haiOrderGoodsEditDetail",relation="haiOrderGoodsEditSubmit",type=PermissionProtocol.BUTTON,sort="7")
	@RequestMapping(value="/haiOrderGoodsEditDetail",method=RequestMethod.GET)
	public String haiOrderGoodsEditDetail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "recId", required = true) Integer recId
			) {
		try{
			ReturnObject<HaiOrderGoodsWithBLOBs> rm = haiOrderInfoService.ordergoods_update(request,recId);
			modelMap.addAttribute("rm", rm);
			//return "/"+this.getAdminProjectFolder(request)+"/orderinfo/ordergoods_detail";
			return this.view(request, "/orderinfo/ordergoods_detail");
		}catch(Exception e){
			e.printStackTrace();
			log.error("ordergoods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
**/


	@ResponseBody
	@EPermissionMethod(name="编辑",intro="编辑订单信息",value="haiOrderGoodsEditData",relation="haiOrderGoodsEditSubmit",type=PermissionProtocol.BUTTON,sort="7")
	@RequestMapping(value="/haiOrderGoodsEditData",method=RequestMethod.POST)
	public String haiOrderGoodsEditData(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "recId", required = true) Long recId
			) {
		try{
			ReturnObject<HaiOrderGoods> rm = haiOrderInfoService.ordergoods_update(request,recId);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("ordergoods", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}


	
	@ResponseBody
	@EPermissionMethod(intro="编辑提交订单信息",value="haiOrderGoodsEditSubmit",type=PermissionProtocol.DATA)
	@RequestMapping(value="/haiOrderGoodsEditSubmit",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderGoodsEditSubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@Validated({EUpdateValidator.class}) @ModelAttribute("ordergoods") HaiOrderGoods ordergoods,
			BindingResult result
			) {
			if(result.hasErrors())return this.writeBindingResult(result);
		try{
			return this.writeJson(haiOrderInfoService.ordergoods_update_submit(request,ordergoods));
		}catch(Exception e){
			e.printStackTrace();
			log.error("ordergoods", e);
			return this.errorJSON(e);
		}
	}
	
	
	@ResponseBody
	@EPermissionMethod(name="删除",intro="删除订单信息",value="haiOrderGoodsDelete",type=PermissionProtocol.BUTTON,sort="8")
	@RequestMapping(value="/haiOrderGoodsDelete",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiOrderGoodsDelete(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "recId", required = true) Long recId
			) {
		try{
			return this.writeJson(haiOrderInfoService.ordergoods_delete(request, recId));
		}catch(Exception e){
			e.printStackTrace();
			log.error("ordergoods", e);
			return this.errorJSON(e);
		}
	}

}


