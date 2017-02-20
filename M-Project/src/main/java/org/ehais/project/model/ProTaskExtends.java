package org.ehais.project.model;

public class ProTaskExtends extends ProTask{

    private String userName;
    
    public ProTaskExtends(ProTask e){
    	super();
    	
		this.taskId = e.getTaskId();
		this.taskTitle = e.getTaskTitle();
		this.proId = e.getProId();
		this.planStartDate = e.getPlanStartDate();
		this.planEndDate = e.getPlanEndDate();
		this.truthStartDate = e.getTruthStartDate();
		this.truthEndDate = e.getTruthEndDate();
		this.userId = e.getUserId();
		this.dispatchUserId = e.getDispatchUserId();
		this.progress = e.getProgress();
		this.createDate = e.getCreateDate();
		this.updateDate = e.getUpdateDate();
		
    }
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    
}