package org.ehais.project.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.ehais.project.mapper.HaiUsersMapper;
import org.ehais.project.mapper.ProProjectMapper;
import org.ehais.project.mapper.ProWorkLogMapper;
import org.ehais.project.model.HaiUsers;
import org.ehais.project.model.ProProject;
import org.ehais.project.model.ProWorkLog;
import org.ehais.project.model.ProWorkLogExample;
import org.ehais.project.model.ProWorkLogWithBLOBs;
import org.ehais.project.service.WorkLogService;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkLogServiceImpl extends CommonServiceImpl implements WorkLogService{
	
	@Autowired
	private ProWorkLogMapper proWorkLogMapper;
	@Autowired
	private ProProjectMapper ProProjectMapper;
	@Autowired
	private HaiUsersMapper haiUsersMapper;

	public ReturnObject<ProWorkLog> InsertWorkLog(ProWorkLogWithBLOBs workLog)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProWorkLog> ro = new ReturnObject<ProWorkLog>();
		workLog.setStatus(Short.valueOf("1"));
		Date date = new Date();
		workLog.setCreateDate(date);
		workLog.setUpdateDate(date);
		proWorkLogMapper.insert(workLog);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProWorkLog> UpdateWorkLog(ProWorkLogWithBLOBs workLog)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProWorkLog> ro = new ReturnObject<ProWorkLog>();
		Date date = new Date();
		workLog.setUpdateDate(date);
		
		int i = proWorkLogMapper.updateByPrimaryKeySelective(workLog);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProWorkLogWithBLOBs> ListWorkLog(
			Integer userId,
			Integer proId,
			String startDate,
			String endDate, 
			Integer pageNo,
			Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		if(pageNo == null || pageNo < 1)pageNo=1;
		if(pageSize == null )pageSize=1;
		ReturnObject<ProWorkLogWithBLOBs> ro = new ReturnObject<ProWorkLogWithBLOBs>();
		List<ProWorkLogWithBLOBs> workLogList = proWorkLogMapper.workLogList(
				userId,
				proId, 
				startDate,
				endDate,
				(pageNo - 1) * pageSize, 
				pageSize);

		ProWorkLogExample example = new ProWorkLogExample();
		ProWorkLogExample.Criteria c = example.or();
		c.andUserIdEqualTo(userId);
		c.andProIdEqualTo(proId);
		c.andStatusEqualTo(Short.valueOf("1"));
		if(startDate!=null && !startDate.equals("") && endDate!=null && !endDate.equals(""))c.andWorkDateBetween(DateUtil.formatDate(startDate, DateUtil.FORMATSTR_3), DateUtil.formatDate(endDate, DateUtil.FORMATSTR_3));
		Integer count = proWorkLogMapper.countByExample(example);
		
		ro.setTotal(count);
		ro.setRows(workLogList);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProWorkLog> RecoverWorkLog(ProWorkLog workLog)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProWorkLog> ro = new ReturnObject<ProWorkLog>();
		ro.setCode(0);
		if(workLog.getWorkId()==null){
			ro.setMsg("唯一编号为空");
			return ro;
		}
		ProWorkLogExample example = new ProWorkLogExample();
		example.or().andUserIdEqualTo(workLog.getUserId());
		example.or().andStatusEqualTo(Short.valueOf("0"));
		proWorkLogMapper.Recover(workLog.getUserId(),workLog.getProId(), workLog.getWorkId());
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProWorkLog> FindWorkLog(Integer userId,Integer proId, Integer workId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProWorkLog> ro = new ReturnObject<ProWorkLog>();
		ro.setCode(0);
		if(workId==null){
			ro.setMsg("唯一编号为空");
			return ro;
		}
		ProWorkLog proWorkLog = proWorkLogMapper.workLogInfo(userId,proId,workId);
		ro.setModel(proWorkLog);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ByteArrayOutputStream exportExcel(List<ProWorkLogWithBLOBs> list,
			Integer proId ,Integer userId)
			throws Exception {
		// TODO Auto-generated method stub
		try{
			ProProject project = ProProjectMapper.selectByPrimaryKey(proId);
			if(project==null){project = new ProProject();project.setProName("");}
			HaiUsers user = haiUsersMapper.selectByPrimaryKey(userId);
			if(user==null){user = new HaiUsers();user.setUserName("");user.setRealname("");}
			//第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
            HSSFSheet sheet = wb.createSheet("工作周报");  
            //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制  
            HSSFRow row = sheet.createRow(0); 
            
            //合并单元格
            sheet.addMergedRegion(new Region(0,(short)0,0,(short)15)); //周报
            
            sheet.addMergedRegion(new Region(1,(short)0,2,(short)0));//项目名称
            sheet.addMergedRegion(new Region(1,(short)1,2,(short)1));//项目类型
            sheet.addMergedRegion(new Region(1,(short)2,1,(short)6));//上周计划
            sheet.addMergedRegion(new Region(1,(short)7,1,(short)10));//本周小结
            sheet.addMergedRegion(new Region(1,(short)11,1,(short)15));//下周计划
            
            sheet.addMergedRegion(new Region(3+list.size(),(short)1,3+list.size(),(short)15));//最后一月小结
            sheet.addMergedRegion(new Region(4+list.size(),(short)0,4+list.size(),(short)15));//备注出处
            
            //第四步，创建单元格样式：居中  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直     
            style.setBorderBottom((short)1);style.setBorderLeft((short)1);style.setBorderRight((short)1);style.setBorderTop((short)1);
            style.setBottomBorderColor((short)0);style.setLeftBorderColor((short)0);style.setRightBorderColor((short)0);style.setTopBorderColor((short)0);
            HSSFFont font = wb.createFont();//font.setFontName("宋体");font.setFontHeightInPoints((short) 14);// 设置字体大小
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            style.setFont(font);
            style.setWrapText(true); 
//            style.setFillForegroundColor(IndexedColors.BROWN.getIndex());
//    		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            row.setRowStyle(style);
            row.setHeight((short)800);
            
            HSSFCellStyle style2 = wb.createCellStyle();  
            style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);// 垂直     
            style2.setBorderBottom((short)1);style2.setBorderLeft((short)1);style2.setBorderRight((short)1);style2.setBorderTop((short)1);
            style2.setBottomBorderColor((short)0);style2.setLeftBorderColor((short)0);style2.setRightBorderColor((short)0);style2.setTopBorderColor((short)0);
            style2.setWrapText(true); 
            
            //第五步，创建表头单元格，并设置样式  
            HSSFCell cell;  
            
            cell = row.createCell(0);cell.setCellValue("周报");cell.setCellStyle(style);
            
            HSSFRow row1 = sheet.createRow(1); 
            row1.setHeight((short)600);
            row1.setRowStyle(style);
            cell = row1.createCell(0);cell.setCellValue("项目名称");cell.setCellStyle(style);            
            cell = row1.createCell(1);cell.setCellValue("项目类型");cell.setCellStyle(style);            
            cell = row1.createCell(2);cell.setCellValue("上周计划");cell.setCellStyle(style);            
            cell = row1.createCell(7);cell.setCellValue("本周小结");cell.setCellStyle(style);
            cell = row1.createCell(11);cell.setCellValue("下周计划");cell.setCellStyle(style);
            
            HSSFRow row2 = sheet.createRow(2); row2.setRowStyle(style);
            row2.setHeight((short)600);
            cell = row2.createCell(2);cell.setCellValue("工作任务");cell.setCellStyle(style);
            cell = row2.createCell(3);cell.setCellValue("工作内容");cell.setCellStyle(style);
            cell = row2.createCell(4);cell.setCellValue("计划完成时间");cell.setCellStyle(style);
            cell = row2.createCell(5);cell.setCellValue("人员");cell.setCellStyle(style);
            cell = row2.createCell(6);cell.setCellValue("工作量(人)");cell.setCellStyle(style);
            cell = row2.createCell(7);cell.setCellValue("实际工作量(人)");cell.setCellStyle(style);
            cell = row2.createCell(8);cell.setCellValue("完成状况");cell.setCellStyle(style);
            cell = row2.createCell(9);cell.setCellValue("工作成果");cell.setCellStyle(style);
            cell = row2.createCell(10);cell.setCellValue("备注/存在问题");cell.setCellStyle(style);
            cell = row2.createCell(11);cell.setCellValue("工作任务");cell.setCellStyle(style);
            cell = row2.createCell(12);cell.setCellValue("工作内容");cell.setCellStyle(style);
            cell = row2.createCell(13);cell.setCellValue("计划完成时间");cell.setCellStyle(style);
            cell = row2.createCell(14);cell.setCellValue("人员");cell.setCellStyle(style);
            cell = row2.createCell(15);cell.setCellValue("工作量(人)");cell.setCellStyle(style);
            
            
            HSSFRow row_last = sheet.createRow(3+list.size()); row_last.setRowStyle(style);
            cell = row_last.createCell(0);cell.setCellValue("月小结(本月最后一周)");cell.setCellStyle(style);
            cell = row_last.createCell(1);cell.setCellValue("");cell.setCellStyle(style);
            
            HSSFRow row_ver = sheet.createRow(4+list.size()); row_ver.setRowStyle(style);
            cell = row_ver.createCell(0);cell.setCellValue("版本出处:");cell.setCellStyle(style);
            
            HSSFRow rowList = null;
            int i = 3;
            for (ProWorkLogWithBLOBs work : list) {
            	rowList = sheet.createRow(i);rowList.setRowStyle(style2);
            	cell = rowList.createCell(0);cell.setCellValue(project.getProName());cell.setCellStyle(style2);
            	cell = rowList.createCell(1);cell.setCellValue("开发");cell.setCellStyle(style2);
            	cell = rowList.createCell(2);cell.setCellValue("开发与测试");cell.setCellStyle(style2);
            	cell = rowList.createCell(3);cell.setCellValue(work.getSimpleContent());cell.setCellStyle(style2);
            	cell = rowList.createCell(4);cell.setCellValue(DateUtil.formatDate(work.getWorkDate(), DateUtil.FORMATSTR_3));cell.setCellStyle(style2);
            	cell = rowList.createCell(5);cell.setCellValue((user.getRealname() == null || user.getRealname().equals(""))?user.getUserName():user.getRealname());cell.setCellStyle(style2);
            	cell = rowList.createCell(6);cell.setCellValue(1);cell.setCellStyle(style2);
            	cell = rowList.createCell(7);cell.setCellValue(1);cell.setCellStyle(style2);
            	cell = rowList.createCell(8);cell.setCellValue(100);cell.setCellStyle(style2);
            	i++;
			}
            
            sheet.autoSizeColumn(0,true); 
            sheet.autoSizeColumn(1,true); 
            sheet.autoSizeColumn(2,true); 
            sheet.autoSizeColumn(3,true); 
            sheet.autoSizeColumn(4,true);
            sheet.autoSizeColumn(5,true);
            sheet.autoSizeColumn(6,true);
            sheet.autoSizeColumn(7,true);
            sheet.autoSizeColumn(8,true);
            sheet.autoSizeColumn(9,true);
            sheet.autoSizeColumn(10,true);
            sheet.autoSizeColumn(11,true);
            sheet.autoSizeColumn(12,true);
            sheet.autoSizeColumn(13,true);
            sheet.autoSizeColumn(14,true);
            sheet.autoSizeColumn(15,true);
            
         // 生成下拉列表  
//            // 只对(0，0)单元格有效  
//            CellRangeAddressList regions = new CellRangeAddressList(8, 9, 9, 10);  
//            // 生成下拉框内容  
//            DVConstraint constraint = DVConstraint.createExplicitListConstraint(new String[] { "2", "3" });  
//            // 绑定下拉框和作用区域  
//            HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);  
//            // 对sheet页生效  
//            sheet.addValidationData(data_validation); 
            
            
          //第七步，将文件存到流中 
			ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  

            return os;//文件流  
            
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
