package org.ehais.enums;

public class EOrderClassifyEnum {
	
	public static String shop = "shop";
	public static String dining = "dining";
	public static String sale = "sale";//销售
	public static String sale_back = "sale_back";//销售退货
	public static String purchase = "purchase";//采购
	public static String purchase_back = "purchase_back";//采购退货
	public static String proceeds = "proceeds";//收款
	public static String payment = "payment";//付款
	
	/**
	 * 获取昵称
	 * @param str
	 * @return
	 */
	public static String getOrderClassify(String str){
		String ret = "";
		if(str.equals(shop)){
			ret = shop;
		}else if(str.equals(dining)){
			ret = dining;
		}else if(str.equals(sale)){
			ret = "销售";
		}else if(str.equals(sale_back)){
			ret = "销售退货";
		}else if(str.equals(purchase)){
			ret = "采购";
		}else if(str.equals(purchase_back)){
			ret = "采购退货";
		}else if(str.equals(proceeds)){
			ret = "收款";
		}else if(str.equals(payment)){
			ret = "付款";
		}
		return ret;
	}
	
}
