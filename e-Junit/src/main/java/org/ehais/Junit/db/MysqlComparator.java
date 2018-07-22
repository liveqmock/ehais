package org.ehais.Junit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class MysqlComparator {

	//本地对阿里
	public static String url1 = "jdbc:mysql://127.0.0.1:3306/sqlehaismall";  
    public static String url2 = "jdbc:mysql://39.108.129.44:3306/sqlehaismall";  
    public static String USERNAME1 = "root";  
    public static String PASSWORD1 = "Ehais42016048ok!";  
    public static String USERNAME2 = "lgj628";  
    public static String PASSWORD2 = "Ehais42016048ok!";
    public static String db1 = "sqlehaismall";
    public static String db2 = "sqlehaismall";
    
    
    //本地对财务
//	public static String url1 = "jdbc:mysql://127.0.0.1:3306/sqlehaismall";  
//    public static String url2 = "jdbc:mysql://183.240.153.79:3306/finance";  
//    public static String USERNAME1 = "root";  
//    public static String PASSWORD1 = "Ehais42016048ok!";  
//    public static String USERNAME2 = "lgj628";  
//    public static String PASSWORD2 = "Ehais42016048ok!";
//    public static String db1 = "sqlehaismall";
//    public static String db2 = "finance";
    
    
    
    
//    public static String url1 = "jdbc:mysql://183.240.153.79:3306/finance";  
//    public static String url2 = "jdbc:mysql://127.0.0.1:3306/sqlehaismall";  
//    public static String USERNAME1 = "lgj628";  
//    public static String PASSWORD1 = "Ehais42016048ok!";  
//    public static String USERNAME2 = "root";  
//    public static String PASSWORD2 = "Ehais42016048ok!";
//    public static String db1 = "finance";
//    public static String db2 = "sqlehaismall";
    
    
    public static void main(String[] args) throws Exception{   
    	MysqlComparator com = new MysqlComparator();  
        Connection con1 = com.getConnection(url1,USERNAME1,PASSWORD1);  
        Connection con2 = com.getConnection(url2,USERNAME2,PASSWORD2);  
        System.out.println("已连接到两个数据库...将以数据库1为主数据库进行比较");  
        String sql = "select * from information_schema.tables where table_schema='"+db1+"' and table_type='base table';";
        System.out.println(sql);
        List list1 = com.Rs2List(com.getRsBySQL(sql, con1));  
        
        sql = "select * from information_schema.tables where table_schema='"+db2+"' and table_type='base table';";
        System.out.println(sql);
        List list2 = com.Rs2List(com.getRsBySQL(sql, con2));  
        com.compare(list1, list2,con1,con2);  
        System.out.println("比较完成....");  
    }  
    private void compare(List list1,List list2,Connection con1,Connection con2) throws Exception {  
        for (Iterator iterator = list1.iterator(); iterator.hasNext();) {  
            String name = (String) iterator.next();  
            if(list2.contains(name)){  
                this.TableCompare(name, con1, con2);  
            }else{  
                System.out.println("数据库2中，缺少表:"+name);  
            }  
        }  
    }  
    private void TableCompare(String name,Connection con1,Connection con2) throws Exception { 
    	
        String sql = "SELECT column_name,data_type,character_maximum_length,numeric_precision,numeric_scale,column_key,is_nullable,CASE WHEN extra = 'auto_increment' THEN 1 ELSE 0 END AS 'auto_increment',column_default ,column_comment  FROM Information_schema.columns WHERE table_Name='"+name+"' and table_schema='"+db1+"' ;";  
        Map<String,String> map1 = this.parseColumnList(this.getRsBySQL(sql, con1));  
        
        sql = "SELECT column_name,data_type,character_maximum_length,numeric_precision,numeric_scale,column_key,is_nullable,CASE WHEN extra = 'auto_increment' THEN 1 ELSE 0 END AS 'auto_increment',column_default ,column_comment  FROM Information_schema.columns WHERE table_Name='"+name+"' and table_schema='"+db2+"' ;";
        Map<String,String> map2 = this.parseColumnList(this.getRsBySQL(sql, con2));
        
        Set set = map1.keySet();  
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {  
            String cname = (String) iterator.next(); 
//            System.out.println(cname);
            if(map2.containsKey(cname)){  
                if(!map2.get(cname).equals(map1.get(cname))){  
                	if(cname.indexOf("_data_type") < 0) {
                		System.out.println("数据库2的 "+name+" 表中的字段:"+cname.replaceAll("_data_type", "")+" 与数据库1中数据类型不一致");
                	}else {
                		System.out.println("数据库2的 "+name+" 表中的字段长度:"+cname.replaceAll("_data_type", "")+" 与数据库1中数据类型长度不一致");
                	}                      
                } 
                
            }else{  
                System.out.println("数据库2的 "+name+" 表中，缺少字段:"+cname.replaceAll("_data_type", ""));  
            }  
        }  
    } 
	
    private Map<String,String> parseColumnList(ResultSet rs1) throws Exception {  
        Map<String,String> map = new HashMap<String,String>();  
        while(rs1.next()){  
            map.put(rs1.getString("column_name"), rs1.getString("data_type"));
            
            String str = StringUtils.isNotBlank(rs1.getString("character_maximum_length"))?
            		rs1.getString("character_maximum_length"):
            			(StringUtils.isNotBlank(rs1.getString("numeric_precision"))?rs1.getString("numeric_precision"):"0");
            	
            map.put(rs1.getString("column_name")+"_data_type", str)  ;
            	
        }  
        return map;  
    }  
    private List Rs2List(ResultSet rs1)throws Exception{  
        List list = new ArrayList();  
        while(rs1.next()){  
            list.add(rs1.getString("TABLE_NAME"));  
        }  
        return list;  
    }  
    private ResultSet getRsBySQL(String sql,Connection con1)throws Exception{  
        Statement stmt = con1.createStatement();  
        return stmt.executeQuery(sql);  
    }  
      
    public static String DRIVER = "com.mysql.jdbc.Driver";  
    static {  
        try {  
            Class.forName(DRIVER).newInstance();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    private Connection getConnection(String url,String username,String password){  
        try {  
            return DriverManager.getConnection(url,username,password);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
}

class MysqlTableFields{
	private String column_name;
	private String data_type;
	private Integer character_maximum_length;
	private Integer numeric_precision;
	private Integer numeric_scale;
	private String column_key;
	private String is_nullable;
	private Integer auto_increment;
	private Integer column_default;
	private String column_comment;
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public Integer getCharacter_maximum_length() {
		return character_maximum_length;
	}
	public void setCharacter_maximum_length(Integer character_maximum_length) {
		this.character_maximum_length = character_maximum_length;
	}
	public Integer getNumeric_precision() {
		return numeric_precision;
	}
	public void setNumeric_precision(Integer numeric_precision) {
		this.numeric_precision = numeric_precision;
	}
	public Integer getNumeric_scale() {
		return numeric_scale;
	}
	public void setNumeric_scale(Integer numeric_scale) {
		this.numeric_scale = numeric_scale;
	}
	public String getColumn_key() {
		return column_key;
	}
	public void setColumn_key(String column_key) {
		this.column_key = column_key;
	}
	public String getIs_nullable() {
		return is_nullable;
	}
	public void setIs_nullable(String is_nullable) {
		this.is_nullable = is_nullable;
	}
	public Integer getAuto_increment() {
		return auto_increment;
	}
	public void setAuto_increment(Integer auto_increment) {
		this.auto_increment = auto_increment;
	}
	public Integer getColumn_default() {
		return column_default;
	}
	public void setColumn_default(Integer column_default) {
		this.column_default = column_default;
	}
	public String getColumn_comment() {
		return column_comment;
	}
	public void setColumn_comment(String column_comment) {
		this.column_comment = column_comment;
	}
	
	
}
