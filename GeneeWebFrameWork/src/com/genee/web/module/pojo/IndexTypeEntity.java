/**
 * 
 */
package com.genee.web.module.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 指标类型表对应的entity类
 * 
 * @author yanan.che 2014年8月13日
 */
public class IndexTypeEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	// 指标类型ID PK
	private int tId;

	// 指标类型名称
	private String tName;
	
	// 指标类型与指标的一对多关系
	private List<IndexEntity> indexs = null;
	
	public IndexTypeEntity() {}
	
	public IndexTypeEntity(int tId, String tName) {
		this.tId = tId;
		this.tName = tName;
	}
		
	public IndexTypeEntity(int tId, String tName, List<IndexEntity> indexs) {
		this.tId = tId;
		this.tName = tName;
		this.indexs = indexs;
	}

	public List<IndexEntity> getIndexs() {
		return indexs;
	}

	public void setIndexs(List<IndexEntity> indexs) {
		this.indexs = indexs;
	}

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}
}
