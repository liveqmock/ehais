package org.ehais.Junit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class LingNansJUnit {
	
	public static final String name = "com.mysql.jdbc.Driver";  
	public static final String db1 = "12345678";
	public static final String db2 = "sqlehaismall";
	
	
	public static final String link1 = "jdbc:mysql://127.0.0.1:3306/"+db1+"?characterEncoding=UTF-8";      
	public static final String link2 = "jdbc:mysql://127.0.0.1:3306/"+db2+"?characterEncoding=UTF-8";      
    public static final String user = "root";  
    public static final String password = "42016048"; 
    
    public static Connection conn1 = null;  
    public static Connection conn2 = null;  
    public static PreparedStatement pst1 = null; 
    public static PreparedStatement pst2 = null; 
    public static Statement statement1 = null;
    public static Statement statement2 = null;
    public ResultSet ret1 = null;  
    public ResultSet ret2 = null;  
    public String br = "\r\n";
    
	@Test
	public void TreeClass(){
		try{
			Class.forName(name);//指定连接类型  
	        conn1 = DriverManager.getConnection(link1, user, password);//获取连接  
	        conn2 = DriverManager.getConnection(link2, user, password);//获取连接  
	        String sql = "select * from treeclass order by ParentId asc";
	        pst1 = conn1.prepareStatement(sql);//准备执行语句  
	        ret1 = pst1.executeQuery();
	        
	        List<Map<String, Object>> listTree = new ArrayList<Map<String, Object>>();
	        while(ret1.next()){
	        	Map<String,Object> map = new HashMap<String,Object>();
	        	map.put("Id", ret1.getInt("Id"));
	        	map.put("ParentId", ret1.getInt("ParentId"));
	        	map.put("ClassName", ret1.getString("ClassName"));
	        	listTree.add(map);
	        }
	        Statement statement = conn2.createStatement();
	        
	        for (Map<String, Object> map : listTree) {
				if(map.get("ParentId").toString().equals("0")){
					String install = this.installArticleCat(map, 0);
					
					statement.executeUpdate(install);
					
					sql = "select * from hai_article_cat order by cat_id desc limit 0,1";
					
					ResultSet ret2 = statement.executeQuery(sql);
					if(ret2.next()){
						Integer parentId = ret2.getInt("cat_id");
						
						for (Map<String, Object> map2 : listTree) {
							if(map.get("Id").toString().equals(map2.get("ParentId").toString())){
								install = this.installArticleCat(map2, parentId);
								statement.executeUpdate(install);
							}
						}
					}
					
				}
			}
	        
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String installArticleCat(Map<String,Object> map,Integer parentId){
		String sql = "insert into hai_article_cat (cat_name,cat_type,module,store_id,parent_id,show_in_nav) values ('"+map.get("ClassName").toString()+"',"+map.get("Id")+",'article',87,"+parentId+","+map.get("ParentId")+") ";
		System.out.println(sql);
		return sql ;
	}
	
	@Test
	public void Action(){
		try{
			Class.forName(name);//指定连接类型  
	        conn1 = DriverManager.getConnection(link1, user, password);//获取连接  
	        conn2 = DriverManager.getConnection(link2, user, password);//获取连接  
	        String sql1 = "select * from actions";
	        String sql2 = "select * from hai_article_cat where store_id = 87";
	        statement1 = conn1.createStatement();
	        statement2 = conn2.createStatement();
	        
	        ret1 = statement1.executeQuery(sql1);
	        ret2 = statement2.executeQuery(sql2);
	        Map<Integer,Integer> mapCat = new HashMap<Integer,Integer>();
	        while(ret2.next()){
	        	mapCat.put(ret2.getInt("cat_type"), ret2.getInt("cat_id"));
	        }
	        
	        while(ret1.next()){
	        	String install = "insert into hai_article (cat_id,title,content,read_count,create_date,module,store_id) values (?,?,?,?,?,?,?) ";
	        	PreparedStatement pstmt = (PreparedStatement) conn2.prepareStatement(install);
	        	 pstmt.setInt(1, Integer.valueOf(mapCat.get(ret1.getInt("ClassId")).toString()));
	             pstmt.setString(2, ret1.getString("Title"));
	             pstmt.setString(3, ret1.getString("Content"));
	             pstmt.setInt(4, ret1.getInt("Hits"));
	             pstmt.setString(5, ret1.getString("AddTime"));
	             pstmt.setString(6, "action");
	             pstmt.setInt(7, 87);
	             pstmt.executeUpdate();
	             pstmt.close();
	        }
	        
	        
		}catch(Exception e){
			
		}		
	}
	
	
	@Test
	public void News(){
		try{
			Class.forName(name);//指定连接类型  
	        conn1 = DriverManager.getConnection(link1, user, password);//获取连接  
	        conn2 = DriverManager.getConnection(link2, user, password);//获取连接  
	        String sql1 = "select * from news";
	        String sql2 = "select * from hai_article_cat where store_id = 87";
	        statement1 = conn1.createStatement();
	        statement2 = conn2.createStatement();
	        
	        ret1 = statement1.executeQuery(sql1);
	        ret2 = statement2.executeQuery(sql2);
	        Map<Integer,Integer> mapCat = new HashMap<Integer,Integer>();
	        while(ret2.next()){
	        	mapCat.put(ret2.getInt("cat_type"), ret2.getInt("cat_id"));
	        }
	        
	        while(ret1.next()){
	        	String install = "insert into hai_article (cat_id,title,content,read_count,create_date,module,store_id,article_thumb) values (?,?,?,?,?,?,?,?) ";
	        	PreparedStatement pstmt = (PreparedStatement) conn2.prepareStatement(install);
	        	 pstmt.setInt(1, Integer.valueOf(mapCat.get(ret1.getInt("ClassId")).toString()));
	             pstmt.setString(2, ret1.getString("Title"));
	             pstmt.setString(3, ret1.getString("Content"));
	             pstmt.setInt(4, ret1.getInt("Hits"));
	             pstmt.setString(5, ret1.getString("AddTime"));
	             pstmt.setString(6, "news");
	             pstmt.setInt(7, 87);
	             pstmt.setString(8, ret1.getString("Pic"));
	             pstmt.executeUpdate();
	             pstmt.close();
	        }
	        
	        
		}catch(Exception e){
			
		}		
	}
	
	
	//展览
	@Test
	public void ExHibit(){
		try{
			Class.forName(name);//指定连接类型  
	        conn1 = DriverManager.getConnection(link1, user, password);//获取连接  
	        conn2 = DriverManager.getConnection(link2, user, password);//获取连接  
	        String sql1 = "select * from exhibit";
	        String sql2 = "select * from hai_article_cat where store_id = 87";
	        statement1 = conn1.createStatement();
	        statement2 = conn2.createStatement();
	        
	        ret1 = statement1.executeQuery(sql1);
	        ret2 = statement2.executeQuery(sql2);
	        Map<Integer,Integer> mapCat = new HashMap<Integer,Integer>();
	        while(ret2.next()){
	        	mapCat.put(ret2.getInt("cat_type"), ret2.getInt("cat_id"));
	        }
	        
	        while(ret1.next()){
	        	String install = "insert into hai_article (cat_id,title,article_thumb,start_publish_date,end_publish_date,article_label,article_source,create_date,module,store_id,content) values (?,?,?,?,?,?,?,?,?,?,?) ";
	        	PreparedStatement pstmt = (PreparedStatement) conn2.prepareStatement(install);
	        	 pstmt.setInt(1, Integer.valueOf(mapCat.get(ret1.getInt("ClassId")).toString()));
	             pstmt.setString(2, ret1.getString("Title"));
	             pstmt.setString(3, ret1.getString("Pic"));
	             pstmt.setString(4, ret1.getString("StartTime"));
	             pstmt.setString(5, ret1.getString("EndTime"));
	             pstmt.setString(6, ret1.getString("Sponsor"));//主办单位
	             pstmt.setString(7, ret1.getString("YardPlan"));//场地
	             pstmt.setString(8, ret1.getString("AddTime"));
	             pstmt.setString(9, "exhibit");
	             pstmt.setInt(10, 87);
	             pstmt.setString(11, "<h2 class='label'>展览信息</h2>"+ret1.getString("Content")+"<h2 class='label'>展览序言</h2>"+ret1.getString("Preface")+"<h2 class='label'>画家介绍</h2>"+ret1.getString("Painter")+"<h2 class='label'>展出作品</h2>"+ret1.getString("Workpiece"));
	             
	             pstmt.executeUpdate();
	             pstmt.close();
	        }
	        
	        
		}catch(Exception e){
			
		}		
	}
	
	
	@Test
	public void Author(){
		try{
			Class.forName(name);//指定连接类型  
	        conn1 = DriverManager.getConnection(link1, user, password);//获取连接  
	        conn2 = DriverManager.getConnection(link2, user, password);//获取连接  
	        String sql1 = "select * from author";
	        String sql2 = "select * from hai_article_cat where store_id = 87";
	        statement1 = conn1.createStatement();
	        statement2 = conn2.createStatement();
	        
	        ret1 = statement1.executeQuery(sql1);
	        ret2 = statement2.executeQuery(sql2);
	        Map<Integer,Integer> mapCat = new HashMap<Integer,Integer>();
	        while(ret2.next()){
	        	mapCat.put(ret2.getInt("cat_type"), ret2.getInt("cat_id"));
	        }
	        
	        while(ret1.next()){
	        	String install = "insert into hai_article (cat_id,title,content,keywords,create_date,module,store_id,article_thumb) values (?,?,?,?,?,?,?,?) ";
	        	PreparedStatement pstmt = (PreparedStatement) conn2.prepareStatement(install);
	        	 pstmt.setInt(1, Integer.valueOf(mapCat.get(ret1.getInt("ClassId")).toString()));
	             pstmt.setString(2, ret1.getString("Title"));
	             pstmt.setString(3, ret1.getString("Content"));
	             pstmt.setString(4, ret1.getString("Birth")+"-"+ret1.getString("PassAway"));
	             pstmt.setString(5, ret1.getString("AddTime"));
	             pstmt.setString(6, "author");
	             pstmt.setInt(7, 87);
	             pstmt.setString(8, ret1.getString("Pic"));
	             pstmt.executeUpdate();
	             pstmt.close();
	        }
	        
	        
		}catch(Exception e){
			
		}		
	}
	
	

	@Test
	public void Book(){
		try{
			Class.forName(name);//指定连接类型  
	        conn1 = DriverManager.getConnection(link1, user, password);//获取连接  
	        conn2 = DriverManager.getConnection(link2, user, password);//获取连接  
	        String sql1 = "select * from book";
	        String sql2 = "select * from hai_article_cat where store_id = 87";
	        statement1 = conn1.createStatement();
	        statement2 = conn2.createStatement();
	        
	        ret1 = statement1.executeQuery(sql1);
	        ret2 = statement2.executeQuery(sql2);
	        Map<Integer,Integer> mapCat = new HashMap<Integer,Integer>();
	        while(ret2.next()){
	        	mapCat.put(ret2.getInt("cat_type"), ret2.getInt("cat_id"));
	        }
	        
	        while(ret1.next()){
	        	String install = "insert into hai_article (cat_id,title,content,read_count,create_date,module,store_id,article_thumb,author,keywords,description,article_label) values (?,?,?,?,?,?,?,?,?,?,?,?) ";
	        	PreparedStatement pstmt = (PreparedStatement) conn2.prepareStatement(install);
	        	 pstmt.setInt(1, Integer.valueOf(mapCat.get(ret1.getInt("ClassId")).toString()));
	             pstmt.setString(2, ret1.getString("Title"));
	             pstmt.setString(3, ret1.getString("Content"));
	             pstmt.setInt(4, ret1.getInt("Hits"));
	             pstmt.setString(5, ret1.getString("AddTime"));
	             pstmt.setString(6, "book");
	             pstmt.setInt(7, 87);
	             pstmt.setString(8, ret1.getString("Pic"));
	             pstmt.setString(9, ret1.getString("Edit"));
	             pstmt.setString(10, ret1.getString("Publisher"));
	             pstmt.setString(11, ret1.getString("PublishYeah"));
	             pstmt.setString(12, ret1.getString("PublishAdd"));
	             pstmt.executeUpdate();
	             pstmt.close();
	        }
	        
	        
		}catch(Exception e){
			
		}		
	}
	
	

}
