package com.genee.service.module.pojo;

import javax.xml.bind.annotation.XmlRootElement;

import com.genee.service.framework.utils.json.JsonUtil;

@XmlRootElement
public class BaseEntity {
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return JsonUtil.getJsonString4JavaPOJO(this);
	}
}
