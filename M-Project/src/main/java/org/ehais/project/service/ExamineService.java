package org.ehais.project.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.ehais.project.model.ProExamine;
import org.ehais.project.model.ProExamineWithBLOBs;
import org.ehais.tools.ReturnObject;

public interface ExamineService {

	public ReturnObject<ProExamine> InsertExamine(ProExamineWithBLOBs Examine) throws Exception;
	public ReturnObject<ProExamine> UpdateExamine(ProExamineWithBLOBs Examine) throws Exception;
	public ReturnObject<ProExamine> FindExamine(Integer userId ,Integer examineId,Integer proId) throws Exception;
	public ReturnObject<ProExamine> ListExamine(Integer proId ,Integer userId ,Integer progress ,Integer pageNo,Integer pageSize) throws Exception;
	public ReturnObject<ProExamine> RecoverExamine(ProExamine Examine) throws Exception;
	
	//将测试工作生成日志
	public ReturnObject<ProExamine> examineToWorkLog(Integer proId ,Integer userId ,Integer examineId) throws Exception;
	
	//导出excel
	public ByteArrayOutputStream exportExcel(List<ProExamine> list,Integer proId ,Integer userId) throws Exception;
	
}
