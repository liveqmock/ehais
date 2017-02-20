package org.ehais.project.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.ehais.model.BootStrapModel;
import org.ehais.project.mapper.ProPlanMapper;
import org.ehais.project.mapper.ProTaskMapper;
import org.ehais.project.model.HaiUsers;
import org.ehais.project.model.ProPlan;
import org.ehais.project.model.ProPlanExample;
import org.ehais.project.model.ProPlanWithBLOBs;
import org.ehais.project.model.ProTask;
import org.ehais.project.model.ProTaskExample;
import org.ehais.project.service.PlanService;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("planService")
public class PlanServiceImpl extends ProCommonServiceImpl implements PlanService{
	
	@Autowired
	private ProPlanMapper proPlanMapper;
	
	
	public ReturnObject<ProPlan> plan_list(Integer proId) throws Exception{
		
		ReturnObject<ProPlan> rm = new ReturnObject<ProPlan>();
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<ProPlan> plan_list_json(Integer proId,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProPlan> rm = new ReturnObject<ProPlan>();
		Integer start = (page - 1 ) * len;
		
		ProPlanExample example = new ProPlanExample();
		ProPlanExample.Criteria c = example.createCriteria();
		c.andProIdEqualTo(proId);
		example.setStart(start);
		example.setLen(len);
		List<ProPlan> list = proPlanMapper.pro_plan_list_by_example(example);
		Integer total = proPlanMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<ProPlanWithBLOBs> plan_insert(Integer proId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProPlanWithBLOBs> rm = new ReturnObject<ProPlanWithBLOBs>();	
		ProPlanWithBLOBs model = new ProPlanWithBLOBs();
		model.setProId(proId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<ProPlanWithBLOBs> plan_insert_submit(ProPlanWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProPlanWithBLOBs> rm = new ReturnObject<ProPlanWithBLOBs>();
		int code = proPlanMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<ProPlanWithBLOBs> plan_update(Integer proId,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProPlanWithBLOBs> rm = new ReturnObject<ProPlanWithBLOBs>();
		
		ProPlanWithBLOBs model = proPlanMapper.selectByPrimaryKey(key);
		model.setProId(proId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<ProPlanWithBLOBs> plan_update_submit(ProPlanWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProPlanWithBLOBs> rm = new ReturnObject<ProPlanWithBLOBs>();
		ProPlanExample example = new ProPlanExample();
		ProPlanExample.Criteria c = example.createCriteria();
		c.andProIdEqualTo(model.getProId());
		c.andPlanIdEqualTo(model.getPlanId());
		int code = proPlanMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<ProPlanWithBLOBs> plan_find(Integer proId,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProPlanWithBLOBs> rm = new ReturnObject<ProPlanWithBLOBs>();
		
		
		ProPlanWithBLOBs model = proPlanMapper.selectByPrimaryKey(key);
		model.setProId(proId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<ProPlan> plan_delete(Integer proId,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProPlan> rm = new ReturnObject<ProPlan>();
		ProPlanExample example = new ProPlanExample();
		ProPlanExample.Criteria c = example.createCriteria();
		
		c.andProIdEqualTo(proId);
		c.andPlanIdEqualTo(key);
		int code = proPlanMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	
	private List<BootStrapModel> formatBootStrapList(ProPlanWithBLOBs model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "planId", "", model.getPlanId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select", "taskId", "所属任务", model.getTaskId(), "请输入", "", "", this.taskMap(model.getProId()), 0));
		bootStrapList.add(new BootStrapModel("text", "planTitle", "标题", model.getPlanTitle(), "请输入", "", "", null, 0));
		
		bootStrapList.add(new BootStrapModel("datepicker", "planStartDate", "计划开始", model.getPlanStartDate(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("datepicker", "planEndDate", "计划结束", model.getPlanEndDate(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("datepicker", "truthStartDate", "实际开始", model.getTruthStartDate(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("datepicker", "truthEndDate", "实际结束", model.getTruthEndDate(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select", "dispatchUserId", "参与人员", model.getDispatchUserId(), "请输入", "", "", this.getDPUser(model.getProId()), 0));
		bootStrapList.add(new BootStrapModel("text", "progress", "进度", model.getProgress(), "请输入", "", "", null, 0));
		

		bootStrapList.add(new BootStrapModel("textarea", "planContent", "计划内容", model.getPlanContent(), "请输入", "", "", null, 0,1));		
		bootStrapList.add(new BootStrapModel("textarea", "planResult", "结果", model.getPlanResult(), "请输入", "", "", null, 0,1));
		bootStrapList.add(new BootStrapModel("textarea", "remark", "备注", model.getRemark(), "请输入", "", "", null, 0,1));

		bootStrapList.add(new BootStrapModel("textarea", "planMContent", "计划内容", model.getPlanMContent(), "请输入", "", "", null, 0,2));
		bootStrapList.add(new BootStrapModel("textarea", "planMResult", "手机结果", model.getPlanMResult(), "请输入", "", "", null, 0,2));
		bootStrapList.add(new BootStrapModel("textarea", "mRemark", "手机备注", model.getmRemark(), "请输入", "", "", null, 0,2));
		
		return bootStrapList;
	}

	public ByteArrayOutputStream plan_export_excel(Integer proId)
			throws Exception {
		// TODO Auto-generated method stub
		
		
        try{
        	
        	List<ProTask> listTask = this.taskList(proId);
    		
    		ProPlanExample example = new ProPlanExample();
    		ProPlanExample.Criteria c = example.createCriteria();
    		c.andProIdEqualTo(proId);
    		example.setOrderByClause("plan_start_date asc");
    		List<ProPlan> listPlan = proPlanMapper.selectByExample(example);
    		
    		if(listTask == null || listTask.size() == 0 
    				|| listPlan == null || listPlan.size() == 0)return null;
    		
    		int startRow = 3;
            int startCell = 3;
            
    		//整理excel
    		//第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
            HSSFSheet sheet = wb.createSheet("项目计划");  
            //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制  
            HSSFRow row = sheet.createRow(0);  
            sheet.createFreezePane( 3, 3, 3, 3 );//冻结
            //第四步，创建单元格样式：居中  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
            
//            style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//            style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//            style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            
            
            HSSFCellStyle style_bg = wb.createCellStyle();
            style_bg.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style_bg.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            
            //第五步，创建表头单元格，并设置样式  
            HSSFCell cell;  
            
            cell = row.createCell(0);  
            cell.setCellValue("项目名称:");  
            cell.setCellStyle(style);
            
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 10));
            sheet.addMergedRegion(new Region(1, (short) 0, 2, (short) 1));
            
            row = sheet.createRow(1); 
            cell = row.createCell(0);  
            cell.setCellValue("日期");  
            cell.setCellStyle(style);
            
            cell = row.createCell(startCell);  
            cell.setCellValue((listPlan.get(0).getPlanStartDate().getMonth() + 1)+"月");
            cell.setCellStyle(style);
            
            Map<Integer,Date> flDate = this.getFLDate(listPlan);
//            if(flDate.get(0)!=null) System.out.println(DateUtil.formatDate(flDate.get(0), DateUtil.FORMATSTR_1));
//            if(flDate.get(1)!=null) System.out.println(DateUtil.formatDate(flDate.get(1), DateUtil.FORMATSTR_1));
            if(flDate.get(0)==null)flDate.put(0, new Date()) ;
            if(flDate.get(1)==null)flDate.put(1, new Date()) ;

          //设置合并单元格
            int diffMonth = DateUtil.diffMonth(flDate.get(0), flDate.get(1));
            int row_space = startCell;
            for(int m = 0 ; m <= diffMonth ; m++){
            	int cur_day = 0;
            	int last_day = 0;
            	Date last_date = new Date();
            	if(m == 0){
            		cur_day = flDate.get(0).getDate();
            		last_date = DateUtil.getLastDay(flDate.get(0));
            	}else{
            		cur_day = 1;
            		last_date = DateUtil.getLastDay(DateUtil.addMonth(flDate.get(0), m));
            	}
            	
            	if(DateUtil.compare_date(last_date,flDate.get(1))> 0){
        			last_date = flDate.get(1);
        		}
            	
            	last_day = last_date.getDate();
        		
        		sheet.addMergedRegion(new Region(1, (short) row_space, 1, (short) ((last_day - cur_day) + row_space)));
        		
        		cell = row.createCell(row_space);  
                cell.setCellValue((last_date.getMonth() + 1)+"月");
                cell.setCellStyle(style);
                
        		row_space += last_day - cur_day +1;
            }
            
            int diffDay = DateUtil.diffDate(flDate.get(0), flDate.get(1));
            
            row = sheet.createRow(2);
            for(int d = 0 ; d <= diffDay ; d++){
            	cell = row.createCell(d + startCell );
            	cell.setCellValue((DateUtil.addDate(flDate.get(0), d).getDate())+"");
            	
            	sheet.setColumnWidth(d + startCell, 1000);//设置宽度
            }
            
            
            
            int rowNo = startRow;
            for (ProTask proTask : listTask) {            	
            	row = sheet.createRow(rowNo); 
                cell = row.createCell(0);  
                cell.setCellValue(proTask.getTaskTitle());
                cell.setCellStyle(style);
                
                int rowiNo = 0;
                for (ProPlan proPlan : listPlan) {
                	if(proPlan.getTaskId().intValue() == proTask.getTaskId().intValue()){
                		if(rowiNo > 0)row = sheet.createRow(rowNo + rowiNo); 
                        cell = row.createCell(1);  
                        cell.setCellValue(proPlan.getPlanTitle());
                        
                        int diffS = DateUtil.diffDate(proPlan.getPlanStartDate(), flDate.get(0));
                        int diffE = DateUtil.diffDate(proPlan.getPlanEndDate(), flDate.get(0));
                        for(int se = 0 ; se <= (diffE - diffS) ; se ++){
                        	cell = row.createCell((startCell + diffS + se));
                        	cell.setCellStyle(style_bg);
                        }
                        
                        rowiNo ++;
                	}                	
    			}
                
                
                
                sheet.addMergedRegion(new Region(rowNo, (short) 0, ( rowNo + rowiNo - 1), (short) 0));
                
                rowNo += rowiNo;
                
			}
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os); 
            return os;//文件流  
            
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        return null;
	}
	
	private Map<Integer,Date> getFLDate(List<ProPlan> listPlan){
		Date fDateTemp = null;
		Date lDateTemp = null;
		Map<Integer,Date> map = new HashMap<Integer, Date>();
		for (ProPlan proPlan : listPlan) {
			if(fDateTemp ==null) {
				if(proPlan.getPlanStartDate()!=null)fDateTemp = proPlan.getPlanStartDate();
				if(proPlan.getPlanEndDate()!=null)fDateTemp = proPlan.getPlanStartDate();
				if(proPlan.getTruthStartDate()!=null)fDateTemp = proPlan.getPlanStartDate();
				if(proPlan.getTruthEndDate()!=null)fDateTemp = proPlan.getPlanStartDate();				
			}
			
			if(lDateTemp ==null) {
				if(proPlan.getPlanStartDate()!=null)lDateTemp = proPlan.getPlanStartDate();
				if(proPlan.getPlanEndDate()!=null)lDateTemp = proPlan.getPlanStartDate();
				if(proPlan.getTruthStartDate()!=null)lDateTemp = proPlan.getPlanStartDate();
				if(proPlan.getTruthEndDate()!=null)lDateTemp = proPlan.getPlanStartDate();				
			}
			
			if(proPlan.getPlanStartDate()!=null && fDateTemp != null) {
				if(DateUtil.compare_date(proPlan.getPlanStartDate(), fDateTemp)<0)fDateTemp = proPlan.getPlanStartDate();
			}
			if(proPlan.getPlanEndDate()!=null && fDateTemp != null) {
				if(DateUtil.compare_date(proPlan.getPlanEndDate(), fDateTemp)<0)fDateTemp = proPlan.getPlanEndDate();
			}
			if(proPlan.getTruthStartDate()!=null && fDateTemp != null) {
				if(DateUtil.compare_date(proPlan.getTruthStartDate(), fDateTemp)<0)fDateTemp = proPlan.getTruthStartDate();
			}
			if(proPlan.getTruthEndDate()!=null && fDateTemp != null) {
				if(DateUtil.compare_date(proPlan.getTruthEndDate(), fDateTemp)<0)fDateTemp = proPlan.getTruthEndDate();
			}
			
			if(proPlan.getPlanStartDate()!=null && lDateTemp != null) {
				if(DateUtil.compare_date(proPlan.getPlanStartDate(), lDateTemp)>0)lDateTemp = proPlan.getPlanStartDate();
			}
			if(proPlan.getPlanEndDate()!=null && lDateTemp != null) {
				if(DateUtil.compare_date(proPlan.getPlanEndDate(), lDateTemp)>0)lDateTemp = proPlan.getPlanEndDate();
			}
			if(proPlan.getTruthStartDate()!=null && lDateTemp != null) {
				if(DateUtil.compare_date(proPlan.getTruthStartDate(), lDateTemp)>0)lDateTemp = proPlan.getTruthStartDate();
			}
			if(proPlan.getTruthEndDate()!=null && lDateTemp != null) {
				if(DateUtil.compare_date(proPlan.getTruthEndDate(), lDateTemp)>0)lDateTemp = proPlan.getTruthEndDate();
			}
		}
		map.put(0, fDateTemp);
		map.put(1, lDateTemp);
		return map;
	}
	
}
