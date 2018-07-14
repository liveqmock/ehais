package org.ehais.enums;

import java.util.HashMap;
import java.util.Map;

public class EOrderClassifyEnum {
	
	private static EOrderClassifyEnum initOCE = null;
	
	public static String shop = "shop";
	public static String dining = "dining";
	public static String sale = "sale";//销售
	public static String sale_back = "sale_back";//销售退货
	public static String purchase = "purchase";//采购
	public static String purchase_back = "purchase_back";//采购退货
	public static String proceeds = "proceeds";//收款
	public static String payment = "payment";//付款
	public static String expense_manage = "expense_manage";//经营费用
	public static String expense_person = "expense_person";//个人费用
	public static String arrearage_customer = "arrearage_customer";//客户未付款
	public static String arrearage_supplier = "arrearage_supplier";//采购未付款
	public static String repayment_customer = "repayment_customer";//客户还款
	public static String repayment_supplier = "repayment_supplier";//还款给供应商
	public static String discounts = "discounts";//优惠
	public static String inventory = "inventory";//库存，盘点
	public static String capital = "capital";//期初资本设置
	
	
	
	
	
	public static Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
	

	public synchronized static EOrderClassifyEnum getOrderClassifyEnum(){
        if(initOCE==null){
        	initOCE = new EOrderClassifyEnum();
        	
        	Map<String,Object> shop_map = new HashMap<String,Object>();
    		shop_map.put("name", "商城");
    		map.put(shop, shop_map);
    		
    		
    		Map<String,Object> dining_map = new HashMap<String,Object>();
    		dining_map.put("name", "自助点餐");
    		map.put(dining, dining_map);
    		
    		
    		Map<String,Object> sale_map = new HashMap<String,Object>();
    		sale_map.put("name", "销售");
    		map.put(sale, sale_map);
    		
    		
    		Map<String,Object> sale_back_map = new HashMap<String,Object>();
    		sale_back_map.put("name", "销售退货");
    		map.put(sale_back, sale_back_map);
    		
    		
    		Map<String,Object> purchase_map = new HashMap<String,Object>();
    		purchase_map.put("name", "采购");
    		map.put(purchase, purchase_map);
    		
    		
    		Map<String,Object> purchase_back_map = new HashMap<String,Object>();
    		purchase_back_map.put("name", "采购退货");
    		map.put(purchase_back, purchase_back_map);
    		
    		
    		Map<String,Object> proceeds_map = new HashMap<String,Object>();
    		proceeds_map.put("name", "收款");
    		map.put(proceeds, proceeds_map);
    		
    		
    		Map<String,Object> payment_map = new HashMap<String,Object>();
    		payment_map.put("name", "付款");
    		map.put(payment, payment_map);
    		
    		
    		Map<String,Object> expense_manage_map = new HashMap<String,Object>();
    		expense_manage_map.put("name", "经营费用");
    		map.put(expense_manage, expense_manage_map);
    		
    		
    		Map<String,Object> expense_person_map = new HashMap<String,Object>();
    		expense_person_map.put("name", "个人费用");
    		map.put(expense_person, expense_person_map);
    		
    		
    		Map<String,Object> arrearage_customer_map = new HashMap<String,Object>();
    		arrearage_customer_map.put("name", "客户欠款");
    		map.put(arrearage_customer, arrearage_customer_map);
    		
    		
    		Map<String,Object> arrearage_supplier_map = new HashMap<String,Object>();
    		arrearage_supplier_map.put("name", "采购未付款");
    		map.put(arrearage_supplier, arrearage_supplier_map);
    		
    		
    		
    		Map<String,Object> repayment_customer_map = new HashMap<String,Object>();
    		repayment_customer_map.put("name", "客户还款");
    		map.put(repayment_customer, repayment_customer_map);
    		
    		
    		
    		Map<String,Object> repayment_supplier_map = new HashMap<String,Object>();
    		repayment_supplier_map.put("name", "还款供应商");
    		map.put(repayment_supplier, repayment_supplier_map);
    		
    		
    		Map<String,Object> discounts_map = new HashMap<String,Object>();
    		discounts_map.put("name", "优惠");
    		map.put(discounts, discounts_map);
    		
    		
    		Map<String,Object> inventory_map = new HashMap<String,Object>();
    		inventory_map.put("name", "盘点");
    		map.put(inventory, inventory_map);
    		
    		
    		Map<String,Object> capital_map = new HashMap<String,Object>();
    		capital_map.put("name", "期初资本");
    		map.put(capital, capital_map);
    		
    		
    		
        }
        return initOCE;
    }


	public static String getOrderClassify(String str){
		return map.get(str).get("name").toString();
	}


	/**
	 * 获取昵称
	 * @param str
	 * @return
	 */
//	public static String getOrderClassify(String str){
//		String ret = "";
//		if(str.equals(shop)){
//			ret = shop;
//		}else if(str.equals(dining)){
//			ret = dining;
//		}else if(str.equals(sale)){
//			ret = "销售";
//		}else if(str.equals(sale_back)){
//			ret = "销售退货";
//		}else if(str.equals(purchase)){
//			ret = "采购";
//		}else if(str.equals(purchase_back)){
//			ret = "采购退货";
//		}else if(str.equals(proceeds)){
//			ret = "收款";
//		}else if(str.equals(payment)){
//			ret = "付款";
//		}
//		return ret;
//	}
	
}
