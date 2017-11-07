package org.ehais.Junit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.ehais.util.FSO;
import org.junit.Test;

public class EmpGzcacJUnit {
	public static final String name = "com.mysql.jdbc.Driver";  
	public static final String db = "emp_gzcac_api";
	
	
	public static final String link = "jdbc:mysql://127.0.0.1:3306/"+db;      
    public static final String user = "root";  
    public static final String password = "42016048"; 
    
    public static Connection conn = null;  
    public static PreparedStatement pst = null; 
    public ResultSet ret = null; 
    public String path = "e://empgzcac/";
    
    @Test
    public void loadimg(){
    	try {
			Class.forName(name);//指定连接类型  
	        conn = DriverManager.getConnection(link, user, password);//获取连接  
	        
	        Map<Integer,String> groupMap = new HashMap<Integer,String>();
	        String gsql = "select * from gzcac_event_group ";
        	PreparedStatement ps_group_event = conn.prepareStatement(gsql);
        	ResultSet rs_group_event = ps_group_event.executeQuery();
        	while(rs_group_event.next()){
        		groupMap.put(rs_group_event.getInt("id"), rs_group_event.getString("name"));
        	}
        	
        	//读取学校
	        String sql  = "select * from gzcac_school ";
	        pst = conn.prepareStatement(sql);//准备执行语句  
	        ret = pst.executeQuery();
	        while(ret.next()){
	        	//创建学校文件夹
	        	String folder_school = path + ret.getString("name");
	        	if(!FSO.FolderExists(folder_school))FSO.CreateFolder(folder_school);
	        	//*****读取学校对应的老师
	        	sql = "select * from gzcac_teacher where school_id = "+ret.getInt("id");
	        	PreparedStatement ps_teacher = conn.prepareStatement(sql);
	        	ResultSet rs_teacher = ps_teacher.executeQuery();
	        	while(rs_teacher.next()){
	        		//*****创建学校对应老师的文件夹
	        		String folder_teacher = folder_school+ "/"+rs_teacher.getString("name");
	        		if(!FSO.FolderExists(folder_teacher))FSO.CreateFolder(folder_teacher);
		        	//*****----读取学生
		        	sql = "select * from gzcac_event_player where teacher_id = "+rs_teacher.getInt("id");
		        	PreparedStatement ps_player = conn.prepareStatement(sql);
		        	ResultSet rs_player = ps_player.executeQuery();
		        	while(rs_player.next()){
		        		String folder_group = folder_teacher+"/"+groupMap.get(rs_player.getInt("group_id"));
		        		if(!FSO.FolderExists(folder_group))FSO.CreateFolder(folder_group);
			        	String folder_player = folder_group +"/"+rs_player.getString("name");
			        	if(!FSO.FolderExists(folder_player))FSO.CreateFolder(folder_player);
			        	
			        	sql = "select * from gzcac_event_player_work where player_id = "+rs_player.getInt("id")+" order by id desc limit 0,1";
			        	PreparedStatement ps_player_work = conn.prepareStatement(sql);
			        	ResultSet rs_player_work = ps_player_work.executeQuery();
			        	
		        	}
		        	
		        	
	        	}
	        }
//	        
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
