package org.ehais.Junit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.model.ExtendEntity;
import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONJunit {

	public static void main(String[] args) {
		ExtendEntity ent = new ExtendEntity();
		ent.setPassword("123456ok");
		
		JSONObject obj = JSONObject.fromObject(ent);
		System.out.println(obj.toString());
	}
	
	@Test
	public void attribute(){
		
		List<Map<String,Object>> listData = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> mapColor = new HashMap<String , Object>();
		mapColor.put("key", "color");
		mapColor.put("value", "颜色");
		List<Map<String,Object>> listColor = new ArrayList<Map<String,Object>>();		
		Map<String, Object> mapColor1 = new HashMap<String, Object>();
		mapColor1.put("key", "红色");
		mapColor1.put("price", 100.00);
		mapColor1.put("inventory", 200);
		listColor.add(mapColor1);
		Map<String, Object> mapColor2 = new HashMap<String, Object>();
		mapColor2.put("key", "黄色");
		listColor.add(mapColor2);		
		Map<String, Object> mapColor3 = new HashMap<String, Object>();
		mapColor3.put("key", "黑色");
		mapColor3.put("price", 120.00);
		listColor.add(mapColor3);		
		mapColor.put("data", listColor);
		listData.add(mapColor);
		
		
		Map<String, Object> mapSize = new HashMap<String,Object>();
		mapSize.put("key", "size");
		mapSize.put("value", "尺寸");
		List<Map<String,Object>> listSize = new ArrayList<Map<String,Object>>();		
		Map<String, Object> mapSize1 = new HashMap<String, Object>();
		mapSize1.put("key", "35码");
		mapSize1.put("price", 100.00);
		mapSize1.put("inventory", 200);
		listSize.add(mapSize1);
		Map<String, Object> mapSize2 = new HashMap<String, Object>();
		mapSize2.put("key", "38码");
		listSize.add(mapSize2);		
		Map<String, Object> mapSize3 = new HashMap<String, Object>();
		mapSize3.put("key", "40码");
		mapSize3.put("price", 120.00);
		listSize.add(mapSize3);		
		mapSize.put("data", listSize);
		listData.add(mapSize);
		
		
		Map<String, Object> mapGroup = new HashMap<String ,Object>();
		mapGroup.put("key", "group");
		mapGroup.put("value", "组合");
		List<Map<String,Object>> listGroup = new ArrayList<Map<String,Object>>();		
		Map<String, Object> mapGroup1 = new HashMap<String, Object>();
		String[] bArray = {"红色","35码"};  
		mapGroup1.put("key", bArray);
		mapGroup1.put("price", 100.00);
		mapGroup1.put("inventory", 200);
		listGroup.add(mapGroup1);
		Map<String, Object> mapGroup2 = new HashMap<String, Object>();
		String[] bArray2 = {"黄色","35码"}; 
		mapGroup2.put("key", bArray2);
		mapGroup2.put("price", 120.00);
		listGroup.add(mapGroup2);		
		Map<String, Object> mapGroup3 = new HashMap<String, Object>();
		String[] bArray3 = {"黄色","36码"}; 
		mapGroup3.put("key", bArray3);
		mapGroup3.put("price", 120.00);
		listGroup.add(mapGroup3);		
		mapGroup.put("data", listGroup);
		listData.add(mapGroup);
		
		JSONArray obj = JSONArray.fromObject(listData);
		System.out.println(obj.toString());
		
	}
}
