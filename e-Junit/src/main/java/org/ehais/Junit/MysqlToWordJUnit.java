package org.ehais.Junit;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

public class MysqlToWordJUnit {

	public static final String name = "com.mysql.jdbc.Driver";  
	public static final String db = "sqlehaismall";
	public static final String table = "hai_account";  
	public static final String sub_table = "";  
	
	
	public static final String link = "jdbc:mysql://127.0.0.1:3306/"+db;      
    public static final String user = "root";  
    public static final String password = "Ehais42016048ok!"; 
    public static Connection conn = null;  
//    public static PreparedStatement pst = null; 
//    public static ResultSet ret = null; 
    
    
	public static void main(String[] args) throws Exception {
		String[] field_arr = {"hai_shop_config","hai_accounting","hai_account","hai_ad","hai_ad_position","hai_business_linkman","hai_business_type","hai_business","hai_category","hai_employee","hai_expenses","hai_goods","hai_goods_attr","hai_income","hai_labour","hai_order_goods","hai_order_info","hai_payment","hai_property","hai_purchase","hai_sectors","hai_store_setting","hai_unit","hai_user_address","hai_warehouse"};
		
		// Blank Document
		XWPFDocument document = new XWPFDocument();
		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File("D:/数据库说明文档.docx"));
		// 添加标题
		XWPFParagraph titleParagraph = document.createParagraph();
		// 设置段落居中
		titleParagraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun titleParagraphRun = titleParagraph.createRun();
		titleParagraphRun.setText("数据库说明文档");
		titleParagraphRun.setColor("000000");
		titleParagraphRun.setFontSize(20);
		
		document.createParagraph();
		
		
		String all_table_sql = "select * from information_schema.tables where table_schema='"+db+"' and table_type='base table';";
		
		Class.forName(name);//指定连接类型  
        conn = DriverManager.getConnection(link, user, password);//获取连接  
//        String sql  = "show columns from "+table;
        
         //执行主类表
        PreparedStatement pst_all_table = conn.prepareStatement(all_table_sql);//准备执行语句  
        ResultSet ret_all_table = pst_all_table.executeQuery();
        while(ret_all_table.next()){
        	int index = Arrays.binarySearch(field_arr,ret_all_table.getString("TABLE_NAME"));
        	if(index < 0) continue;
        	document.createParagraph();
        	XWPFParagraph firstParagraph = document.createParagraph();
    		XWPFRun run = firstParagraph.createRun();
    		run.setText("表名："+ret_all_table.getString("TABLE_NAME")+"【"+ret_all_table.getString("TABLE_COMMENT")+"】");
    		run.setColor("333333");
    		run.setFontSize(16);
    		
    		
    		// 工作经历表格
    		XWPFTable ComTable = document.createTable();
    		// 列宽自动分割
    		CTTblWidth comTableWidth = ComTable.getCTTbl().addNewTblPr().addNewTblW();
    		comTableWidth.setType(STTblWidth.DXA);
    		comTableWidth.setW(BigInteger.valueOf(9072));
    		// 表格第一行
    		XWPFTableRow comTableRowOne = ComTable.getRow(0);
    		comTableRowOne.getCell(0).setText("字段名称");
    		comTableRowOne.addNewTableCell().setText("描述");
    		comTableRowOne.addNewTableCell().setText("类型");
    		comTableRowOne.addNewTableCell().setText("长度");
    		comTableRowOne.addNewTableCell().setText("主键");
    		comTableRowOne.addNewTableCell().setText("必填");
    		
    		String sql  = "SELECT column_name,data_type,character_maximum_length,numeric_precision,numeric_scale,column_key,is_nullable,CASE WHEN extra = 'auto_increment' THEN 1 ELSE 0 END AS 'auto_increment',column_default ,column_comment  FROM Information_schema.columns WHERE table_Name='"+ret_all_table.getString("TABLE_NAME")+"' and table_schema='"+db+"' ;";
            
    		PreparedStatement pst_field = conn.prepareStatement(sql);//准备执行语句  
            ResultSet ret_field = pst_field.executeQuery();
            while(ret_field.next()){
            	XWPFTableRow comTableRowField = ComTable.createRow();
            	comTableRowField.getCell(0).setText(ret_field.getString("column_name"));
            	comTableRowField.getCell(1).setText(ret_field.getString("column_comment"));
            	comTableRowField.getCell(2).setText(ret_field.getString("data_type"));
            	comTableRowField.getCell(3).setText(ret_field.getString("character_maximum_length"));
            	comTableRowField.getCell(4).setText(ret_field.getString("column_key").toUpperCase().equals("PRI")?"是":"");
            	comTableRowField.getCell(5).setText(ret_field.getString("is_nullable").toUpperCase().equals("NO")?"是":"");
        		
            }
    		
        }
		
        document.write(out);
		
		out.close();

		System.out.println("create_table document written success.");
		
		
	}
	
}
