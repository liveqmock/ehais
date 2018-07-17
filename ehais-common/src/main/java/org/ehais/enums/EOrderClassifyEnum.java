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
        	
        	
        	initOCE.createMap(shop, "商城");
        	initOCE.createMap(dining, "点餐");
    		initOCE.createMap(sale, "销售");
    		initOCE.createMap(sale_back, "销售退货");
    		initOCE.createMap(purchase, "采购");
    		initOCE.createMap(purchase_back, "采购退货");
    		initOCE.createMap(proceeds, "收款");
    		initOCE.createMap(payment, "付款");
    		initOCE.createMap(expense_manage, "经营费用");
    		initOCE.createMap(expense_person, "个人费用");
    		initOCE.createMap(arrearage_customer, "客户欠款");
    		initOCE.createMap(arrearage_supplier, "采购未付款");
    		initOCE.createMap(repayment_customer, "客户还款");
    		initOCE.createMap(repayment_supplier, "还款供应商");
    		initOCE.createMap(discounts, "优惠");
    		initOCE.createMap(inventory, "盘点");
    		initOCE.createMap(capital, "期初资本");
    		System.out.println("初始化 Order Classify 信息 **********");
        }
        return initOCE;
    }


	public static String getOrderClassify(String str){
		return map.get(str).get("name").toString();
	}

	
	private static void createMap(String module,String Name) {
		Map<String,Object> module_map = new HashMap<String,Object>();
		module_map.put("name", Name);
		map.put(module, module_map);
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
