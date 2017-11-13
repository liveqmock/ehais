package org.ehais.Junit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
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
    public String path = "e:/empgzcac/";
    
    @Test
    public void readTxt(){
    	String path_txt = path+"txt.txt";
    	try{
    		File file = new File(path_txt);
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                
                String tempString_new = tempString.replaceAll("empgzcac", "empgzcacNew");
                FSO.moveFolder(tempString, tempString_new);
                System.out.println("line " + line + ": " + tempString+"/"+tempString_new);
                line++;
            }
            reader.close();
            
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
//    @Test
    public void loadimg(){
    	try {
			Class.forName(name);//指定连接类型  
	        conn = DriverManager.getConnection(link, user, password);//获取连接  
	        
	        Map<Integer,String> groupMap = new HashMap<Integer,String>();
	        String gsql = "select * from gzcac_event_group ";
        	PreparedStatement ps_group_event = conn.prepareStatement(gsql);
        	ResultSet rs_group_event = ps_group_event.executeQuery();
        	while(rs_group_event.next()){
        		groupMap.put(rs_group_event.getInt("id"), rs_group_event.getString("name").trim());
        	}
        	
        	//读取学校
	        String sql  = "select * from gzcac_school ";
	        pst = conn.prepareStatement(sql);//准备执行语句  
	        ret = pst.executeQuery();
	        while(ret.next()){
	        	//创建学校文件夹
	        	String folder_school = path + ret.getString("name").trim();
	        	if(!FSO.FolderExists(folder_school))FSO.CreateFolder(folder_school);
	        	//*****读取学校对应的老师
	        	sql = "select * from gzcac_teacher where school_id = "+ret.getInt("id");
	        	PreparedStatement ps_teacher = conn.prepareStatement(sql);
	        	ResultSet rs_teacher = ps_teacher.executeQuery();
	        	while(rs_teacher.next()){
	        		//*****创建学校对应老师的文件夹
	        		String folder_teacher = folder_school+ "/"+rs_teacher.getString("name").trim();
	        		if(!FSO.FolderExists(folder_teacher))FSO.CreateFolder(folder_teacher);
		        	//*****----读取学生
		        	sql = "select * from gzcac_event_player where teacher_id = "+rs_teacher.getInt("id");
		        	PreparedStatement ps_player = conn.prepareStatement(sql);
		        	ResultSet rs_player = ps_player.executeQuery();
		        	while(rs_player.next()){
		        		String folder_group = folder_teacher+"/"+groupMap.get(rs_player.getInt("group_id"));
		        		if(!FSO.FolderExists(folder_group))FSO.CreateFolder(folder_group);
			        	String folder_player = folder_group +"/"+rs_player.getString("name").trim();
			        	if(!FSO.FolderExists(folder_player))FSO.CreateFolder(folder_player);
			        	System.out.println(folder_player);
			        	sql = "select * from gzcac_event_player_work where player_id = "+rs_player.getInt("id")+" order by id desc limit 0,1";
			        	PreparedStatement ps_player_work = conn.prepareStatement(sql);
			        	ResultSet rs_player_work = ps_player_work.executeQuery();
			        	while(rs_player_work.next()){
			        		String txt = folder_player+"/"+rs_player_work.getString("name").trim()+".txt";
			        		if(!FSO.TextFileExists(txt))FSO.CreateTextFile(txt);
			        		FSO.WriteTextFileZh(txt, rs_player_work.getString("description"));
			        		
			        		sql = "select * from gzcac_event_player_work_attachment where work_id = "+rs_player_work.getInt("id")+"  order by id desc limit 0,1 ";
			        		PreparedStatement ps_player_work_attachment = conn.prepareStatement(sql);
				        	ResultSet rs_player_work_attachment = ps_player_work_attachment.executeQuery();
				        	if(rs_player_work_attachment.next()){
				        		String url = rs_player_work_attachment.getString("url");
				        		EHttpClientUtil.downloadFile(url, folder_player+"/作品.jpg");
				        	}
				        	
			        	}
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
