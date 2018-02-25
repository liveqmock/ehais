package org.ehais.Junit.shop;

import java.util.ArrayList;
import java.util.List;

import org.ehais.model.HaiGoodsAttr;
import org.ehais.model.HaiGoodsEntity;
import org.ehais.model.HaiGoodsGallery;
import org.ehais.model.HaiGoodsWithBLOBs;
import org.ehais.util.Bean2Utils;
import org.ehais.util.EHttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class XinshipuReptile extends ShopCommonReptile{
	private static String website = "https://www.xinshipu.com";
	public static String store_id = "30";
	public static String goodsSource = "心食谱";
	
	
	
	@Test
	public void list() {
//		String url = "https://www.xinshipu.com/s/b9e3b6abd2a9b2c4ccc0/";
		String url = "https://www.xinshipu.com/s/b9e3b6abd2a9b2c4ccc0/?page=10";
		this.list(url);
	}
	
	private void list(String url ) {
		System.out.println(url);
		try {
			String result = EHttpClientUtil.methodGet(url);
//			System.out.println(result);
			Document doc = Jsoup.parse(result);
			
			Element ul = doc.getElementsByClass("search-list").first();
			System.out.println(ul.html());
			Elements li = ul.select(">li.gb-block");
			for (Element ele : li) {
				String title = ele.getElementsByTag("a").first().text();
				String a = website + ele.getElementsByTag("a").first().attr("href");
				System.out.println(title+"---"+a);
				this.info(a);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void info() {
		String url = "https://www.xinshipu.com/zuofa/65569";
		this.info(url);
	}
	
	private void info(String url) {
		System.out.println(url);
		try {
			HaiGoodsEntity entity = new HaiGoodsEntity();
			HaiGoodsWithBLOBs goods = new HaiGoodsWithBLOBs();
			List<HaiGoodsGallery> ggList = new ArrayList<HaiGoodsGallery>();
			List<HaiGoodsAttr> goodsAttrList = new ArrayList<HaiGoodsAttr>();
			
			String result = EHttpClientUtil.methodGet(url);
			System.out.println(result);
			Document doc = Jsoup.parse(result);
			Element main = doc.getElementsByClass("content").first();
			String title = main.getElementsByTag("h1").first().text();
			String thumb = "https:"+main.getElementsByTag("img").first().attr("src");
			Element script = doc.getElementsByTag("script").last();
			String json = script.html().replaceAll("\r", "").replaceAll("\n", "");
			System.out.println(json);
			JSONObject obj = JSONObject.fromObject(json);
			
			System.out.println(obj.toString());
			
			goods.setGoodsName(title);
			goods.setGoodsThumb(thumb);
			goods.setGoodsImg(thumb);
			goods.setOriginalImg(thumb);
			
			//材料
			String recipeIngredient = "";
			JSONArray ri = obj.getJSONArray("recipeIngredient");
			for (Object object : ri) {
				if(object.toString().indexOf(" ")>0) {
					String[] o = object.toString().split(" ");
					recipeIngredient +="<p>"+o[0]+"："+o[1]+"</p>";
				}else {
					recipeIngredient += "<p>"+object.toString()+"</p>";
				}
				
			}
			
			//做法
			String step = obj.getString("recipeInstructions");
			
			String desc = "<p>材料</p><hr/>"+recipeIngredient+"<p><br/></p><p>简介</p><hr/><p>"+obj.getString("description")+"</p><p><br/></p><p>做法</p><hr/>"+step;
			goods.setGoodsDesc(desc);
			
			Bean2Utils.printEntity(goods);
			entity.setGoods(goods);
			entity.setGoodsGalleryList(ggList);
			entity.setGoodsAttrList(goodsAttrList);
			
			goods_save(store_id, entity);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
