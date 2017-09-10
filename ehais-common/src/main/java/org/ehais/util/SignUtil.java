package org.ehais.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;



public class SignUtil {


    
    public static String getSign(Map<String,Object> map,String secret) throws UnsupportedEncodingException{
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + secret;
//        System.out.println(result);
        System.out.println("Sign Before MD5:" + result);
        result = EncryptUtils.md5(result).toUpperCase();
        System.out.println("Sign Result:" + result);
        return result;
    }
	
    
    /**
     * 自定义的排序map后进行签名
     * @param map
     * @param secret
     * @return
     * @throws UnsupportedEncodingException 
     */
	public static String getSignWS(Map<String,String> map,String secret) throws UnsupportedEncodingException{
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,String> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey());
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(map.get(arrayToSort[i])!=null?map.get(arrayToSort[i]):"");
        }
        sb.append(secret);
        String result = sb.toString();
//        result += "key=" + Configure.getKey();
//        System.out.println(result);
        System.out.println("Sign Before MD5:" + result);
        result = EncryptUtils.md5(result).toUpperCase();
        System.out.println("Sign Result:" + result);
        return result;
    }
	
	
	
	/**
	 * 适用软文加密
	 * 加密设置
	 * 0-5+商家ID
	 * 6-10+代理ID
	 * 11-15+上级分销人ID
	 * 16-20+自己或分销的人ID
	 * 21-25+文章ID
	 * 26-30+商品ID
	 * 31-32原字符串
	 * @param agencyId
	 * @param articleId
	 * @param userId
	 * @param goodsId
	 * @param store_id
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public static String setSid(Integer store_id,Integer agencyId,Long parentId,Long userId,Integer articleId,Long goodsId,String secret) throws Exception{
		String md5 = EncryptUtils.md5(store_id.toString()+agencyId.toString()+parentId.toString()+userId.toString()+articleId.toString()+goodsId.toString()+secret);
		String sid = md5.substring(0, 5)+store_id.toString()+"0-0"+
				md5.substring(5,10)+agencyId.toString()+"1-1"+
				md5.substring(10,15)+parentId.toString()+"2-2"+
				md5.substring(15,20)+userId.toString()+"3-3"+
				md5.substring(20,25)+articleId.toString()+"4-4"+
				md5.substring(25,30)+goodsId.toString()+"5-5"+
				md5.substring(30,32);
		return sid;
	}
	
		
	/**
	 * 适用软文加密
	 * 解析参数
	 * @param sid
	 * @return
	 */
	public static Map<String,Object> getSid(String sid,String secret){
		if(StringUtils.isEmpty(sid))return null;
		Map<String,Object> map = null;
		try{
			Integer n0 = sid.indexOf("0-0");
			Integer n1 = sid.indexOf("1-1");
			Integer n2 = sid.indexOf("2-2");
			Integer n3 = sid.indexOf("3-3");
			Integer n4 = sid.indexOf("4-4");
			Integer n5 = sid.indexOf("5-5");
			
			String s0 = sid.substring(0, 5);
			String s_store_id = sid.substring(5,n0);
			
			String s1 = sid.substring(n0+3,n0+8);
			String s_agencyId = sid.substring(n0+8, n1);
			
			String s2 = sid.substring(n1+3, n1+8);
			String s_parentId = sid.substring(n1+8, n2);
			
			String s3 = sid.substring(n2+3,n2+8);
			String s_userId = sid.substring(n2+8, n3);
			
			String s4 = sid.substring(n3+3,n3+8);
			String s_articleId = sid.substring(n3+8, n4);
			
			String s5 = sid.substring(n4+3,n4+8);
			String s_goodsId = sid.substring(n4+8, n5);
			
			String s6 = sid.substring(n5+3,n5+5);
			
			if(EncryptUtils.md5(s_store_id+s_agencyId+s_parentId+s_userId+s_articleId+s_goodsId+secret).equals(s0+s1+s2+s3+s4+s5+s6)){
				map = new HashMap<String,Object>();
				map.put("store_id", s_store_id);
				map.put("agencyId", s_agencyId);
				map.put("parentId", s_parentId);
				map.put("userId", s_userId);
				map.put("articleId", s_articleId);				
				map.put("goodsId", s_goodsId);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return map;
	}
	
	/**
	 * 获取商户编号，适用 所有
	 * @param sid
	 * @return
	 */
	public static Integer getUriStoreId(String sid){
		Integer n0 = sid.indexOf("0-0");
		if(n0 < 6)return 0;
		String s_store_id = sid.substring(5,n0);
		if(StringUtils.isNotEmpty(s_store_id)){
			return Integer.valueOf(s_store_id);
		}
		return 0;
	}
	
	/**
	 * 订单详情查看算法
	 * @param store_id
	 * @param orderId
	 * @param orderSn
	 * @param userId
	 * @param openId
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static String setOid(Integer store_id,Long orderId,String orderSn,Long userId,String openId,String secret) throws Exception{
		String md5 = EncryptUtils.md5(store_id.toString()+orderId.toString()+orderSn+userId.toString()+openId+secret);
		String sid = md5.substring(0, 5)+store_id.toString()+"0-0"+
				md5.substring(5,10)+orderId.toString()+"1-1"+
				md5.substring(10,15)+orderSn.toString()+"2-2"+
				md5.substring(15,20)+userId.toString()+"3-3"+
				md5.substring(20,25)+openId.toString()+"4-4"+
				md5.substring(25,32);
		return sid;
	}
	
	/**
	 * 
	 * @param oid
	 * @param secret
	 * @return
	 */
	public static Map<String,Object> getOid(String oid,String secret){
		if(StringUtils.isEmpty(oid))return null;
		Map<String,Object> map = null;
		try{
			Integer n0 = oid.indexOf("0-0");
			Integer n1 = oid.indexOf("1-1");
			Integer n2 = oid.indexOf("2-2");
			Integer n3 = oid.indexOf("3-3");
			Integer n4 = oid.indexOf("4-4");
			
			String s0 = oid.substring(0, 5);
			String store_id = oid.substring(5,n0);
			
			String s1 = oid.substring(n0+3,n0+8);
			String orderId = oid.substring(n0+8, n1);
			
			String s2 = oid.substring(n1+3, n1+8);
			String orderSn = oid.substring(n1+8, n2);
			
			String s3 = oid.substring(n2+3,n2+8);
			String userId = oid.substring(n2+8, n3);
			
			String s4 = oid.substring(n3+3,n3+8);
			String openId = oid.substring(n3+8, n4);
			
			String s5 = oid.substring(n4+3,n4+10);
			
			if(EncryptUtils.md5(store_id+orderId+orderSn+userId+openId+secret).equals(s0+s1+s2+s3+s4+s5)){
				map = new HashMap<String,Object>();
				map.put("store_id", store_id);
				map.put("orderId", orderId);
				map.put("orderSn", orderSn);
				map.put("userId", userId);
				map.put("openId", openId);				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return map;
	}
	
	
	/**
	 * 适用通用型商家模式
	 * @param store_id
	 * @param agencyId
	 * @param parentId
	 * @param userId
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static String setCid(Integer store_id,Integer agencyId,Long parentId,Long userId,String secret) throws Exception{
		String md5 = EncryptUtils.md5(store_id.toString()+agencyId.toString()+parentId.toString()+userId.toString()+secret);
		String did = md5.substring(0, 5)+store_id.toString()+"0-0"+
				md5.substring(5,10)+agencyId.toString()+"1-1"+
				md5.substring(10,15)+parentId.toString()+"2-2"+
				md5.substring(15,20)+userId.toString()+"3-3"+
				md5.substring(20,32);
		return did;
	}
	
	public static Map<String,Object> getCid(String did,String secret){
		if(StringUtils.isEmpty(did))return null;
		Map<String,Object> map = null;
		try{
			Integer n0 = did.indexOf("0-0");
			Integer n1 = did.indexOf("1-1");
			Integer n2 = did.indexOf("2-2");
			Integer n3 = did.indexOf("3-3");
			
			String s0 = did.substring(0, 5);
			String s_store_id = did.substring(5,n0);
			
			String s1 = did.substring(n0+3,n0+8);
			String s_agencyId = did.substring(n0+8, n1);
			
			String s2 = did.substring(n1+3, n1+8);
			String s_parentId = did.substring(n1+8, n2);
			
			String s3 = did.substring(n2+3,n2+8);
			String s_userId = did.substring(n2+8, n3);
			
			String s4 = did.substring(n3+3,n3+15);
			
			if(EncryptUtils.md5(s_store_id+s_agencyId+s_parentId+s_userId+secret).equals(s0+s1+s2+s3+s4)){
				map = new HashMap<String,Object>();
				map.put("store_id", s_store_id);
				map.put("agencyId", s_agencyId);
				map.put("parentId", s_parentId);
				map.put("userId", s_userId);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return map;
	}
	
	
	/**
	 * 适应点餐模式
	 * @param store_id
	 * @param agencyId
	 * @param parentId
	 * @param userId
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static String setDiningId(Integer store_id,
			Integer agencyId,
			Long parentId,
			Long userId,
			String tableNo,
			String secret) throws Exception{
		String md5 = EncryptUtils.md5(store_id.toString()+agencyId.toString()+parentId.toString()+userId.toString()+tableNo+secret);
		String did = md5.substring(0, 5)+store_id.toString()+"0-0"+
				md5.substring(5,10)+agencyId.toString()+"1-1"+
				md5.substring(10,15)+parentId.toString()+"2-2"+
				md5.substring(15,20)+userId.toString()+"3-3"+
				md5.substring(20,25)+tableNo+"4-4"+
				md5.substring(25,32);
		return did;
	}
	
	
	public static Map<String,Object> getDiningId(String did,String secret){
		if(StringUtils.isEmpty(did))return null;
		Map<String,Object> map = null;
		try{
			Integer n0 = did.indexOf("0-0");
			Integer n1 = did.indexOf("1-1");
			Integer n2 = did.indexOf("2-2");
			Integer n3 = did.indexOf("3-3");
			Integer n4 = did.indexOf("4-4");
			
			String s0 = did.substring(0, 5);
			String s_store_id = did.substring(5,n0);
			
			String s1 = did.substring(n0+3,n0+8);
			String s_agencyId = did.substring(n0+8, n1);
			
			String s2 = did.substring(n1+3, n1+8);
			String s_parentId = did.substring(n1+8, n2);
			
			String s3 = did.substring(n2+3,n2+8);
			String s_userId = did.substring(n2+8, n3);
			
			String s4 = did.substring(n3+3,n3+8);
			String s_tableNo = did.substring(n3+8, n4);
			
			String s5 = did.substring(n4+3,n4+10);
			if(EncryptUtils.md5(s_store_id+s_agencyId+s_parentId+s_userId+s_tableNo+secret).equals(s0+s1+s2+s3+s4+s5)){
				map = new HashMap<String,Object>();
				map.put("store_id", s_store_id);
				map.put("agencyId", s_agencyId);
				map.put("parentId", s_parentId);
				map.put("userId", s_userId);
				map.put("tableNo", s_tableNo);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return map;
	}
	
	
	
	public static String setPartnerId(
			Integer partnerId,
			Long parentId,
			Long userId,
			String secret) throws Exception{
		String md5 = EncryptUtils.md5(partnerId.toString()+parentId.toString()+userId.toString()+secret);
		String pid = md5.substring(0, 5)+partnerId.toString()+"0-0"+
				md5.substring(5,10)+parentId.toString()+"1-1"+
				md5.substring(10,15)+userId.toString()+"2-2"+
				md5.substring(15,32);
		return pid;
	}
	
	public static Map<String,Object> getPartnerId(String pid,String secret){
		if(StringUtils.isEmpty(pid))return null;
		Map<String,Object> map = null;
		try{
			Integer n0 = pid.indexOf("0-0");
			Integer n1 = pid.indexOf("1-1");
			Integer n2 = pid.indexOf("2-2");
			
			String s0 = pid.substring(0, 5);
			String partnerId = pid.substring(5,n0);
			
			String s1 = pid.substring(n0+3,n0+8);
			String parentId = pid.substring(n0+8, n1);
			
			String s2 = pid.substring(n1+3, n1+8);
			String userId = pid.substring(n1+8, n2);
			
			String s3 = pid.substring(n2+3,n2+20);
			if(EncryptUtils.md5(partnerId+parentId+userId+secret).equals(s0+s1+s2+s3)){
				map = new HashMap<String,Object>();
				map.put("partnerId", partnerId);
				map.put("parentId", parentId);
				map.put("userId", userId);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return map;
	}
	
	public static void main(String[] args) throws Exception {
		Integer store_id = 20;
		Long orderId = 23l;
		String orderSn = "201709061320282629125";
		Long userId = 125l;
		String openId = "sdfsrewrsdfsdfsdf";
		String secret = "ehais_wxdev";
		Integer agencyId = 0;
		Long parentId = 30L;
		String tableNo = "C10";
		Integer articleId = 9086;
		Long goodsId = 2381L;
		Integer partnerId = 10;
		
//		String sid = SignUtil.setSid(store_id, agencyId, parentId, userId, articleId, goodsId, secret);
//		System.out.println(sid);
//		String did = SignUtil.setDiningId(store_id, agencyId, parentId, userId,tableNo, secret);
//		System.out.println(did);
//		Map<String,Object> mid = SignUtil.getDiningId(did, secret);
//		Bean2Utils.printMap(mid);
		
		String pid = SignUtil.setPartnerId(partnerId, parentId, userId, secret);
		System.out.println(pid);
		Map<String,Object> mid = SignUtil.getPartnerId(pid, secret);
		Bean2Utils.printMap(mid);
		
	}
	
}
