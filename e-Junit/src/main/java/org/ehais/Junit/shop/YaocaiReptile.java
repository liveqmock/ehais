package org.ehais.Junit.shop;

import java.util.ArrayList;
import java.util.List;

import org.ehais.model.HaiCategoryEntity;
import org.ehais.model.HaiGoodsAttr;
import org.ehais.model.HaiGoodsEntity;
import org.ehais.model.HaiGoodsGallery;
import org.ehais.model.HaiGoodsWithBLOBs;
import org.ehais.util.Bean2Utils;
import org.ehais.util.FSO;
import org.ehais.util.PythonUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class YaocaiReptile extends ShopCommonReptile{

	private static String website = "http://www.yaocai.com";
	public static String store_id = "71";
	public static String goodsSource = "药材网";
	
	@Test
	public void test_catelist(){
		this.catelist("http://www.yaocai.com/category/");
		
	}
	
	public static void main(String[] args) {
		YaocaiReptile y = new YaocaiReptile();
		y.catelist("http://www.yaocai.com/category/");
	}
	
	
	public void catelist(String url ){
		System.out.println("catelist------------"+url);
		try {
//			String result = PythonUtil.python("D:/workspace_jee/GIT/eh-project/FigoShop/getAjaxWeb.py", url);
//			FSO.WriteTextFile("E:/temp/tt.txt", result);
			String result = FSO.ReadFileName("E:/temp/tt.txt");
			Document doc = Jsoup.parse(result);
			Elements headline = doc.getElementsByClass("headline");
			Elements div = headline.first().select(">div");
			System.out.println(div.size());
			List<HaiCategoryEntity> categorylist = new ArrayList<HaiCategoryEntity>();
			List<HaiGoodsEntity> goodsList = new ArrayList<HaiGoodsEntity>();
			
			for (Element e : div) {
				Element catName = e.getElementsByClass("proLeft").first();
				System.out.println(catName.text());
				HaiCategoryEntity c = new HaiCategoryEntity();
				c.setCatName(catName.text());
				c.setCategoryUrl(catName.getElementsByTag("a").first().attr("href"));
				
				Element proRight = e.getElementsByClass("proRight").first();
				Elements li = proRight.getElementsByTag("li");
				for (Element el : li) {
					HaiGoodsEntity g = new HaiGoodsEntity();
					g.setCatName(catName.text());
					g.setCategoryUrl(c.getCategoryUrl());
					HaiGoodsWithBLOBs p = new HaiGoodsWithBLOBs();
					p.setGoodsName(el.getElementsByTag("a").first().text());
					p.setGoodsUrl(el.getElementsByTag("a").first().attr("href"));
					g.setGoods(p);
					
					goodsList.add(g);
				}
				
				categorylist.add(c);
			}
			
			//保存分类
			JSONArray arr = JSONArray.fromObject(categorylist);
			category_save(store_id, arr.toString());
			
			for (HaiGoodsEntity c : goodsList) {
				this.list(c.getCatName(), website + "/" + c.getGoods().getGoodsUrl());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	@Test
	public void l(){
		String cat_name = "根茎类";
		String url = "http://www.yaocai.com/item/5485089ed0b47517c36c6397/";
		this.list(cat_name, url);
		
	}
	
	public void list(String cat_name,String url){
		System.out.println(cat_name+"*****************"+url);
		try{
			String result = PythonUtil.python("D:/workspace_jee/GIT/eh-project/FigoShop/getAjaxWeb.py", url);
//			FSO.WriteTextFile("E:/temp/goods.txt", result);
			
//			String result = FSO.ReadFileName("E:/temp/goods.txt");
			Document doc = Jsoup.parse(result);
			HaiGoodsEntity entity = new HaiGoodsEntity();
			HaiGoodsWithBLOBs goods = new HaiGoodsWithBLOBs();
			entity.setCatName(cat_name);
			goods.setGoodsUrl(url);
			goods.setGoodsSource(goodsSource);
			
			Element depictname = doc.getElementsByClass("depict-name").first();
			goods.setGoodsName(depictname.getElementsByTag("h1").text());
			Element dNameBuy = depictname.getElementsByClass("dName-Buy").first();
			goods.setGoodsBrief(dNameBuy.text());
			
			
			
			
			Element goodsprice = doc.getElementById("goods-price");
			Element marketPic = goodsprice.getElementsByTag("em").first();
			String price = marketPic.text();
			Double priceDou = Double.parseDouble(price)*100;
			goods.setMarketPrice(priceDou.intValue());
			goods.setShopPrice(priceDou.intValue());

			
			
			
			Element detailshow = doc.getElementsByClass("detail-show").first();
			
			Element showlistcon = detailshow.getElementsByClass("show-list-con").first();
			Elements imglist = showlistcon.select(">ul>li");
			List<HaiGoodsGallery> ggList = new ArrayList<HaiGoodsGallery>();
			
			for (Element ei : imglist) {
				HaiGoodsGallery gg = new HaiGoodsGallery();
				
				Element ea = ei.getElementsByTag("a").first();
				Element timg = ea.getElementsByTag("img").first();
				String thumb = timg.attr("src");
				
				String rel = ea.attr("rel");
				System.out.println(rel);
				
				JSONObject obj = JSONObject.fromObject(rel);
				String goodsImg = obj.getString("smallimage");
				String originalImg = obj.getString("largeimage");
				
				gg.setThumbUrl(thumb);
				gg.setImgUrl(goodsImg);
				gg.setImgOriginal(originalImg);
				
				ggList.add(gg);
				
			}
			
			if(ggList.size()>0){
				goods.setGoodsThumb(ggList.get(0).getThumbUrl());
				goods.setGoodsImg(ggList.get(0).getImgUrl());
				goods.setOriginalImg(ggList.get(0).getImgOriginal());
			}
			
			List<HaiGoodsAttr> goodsAttrList = new ArrayList<HaiGoodsAttr>();
			Element kind = doc.getElementById("kind");
			Elements kli = kind.select(">ul>li>a");
			for (Element ea : kli) {
				String attr = ea.text();
				HaiGoodsAttr ga = new HaiGoodsAttr();
				ga.setAttrName(attr);
				goodsAttrList.add(ga);
			}
			
			Element tab1 = doc.getElementById("tab1");
			goods.setGoodsDesc(tab1.html());
			
			
			
			Bean2Utils.printEntity(goods);
			entity.setGoods(goods);
			entity.setGoodsGalleryList(ggList);
			entity.setGoodsAttrList(goodsAttrList);
			
			goods_save(store_id, entity);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
