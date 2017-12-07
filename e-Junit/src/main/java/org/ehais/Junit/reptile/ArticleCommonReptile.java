package org.ehais.Junit.reptile;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;

public class ArticleCommonReptile {
//	private static String web_url = "http://mg.ehais.com";
	private static String web_url = "http://127.0.0.1";
	public static String appkey = "Ehais";
	public static String secret = "EhaisSecret";
	
	
	public static void article_save(String store_id,
			String cat_name,
			String title,
			String articleThumb,
			String articleImages,
			String description,
			String content,
			String articleSource,
			String link
			){
		article_save(store_id, cat_name, title, articleThumb, articleImages, description, content, articleSource, link,"","");
	}
	
	public static void article_save(String store_id,
			String cat_name,
			String title,
			String articleThumb,
			String articleImages,
			String description,
			String content,
			String articleSource,
			String link,
			String parent_cat_name,
			String article_date
			){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", appkey);
    	paramsMap.put("version", "v1.0");
    	paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
    	
    	paramsMap.put("title", title);
    	paramsMap.put("store_id", store_id);
    	paramsMap.put("storeId", store_id);
    	paramsMap.put("link", link);
    	paramsMap.put("description", description);
    	paramsMap.put("content", content);
    	paramsMap.put("cat_name", cat_name);
    	paramsMap.put("articleSource", articleSource);
    	paramsMap.put("articleThumb", articleThumb);
    	paramsMap.put("articleImages", articleImages);
    	paramsMap.put("parent_cat_name", parent_cat_name);
    	paramsMap.put("article_date", article_date);
    	
    	
		try {
			String sign = SignUtil.getSignWS(paramsMap,secret).toLowerCase();
			paramsMap.put("sign", sign);

	    	String req = EHttpClientUtil.httpPost(web_url+"/api/article_save", paramsMap);
			System.out.println(req);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
	}
	
}
