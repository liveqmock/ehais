package org.ehais.project.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ehais.project.mapper.HaiUsersMapper;
import org.ehais.project.mapper.ProTaskMapper;
import org.ehais.project.mapper.ProWorkLogMapper;
import org.ehais.project.model.HaiUsers;
import org.ehais.project.model.ProTask;
import org.ehais.project.model.ProTaskExample;
import org.ehais.project.model.ProTaskExtends;
import org.ehais.project.model.ProTaskWithBLOBs;
import org.ehais.project.model.ProWorkLog;
import org.ehais.project.model.ProWorkLogWithBLOBs;
import org.ehais.project.service.TaskService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired
	private ProTaskMapper proTaskMapper;
	
	@Autowired
	private ProWorkLogMapper proWorkLogMapper;
	
	@Autowired
	private HaiUsersMapper haiUsersMapper;
	
	public ReturnObject<ProTask> InsertTask(ProTaskWithBLOBs Task)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProTask> ro = new ReturnObject<ProTask>();
		ro.setCode(0);
		if(Task.getTaskTitle() == null || Task.getTaskTitle().equals("")){
			ro.setMsg("请输入标题!");
			return ro;
		}
		Date date = new Date();
		Task.setCreateDate(date);
		Task.setUpdateDate(date);

		
		proTaskMapper.insertSelective(Task);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProTask> UpdateTask(ProTaskWithBLOBs Task)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProTask> ro = new ReturnObject<ProTask>();
		ro.setCode(0);
		if(Task.getTaskTitle() == null || Task.getTaskTitle().equals("")){
			ro.setMsg("请输入标题!");
			return ro;
		}
		
		
		Date date = new Date();
		Task.setUpdateDate(date);
		proTaskMapper.updateByPrimaryKeySelective(Task);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProTask> FindTask(Integer userId,
			Integer taskId,Integer proId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProTask> ro = new ReturnObject<ProTask>();
		ro.setCode(0);
		
		
//		ProTaskExample example = new ProTaskExample();
//		example.or().andUserIdEqualTo(userId);
//		example.or().andTaskIdEqualTo(taskId);
		ProTaskWithBLOBs model = proTaskMapper.getByExample(taskId,proId);
		ro.setModel(model);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProTask> ListTask(Integer proId ,Integer userId ,Integer progress, Integer pageNo,
			Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		if(pageNo == null || pageNo < 1)pageNo=1;
		if(pageSize == null )pageSize=50;
		if(userId == null )userId = 0;
		
		ReturnObject<ProTask> ro = new ReturnObject<ProTask>();
		ro.setCode(0);
		
		List<ProTask> taskList = proTaskMapper.taskSearchList(proId,userId,progress,(pageNo - 1) * pageSize, pageSize);
		ro.setRows(taskList);
		
		
		ProTaskExample example = new ProTaskExample();
		ProTaskExample.Criteria criteria = example.or();
		criteria.andProIdEqualTo(proId);
		if(userId.intValue() > 0)criteria.andUserIdEqualTo(userId);
		int count = proTaskMapper.countByExample(example);
		
		
		
		ro.setTotal(count);
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProTask> RecoverTask(ProTask Task)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProTask> ro = new ReturnObject<ProTask>();
		ro.setCode(0);
		
		proTaskMapper.deleteByPrimaryKey(Task.getTaskId());
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ReturnObject<ProTask> taskToWorkLog(Integer proId,
			Integer userId, Integer taskId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<ProTask> ro = new ReturnObject<ProTask>();
		ro.setCode(0);
		
		ProTaskWithBLOBs task = proTaskMapper.selectByPrimaryKey(taskId);
		if(task == null){
			ro.setMsg("不存在此记录");
			return ro;
		}
		if(task.getUserId() == null || task.getUserId() == 0 || task.getUserId() != userId){
			ro.setMsg("此记录不是你的工作数据");
			return ro;
		}
		if(task.getTruthStartDate() == null || task.getTruthEndDate() == null){
			ro.setMsg("实际开始与结束日期不完整");
			return ro;
		}
		
		int diff = DateUtil.diffDate(task.getTruthStartDate(), task.getTruthEndDate());
		Date date = new Date();
		for(int i = 0 ; i <= diff ; i++){
			Date ddays = DateUtil.getInternalDateByDay(task.getTruthStartDate(), i);
			
			ProWorkLogWithBLOBs workLogDateInfo = proWorkLogMapper.workLogDateInfo(userId, proId, DateUtil.formatDate(ddays, DateUtil.FORMATSTR_3));
			if(workLogDateInfo == null){
				workLogDateInfo = new ProWorkLogWithBLOBs();
				workLogDateInfo.setTitle(task.getTaskTitle());
				workLogDateInfo.setSimpleContent(task.getTaskTitle());
				workLogDateInfo.setCreateDate(date);
				workLogDateInfo.setStatus(Short.valueOf("1"));
				workLogDateInfo.setUpdateDate(date);
				workLogDateInfo.setWorkDate(ddays);
				workLogDateInfo.setUserId(userId);
				workLogDateInfo.setProId(proId);
				
				proWorkLogMapper.insertSelective(workLogDateInfo);
				
			}else{
				
				workLogDateInfo.setSimpleContent(workLogDateInfo.getSimpleContent() +"\r\n"+ task.getTaskTitle());
				workLogDateInfo.setUpdateDate(date);
				
				proWorkLogMapper.updateByPrimaryKeySelective(workLogDateInfo);
			}
			
		}
		
		ro.setCode(1);
		ro.setMsg("success");
		return ro;
	}

	public ByteArrayOutputStream exportExcel(List<ProTask> list,
			Integer proId, Integer userId) throws Exception {
		// TODO Auto-generated method stub
		try{
			if(list == null || list.size() == 0)return null;
			StringBuilder sb = new StringBuilder();
			List<ProTaskExtends> eList = new ArrayList<ProTaskExtends>();
			for (ProTask proTask : list) {
				eList.add(new ProTaskExtends(proTask));
				if(proTask.getUserId()!=null && proTask.getUserId()!=0)sb.append(proTask.getUserId()+",");
			}
			//整理用户名
			if(sb.length() > 0 ){
				List<HaiUsers> uList = haiUsersMapper.inUserIdList(sb.toString().substring(0, sb.toString().length()-1));
				Map<Integer, String> map = new HashMap<Integer, String>();
				for (HaiUsers haiUsers : uList) {
					map.put(haiUsers.getUserId(), (haiUsers.getRealname()==null || haiUsers.getRealname().equals(""))?haiUsers.getUserName():haiUsers.getRealname());
				}
				for (ProTaskExtends e : eList) {
					e.setUserName(map.get(e.getUserId()));
				}
			}
			//整理excel
			//第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
            HSSFSheet sheet = wb.createSheet("任务");  
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
            cell.setCellValue("标题");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(2);  
            cell.setCellValue("计划开始");  
            cell.setCellStyle(style);
            
            cell = row.createCell(3);  
            cell.setCellValue("计划结束");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(4);  
            cell.setCellValue("实际开始");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(5);  
            cell.setCellValue("实际结束");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(6);  
            cell.setCellValue("进度");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(7);  
            cell.setCellValue("参与者");  
            cell.setCellStyle(style); 
            
            int i = 1;
            for (ProTaskExtends e : eList) {
            	row = sheet.createRow(i);  
            	row.createCell(0).setCellValue(e.getTaskId());
            	row.createCell(1).setCellValue(e.getTaskTitle());
            	
            	row.createCell(2).setCellValue(DateUtil.formatDate(e.getPlanStartDate(), DateUtil.FORMATSTR_3));
            	row.createCell(3).setCellValue(DateUtil.formatDate(e.getPlanEndDate(), DateUtil.FORMATSTR_3));
            	row.createCell(4).setCellValue(DateUtil.formatDate(e.getTruthStartDate(), DateUtil.FORMATSTR_3));
            	row.createCell(5).setCellValue(DateUtil.formatDate(e.getTruthEndDate(), DateUtil.FORMATSTR_3));
            	
            	row.createCell(6).setCellValue(e.getProgress());
            	row.createCell(7).setCellValue(e.getUserName());
            	
            	i++;
            }
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os); 
            return os;//文件流  
            
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

}
