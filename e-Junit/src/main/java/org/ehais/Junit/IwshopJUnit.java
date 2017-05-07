package org.ehais.Junit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.model.iwshop.ProductCategory;
import org.ehais.model.iwshop.ProductImages;
import org.ehais.model.iwshop.ProductsInfoEntity;
import org.ehais.model.iwshop.ProductsInfoWithBLOBs;
import org.ehais.util.Bean2Utils;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.FSO;
import org.ehais.util.PythonUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class IwshopJUnit {
	
	
	public void cekid_category(String categoryUrl){
		String result = "";
		try {
//			result = PythonUtil.python(request.getRealPath("/getAjaxWeb.py"), categoryUrl);
//			result = PythonUtil.python("D:/workspace_jee/figoarticle/src/main/webapp/getAjaxWeb.py", categoryUrl);
			result = FSO.ReadFileName("E:/temp/cekid.htm");
			System.out.println(result);
			Document doc = Jsoup.parse(result);
					
			List<ProductCategory> list = new ArrayList<ProductCategory>();
			// TODO
			
			Elements sort_list = doc.getElementsByClass("sort-list");			
			Elements sort_list_li = sort_list.select(">li");
			Elements sort_detail = doc.getElementsByClass("sort-detail");
			
			int i = 0;
			for (Element element : sort_list_li) {
				String catName = element.text();
				ProductCategory cat = new ProductCategory();
				cat.setCatName(catName);
				cat.setCatUrl(element.getElementsByTag("a").first().attr("href"));
				cat.setCatLevel(0);
				cat.setCatOrder(i);
				
				//获取第二层分类
				Element sortDetail = sort_detail.get(i);
				Element keywordsElement = sortDetail.getElementsByClass("keywords").first();
				Elements dl = keywordsElement.getElementsByTag("dl");
				Elements dd_span = keywordsElement.getElementsByTag("dd").first().getElementsByTag("span");
				int j = 0;
				List<ProductCategory> children2 = new ArrayList<ProductCategory>();
				for (Element element2 : dl) {
					ProductCategory cat2 = new ProductCategory();
					cat2.setCatName(element2.getElementsByTag("dt").text());
					cat2.setCatUrl(element2.getElementsByTag("dt").first().getElementsByTag("a").first().attr("href"));
					cat2.setCatLevel(2);
					cat2.setCatOrder(j);					
					int k = 0;
					List<ProductCategory> children3 = new ArrayList<ProductCategory>();
					for (Element element3 : dd_span) {
						ProductCategory cat3 = new ProductCategory();
						cat3.setCatName(element3.getElementsByTag("a").text());
						cat3.setCatUrl(element3.getElementsByTag("a").first().getElementsByTag("a").first().attr("href"));
						cat3.setCatLevel(3);
						cat3.setCatOrder(k);
						
						children3.add(cat3);
						k++;
					}
					
					cat2.setChildren(children3);
					children2.add(cat2);
					j++;
				}
				cat.setChildren(children2);
				
				list.add(cat);
				i++;
			}
			
			System.out.println(sort_list.size());
			System.out.println(sort_list_li.size());
			System.out.println(sort_detail.size());
			
			
			
			
				
			System.out.println("==========================================");
			System.out.println("==========================================");
			
			JSONArray arr = JSONArray.fromObject(list);
			System.out.println(arr.toString());
//			System.out.println(element.html());
			System.out.println("==========================================");
			
			Map<String, String> paramsMap = new HashMap<String,String>();
			paramsMap.put("json", arr.toString());
//			String api = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+"/api/category";
			String api = "http://localhost:81/ws/category";
			String apiresult = EHttpClientUtil.httpPost(api, paramsMap);
			System.out.println(apiresult);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void cekid_category_junit(){
		this.cekid_category("http://www.cekid.com/");
	}
	
	@Test
	public void cekid_goods_list_junit(){
		System.out.println("获取商品列表");

//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009447_5009449_5009451&metaattrs=55_4590",11);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009447_5009449_5009451&metaattrs=55_3249",12);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009447_5009449_5009451&metaattrs=55_4",13);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009447_5009449_5009451&metaattrs=55_1890",14);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009447_5009449_5009451&metaattrs=55_588",15);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009447_5009449_5009451&metaattrs=55_3",16);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009447_5009449_5009451&metaattrs=55_4595",17);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009447_5009449_5009451&metaattrs=55_2657",18);
//
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%B1%81%E5%B1%81%E6%8A%A4%E7%90%86",20);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E7%BA%B8%E5%B0%BF%E8%A3%A4",21);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009463_5009465_5009467&metaattrs=55_4661",22);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009463_5009465_5009467&metaattrs=55_3415",23);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009463_5009465_5009467&metaattrs=55_4615",24);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009463_5009465_5009467&metaattrs=55_2902",25);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009463_5009465_5009467&metaattrs=55_4612",26);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009463_5009465_5009467&metaattrs=55_1150",27);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009463_5009465_5009467&metaattrs=55_3783",28);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E6%8B%89%E6%8B%89%E8%A3%A4",29);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E6%B9%BF%E5%B7%BE",30);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E8%90%A5%E5%85%BB%E8%BE%85%E9%A3%9F",31);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%AE%9D%E5%AE%9D%E9%A3%9F%E5%93%81",32);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009475_5009477_5009479",33);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009475_5009477_5009485",34);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009475_5009477_5009483",35);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009475_5009477_5009495",36);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%AE%9D%E5%AE%9D%E8%90%A5%E5%85%BB",37);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E7%AB%A5%E8%A3%85%E7%AB%A5%E9%9E%8B",38);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E7%AB%A5%E8%A3%85",39);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009521_5009523_5009525",40);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009521_5009523_5009527",41);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009521_5009523_5009531",42);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009521_5009523_5009533",43);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009521_5009523_5009535",44);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009521_5009523_5009537",45);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009521_5009523_5009539",46);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009521_5009523_5009543",47);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E7%AB%A5%E9%9E%8B",48);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%AE%B6%E7%BA%BA",49);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%AF%9D%E5%B1%85",50);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009549_5009551_5009553",51);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009549_5009551_5009555",52);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009549_5009551_5009557",53);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009549_5009551_5009559",54);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009549_5009551_5009561",55);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009549_5009551_5009563",56);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%AE%B6%E7%BA%BA",57);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%AE%B6%E5%B1%85",58);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%AD%95%E4%BA%A7%E7%94%A8%E5%93%81",59);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%AD%95%E5%A6%88%E7%94%A8%E5%93%81",60);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009575_5009577_5009579",61);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009575_5009577_5009581",62);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%AD%95%E5%A6%88%E6%B4%97%E6%8A%A4",63);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%96%82%E5%85%BB%E7%94%A8%E5%93%81",64);
//
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009593_5009595_5009597",66);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009593_5009595_5009599",67);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009593_5009595_5009601",68);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009593_5009595_5009603",69);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E6%B4%97%E6%8A%A4%E7%94%A8%E5%93%81",70);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009605_5009607_5009609",71);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009605_5009607_5009611",72);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E6%B8%85%E6%B4%81%E6%B6%88%E6%AF%92",73);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E6%B8%85%E6%B4%81",74);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009613_5009615_5009617",75);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009613_5009615_5009619",76);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009613_5009615_5009621",77);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009613_5009615_5009623",78);
//		this.cekid_goods_list("http://list.cekid.com/?key=%E6%96%87%E4%BD%93%E7%8E%A9%E5%85%B7",79);
//
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009629_5009649_5009651",81);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009629_5009649_5009655",82);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009629_5009649_5009657",83);
//		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009629_5009631_5009647",84);

