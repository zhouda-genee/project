package com.genee.web.module.pojo;

public class LabEntity {
	
	// 课题组ID
	private int labId;
	
	// 课题组名称
	private String labName;
	
	// 课题组负责人
	private int labOwner;
	
	// 课题组组织机构
	private int labGroupId;
	
	public int getLabGroupId() {
		return labGroupId;
	}

	public void setLabGroupId(int labGroupId) {
		this.labGroupId = labGroupId;
	}

	public int getLabId() {
		return labId;
	}

	public void setLabId(int labId) {
		this.labId = labId;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public int getLabOwner() {
		return labOwner;
	}

	public void setLabOwner(int labOwner) {
		this.labOwner = labOwner;
	}
	
	

}
