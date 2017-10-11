package org.ehais.Junit.shop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.ehais.model.HaiCategoryEntity;
import org.ehais.model.HaiGoodsAttr;
import org.ehais.model.HaiGoodsEntity;
import org.ehais.model.HaiGoodsGallery;
import org.ehais.model.HaiGoodsWithBLOBs;
import org.ehais.util.Bean2Utils;
import org.ehais.util.EHtmlUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import net.sf.json.JSONArray;

public class Mia extends ShopCommonReptile{
	
	private static String website = "https://www.mia.com";
	public static String store_id = "9";
	public static String goodsSource = "密芽";
	
	@Test
	public void test_catelist(){
		this.catelist(website);
		
//		try {
//			Thread.sleep(100000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void catelist(String url ){
		System.out.println("catelist------------"+url);
		try {
			Document docData = Jsoup.connect(url).timeout(20000).get();
			
			Element navMenus = docData.getElementById("navMenus");
			
			Elements colum = navMenus.getElementsByClass("colum");
			List<HaiCategoryEntity> categorylist = new ArrayList<HaiCategoryEntity>();
			for (Element element : colum) {
				HaiCategoryEntity c = new HaiCategoryEntity();
				String catename = element.getElementsByTag("h3").first().text();
				c.setCatName(catename);
				
				List<HaiCategoryEntity> categorylist2 = new ArrayList<HaiCategoryEntity>();
				Elements a = element.getElementsByTag("a");
				for (Element element2 : a) {
					
					if(element2.attr("href").indexOf("javascript")>0)continue;
					
					String href = element2.attr("href");
					if(href.indexOf("http") < 0)href = website + href;
					
					HaiCategoryEntity c2 = new HaiCategoryEntity();
					c2.setCatName(element2.text());
					c2.setCategoryUrl(href);
					
					categorylist2.add(c2);
					
				}
				
				c.setChildren(categorylist2);
				
				categorylist.add(c);
				
			}
			
			//保存分类
			JSONArray arr = JSONArray.fromObject(categorylist);
			category_save(store_id, arr.toString());
			
			for (HaiCategoryEntity c : categorylist) {
				for (HaiCategoryEntity c2 : c.getChildren()) {
					this.list(c2.getCatName(), c2.getCategoryUrl());
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	public void test_list(){
		String url = "https://www.mia.com/s/cat13_tid1013.html";
		String cat_name = "婴儿奶粉";
		this.list(cat_name, url);
	}
	public void list(String cat_name,String url){
		System.out.println(cat_name+"*****************"+url);
		try{
			Document docData = Jsoup.connect(url).timeout(20000).get();
			Elements block = docData.select(".block");
			for (Element element : block) {
				Elements a = element.getElementsByTag("a");
				String href = a.first().attr("href");
				String thumb = a.first().getElementsByTag("img").attr("src");
				String data_thumb = a.first().getElementsByTag("img").attr("data-src");
				String goodsThumb = StringUtils.isBlank(thumb)?data_thumb : thumb;
				if(href.indexOf("http") < 0)href = website + href;
				
//				System.out.println("详情页:"+href+"====");
				view(cat_name, goodsThumb, href);
				
//				new Thread(new TheadReptile(cat_name, goodsThumb, href)).start();
				
			}
			
			Element Lpage = docData.select(".Lpage").first();
			Elements apage = Lpage.getElementsByTag("a");
			for (Element e : apage) {
				if(e.text().equals(">")){
					System.out.println("下一页"+e.attr("href"));
//					this.list(cat_name, website+e.attr("href"));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_view(){
		String url = "https://www.mia.com/item-1127489.html";
		view("婴儿奶粉", "https://img05.miyabaobei.com/d1/p2/item/11/1127/1127489_topic_2.jpg@base@tag=imgScale&w=447", url);
	}
	
	public static void view(String cat_name,String goodsThumb,String goodsUrl){
		System.out.println("详情页:"+cat_name+"!!!!!!!!!!!!!!!!!!!!"+goodsUrl);
		try {
			HaiGoodsEntity goodsEntity = new HaiGoodsEntity();
			goodsEntity.setCatName(cat_name);
			goodsEntity.setStoreId(store_id);
			
			HaiGoodsWithBLOBs goods = new HaiGoodsWithBLOBs();
			goods.setGoodsUrl(goodsUrl);
			goods.setGoodsSource(goodsSource);
			goods.setGoodsThumb(goodsThumb);
			
			String content = EHtmlUnit.getAjaxPage(goodsUrl);
			Document docData = Jsoup.parse(content);
			String brand = docData.select(".brand").first().text();
			String titlecon = docData.select(".titlecon").first().text();
			String description = docData.select(".area").first().html();
			String item_price = docData.getElementById("item_price").text();
			String mprice = docData.getElementById("mprice").text();
			String big = docData.select(".big").first().getElementsByTag("img").attr("src");
			
			Element pi_attr_box = docData.getElementsByClass("pi_attr_box").first();
			Element J_num_select = pi_attr_box.getElementById("J_num_select");
			J_num_select.remove();
			
			List<HaiGoodsAttr> attrList = new ArrayList<HaiGoodsAttr>();
			Elements dl = pi_attr_box.getElementsByTag("dl");
			for (Element element : dl) {
				String dt = element.getElementsByTag("dt").text();
				Elements li = element.getElementsByTag("li");
				for (Element element2 : li) {
					HaiGoodsAttr attr = new HaiGoodsAttr();
					Elements img = element2.getElementsByTag("img");
					String pic = "";
					String val = element2.text();
					if(img!=null && img.size() > 0){
						pic = img.first().attr("src");
					}
					attr.setAttrName(dt);
					attr.setAttrValue(val);
					attr.setAttrPic(pic);
					attrList.add(attr);
					
				}
			}
			
			List<HaiGoodsGallery> gallery = new ArrayList<HaiGoodsGallery>();
			Elements change_pic = docData.select(".change_pic");
			if(change_pic!=null && change_pic.size() > 0){
				for (Element element : change_pic) {
					HaiGoodsGallery gg = new HaiGoodsGallery();
					gg.setImgOriginal(element.attr("src"));
					gallery.add(gg);
				}
			}
			
			
			goods.setGoodsName(brand);
			goods.setActDesc(titlecon);
			goods.setGoodsDesc(description);
			goods.setOriginalImg(big);
			goods.setShopPrice(Integer.valueOf(item_price.replace(".", "")));
			goods.setMarketPrice(Integer.valueOf(mprice.replace(".", "")));
			
			
			Bean2Utils.printEntity(goods);
			
			goodsEntity.setGoods(goods);
			goodsEntity.setGoodsAttrList(attrList);
			goodsEntity.setGoodsGalleryList(gallery);
			
			goods_save(store_id, goodsEntity);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	class TheadReptile implements Runnable{
		
		private String catName;
		private String goodsThumb;
		private String goodsUrl;
		
		
		public TheadReptile(String catName,
		String goodsThumb,
		String goodsUrl){
			this.catName = catName;
			this.goodsThumb = goodsThumb;
			this.goodsUrl = goodsUrl;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Mia.view(catName, goodsThumb, goodsUrl);
		}
		
	}
	
	
}
