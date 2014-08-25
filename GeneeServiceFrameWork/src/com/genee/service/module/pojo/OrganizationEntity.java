package com.genee.service.module.pojo;

import javax.xml.bind.annotation.XmlRootElement;

import com.genee.service.framework.utils.json.JsonUtil;

@XmlRootElement
public class OrganizationEntity extends BaseEntity {

	private int ccount;

	public int getCcount() {
		return ccount;
	}

	public void setCcount(int ccount) {
		this.ccount = ccount;
	}

	@Override
	public String toString() {
		return JsonUtil.getJsonString4JavaPOJO(this);
	}
}
