package org.ehais.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



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
	
	
}
