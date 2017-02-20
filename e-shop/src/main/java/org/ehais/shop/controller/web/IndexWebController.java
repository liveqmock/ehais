package org.ehais.shop.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.Constants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.EHaiAd;
import org.ehais.shop.model.HaiBrand;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.model.HaiNav;
import org.ehais.shop.model.HaiUserAddress;
import org.ehais.shop.service.AdService;
import org.ehais.shop.service.BrandService;
import org.ehais.shop.service.CartService;
import org.ehais.shop.service.CategoryService;
import org.ehais.shop.service.GoodsService;
import org.ehais.shop.service.NavService;
import org.ehais.shop.service.UserAddressService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ECommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class IndexWebController extends CommonController{

	private String themes = "tianmao";
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private AdService adService;
	@Autowired
	private NavService navService;
	@Autowired
	private CartService cartService;
	@Autowired
	private UserAddressService userAddressService;
	
	
	public void initWebController(HttpServletRequest request){	
		if(request.getSession().getAttribute(Constants.SESSION_SHOP_ENCODE) == null){
			//生成随机数的session
			String webSession = ECommon.nonceStr(32);
			request.getSession().setAttribute(Constants.SESSION_SHOP_ENCODE, webSession);
			System.out.println("网站的随机数："+webSession);
		}
		
		//获取store_id 如果是空则有默认
		if(request.getParameter("store_id") != null){
			request.getSession().setAttribute(Constants.SESSION_STORE_ID, Integer.valueOf(request.getParameter("store_id")));
		}else{
			request.getSession().setAttribute(Constants.SESSION_STORE_ID, 3);
		}
	}
	
	@RequestMapping("/index")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws Exception {	
		
		initWebController(request);//初始化的session随机数
		
		List<HaiCategory> cateList = categoryService.list(request);
		modelMap.addAttribute("cateList", cateList);
		
		List<HaiBrand> brandList = brandService.list(request);
		modelMap.addAttribute("brandList", brandList);
		
		List<EHaiAd> adList = adService.list(request);
		modelMap.addAttribute("adList", adList);
		
		List<HaiNav> navList = navService.list(request);
		modelMap.addAttribute("navList", navList);
		
		List<HaiGoods> goodsList = goodsService.list(request, null, 1, 100);
		modelMap.addAttribute("goodsList", goodsList);
		
		return "/web/"+themes+"/index";
	}
	
	@RequestMapping("/cate-{catId}")
	public String cate(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) throws Exception {	
		return "/web/"+themes+"/cate";
	}
	
	
	@RequestMapping("/goods-{goodsId}.html")
	public String goods_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ,
			@PathVariable(value="goodsId") Long goodsId
			) throws Exception {	
		
		initWebController(request);//初始化的session随机数
		
		HaiGoods goods = goodsService.info(request, goodsId);
		modelMap.addAttribute("goods", goods);
		
		List<HaiGoodsGallery> galleryList = goodsService.galleryList(request, goodsId);
		modelMap.addAttribute("galleryList", galleryList);
		
		
		return "/web/"+themes+"/goods_detail";
	}
	
	
	@RequestMapping("/cart.html")
	public String cart_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) throws Exception {	
		
		initWebController(request);//初始化的session随机数
		
		ReturnObject<HaiCart> rm = cartService.cart_list(request, null,null);
		modelMap.addAttribute("cartList", rm.getRows());
		
		
		return "/web/"+themes+"/cart_list";
	}
	
	@RequestMapping("/checkout.html")
	public String checkout(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) throws Exception {	
		
		initWebController(request);//初始化的session随机数
		
		ReturnObject<HaiCart> cartRm = cartService.cart_list(request, null,null);
		modelMap.addAttribute("cartList", cartRm.getRows());
		
		ReturnObject<HaiUserAddress> addressRm = userAddressService.useraddress_lists(request, null);
		modelMap.addAttribute("addressList", addressRm.getRows());
		
		return "/web/"+themes+"/checkout";
	}
	
	
	@RequestMapping("/tpermission.html")
	public String tpermission(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response
			) throws Exception {	
		modelMap.addAttribute("name", "fyhells");
		return "/web/test/tpermission";
	}
	
	
	
	
}
