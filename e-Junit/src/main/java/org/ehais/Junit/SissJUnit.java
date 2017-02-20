package org.ehais.Junit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SissJUnit {

	public static void main(String[] args) {
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		String dbURL = "jdbc:sqlserver://192.168.1.100:1433;DatabaseName=issyytv3";

		String userName = "sa";

		String userPwd = "topzh123";
		
		String value = "促销";

		
		try

		{

			Class.forName(driverName);

			Connection dbConn = DriverManager.getConnection(dbURL, userName, userPwd);

			System.out.println("连接数据库成功");
			
			Statement sta=dbConn.createStatement();
			Statement sta2=dbConn.createStatement();
			Statement sta3=dbConn.createStatement();
	        ResultSet rs=sta.executeQuery("select * from  SysObjects Where XType='U'");
	        while(rs.next()){
	        	int id = rs.getInt("id");
	        	String name = rs.getString("name");
	        	
	        	ResultSet rs2 = sta2.executeQuery("SELECT * FROM SysColumns WHERE id="+id);
	        	
	        	String where = "";
	        	while(rs2.next()){
	        		where += rs2.getString("name") + " like '%"+value+"%' or ";
	        	}
	        	where = where.substring(0, where.length() - 3);
//	        	System.out.println(where);
	        	
	        	ResultSet rs3 = sta3.executeQuery("SELECT * FROM "+name+" WHERE "+where);
	        	if(rs3.next()){
	        		System.out.println("SELECT * FROM "+name+" WHERE "+where);
	        		System.out.println("存在表："+name);
	        	}
	        	
	        }

		}

		catch (Exception e)

		{

			e.printStackTrace();

			System.out.print("连接失败");

		}

	}

	// select * from SysObjects Where XType='U' and name like '%protion%'
	//
	// SELECT * FROM SysColumns WHERE id=2108846875

}
