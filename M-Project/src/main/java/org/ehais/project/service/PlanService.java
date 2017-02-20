package org.ehais.project.service;

import java.io.ByteArrayOutputStream;

import org.ehais.project.model.ProPlan;
import org.ehais.project.model.ProPlanWithBLOBs;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface PlanService extends ProCommonService{
	public ReturnObject<ProPlan> plan_list(Integer proId) throws Exception;
	public ReturnObject<ProPlan> plan_list_json(Integer proId,Integer page,Integer len) throws Exception;
	public ReturnObject<ProPlanWithBLOBs> plan_insert(Integer proId) throws Exception;
	public ReturnObject<ProPlanWithBLOBs> plan_insert_submit(ProPlanWithBLOBs model) throws Exception;
	public ReturnObject<ProPlanWithBLOBs> plan_update(Integer proId,Integer key) throws Exception;
	public ReturnObject<ProPlanWithBLOBs> plan_update_submit(ProPlanWithBLOBs model) throws Exception;
	public ReturnObject<ProPlanWithBLOBs> plan_find(Integer proId,Integer key) throws Exception;
	public ReturnObject<ProPlan> plan_delete(Integer proId,Integer key) throws Exception;
	
	public ByteArrayOutputStream plan_export_excel(Integer proId) throws Exception;

}
