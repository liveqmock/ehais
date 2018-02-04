package org.ehais.shop.model.iwshop;

import java.util.List;

public class ProductsInfoEntity {
	
	private ProductsInfoWithBLOBs productInfo;
	private List<ProductImages> listProductImages;

	public ProductsInfoWithBLOBs getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductsInfoWithBLOBs productInfo) {
		this.productInfo = productInfo;
	}

	public List<ProductImages> getListProductImages() {
		return listProductImages;
	}

	public void setListProductImages(List<ProductImages> listProductImages) {
		this.listProductImages = listProductImages;
	}
	
	

}
