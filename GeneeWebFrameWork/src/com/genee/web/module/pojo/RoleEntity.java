/**
 * 
 */
package com.genee.web.module.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 指标角色对应的entity类
 * 
 * @author yanan.che 2014年8月13日
 */
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 角色ID PK
	private int rId;

	// 角色名称
	private String rName;
	
	// 角色与指标的多对多关系
	private List<IndexEntity> indexs = null;
	
	// 用于从数据库的取值 和Dao层结合使用
	public void fromResultSet(Map<String, Object> result) {
		this.rId = (Integer)result.get("r_id");
		this.rName = (String)result.get("r_name");
	}
	
	public RoleEntity() {}

	public RoleEntity(int rId, String rName) {
		this.rId = rId;
		this.rName = rName;
	}
	
	public RoleEntity(int rId, String rName, List<IndexEntity> indexs) {
		this.rId = rId;
		this.rName = rName;
		this.indexs = indexs;
	}
	
	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public List<IndexEntity> getIndexs() {
		return indexs;
	}

	public void setIndexs(List<IndexEntity> indexs) {
		this.indexs = indexs;
	}
	
}
