package org.ehais.project.model;

import java.util.Date;

public class ProWbsWorkTree extends ProWbsWork{

	private Integer level;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public void setValueByProWbsWork(ProWbsWork proWbsWork){
		this.setWbsId(proWbsWork.getWbsId());
		this.setWbsName(proWbsWork.getWbsName());
		this.setProId(proWbsWork.getProId());
		this.setWbsParentId(proWbsWork.getWbsParentId());
		this.setPlanStartDate(proWbsWork.getPlanStartDate());
		this.setPlanEndDate(proWbsWork.getPlanEndDate());
		this.setTruthStartDate(proWbsWork.getTruthStartDate());
		this.setTruthEndDate(proWbsWork.getTruthEndDate());
		this.setUserId(proWbsWork.getUserId());
		this.setProgress(proWbsWork.getProgress());
		this.setSort(proWbsWork.getSort());
		this.setRemark(proWbsWork.getRemark());
	    
	    
	}
}
