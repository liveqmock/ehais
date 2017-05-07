package org.ehais.shop.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.shop.mapper.iwshop.ProductCategoryMapper;
import org.ehais.shop.mapper.iwshop.ProductImagesMapper;
import org.ehais.shop.mapper.iwshop.ProductsInfoMapper;
import org.ehais.shop.model.iwshop.ProductCategory;
import org.ehais.shop.model.iwshop.ProductCategoryExample;
import org.ehais.shop.model.iwshop.ProductImages;
import org.ehais.shop.model.iwshop.ProductImagesExample;
import org.ehais.shop.model.iwshop.ProductsInfo;
import org.ehais.shop.model.iwshop.ProductsInfoEntity;
import org.ehais.shop.model.iwshop.ProductsInfoExample;
import org.ehais.shop.model.iwshop.ProductsInfoWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Controller
@RequestMapping("/ws")
public class IwshopApiController extends CommonController{


	@Autowired
	protected ProductCategoryMapper productCategoryMapper;
	@Autowired
	protected ProductsInfoMapper productsInfoMapper;
	@Autowired
	protected ProductImagesMapper productImagesMapper;

	
	@ResponseBody
	@RequestMapping("/categoryT")
	public String categoryT(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		try{
			List<ProductCategory> listModel = productCategoryMapper.selectByExample(null);
			for (ProductCategory productCategory : listModel) {
				System.out.println("this.cekid_goods_list(\""+productCategory.getCatUrl()+"\","+productCategory.getCatId()+");");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	
	@ResponseBody
	@RequestMapping("/category")
	public String category(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "json", required = true) String json){
		try{
			Gson gson = new Gson();
			List<ProductCategory> list = gson.fromJson(json, new TypeToken<List<ProductCategory>>(){}.getType());  
			for (ProductCategory haiCategory : list) {
				this.saveCategory(haiCategory, 0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	
	private void saveCategory(ProductCategory haiCategory,Integer parentId){
		Integer catId = null;
		ProductCategoryExample example = new ProductCategoryExample();
		ProductCategoryExample.Criteria c = example.createCriteria();
		c.andCatNameEqualTo(haiCategory.getCatName().trim());
		if(haiCategory.getCatUrl() != null && !haiCategory.getCatUrl().equals(""))
			c.andCatUrlEqualTo(haiCategory.getCatUrl().trim());

		haiCategory.setCatParent(parentId);
		
		List<ProductCategory> listModel = productCategoryMapper.selectByExample(example);
		if(listModel == null || listModel.size() == 0){
			productCategoryMapper.insertSelective(haiCategory);
			catId = haiCategory.getCatId();
		}else{
			catId = listModel.get(0).getCatId();
			haiCategory.setCatId(null);
			productCategoryMapper.updateByExampleSelective(haiCategory, example);
		}
		
		if(haiCategory.getChildren()!=null && haiCategory.getChildren().size()>0){
			for (ProductCategory haiCategory2 : haiCategory.getChildren()) {
				this.saveCategory(haiCategory2, catId);
			}
		}
		
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/goods")
	public String goods(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "json", required = true) String json){
		System.out.println(json);
		try{
			Gson gson = new Gson();
			ProductsInfoEntity entity = gson.fromJson(json, ProductsInfoEntity.class);
			ProductsInfoWithBLOBs goods = entity.getProductInfo();
			ProductsInfoExample example = new ProductsInfoExample();
			example.createCriteria().andProductUrlEqualTo(goods.getProductUrl());
			long count = productsInfoMapper.countByExample(example);
			
			if(count == 0){
				productsInfoMapper.insertSelective(goods);
			}else{
				goods.setProductId(null);
				productsInfoMapper.updateByExampleSelective(goods, example);
				List<ProductsInfo> listG = productsInfoMapper.selectByExample(example);
				goods.setProductId(listG.get(0).getProductId());
			}

			
//			ProductImagesExample exampleAttr = new ProductImagesExample();
//			exampleAttr.createCriteria().andProductIdEqualTo(goods.getProductId());
//			haiGoodsAttrMapper.deleteByExample(exampleAttr);
//			
//			List<ProductsInfoAttr> goodsAttrList = entity.getGoodsAttrList();
//			for (ProductsInfoAttr haiGoodsAttr : goodsAttrList) {
//				haiGoodsAttr.setProductId(goods.getProductId());						
//				haiGoodsAttrMapper.insertSelective(haiGoodsAttr);
//			}
			
			
			List<ProductImages> galleryList = entity.getListProductImages();
			ProductImagesExample exampleGallery = new ProductImagesExample();
			exampleGallery.createCriteria().andProductIdEqualTo(goods.getProductId());
			productImagesMapper.deleteByExample(exampleGallery);
			for (ProductImages haiGoodsGallery : galleryList) {
				haiGoodsGallery.setProductId(goods.getProductId());
				productImagesMapper.insertSelective(haiGoodsGallery);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	
	
	
}
