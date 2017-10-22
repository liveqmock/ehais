package org.ehais.enums;

import java.util.Map;
import java.util.TreeMap;

public class EStoreDistributionTypeEnum {

	public static Integer not_join = 0;//不参与
	public static Integer goods_percentage = 1;//商品比例
	public static Integer goods_price = 2;//商品价格
	public static Integer commission = 3;//佣金比例
	
	
	public static Map<String,String> map = new TreeMap<String,String>(){{  
	      put("0","不参与");
	      put("1","商品比例");
	      put("2","商品价格");
	      put("3","佣金比例");
	}};
	
}
