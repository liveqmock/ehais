package org.ehais.project.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.ehais.project.model.ProTask;
import org.ehais.project.model.ProTaskWithBLOBs;
import org.ehais.tools.ReturnObject;

public interface TaskService {

	public ReturnObject<ProTask> InsertTask(ProTaskWithBLOBs task) throws Exception;
	public ReturnObject<ProTask> UpdateTask(ProTaskWithBLOBs task) throws Exception;
	public ReturnObject<ProTask> FindTask(Integer userId ,Integer taskId,Integer proId) throws Exception;
	public ReturnObject<ProTask> ListTask(Integer proId ,Integer userId ,Integer progress ,Integer pageNo,Integer pageSize) throws Exception;
	public ReturnObject<ProTask> RecoverTask(ProTask task) throws Exception;
	
	//将测试工作生成日志
	public ReturnObject<ProTask> taskToWorkLog(Integer proId ,Integer userId ,Integer taskId) throws Exception;
	
	//导出excel
	public ByteArrayOutputStream exportExcel(List<ProTask> list,Integer proId ,Integer userId) throws Exception;
	
}