//		this.cekid_goods_list("http://list.cekid.com/?key=%E5%87%BA%E8%A1%8C%E8%A3%85%E5%A4%87",95);
		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009745_5009747_5009749",96);
		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009745_5009747_5009751",97);
		this.cekid_goods_list("http://list.cekid.com/?searchpath=5009745_5009747_5009759",98);

	}

	
	public void cekid_goods_list(String searchUrl,Integer catId){
		String result = "";
		try {
//			result = PythonUtil.python(request.getRealPath("/getAjaxWeb.py"), categoryUrl);
			result = PythonUtil.python("D:/workspace_jee/figoarticle/src/main/webapp/getAjaxWeb.py", searchUrl);
//			result = FSO.ReadFileName("E:/temp/cekid.htm");
//			System.out.println(result);
			Document doc = Jsoup.parse(result);
					
			List<ProductCategory> list = new ArrayList<ProductCategory>();
			// TODO
			
			Element search_result_list = doc.getElementById("search-result-list");			
			Elements search_list_li = search_result_list.select(">li");
			for (Element element : search_list_li) {
				Element a = element.getElementsByTag("a").first();				
				String href = a.attr("href");
				if(href.indexOf("http")<0)href = "http:"+href;
				this.cekid_goods_detail(href,catId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void cekid_goods_detail_junit(){
		System.out.println("获取商品列表");
		this.cekid_goods_detail("http://detail.cekid.com/detail-473.html",1);
	}
	
	
	public void cekid_goods_detail(String goodsUrl,Integer catId){
		System.out.println("详情："+goodsUrl);
		String result = "";
		try {
//			result = PythonUtil.python(request.getRealPath("/getAjaxWeb.py"), categoryUrl);
			result = PythonUtil.python("D:/workspace_jee/figoarticle/src/main/webapp/getAjaxWeb.py", goodsUrl);
//			result = FSO.ReadFileName("E:/temp/cekid_detail.htm");
//			System.out.println(result);
			Document doc = Jsoup.parse(result);
			ProductsInfoEntity entity = new ProductsInfoEntity();
			ProductsInfoWithBLOBs product = new ProductsInfoWithBLOBs();
			product.setProductCat(catId);
			product.setProductUrl(goodsUrl);
			String productName = doc.getElementsByClass("goods-name").first().text();
			product.setProductName(productName);
			product.setProductSubname(productName);
			String now_price = doc.getElementsByClass("now-price").first().text();
			product.setMarketPrice(Float.valueOf(now_price));
			product.setSellPrice(Float.valueOf(now_price));
			product.setProductOnline(Byte.valueOf("1"));
			String detail = doc.getElementById("detail").html();
			detail = detail.replaceAll("lazy-src", "src");
			product.setProductDesc(detail);
			
			Element slick_track = doc.getElementsByClass("slick-track").first();
			
			Elements grally = slick_track.getElementsByTag("img");
			List<ProductImages> listProductImages = new ArrayList<ProductImages>();
			for (Element element : grally) {
				ProductImages img = new ProductImages();
				img.setImagePath(element.attr("origin"));
				listProductImages.add(img);
			}
			if(listProductImages.size()>0){
				product.setCatimg(listProductImages.get(0).getImagePath());
			}
			
			Bean2Utils.printEntity(product);
			entity.setProductInfo(product);
			entity.setListProductImages(listProductImages);
			
			JSONObject json = JSONObject.fromObject(entity);
			System.out.println(json.toString());
			
			Map<String, String> paramsMap = new HashMap<String,String>();
			paramsMap.put("json", json.toString());
//			String api = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+"/api/goods";
			String api = "http://localhost:81/ws/goods";
			String apiresult = EHttpClientUtil.httpPost(api, paramsMap);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
