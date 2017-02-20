package org.ehais.project.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

import org.ehais.project.model.HaiUsers;
import org.ehais.project.model.ProProject;
import org.ehais.project.model.ProWbsWork;
import org.ehais.project.model.ProWbsWorkTree;
import org.ehais.tools.ReturnObject;

public interface ProWbsWorkService extends ProCommonService{
	
	/**
	 * 获取某用户的项目名称
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<ProProject> projectList(Integer userId)throws Exception;
	

	
	/**
	 * 项目wbs工作分解编号
	 * @param proId
	 * @return
	 * @throws Exception
	 */
	public List<ProWbsWorkTree> treeWbsWork(Integer proId) throws Exception;
	
	public List<ProWbsWork> treeWbsWorkNotNullDate(Integer proId) throws Exception ;
	
	//更新工作名
	public ReturnObject<ProWbsWork> proWbsWorkSave(Integer wbsId,String wbsName) throws Exception;
	//更新负责人
	public ReturnObject<ProWbsWork> proWbsWorkSave(Integer wbsId,Integer userId) throws Exception;
	//更新计划日期
	public ReturnObject<ProWbsWork> proWbsWorkSave(Integer wbsId,
			String planStartDate,
			String planEndDate,
			String truthStartDate,
			String truthEndDate,
			Integer progress
			) throws Exception;
	//更新备注
	public ReturnObject<ProWbsWork> proWbsWorkRemarkSave(Integer wbsId,String remark) throws Exception;
	
	/**
	 * 添加项目树
	 * @param wbsName
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<ProWbsWork> proWbsWorkAdd(String wbsName,Integer wbsParentId,Integer proId)throws Exception;
	
	/**
	 * 设置默认项目值
	 * @param userId
	 * @param proId
	 * @throws Exception
	 */
	public void setDefaultProject(Integer userId,Integer proId)throws Exception;
	
	/**
	 * 导出excel
	 * @param proWbsWorkTreeList
	 * @return
	 * @throws Exception
	 */
	public ByteArrayOutputStream exportExcel(List<ProWbsWorkTree> proWbsWorkTreeList) throws Exception;
	
}
