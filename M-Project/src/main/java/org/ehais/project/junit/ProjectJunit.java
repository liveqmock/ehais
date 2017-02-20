package org.ehais.project.junit;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ehais.project.model.ProWbsWork;
import org.ehais.project.service.ProWbsWorkService;
import org.ehais.project.util.ParseProjectMpp;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProjectJunit {

	
	@Test
	public void prowbs_To_DB() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/appContext.xml");
		ProWbsWorkService proWbsWorkService = (ProWbsWorkService) context.getBean("proWbsWorkService");
		String file = "E:/LGJ/a广州明动/信息推送/专利推送.mpp";
		Map<Integer,Integer> wbsIdMap = new HashMap<Integer,Integer>();
		List<Map<String,Object>> listPro = ParseProjectMpp.psrseProjectFile(file);
		for (Map<String, Object> map : listPro) {
			Integer wbsParentId = Integer.valueOf(map.get("parentId").toString());
			if(wbsParentId > 0){
				wbsParentId = wbsIdMap.get(wbsParentId) != null ? wbsIdMap.get(wbsParentId) : 0;
			}
			ReturnObject<ProWbsWork> rm = proWbsWorkService.proWbsWorkAdd(map.get("taskName").toString(), wbsParentId, 13);
			ProWbsWork wbs = rm.getModel();
			wbsIdMap.put(Integer.valueOf(map.get("id").toString()), wbs.getWbsId());
			System.out.println(wbs.getWbsId() + "  ----    "  + map.get("taskName").toString()+"  ---  "+ wbsParentId);
			
		}		
	}
	
	@Test
	public void prowbs_To_XML() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/appContext.xml");
		ProWbsWorkService proWbsWorkService = (ProWbsWorkService) context.getBean("proWbsWorkService");
		
		
		//第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
        HSSFSheet sheet = wb.createSheet("项目计划");  
        //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制  
        HSSFRow row = sheet.createRow(0);  
        //第四步，创建单元格样式：居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        //第五步，创建表头单元格，并设置样式  
        HSSFCell cell;  
        
        cell = row.createCell(0);  
        cell.setCellValue("编号");  
        cell.setCellStyle(style);  

        cell = row.createCell(1);
        cell.setCellValue("功能");  
        cell.setCellStyle(style); 
        
        cell = row.createCell(10);
        cell.setCellValue("计划开始");  
        cell.setCellStyle(style); 
        
        cell = row.createCell(11);
        cell.setCellValue("计划结束");  
        cell.setCellStyle(style);
        
        
		String file = "E:/LGJ/a广州明动/信息推送/专利推送.mpp";
		Map<Integer,Integer> wbsIdMap = new HashMap<Integer,Integer>();
		List<Map<String,Object>> listPro = ParseProjectMpp.psrseProjectFile(file);
		
		int i = 1;
		for (Map<String, Object> map : listPro) {
			row = sheet.createRow(i); 
			row.createCell(0).setCellValue(map.get("id").toString());
			row.createCell(1+Integer.valueOf(map.get("level").toString())).setCellValue( map.get("taskName").toString());
//			level_str(Integer.valueOf(map.get("level").toString())) +
			row.createCell(10).setCellValue(map.get("startDate").toString());
			row.createCell(11).setCellValue(map.get("endDate").toString());
			
			i++;
			
		}
		
//		ByteArrayOutputStream os = new ByteArrayOutputStream();  
		FileOutputStream os = new FileOutputStream( "D:/project.xls"); 
        wb.write(os); 
        os.close();
        
	}
	
	
	private static String level_str(int level){
		String str = "";
		for(int i = 0 ; i < level ; i++){
			str+="    ";
		}
		
		return str;
	}
	
	
}
