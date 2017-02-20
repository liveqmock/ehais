package org.ehais.project.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.ehais.project.model.ProWorkLog;
import org.ehais.project.model.ProWorkLogWithBLOBs;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface WorkLogService extends CommonService{

	public ReturnObject<ProWorkLog> InsertWorkLog(ProWorkLogWithBLOBs workLog) throws Exception;
	public ReturnObject<ProWorkLog> UpdateWorkLog(ProWorkLogWithBLOBs workLog) throws Exception;
	public ReturnObject<ProWorkLog> FindWorkLog(Integer userId ,Integer proId,Integer workId) throws Exception;
	public ReturnObject<ProWorkLogWithBLOBs> ListWorkLog(Integer userId ,Integer proId,String startDate,String endDate,Integer pageNo,Integer pageSize) throws Exception;
	public ReturnObject<ProWorkLog> RecoverWorkLog(ProWorkLog workLog) throws Exception;
	
	public ByteArrayOutputStream exportExcel(List<ProWorkLogWithBLOBs> list,Integer proId ,Integer userId) throws Exception;
	
}
