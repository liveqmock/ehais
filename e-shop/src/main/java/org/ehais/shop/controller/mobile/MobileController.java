package org.ehais.shop.controller.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/m")
public class MobileController extends CommonController{

	private String themes = "simple";
	
//	public MobileController(){
//		super();
//		System.out.println("构造函数!");
//	}
	
	@RequestMapping("/index-{store_id}")
	public String index_simple(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ,
			@PathVariable(value="store_id") Integer store_id
			) {	
		modelMap.addAttribute("store_id", store_id);
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/index_simple";
	}
	
	@RequestMapping("/login")
	public String login(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/login";
	}
	
	@RequestMapping("/register")
	public String register(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/register";
	}
	
	
	@RequestMapping("/g-{store_id}-{goods_id}")
	public String goods_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "store_id") Integer store_id,
			@PathVariable(value = "goods_id") Integer goods_id){	
		try{
			modelMap.addAttribute("store_id", store_id);
			modelMap.addAttribute("goods_id", goods_id);
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/goods_info";
	}
	
	@RequestMapping("/cart_list")
	public String cart_list(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/cart_list";
	}
	
	
	@RequestMapping("/checkout")
	public String checkout(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/checkout";
	}
	
	@RequestMapping("/done")
	public String done(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/done";
	}
	
	@RequestMapping("/member")
	public String member(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/member";
	}
	
	@RequestMapping("/order_list")
	public String order_list(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/order_list";
	}
	
	@RequestMapping("/order_info")
	public String order_info(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="orderId", required = true) Integer orderId) {	
		modelMap.addAttribute("orderId", orderId);
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/order_info";
	}
	
	
	@RequestMapping("/user_info")
	public String user_info(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/user_info";
	}
	
	@RequestMapping("/user_address_list")
	public String user_address_list(ModelMap modelMap,HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="action",required=false) String action) {	
		
		modelMap.addAttribute("action", action);
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/user_address_list";
	}
	
	
	//添加地址
	@RequestMapping("/user_address_add")
	public String user_address_add(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ) {	
		modelMap.addAttribute("init_js_event", "");
		modelMap.addAttribute("submit_event", "add_user_address();");
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/user_address_info";
	}
	
	//获取地址与编辑地址
	@RequestMapping("/user_address_info")
	public String user_address_info(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ,
			@RequestParam(value = "addressId", required = true) Integer addressId) {	
		modelMap.addAttribute("init_js_event", "get_user_address("+addressId+");");
		modelMap.addAttribute("submit_event", "edit_user_address();");
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/user_address_info";
	}
	
	
	@RequestMapping("/my_favorites")
	public String my_favorites(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/"+themes+"/my_favorites";
	}
	
	
	
	@RequestMapping("/ejq")
	public String ejq(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		return "/mobile/ejqdemo";
	}
	
	
}
