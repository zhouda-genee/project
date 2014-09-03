package com.genee.web.module.pojo;

import java.io.Serializable;
import java.util.Map;

/**
 * 指标角色关系表对应的实体类
 * @author yanan.che
 * 2014年8月18日
 */
public class IndexRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// 关联指标外键
	private int sId;
	
	// 关联角色外键
	private int rId;
	
	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}	
}
