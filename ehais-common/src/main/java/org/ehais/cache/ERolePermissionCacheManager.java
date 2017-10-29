package org.ehais.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ERolePermissionCacheManager {
	private static ERolePermissionCacheManager instance;
	private static Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
	
	public ERolePermissionCacheManager (){
		System.out.println("ERolePermissionCacheManager....初始化 ");
		
	}
	
	public static ERolePermissionCacheManager getInstance() {
		if (instance == null) {
			instance = new ERolePermissionCacheManager();
		}
		return instance;
	}
	
	public static void putRolePermission(Integer roleId,List<String> permission){
		map.put(roleId,permission);
	}
	
	public static List<String> getRolePermission(Integer roleId) throws Exception{
		return map.get(roleId);
	}
	
	//检验角色是否存在
	public static boolean checkRolePermission(Integer roleId,String url){
		List<String> permission = map.get(roleId);
		if(permission == null || permission.size() == 0)return false;
		for (String string : permission) {
			if(string.equals(url) || string.toUpperCase().equals("ALL"))return true;
		}
		return false;
	}
	
}
