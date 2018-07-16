package org.ehais.shop.controller.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.enums.EAdMediaTypeEnum;
import org.ehais.enums.EArticleClassifyEnum;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.shop.mapper.HaiAdMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.tools.EConditionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/lyh")
public class LyhCarWebController extends CommonController{
	
	private Integer store_id = 106;
	private String folder = "lyhcar";
	@Autowired
	private HaiAdMapper haiAdMapper;
	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	
	@RequestMapping("/index.html")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		String browser = "pc";
		try {
			if(isWeiXin(request) || JudgeIsMoblie(request)){
				browser = "mobile";
			}
			
			//获取广告
			HaiAdExample adExp = new HaiAdExample();
			adExp.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andMediaTypeEqualTo(EAdMediaTypeEnum.carousel_pc)
			.andEnabledEqualTo(true);
			List<HaiAd> ad_list = haiAdMapper.selectByExample(adExp);
			modelMap.addAttribute("ad_list", ad_list);
			
			//获取新车推荐
			HaiGoodsExample goodsExp = new HaiGoodsExample();
			goodsExp.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andIsNewEqualTo(true);
			List<HaiGoods> new_goods_list = haiGoodsMapper.selectByExample(goodsExp);
			modelMap.addAttribute("new_goods_list", new_goods_list);
			
			//获取最新动态
			EHaiArticleExample artExp = new EHaiArticleExample();
			artExp.createCriteria().andStoreIdEqualTo(store_id)
			.andClassifyEqualTo(EArticleClassifyEnum.LIST)
			.andModuleEqualTo(EArticleModuleEnum.ARTICLE)
			.andIsHotEqualTo(true);
			artExp.setOrderByClause("article_date desc");
			artExp.setLimitStart(0);
			artExp.setLimitEnd(4);
			List<EHaiArticle> article_list = eHaiArticleMapper.selectByExample(artExp);
			modelMap.addAttribute("article_list", article_list);
			
			//获取品牌信息
			EHaiArticleExample brandExp = new EHaiArticleExample();
			brandExp.createCriteria().andStoreIdEqualTo(store_id)
			.andClassifyEqualTo(EArticleClassifyEnum.SINGLE)
			.andModuleEqualTo(EArticleModuleEnum.BRANDSTORY);
			brandExp.setOrderByClause("article_date desc");
			brandExp.setLimitStart(0);
			brandExp.setLimitEnd(1);
			List<EHaiArticle> brand_list = eHaiArticleMapper.selectByExample(brandExp);
			if(brand_list.size() > 0) {
				modelMap.addAttribute("brand", brand_list.get(0));
			}else {
				modelMap.addAttribute("brand", new EHaiArticle());
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/"+folder+"/"+browser+"/index";
	}
	
	//单页面
	@RequestMapping("/article_{module}.html")
	public String article(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "module") String module) {
		
		String browser = "pc";
		try {
			if(isWeiXin(request) || JudgeIsMoblie(request)){
				browser = "mobile";
			}
			
			//读取所有单文章列表
			EHaiArticleExample artCatExp = new EHaiArticleExample();
			artCatExp.createCriteria().andStoreIdEqualTo(store_id)
			.andClassifyEqualTo(EArticleClassifyEnum.SINGLE);
			List<EHaiArticle> cat_list = eHaiArticleMapper.selectByExample(artCatExp);
			modelMap.addAttribute("cat_list", cat_list);
			
			
			//获取单文章信息
			EHaiArticleExample artExp = new EHaiArticleExample();
			artExp.createCriteria().andStoreIdEqualTo(store_id)
			.andClassifyEqualTo(EArticleClassifyEnum.SINGLE)
			.andModuleEqualTo(module);
			artExp.setOrderByClause("article_date desc");
			artExp.setLimitStart(0);
			artExp.setLimitEnd(1);
			List<EHaiArticle> article_list = eHaiArticleMapper.selectByExample(artExp);
			if(article_list.size() > 0) {
				modelMap.addAttribute("article_list", article_list.get(0));
			}else {
				modelMap.addAttribute("article_list", new EHaiArticle());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/"+folder+"/"+browser+"/article";
	}
	
	//评估中心
	@RequestMapping("/evaluate.html")
	public String evaluate(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		String browser = "pc";
		try {
			if(isWeiXin(request) || JudgeIsMoblie(request)){
				browser = "mobile";
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/"+folder+"/"+browser+"/evaluate";
	}
	
	//新闻中心&服务中心&促销优惠
	@RequestMapping("/article_{module}_{cid}.html")
	public String news(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") Integer cid,
			@PathVariable(value = "module") String module,
			@ModelAttribute EConditionObject condition) {
		String browser = "pc";
		try {
			if(isWeiXin(request) || JudgeIsMoblie(request)){
				browser = "mobile";
			}
			
			
			//查找分类列表
			EHaiArticleCatExample catExp = new EHaiArticleCatExample();
			catExp.createCriteria().andStoreIdEqualTo(store_id).andModuleEqualTo(module);
			List<EHaiArticleCat> cat_list = eHaiArticleCatMapper.selectByExample(catExp);
			modelMap.addAttribute("cat_list", cat_list);
			
			
			//查找资讯列表
			EHaiArticleExample artExp = new EHaiArticleExample();
			artExp.createCriteria().andStoreIdEqualTo(store_id)
			.andClassifyEqualTo(EArticleClassifyEnum.LIST)
			.andModuleEqualTo(module)
			.andCatIdEqualTo(cid);
			artExp.setOrderByClause("article_date desc");
			artExp.setLimitStart(condition.getStart());
			artExp.setLimitEnd(condition.getRows());
			List<EHaiArticle> article_list = eHaiArticleMapper.selectByExample(artExp);
			modelMap.addAttribute("article_list", article_list);
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/"+folder+"/"+browser+"/article_list";
	}
	
//	@RequestMapping("/service")
//	public String service(ModelMap modelMap,
//			HttpServletRequest request,HttpServletResponse response) {
//		String browser = "pc";
//		try {
//			if(isWeiXin(request) || JudgeIsMoblie(request)){
//				browser = "mobile";
//			}
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return "/"+folder+"/"+browser+"/service";
//	}
	
	
	@RequestMapping("/shop")
	public String shop(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		String browser = "pc";
		try {
			if(isWeiXin(request) || JudgeIsMoblie(request)){
				browser = "mobile";
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/"+folder+"/"+browser+"/shop";
	}
	
	
	@RequestMapping("/shop_detail")
	public String shop_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		String browser = "pc";
		try {
			if(isWeiXin(request) || JudgeIsMoblie(request)){
				browser = "mobile";
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "/"+folder+"/"+browser+"/shop";
	}
	
	

}
