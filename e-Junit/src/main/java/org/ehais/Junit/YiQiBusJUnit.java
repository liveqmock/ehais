package org.ehais.Junit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class YiQiBusJUnit {

	public static final String url = "jdbc:mysql://localhost:3306/yiqitg";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password = "42016048"; 
    public Connection conn = null;  
    public PreparedStatement pst = null; 
    public ResultSet ret = null;  
    
	@Test
	public void importExcel() throws IOException, ClassNotFoundException, SQLException {
		String sql = "select * from tg_user";
		Class.forName(name);//指定连接类型  
        conn = DriverManager.getConnection(url, user, password);//获取连接  
        
        
        
		boolean isE2007 = false; // 判断是否是excel2007格式

		String fileName = "E:/yiqiweixin.xls";
		if (fileName.endsWith("xlsx"))
			isE2007 = true;

		InputStream input = new FileInputStream(fileName); // 建立输入流
		Workbook wb = null;

		if (isE2007)
			wb = new XSSFWorkbook(input);
		else
			wb = new HSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0); // 获得第一个表单

		Iterator<Row> rows = sheet.rowIterator(); // 获得第一个表单的迭代器
		Integer user_id = 0;
		int i = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		DecimalFormat df = new DecimalFormat("0");
		
		while (rows.hasNext()) {
			Row row = rows.next(); // 获得行数据
			if(i == 0){
//				System.out.println(i+"存在此零点："+user_id+";cell="+row.getCell(6).getStringCellValue());
				i++;
				continue;
			}
			
//			System.out.println("Row #" + row.getRowNum()); // 获得行号从0开始
//			Iterator<Cell> cells = row.cellIterator(); // 获得第一行的迭代器
//			while (cells.hasNext()) {
//				Cell cell = cells.next();
//				System.out.println("Cell #" + cell.getColumnIndex());
//				switch (cell.getCellType()) { // 根据cell中的类型来输出数据
//				case HSSFCell.CELL_TYPE_NUMERIC:
//					System.out.println(cell.getNumericCellValue());
//					break;
//				case HSSFCell.CELL_TYPE_STRING:
//					System.out.println(cell.getStringCellValue());
//					break;
//				case HSSFCell.CELL_TYPE_BOOLEAN:
//					System.out.println(cell.getBooleanCellValue());
//					break;
//				case HSSFCell.CELL_TYPE_FORMULA:
//					System.out.println(cell.getCellFormula());
//					break;
//				default:
//					System.out.println("unsuported sell type");
//					break;
//				} 
//			}
			
			
			
			// 列
//			141             List<Object> cellList = new LinkedList<Object>();
//			142             for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
//			143                 cell = row.getCell(j);
//			144                 if (cell == null) {
//			145                     continue;
//			146                 }
//			147 
//			148                 DecimalFormat df = new DecimalFormat("0");// 格式化 number String
//			149                 DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
//			150                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
//			151                 switch (cell.getCellType()) {
//			152                     case XSSFCell.CELL_TYPE_STRING:// 字符串——String type
//			153                         value = cell.getStringCellValue();
//			154                         break;
//			155                     case XSSFCell.CELL_TYPE_NUMERIC:// 数字——Number type
//			156                         if ("@".equals(cell.getCellStyle().getDataFormatString())) {
//			157                             value = df.format(cell.getNumericCellValue());
//			158                         } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
//			159                             value = nf.format(cell.getNumericCellValue());
//			160                         } else {
//			161                             value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
//			162                         }
//			163                         break;
//			164                     case XSSFCell.CELL_TYPE_BOOLEAN:// boolean——Boolean type
//			165                         value = cell.getBooleanCellValue();
//			166                         break;
//			167                     case XSSFCell.CELL_TYPE_BLANK:// 空白——Blank type
//			168                         value = "";
//			169                         break;
//			170                     default:// default type
//			171                         value = cell.toString();
//			172                 }
//			173                 if (value == null || "".equals(value)) {
//			174                     continue;
//			175                 }
//			176                 cellList.add(value);
//			177             }
//			
//			
			int iii = HSSFCell.CELL_TYPE_NUMERIC;
			sql = "select * from tg_user where OPEN_ID = '"+row.getCell(6).getStringCellValue()+"'";
	        pst = conn.prepareStatement(sql);//准备执行语句  
	        ret = pst.executeQuery();
	        int result = 0;
	        if(ret != null && ret.next()){
	        	user_id = ret.getInt("USER_ID");
//	        	System.out.println(i+"存在此零点："+user_id+";cell="+row.getCell(6).getStringCellValue());
	        	
	        	
	        	sql = "insert into tg_bus_line_expand(CREATE_DATE,BUS_NAME,BUS_CODE,OPENID,SEX,SCORE_A,SECTIONNAME) values('"+
	        	sdf.format(row.getCell(0).getDateCellValue())+"','"+
	        	row.getCell(1).getStringCellValue().toString()+"','"+
	        	row.getCell(2).getStringCellValue().toString()+"','"+
	        			row.getCell(6).getStringCellValue()+"','"+
	        			df.format(row.getCell(7).getNumericCellValue())+"','"+
	        			df.format(row.getCell(8).getNumericCellValue())+"','"+
	        			df.format(row.getCell(3).getNumericCellValue())+"'"+
	        			")";
	        	result = pst.executeUpdate(sql);
	        	
	        	System.out.println(i+"==>"+result+"="+sql);
	        }
	        
	        i++;
		}

	}

}
